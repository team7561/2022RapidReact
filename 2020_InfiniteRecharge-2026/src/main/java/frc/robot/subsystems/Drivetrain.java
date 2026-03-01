package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Ports;
import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class Drivetrain extends SubsystemBase {

    private final SparkMax leftA, leftB, rightA, rightB;

    private final SparkMaxConfig leftAConfig  = new SparkMaxConfig();
    private final SparkMaxConfig leftBConfig  = new SparkMaxConfig();
    private final SparkMaxConfig rightAConfig = new SparkMaxConfig();
    private final SparkMaxConfig rightBConfig = new SparkMaxConfig();

    private final SimpleMotorFeedforward m_FF;
    private final DifferentialDriveKinematics m_kinematics;

    private final PIDController m_leftController;
    private final PIDController m_rightController;

    public ADXRS450_Gyro gyro;

    private int current = 99;

    @SuppressWarnings("removal")
    public Drivetrain() {
        // Spark Flex + NEO
        leftA  = new SparkMax(Ports.DRIVE_LEFT_A_CANID, MotorType.kBrushless);
        leftB  = new SparkMax(Ports.DRIVE_LEFT_B_CANID, MotorType.kBrushless);
        //leftC  = new SparkMax(Ports.DRIVE_LEFT_C_CANID, MotorType.kBrushless);
        rightA = new SparkMax(Ports.DRIVE_RIGHT_A_CANID, MotorType.kBrushless);
        rightB = new SparkMax(Ports.DRIVE_RIGHT_B_CANID, MotorType.kBrushless);
        
        // Gear ratio * wheel circumference (m)
        double conversionFactor = Constants.DRIVE_GEAR_RATIO * 2 * Math.PI * 3 * 2.54 / 100.0;

        // Leader configs
        leftAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(current);
        leftAConfig.encoder
            .positionConversionFactor(conversionFactor)
            .velocityConversionFactor(conversionFactor / 60.0);

        rightAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(current);
        rightAConfig.encoder
            .positionConversionFactor(conversionFactor)
            .velocityConversionFactor(conversionFactor / 60.0);

        // Followers
        leftBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(current);

        rightBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(current);

        // Apply configs
        leftA.configure(leftAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        leftB.configure(leftBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightA.configure(rightAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightB.configure(rightBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        
        // Kinematics (track width from Constants)
        m_kinematics = new DifferentialDriveKinematics(Constants.DRIVE_TRACK_WIDTH);

        m_leftController  = new PIDController(0.295, 0, 0);
        m_rightController = new PIDController(0.295, 0, 0);
        m_FF = new SimpleMotorFeedforward(0.3, 1.96, 0.06);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        resetGyro();
    }

    // Open-loop tank drive
    public void drive(double leftSpeed, double rightSpeed) {
        leftA.set(leftSpeed);
        leftB.set(leftA.get());
        rightA.set(rightSpeed);
        rightB.set(rightA.get());
    }

    public void resetEncoders() {
        leftA.getEncoder().setPosition(0.0);
        rightA.getEncoder().setPosition(0.0);
    }

    public void resetGyro() {
        gyro.reset();
    }

    public double getHeading() {
        return getGyroRotation().getDegrees();
    }

    public Rotation2d getGyroRotation() {
        return gyro.getRotation2d();
    }

    public DifferentialDriveKinematics getKinematics() {
        return m_kinematics;
    }

    // Closed-loop drive for trajectory following (m/s)
    public void driveClosedLoop(DifferentialDriveWheelSpeeds speeds) {
        double left  = speeds.leftMetersPerSecond;
        double right = speeds.rightMetersPerSecond;

        double leftVelocity  = leftA.getEncoder().getVelocity();
        double rightVelocity = rightA.getEncoder().getVelocity();

        double leftVoltage =
            m_FF.calculate(left) + m_leftController.calculate(leftVelocity, left);
        double rightVoltage =
            m_FF.calculate(right) + m_rightController.calculate(rightVelocity, right);

        leftA.setVoltage(leftVoltage);
        rightA.setVoltage(rightVoltage);
    }

    @Override
    public void periodic() {
        if (Constants.DEBUG_DRIVETRAIN) {
            SmartDashboard.putNumber("left_enc", leftA.getEncoder().getPosition());
            SmartDashboard.putNumber("right_enc", rightA.getEncoder().getPosition());
        }
    }

    // Teleop arcade drive
    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        double left  = (-(y - x)) * speed;
        double right = ( -y - x) * speed;

        left  = Math.max(-1.0, Math.min(1.0, left));
        right = Math.max(-1.0, Math.min(1.0, right));

        if (inverted) {
            drive(-left, right);
        } else {
            drive(left, -right);
        }
    }

    public void setOutputVolts(double leftVolts, double rightVolts) {
        leftA.setVoltage(leftVolts);
        rightA.setVoltage(-rightVolts);
    }

    public void resetPose() {
        leftA.getEncoder().setPosition(0);
        rightA.getEncoder().setPosition(0);
        gyro.reset();
    }

    public void setPose(double x, double y) {
        leftA.getEncoder().setPosition(x);
        rightA.getEncoder().setPosition(y);
        gyro.reset();
    }

    public void setPose(Pose2d pose2d) {
        leftA.getEncoder().setPosition(pose2d.getX());
        rightA.getEncoder().setPosition(pose2d.getY());
    }

    public void updateDashboard() {
        if (Constants.DEBUG_DRIVETRAIN) {
            SmartDashboard.putNumber("Gyro Angle", getGyroRotation().getDegrees());
            SmartDashboard.putNumber("Left A Power", leftA.getAppliedOutput());
            SmartDashboard.putNumber("Left B Power", leftB.getAppliedOutput());
            SmartDashboard.putNumber("Right A Power", rightA.getAppliedOutput());
            SmartDashboard.putNumber("Right B Power", rightB.getAppliedOutput());
            SmartDashboard.putNumber("Left A Encoder", leftA.getEncoder().getPosition());
            SmartDashboard.putNumber("Left B Encoder", leftB.getEncoder().getPosition());
            SmartDashboard.putNumber("Right A Encoder", rightA.getEncoder().getPosition());
            SmartDashboard.putNumber("Left A Current", leftA.getOutputCurrent());
            SmartDashboard.putNumber("Left B Current", leftB.getOutputCurrent());
            SmartDashboard.putNumber("Right A Current", rightA.getOutputCurrent());
            SmartDashboard.putNumber("Right B Current", rightB.getOutputCurrent());
        }
    }

    public int getLeftEncoder() {
        return (int)((leftA.getEncoder().getPosition() + leftB.getEncoder().getPosition()) / 2.0);
    }

    public int getLeftEncoderRate() {
        return (int)((leftA.getEncoder().getVelocity()
                    + leftB.getEncoder().getVelocity()) / 2.0);
    }

    public int getRightEncoder() {
        return (int)(-(rightA.getEncoder().getPosition() + rightB.getEncoder().getPosition()) / 2.0);
    }

    public int getRightEncoderRate() {
        return (int)((rightA.getEncoder().getVelocity()
                    + rightB.getEncoder().getVelocity()) / 2.0);
    }

    public void stop() {
        drive(0, 0);
    }

    @SuppressWarnings("removal")
    public void setCoast() {
        leftAConfig.idleMode(IdleMode.kCoast);
        leftBConfig.idleMode(IdleMode.kCoast);
        rightAConfig.idleMode(IdleMode.kCoast);
        rightBConfig.idleMode(IdleMode.kCoast);

        leftA.configure(leftAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        leftB.configure(leftBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightA.configure(rightAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightB.configure(rightBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }

    @SuppressWarnings("removal")
    public void setBrake() {
        leftAConfig.idleMode(IdleMode.kBrake);
        leftBConfig.idleMode(IdleMode.kBrake);
        rightAConfig.idleMode(IdleMode.kBrake);
        rightBConfig.idleMode(IdleMode.kBrake);

        leftA.configure(leftAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        leftB.configure(leftBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightA.configure(rightAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightB.configure(rightBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftEncoderRate(), getRightEncoderRate());
    }

    public SimpleMotorFeedforward getFeedforward() {
        return m_FF;
    }

    public PIDController getLeftPIDController() {
        return m_leftController;
    }

    public PIDController getRightPIDController() {
        return m_rightController;
    }
}