package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Injector extends SubsystemBase {

    private final SparkMax injectorMotor;
    private final SparkMaxConfig injectorConfig = new SparkMaxConfig();

    private double setpoint = 0;

    @SuppressWarnings("removal")
    public Injector() {

        // Spark Flex controller
        injectorMotor = new SparkMax(Ports.INJECTOR_CANID, MotorType.kBrushless);

        // Configure using the new API
        injectorConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(20);

        injectorMotor.configure(injectorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }

    private void setSpeed(double speed) {
        setpoint = Math.max(-1.0, Math.min(1.0, speed));
    }

    public void transferBall() {
        setSpeed(Speeds.INJECTOR_TRANSFER_SPEED);
    }

    public void stop() {
        setSpeed(Speeds.INJECTOR_STOP_SPEED);
    }

    public void reverse() {
        setSpeed(Speeds.INJECTOR_BACKFEED_SPEED);
    }

    @Override
    public void periodic() {
        injectorMotor.set(setpoint);
        updateDashboard();
    }

    public void updateDashboard() {
        if (Constants.DEBUG_INJECTOR) {
            SmartDashboard.putNumber("Injector Power", injectorMotor.getAppliedOutput());
            SmartDashboard.putNumber("Injector Current", injectorMotor.getOutputCurrent());
        }
    }
}