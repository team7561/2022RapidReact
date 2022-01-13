package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;


public class Intake extends SubsystemBase{
    double m_deployTarget;

    CANSparkMax intakeMotor;
    CANSparkMax intakeDeployMotor;


    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Intake(){
        intakeMotor = new CANSparkMax(Ports.CAN_ID_INTAKE, MotorType.kBrushless);
        intakeDeployMotor = new CANSparkMax(Ports.CAN_ID_INTAKE_DEPLOY, MotorType.kBrushless);
        
        intakeMotor.restoreFactoryDefaults();
        intakeDeployMotor.restoreFactoryDefaults();

        intakeMotor.setIdleMode(IdleMode.kCoast);
        intakeDeployMotor.setIdleMode(IdleMode.kBrake);
        
        intakeMotor.setSmartCurrentLimit(45);
        intakeDeployMotor.setSmartCurrentLimit(45);

        SmartDashboard.putNumber("intakeMotor", 0.1);
        SmartDashboard.putNumber("intakeDeployMotor", 0.1);

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

    }
    public void stop()
    {
        intakeMotor.set(0);
    }
    public void grabBall()
    {
        intakeMotor.set(Speeds.GET_BALL_SPEED);
    }
    public void ejectBall()
    {
        intakeMotor.set(Speeds.EJECT_BALL_SPEED);
    }
    public void periodic(){
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INTAKE)
        {
            SmartDashboard.putNumber("Intake Speed", intakeMotor.get());
            SmartDashboard.putNumber("Intake Deploy Speed", intakeDeployMotor.get());
        }
    }

}
