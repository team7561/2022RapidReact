package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Ports;
import frc.robot.utility.Coordinate;

public class Drivetrain extends SubsystemBase {

    private final SparkMax leftA, leftB, rightA, rightB;

    private final SparkMaxConfig leftAConfig = new SparkMaxConfig();
    private final SparkMaxConfig leftBConfig = new SparkMaxConfig();
    private final SparkMaxConfig rightAConfig = new SparkMaxConfig();
    private final SparkMaxConfig rightBConfig = new SparkMaxConfig();

    private final BuiltInAccelerometer rioAccel;
    private final ADXRS450_Gyro gyro;

    private double spin = 1;

    public Coordinate currentCoordinate;

    @SuppressWarnings("removal")
    public Drivetrain() {

        gyro = new ADXRS450_Gyro();
        rioAccel = new BuiltInAccelerometer();

        // -----------------------------
        // Motor objects
        // -----------------------------
        leftA  = new SparkMax(Ports.Drivetrain_FL_ID, MotorType.kBrushless);
        leftB  = new SparkMax(Ports.Drivetrain_BL_ID, MotorType.kBrushless);
        rightA = new SparkMax(Ports.Drivetrain_FR_ID, MotorType.kBrushless);
        rightB = new SparkMax(Ports.Drivetrain_BR_ID, MotorType.kBrushless);

        // -----------------------------
        // Motor configs
        // -----------------------------
        leftAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40);

        leftBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40);

        rightAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40);

        rightBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40);

        // -----------------------------
        // Apply configs
        // -----------------------------
        leftA.configure(leftAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        leftB.configure(leftBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightA.configure(rightAConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        rightB.configure(rightBConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        updateDashboard();

        // Follower fallback (your Spark API has no follower support)
        leftB.set(leftA.get());
        rightB.set(rightA.get());
    }

    // -----------------------------
    // Gyro + Encoders
    // -----------------------------
    public double getGyroRotation() {
        return gyro.getAngle();
    }

    public void resetEncoders() {
        leftA.getEncoder().setPosition(0);
        leftB.getEncoder().setPosition(0);
        rightA.getEncoder().setPosition(0);
        rightB.getEncoder().setPosition(0);
    }

    public double getLeftEncoder() {
        return 0.5 * (leftA.getEncoder().getPosition() + leftB.getEncoder().getPosition());
    }

    public double getRightEncoder() {
        return 0.5 * (rightA.getEncoder().getPosition() + rightB.getEncoder().getPosition());
    }

    // -----------------------------
    // Drive control
    // -----------------------------
    public void stop() {
        set(0, 0);
    }

    public void drive(double left, double right) {

        if (SmartDashboard.getBoolean("Cylinder Extended?", false)) {
            set(right / 5, left / 5);
        } else {
            set(left, right);
        }
    }

    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        x = x * spin;

        double left  = (-y + x) * speed;
        double right = ( y + x) * speed;

        left  = Math.max(-1, Math.min(1, left));
        right = Math.max(-1, Math.min(1, right));

        drive(left, right);

        SmartDashboard.putNumber("Left", left);
        SmartDashboard.putNumber("Right", right);
    }

    public void setSpin(double speed) {
        spin = speed;
    }

    // -----------------------------
    // Tilt / Pitch / Roll
    // -----------------------------
    public double getPitch() {
        return Math.atan2(
            -rioAccel.getX(),
            Math.sqrt(rioAccel.getY() * rioAccel.getY() + rioAccel.getZ() * rioAccel.getZ())
        ) * 57.3;
    }

    public double getRoll() {
        return Math.atan2(rioAccel.getY(), rioAccel.getZ()) * 57.3;
    }

    public double getTilt() {
        double pitch = getPitch();
        double roll  = getRoll();
        double tilt  = Math.sqrt(pitch * pitch + roll * roll);
        return (pitch + roll >= 0) ? tilt : -tilt;
    }

    // -----------------------------
    // Motor output
    // -----------------------------
    public void set(double left, double right) {
        leftA.set(left);
        leftB.set(left);
        rightA.set(right);
        rightB.set(right);
    }

    // -----------------------------
    // Dashboard
    // -----------------------------
    public void updateDashboard() {
        SmartDashboard.putNumber("Drive Left A Current", leftA.getOutputCurrent());
        SmartDashboard.putNumber("Drive Left B Current", leftB.getOutputCurrent());
        SmartDashboard.putNumber("Drive Right A Current", rightA.getOutputCurrent());
        SmartDashboard.putNumber("Drive Right B Current", rightB.getOutputCurrent());

        SmartDashboard.putNumber("Robot Angle", getGyroRotation());
        SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", getRightEncoder());
    }
}