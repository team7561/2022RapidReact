package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.Ports;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

public class Shooter extends SubsystemBase {

    private final SparkMax shooterA;
    private final SparkMax shooterB;

    private final SparkMaxConfig shooterAConfig = new SparkMaxConfig();
    private final SparkMaxConfig shooterBConfig = new SparkMaxConfig();

    private final double speedFast = -1.0;
    private final double speedSlow = -0.3;

    @SuppressWarnings("removal")
    public Shooter() {

        shooterA = new SparkMax(Ports.Shooter_A_ID, MotorType.kBrushless);
        shooterB = new SparkMax(Ports.Shooter_B_ID, MotorType.kBrushless);

        // -----------------------------
        // Motor A config
        // -----------------------------
        shooterAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40)
            .openLoopRampRate(2.0);

        // -----------------------------
        // Motor B config
        // -----------------------------
        shooterBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(40)
            .openLoopRampRate(2.0)
            .inverted(true);

        // -----------------------------
        // Apply configs
        // -----------------------------
        shooterA.configure(
            shooterAConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        shooterB.configure(
            shooterBConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    // -----------------------------
    // Shooter control
    // -----------------------------
    public void shootFast() {
        shooterA.set(speedFast);
    }

    public void shootSlow() {
        shooterA.set(speedSlow);
    }

    public void shootSpeed(double speed) {
        shooterA.set(speed);
    }

    public void stop() {
        shooterA.set(0.0);
    }

    @Override
    public void periodic() {

        // Follower fallback for your Spark API version
        shooterB.set(shooterA.get());

        updateDashboard();

        if (Constants.AUTO_MODE) {
            if (SmartDashboard.getBoolean("Holding Note", true)) {
                shootFast();
            } else {
                stop();
            }
        }
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("Shooter A Current", shooterA.getOutputCurrent());
        SmartDashboard.putNumber("Shooter B Current", shooterB.getOutputCurrent());
        SmartDashboard.putNumber("Shooter Speed", shooterA.get());
        SmartDashboard.putNumber("Shooter RPM", shooterA.getEncoder().getVelocity());
    }
}