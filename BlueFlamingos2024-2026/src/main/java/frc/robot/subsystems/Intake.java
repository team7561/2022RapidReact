package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.utility.Lidar;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final SparkMax intakeFront;
    private final SparkMax intakeRear;

    private final SparkMaxConfig frontConfig = new SparkMaxConfig();
    private final SparkMaxConfig rearConfig  = new SparkMaxConfig();

    private final Timer intakeTimer = new Timer();
    private final Lidar lidar;
    private final DigitalInput limitSwitch = new DigitalInput(1);

    private boolean intakeAtSpeed = false;
    private boolean intakeLostSpeed = false;

    @SuppressWarnings("removal")
    public Intake() {

        intakeFront = new SparkMax(Ports.Intake_Front_ID, MotorType.kBrushless);
        intakeRear  = new SparkMax(Ports.Intake_Rear_ID, MotorType.kBrushless);

        // -----------------------------
        // Motor configs
        // -----------------------------
        frontConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40)
            .openLoopRampRate(0.1);

        rearConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40)
            .openLoopRampRate(0.1);

        // Apply configs
        intakeFront.configure(
            frontConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        intakeRear.configure(
            rearConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        // LIDAR
        DigitalSource lidarSource = new DigitalInput(0);
        lidar = new Lidar(lidarSource);
    }

    // -----------------------------
    // Intake control
    // -----------------------------
    public void grab() {
        intakeFront.set(0.5);
        intakeRear.set(0.5);
    }

    public void stop() {
        intakeFront.set(0.0);
        intakeRear.set(0.0);
    }

    public void reverse() {
        intakeFront.set(-0.2);
        intakeRear.set(-0.2);
    }

    @Override
    public void periodic() {

        updateDashboard();

        // Reset speed flags when holding a note
        if (SmartDashboard.getBoolean("Holding Note", true)) {
            intakeAtSpeed = false;
            intakeLostSpeed = false;
        }

        // Auto mode logic
        if (Constants.AUTO_MODE) {
            if (SmartDashboard.getBoolean("Holding Note", true)) {
                stop();
            } else {
                grab();
            }
        }

        // Limit switch timing logic
        if (limitSwitch.get()) {
            intakeTimer.start();
        }

        if (intakeTimer.get() > 0.2) {
            SmartDashboard.putBoolean("Holding Note", true);
            intakeTimer.reset();
            intakeTimer.stop();
        }
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Intake Front Speed", intakeFront.get());
        SmartDashboard.putNumber("Intake Rear Speed", intakeRear.get());
        SmartDashboard.putNumber("Intake Front RPM", intakeFront.getEncoder().getVelocity());
        SmartDashboard.putNumber("Intake Rear RPM", intakeRear.getEncoder().getVelocity());
        SmartDashboard.putNumber("Intake Front Current", intakeFront.getOutputCurrent());
        SmartDashboard.putNumber("Intake Rear Current", intakeRear.getOutputCurrent());
        SmartDashboard.putBoolean("Intake Lost Speed", intakeLostSpeed);
        SmartDashboard.putBoolean("Intake At Speed", intakeAtSpeed);
        SmartDashboard.putNumber("LIDAR Distance", lidar.getDistance());
        SmartDashboard.putBoolean("Conveyor Limit Switch", limitSwitch.get());
        SmartDashboard.putNumber("Intake Limit Timer", intakeTimer.get());
        SmartDashboard.putBoolean("Robot Climbing Indicator", SmartDashboard.getBoolean("Holding Note", true));
    }
}