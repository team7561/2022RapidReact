package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Ports;

public class Injector extends SubsystemBase{
    double m_deployTarget;

    CANSparkMax injectorMotor;

    private SparkMaxPIDController m_DeployPIDController;
    //Hood Controller

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Injector(){
        injectorMotor = new CANSparkMax(Ports.CAN_ID_INJECTOR, MotorType.kBrushless);
        
        injectorMotor.restoreFactoryDefaults();

        injectorMotor.setIdleMode(IdleMode.kCoast);
        
        injectorMotor.setSmartCurrentLimit(45);

        SmartDashboard.putNumber("injectorMotor", 0.1);

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
    public void transferBall()
    {
        injectorMotor.set(1);
    }
    public void reverse()
    {
        injectorMotor.set(-1);
    }
    public void stop()
    {
        injectorMotor.set(0);
    }
    public void periodic(){
        
        //intakeDeployMotor.setReference(m_deployTarget, ControlType.kPosition);
    }
    public void updateDashboard()
    {}
}
