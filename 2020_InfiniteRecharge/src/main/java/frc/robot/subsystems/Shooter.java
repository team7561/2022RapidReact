package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
public class Shooter extends SubsystemBase {
    CANSparkMax shooterMotorA;
    CANSparkMax shooterMotorB;
    TalonSRX shooterHood;

    private CANPIDController m_pidController;
    private RelativeEncoder m_flywheel_encoder;
    private boolean shooting, hood_auto, shooter_vision_auto;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Shooter()
    {
        shooterMotorA = new CANSparkMax(Ports.SHOOTER_A_CANID, MotorType.kBrushless);
        shooterMotorB = new CANSparkMax(Ports.SHOOTER_B_CANID, MotorType.kBrushless);
        shooterHood = new TalonSRX(Ports.SHOOTER_HOOD_CANID);
        
        shooterMotorA.restoreFactoryDefaults();
        shooterMotorB.restoreFactoryDefaults();

        shooterMotorA.setIdleMode(IdleMode.kCoast);
        shooterMotorB.setIdleMode(IdleMode.kCoast);
        
        shooterMotorA.setSmartCurrentLimit(45);
        shooterMotorB.setSmartCurrentLimit(45);
        
        m_pidController = shooterMotorA.getPIDController();
        m_flywheel_encoder = shooterMotorA.getEncoder();
        
        shooterHood.setNeutralMode(NeutralMode.Brake);
        shooterHood.enableCurrentLimit(true);
        shooterHood.configContinuousCurrentLimit(2);
        
        shooterHood.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        shooterHood.setSelectedSensorPosition(0);

        hood_auto = false;
        shooter_vision_auto = false;
        shooting = false; 
        m_hood_position = 0;

        // PID coefficients
        kP = 0.003; 
        kI = 0.000002;
        kD = 0.000004; 
        kIz = 500; // Error process value must be within before I is used.
        kFF = 0; 
        m_setpoint = 100;
        m_hood_setpoint = 0.2;
        kMaxOutput = 0.85; 
        kMinOutput = -0.85;
        maxRPM = 450;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        shooterMotorB.follow(ExternalFollower.kFollowerSparkMax, Ports.SHOOTER_A_CANID, true);
    }

    public void startFlywheel()
    {
        shooting = true;
    }
    public void periodic()
    {
        m_hood_setpoint = SmartDashboard.getNumber("Hood Set Point", m_hood_setpoint);
        m_hood_position = shooterHood.getSelectedSensorPosition();
        if (shooting)
        {
            //System.out.println("Shooting at speed: " + m_setpoint);
            double max = SmartDashboard.getNumber("Max Output", kMaxOutput);
            double min = SmartDashboard.getNumber("Min Output", kMinOutput);
            m_setpoint = SmartDashboard.getNumber("Set Point", m_setpoint);

            if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_pidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
            }
            m_pidController.setReference(m_setpoint, ControlType.kVelocity);
            
        }
        else
        {
            shooterMotorA.set(0);
        }
        if (shooter_vision_auto)
        {
            m_setpoint = limelight_speed_setpoint();
            SmartDashboard.putNumber("Set Point", m_setpoint);
        }
        if (hood_auto)
        {
            autoHood();
        }
    }
    public double getVelocity()
    {
        return m_flywheel_encoder.getVelocity();
    }
    public void extendHood()
    {
        shooterHood.set(ControlMode.PercentOutput, -0.25);
    }
    public void retractHood()
    {
        shooterHood.set(ControlMode.PercentOutput, 0.25);
    }
    public void extendHoodSlow()
    {
        shooterHood.set(ControlMode.PercentOutput, -0.1);
    }
    public void retractHoodSlow()
    {
        shooterHood.set(ControlMode.PercentOutput, 0.1);
    }
    public void autoHood()
    {
        boolean slow = false;
        if (hood_at_setpoint())
        {
            stopHood();
            return;
        }
        if (Math.abs(m_hood_setpoint - m_hood_position) < Constants.HOOD_SLOW_TOLERANCE)
        {
            slow = true;
        }
        if (m_hood_setpoint < m_hood_position)
        {
            if (slow)
            {
                retractHoodSlow();
            }
            else
            {
                retractHood();
            }
        }
        if (m_hood_setpoint > m_hood_position)
        {
            if (slow)
            {
                extendHoodSlow();
            }
            else
            {
                extendHood();
            }
        }
    }
    public boolean hood_at_setpoint()
    {
        System.out.println("Hood Setpoint: " + m_hood_setpoint);
        System.out.println("Hood Position: " + m_hood_position);
        if (Math.abs(m_hood_setpoint - m_hood_position) < Constants.HOOD_TOLERANCE)
        {
            return true;
        }
        return false;
    }
    public void stopHood()
    {
        System.out.println("Stopping hood");
        shooterHood.set(ControlMode.PercentOutput, 0);
    }
    public void setSetpoint(double setPoint)
    {
        m_setpoint = setPoint;
    }

    // Change hood to auto
    public void start_auto_hood()
    {
        hood_auto = true;
    }    
    // Change hood to manual
    public void stop_auto_hood()
    {
        hood_auto = false;
    }    
    // Change hood to auto
    public void start_auto_vision_speed()
    {
        shooter_vision_auto = true;
    }    
    // Change hood to manual
    public void stop_auto_vision_speed()
    {
        shooter_vision_auto = false;
    }    
    
    //Stops shooter
    public void stop()
    {
        shooting = false;
        //hood_auto = false;
        //hood_vision_auto = false;
    }
    //Stops shooter
    public void reset_Hood()
    {
        System.out.println("Reseting hood positiion");
        shooterHood.setSelectedSensorPosition(0);
    }
    public double limelight_speed_setpoint()
    {
        return 250*(SmartDashboard.getNumber("ty", 0)/20)+2050;
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_SHOOTER)
        {
            SmartDashboard.putNumber("Set Point", m_setpoint);
            
            
            SmartDashboard.putNumber("Shooter A Power", shooterMotorA.getAppliedOutput());
            SmartDashboard.putNumber("Shooter B Power", shooterMotorB.getAppliedOutput());
            SmartDashboard.putNumber("Shooter A Current", shooterMotorA.getOutputCurrent());
            SmartDashboard.putNumber("Shooter B Current", shooterMotorB.getOutputCurrent());
            SmartDashboard.putNumber("Shooter Hood Position", m_hood_position);
            SmartDashboard.putNumber("Shooter Hood Voltage", shooterHood.getMotorOutputVoltage());
            SmartDashboard.putNumber("Shooter Hood Current", shooterHood.getOutputCurrent());
        }
    }
 }