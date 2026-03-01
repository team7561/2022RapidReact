package frc.robot.subsystems;

import frc.robot.Ports;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {

    private final SparkMax conveyor;
    private final SparkMaxConfig conveyorConfig = new SparkMaxConfig();

    private final double speed = 0.4;
    private boolean holdingNote;

    private final Timer conveyorTimer = new Timer();

    @SuppressWarnings("removal")
    public Conveyor() {

        conveyor = new SparkMax(Ports.Conveyor_ID, MotorType.kBrushless);

        // -----------------------------
        // Motor configuration
        // -----------------------------
        conveyorConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(30);

        conveyor.configure(
            conveyorConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        holdingNote = false;
        SmartDashboard.putBoolean("Holding Note", holdingNote);
    }

    // -----------------------------
    // Conveyor control
    // -----------------------------
    public void goUp() {
        conveyor.set(-speed);
    }

    public void goUpSlow() {
        conveyor.set(-speed / 2);
    }

    public void goDown() {
        conveyor.set(speed * 0.2);
    }

    public void stop() {
        conveyor.set(0.0);
    }

    @Override
    public void periodic() {

        updateDashboard();

        holdingNote = SmartDashboard.getBoolean("Holding Note", true);

        if (!holdingNote) {
            conveyorTimer.reset();
            conveyorTimer.stop();
            goUpSlow();
        } else {
            conveyorTimer.start();

            if (conveyorTimer.get() > 0.05) {
                stop();
            } else {
                goUpSlow();
            }
        }
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Conveyor Speed", conveyor.get());
        SmartDashboard.putNumber("Conveyor Current", conveyor.getOutputCurrent());
        SmartDashboard.putNumber("Conveyor Velocity", conveyor.getEncoder().getVelocity());
    }
}