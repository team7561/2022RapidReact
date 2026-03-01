package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkMaxConfig;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Ports;

public class Shooter extends SubsystemBase {

    private final SparkMax shooterMotorA;
    private final SparkMax shooterMotorB;
    private final TalonSRX shooterHood;

    private final SparkMaxConfig shooterAConfig = new SparkMaxConfig();
    private final SparkMaxConfig shooterBConfig = new SparkMaxConfig();

    private boolean shooting = false;
    private boolean hood_auto = false;
    private boolean shooter_vision_auto = false;

    private double m_setpoint = 100;
    private double m_hood_setpoint = 0.2;
    private double m_hood_position = 0;

    @SuppressWarnings("removal")
    public Shooter() {

        // Spark Flex controllers running NEO motors
        shooterMotorA = new SparkMax(Ports.SHOOTER_A_CANID, MotorType.kBrushless);
        shooterMotorB = new SparkMax(Ports.SHOOTER_B_CANID, MotorType.kBrushless);

        // -----------------------------
        // SparkMaxConfig for Motor A
        // -----------------------------
        shooterAConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(45);

        // Encoder configuration (NEO hall sensor)
        shooterAConfig.encoder
            .positionConversionFactor(1.0)
            .velocityConversionFactor(1.0); // native RPM

        // PIDF configuration
        shooterAConfig.closedLoop
            .pid(0.003, 0.000002, 0.000004)
            .iZone(500)
            .outputRange(-0.85, 0.85)
            .velocityFF(0.0);

        // Apply configs
        shooterMotorA.configure(
            shooterAConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        
        shooterBConfig
            .idleMode(IdleMode.kCoast)
            .smartCurrentLimit(45);

        shooterMotorB.configure(
            shooterBConfig,
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters
        );

        
        // -----------------------------
        // Hood (TalonSRX)
        // -----------------------------
        shooterHood = new TalonSRX(Ports.SHOOTER_HOOD_CANID);
        shooterHood.setNeutralMode(NeutralMode.Brake);
        shooterHood.enableCurrentLimit(true);
        shooterHood.configContinuousCurrentLimit(2);
        shooterHood.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        shooterHood.setSelectedSensorPosition(0);
    }

    public void startFlywheel() {
        shooting = true;
    }

    public void stop() {
        shooting = false;
        shooterMotorA.stopMotor();
    }

    @Override
    public void periodic() {

        m_hood_setpoint = SmartDashboard.getNumber("Hood Set Point", m_hood_setpoint);
        m_hood_position = shooterHood.getSelectedSensorPosition();

        if (shooting) {
            m_setpoint = SmartDashboard.getNumber("Set Point", m_setpoint);

            shooterMotorA.getClosedLoopController().setSetpoint(
                m_setpoint,
                ControlType.kVelocity
            );
            shooterMotorB.set(shooterMotorA.get());
        }

        if (shooter_vision_auto) {
            m_setpoint = limelight_speed_setpoint();
            SmartDashboard.putNumber("Set Point", m_setpoint);
        }

        if (hood_auto) {
            autoHood();
        }
    }

    public void start_auto_hood() { hood_auto = true; }
    public void stop_auto_hood() { hood_auto = false; }

    public double getVelocity() {
        return shooterMotorA.getEncoder().getVelocity();
    }

    // -----------------------------
    // Hood control
    // -----------------------------
    public void extendHood() { shooterHood.set(ControlMode.PercentOutput, -0.25); }
    public void retractHood() { shooterHood.set(ControlMode.PercentOutput, 0.25); }
    public void extendHoodSlow() { shooterHood.set(ControlMode.PercentOutput, -0.1); }
    public void retractHoodSlow() { shooterHood.set(ControlMode.PercentOutput, 0.1); }

    public void autoHood() {
        boolean slow = Math.abs(m_hood_setpoint - m_hood_position) < Constants.HOOD_SLOW_TOLERANCE;

        if (hood_at_setpoint()) {
            stopHood();
            return;
        }

        if (m_hood_setpoint < m_hood_position) {
            if (slow) retractHoodSlow();
            else retractHood();
        } else {
            if (slow) extendHoodSlow();
            else extendHood();
        }
    }

    public boolean hood_at_setpoint() {
        return Math.abs(m_hood_setpoint - m_hood_position) < Constants.HOOD_TOLERANCE;
    }

    public void stopHood() {
        shooterHood.set(ControlMode.PercentOutput, 0);
    }

    public void reset_Hood() {
        shooterHood.setSelectedSensorPosition(0);
    }

    public double limelight_speed_setpoint() {
        return 250 * (SmartDashboard.getNumber("ty", 0) / 20) + 2050;
    }
}