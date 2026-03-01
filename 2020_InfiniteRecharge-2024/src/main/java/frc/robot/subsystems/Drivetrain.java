package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class Drivetrain extends SubsystemBase {

    double lastError;
    int current = 99;
    public ADXRS450_Gyro gyro;

    private final DifferentialDrive m_drive;
    private final MotorControllerGroup m_leftMotors;
    private final MotorControllerGroup m_rightMotors;
        
    private SimpleMotorFeedforward m_FF;

    // Odometry class for tracking robot pose
    //private final DifferentialDriveOdometry m_odometry;
    private DifferentialDriveKinematics m_kinematics;

    CANSparkMax leftA, leftB, leftC, rightA, rightB, rightC;
    private CANEncoder m_leftEncoder;
    private CANEncoder m_rightEncoder;
    
    private PIDController m_leftController;
    private PIDController m_rightController;

    public Drivetrain()
    {
        leftA = new CANSparkMax(Ports.DRIVE_LEFT_A_CANID, MotorType.kBrushless);
        leftB = new CANSparkMax(Ports.DRIVE_LEFT_B_CANID, MotorType.kBrushless);
        leftC = new CANSparkMax(Ports.DRIVE_LEFT_C_CANID, MotorType.kBrushless);
        rightA = new CANSparkMax(Ports.DRIVE_RIGHT_A_CANID, MotorType.kBrushless);
        rightB = new CANSparkMax(Ports.DRIVE_RIGHT_B_CANID, MotorType.kBrushless);
        rightC = new CANSparkMax(Ports.DRIVE_RIGHT_C_CANID, MotorType.kBrushless);

        /*leftA.restoreFactoryDefaults();
        leftB.restoreFactoryDefaults();
        leftC.restoreFactoryDefaults();
        rightA.restoreFactoryDefaults();
        rightB.restoreFactoryDefaults();
        rightC.restoreFactoryDefaults();*/

        m_leftMotors = new MotorControllerGroup(leftA, leftB, leftC);
        m_rightMotors = new MotorControllerGroup(rightA, rightB, rightC);
        m_leftEncoder = leftA.getEncoder();
        m_rightEncoder = rightA.getEncoder();
        
        // set up encoder conversion factor
        double conversionFactor = Constants.DRIVE_GEAR_RATIO * 2*Math.PI*3*2.54/100;

        rightA.setInverted(false);
        m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);
        m_drive.setSafetyEnabled(false);
        m_drive.setDeadband(0.05);

        m_leftEncoder.setVelocityConversionFactor(conversionFactor/60);
        m_leftEncoder.setPositionConversionFactor(conversionFactor);

        m_rightEncoder.setVelocityConversionFactor(conversionFactor/60);
        m_rightEncoder.setPositionConversionFactor(conversionFactor);
        
      
        m_leftController = new PIDController(0.295, 0, 0);
        m_rightController = new PIDController(0.295, 0, 0);
        m_FF = new SimpleMotorFeedforward(0.3, 1.96, 0.06);
  
        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        resetGyro();

        //adis = new ADIS16448_IMU();
        leftA.setIdleMode(IdleMode.kCoast);
        leftB.setIdleMode(IdleMode.kCoast);
        leftC.setIdleMode(IdleMode.kCoast);
        rightA.setIdleMode(IdleMode.kCoast);
        rightB.setIdleMode(IdleMode.kCoast);
        rightC.setIdleMode(IdleMode.kCoast);

        leftA.setSmartCurrentLimit(current);
        leftB.setSmartCurrentLimit(current);
        leftC.setSmartCurrentLimit(current);
        rightA.setSmartCurrentLimit(current);
        rightB.setSmartCurrentLimit(current);
        rightC.setSmartCurrentLimit(current);

        leftB.follow(leftA);
        leftC.follow(leftA);
        rightB.follow(rightA);
        rightC.follow(rightA);
    }

    //sets the speeds of all driving motors
    public void drive(double leftSpeed, double rightSpeed) {
        m_drive.tankDrive(leftSpeed, rightSpeed, false);
        m_drive.feed();
    }
    public void resetEncoders()
    {
        leftA.getEncoder().setPosition(0);
        leftB.getEncoder().setPosition(0);
        leftC.getEncoder().setPosition(0);
        rightA.getEncoder().setPosition(0);
        rightB.getEncoder().setPosition(0);
        rightC.getEncoder().setPosition(0);
    }

    // resets gyro
    public void resetGyro()
    {
        gyro.reset();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        return getGyroRotation().getDegrees();
    }
    /**
     * Gets the drivetrain's kinematic model.
     */
    public DifferentialDriveKinematics getKinematics() {
        return m_kinematics;
    }
    /**
     * Drives the robot using commanded chassis speeds. Call repeatedly.
     */
    public void driveClosedLoop(DifferentialDriveWheelSpeeds speeds) {

        double left = speeds.leftMetersPerSecond;
        double right = speeds.rightMetersPerSecond;

        double leftVoltage = m_FF.calculate(left) + m_leftController.calculate(m_leftEncoder.getVelocity(), left);
        double rightVoltage = m_FF.calculate(right) + m_rightController.calculate(m_rightEncoder.getVelocity(), right);

        leftA.setVoltage(leftVoltage);
        rightA.setVoltage(rightVoltage);
    }
    public Rotation2d getGyroRotation() {
        return gyro.getRotation2d();
      }
    public void periodic() {
        // update the drivetrain's position estimate
        //m_odometry.update(getGyroRotation(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
    
        if (Constants.DEBUG_DRIVETRAIN)
        {
            SmartDashboard.putNumber("left_enc", m_leftEncoder.getPosition());
            SmartDashboard.putNumber("right_enc", m_rightEncoder.getPosition());
            // publish debug odometry values
            //SmartDashboard.putNumber("odometry_x", m_odometry.getPoseMeters().getX());
            //SmartDashboard.putNumber("odometry_y", m_odometry.getPoseMeters().getY());
            //SmartDashboard.putNumber("odometry_theta", m_odometry.getPoseMeters().getRotation().getDegrees());
        }
    }
    //teleop driving
    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        double left = (- (y - x))*speed;
        double right = (-y - x)*speed;
      
        left = Math.min(left, 1);
        right = Math.min(right, 1);

        if (inverted) {
            drive(-left, right);
        }
        else
        {
            drive(left, -right);
        }
    }
    public void setOutputVolts(double leftVolts, double rightVolts) {
        m_leftMotors.setVoltage(leftVolts);
        m_rightMotors.setVoltage(-rightVolts);
        m_drive.feed();
      }
      /**
       * Resets the drivetrain's stored pose and encoder values.
       */
    public void resetPose() {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        gyro.reset();

    }
    /**
     * Resets the drivetrain's stored pose and encoder values.
     */
    public void setPose(double x, double y) {
        m_leftEncoder.setPosition(x);
        m_rightEncoder.setPosition(y);
        gyro.reset();
        //m_odometry.resetPosition(new Pose2d(x, y, getGyroRotation()), getGyroRotation());
    }      /**
    * Resets the drivetrain's stored pose and encoder values.
    */
    public void setPose(Pose2d pose2d) {
        m_leftEncoder.setPosition(pose2d.getX());
        m_rightEncoder.setPosition(pose2d.getY());
        //m_odometry.resetPosition(pose2d, getGyroRotation());
    }

    //put dashboard stuff here
    public void updateDashboard()
    {
        if (Constants.DEBUG_DRIVETRAIN)
        {
            SmartDashboard.putNumber("Gyro Angle", getGyroRotation().getDegrees());
            SmartDashboard.putNumber("Left A Power", leftA.get());
            SmartDashboard.putNumber("Left B Power", leftB.get());
            SmartDashboard.putNumber("Left C Power", leftC.get());
            SmartDashboard.putNumber("Right A Power", rightA.get());
            SmartDashboard.putNumber("Right B Power", rightB.get());
            SmartDashboard.putNumber("Right C Power", rightC.get());
            SmartDashboard.putNumber("Left A Encoder", leftA.getEncoder().getPosition());
            SmartDashboard.putNumber("Left B Encoder", leftB.getEncoder().getPosition());
            SmartDashboard.putNumber("Left C Encoder", leftC.getEncoder().getPosition());
            SmartDashboard.putNumber("Right A Encoder", rightA.getEncoder().getPosition());
            SmartDashboard.putNumber("Right B Encoder", rightB.getEncoder().getPosition());
            SmartDashboard.putNumber("Right C Encoder", rightC.getEncoder().getPosition());
            SmartDashboard.putNumber("Left A Current", leftA.getOutputCurrent());
            SmartDashboard.putNumber("Left B Current", leftB.getOutputCurrent());
            SmartDashboard.putNumber("Left C Current", leftC.getOutputCurrent());
            SmartDashboard.putNumber("Right A Current", rightA.getOutputCurrent());
            SmartDashboard.putNumber("Right B Current", rightB.getOutputCurrent());
            SmartDashboard.putNumber("Right C Current", rightC.getOutputCurrent());
            
        }
    }
    public int getLeftEncoder()
    {
        return (int) (leftA.getEncoder().getPosition()+leftB.getEncoder().getPosition())/2;
    }
    public int getLeftEncoderRate()
    {
        return (int) (leftA.getEncoder().getVelocity()+leftB.getEncoder().getVelocity()+leftC.getEncoder().getVelocity())/3;
    }
    public int getRightEncoder()
    {
        return (int) -(rightA.getEncoder().getPosition()+rightB.getEncoder().getPosition())/2;
    }
    public int getRightEncoderRate()
    {
        return (int) (rightA.getEncoder().getVelocity()+rightB.getEncoder().getVelocity()+rightC.getEncoder().getVelocity())/3;
    }

	public void stop() {
        drive(0, 0);
    }

    public void setCoast()
    {
        leftA.setIdleMode(IdleMode.kCoast);
        leftB.setIdleMode(IdleMode.kCoast);
        leftC.setIdleMode(IdleMode.kCoast);
        rightA.setIdleMode(IdleMode.kCoast);
        rightB.setIdleMode(IdleMode.kCoast);
        rightC.setIdleMode(IdleMode.kCoast);
    }
   
    public void setBrake()
    {
        leftA.setIdleMode(IdleMode.kBrake);
        leftB.setIdleMode(IdleMode.kBrake);
        leftC.setIdleMode(IdleMode.kBrake);
        rightA.setIdleMode(IdleMode.kBrake);
        rightB.setIdleMode(IdleMode.kBrake);
        rightC.setIdleMode(IdleMode.kBrake);
    }

    /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
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