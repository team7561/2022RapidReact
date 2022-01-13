package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;


public class Climber extends SubsystemBase{
    double m_deployTarget;

    CANSparkMax climberMotorA, climberMotorB;

    private SparkMaxPIDController m_DeployPIDController;
    //Hood Controller

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Climber(){
        climberMotorA = new CANSparkMax(Ports.CAN_ID_CLIMBER_A, MotorType.kBrushless);
        climberMotorB = new CANSparkMax(Ports.CAN_ID_CLIMBER_B, MotorType.kBrushless);
        
        climberMotorA.restoreFactoryDefaults();
        climberMotorB.restoreFactoryDefaults();

        climberMotorA.setIdleMode(IdleMode.kCoast);
        climberMotorB.setIdleMode(IdleMode.kCoast);
        
        climberMotorA.setSmartCurrentLimit(45);
        climberMotorB.setSmartCurrentLimit(45);

        SmartDashboard.putNumber("climberMotorA", 0.1);
        SmartDashboard.putNumber("climberMotorB", 0.1);

        // PID coefficients
        kP = 0.003; 
        kI = 0.000002;
        kD = 0.000004; 
        kIz = 500; // Error process value must be within before I is used.
        kFF = 0; 
        m_deployTarget = 10;
        m_hood_setpoint = 0.2;
        kMaxOutput = 0.85; 
        kMinOutput = -0.85;
        maxRPM = 4500;

        // set PID coefficients
        m_DeployPIDController.setP(kP);
        m_DeployPIDController.setI(kI);
        m_DeployPIDController.setD(kD);
        m_DeployPIDController.setIZone(kIz);
        m_DeployPIDController.setFF(kFF);
        m_DeployPIDController.setOutputRange(kMinOutput, kMaxOutput);

    }
    public void climb()
    {
        climberMotorA.set(Speeds.CLIMBER_LIFT_SPEED);
        climberMotorB.set(Speeds.CLIMBER_LIFT_SPEED);
    }
    public void climbDeploy()
    {
        climberMotorA.set(Speeds.CLIMBER_DEPLOY_SPEED);
        climberMotorB.set(Speeds.CLIMBER_DEPLOY_SPEED);
    }
    public void climbReverse()
    {
        climberMotorA.set(Speeds.CLIMBER_REVERSE_SPEED);
        climberMotorB.set(Speeds.CLIMBER_REVERSE_SPEED);
    }
    public void stop()
    {
        climberMotorA.set(Speeds.CLIMBER_STOP_SPEED);
        climberMotorB.set(Speeds.CLIMBER_STOP_SPEED);
    }
    public void periodic(){
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_CLIMBER)
        {
            SmartDashboard.putNumber("Climber A Speed", climberMotorA.get());
            SmartDashboard.putNumber("Climber B Speed", climberMotorB.get());
        }
    }
}
