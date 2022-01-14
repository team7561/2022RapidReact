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


public class Intake extends SubsystemBase{
    double m_deployTarget;

    private CANSparkMax intakeMotor;
    private CANSparkMax intakeDeployMotor;
    private SparkMaxPIDController m_pidController;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Intake(){
        intakeMotor = new CANSparkMax(Ports.CAN_ID_INTAKE, MotorType.kBrushless);
        intakeDeployMotor = new CANSparkMax(Ports.CAN_ID_INTAKE_DEPLOY, MotorType.kBrushless);
        
        intakeMotor.restoreFactoryDefaults();
        intakeDeployMotor.restoreFactoryDefaults();

        intakeMotor.setIdleMode(IdleMode.kCoast);
        intakeDeployMotor.setIdleMode(IdleMode.kBrake);
        
        intakeMotor.setSmartCurrentLimit(15);
        intakeDeployMotor.setSmartCurrentLimit(4);

        SmartDashboard.putNumber("intakeMotor", 0.1);
        SmartDashboard.putNumber("intakeDeployMotor", 0.1);

        // PID coefficients
        kP = 0.003; 
        kI = 0.000002;
        kD = 0.000004; 
        kIz = 500; // Error process value must be within before I is used.
        kFF = 0; 
        m_deployTarget = 0;
        m_hood_setpoint = 0.2;
        kMaxOutput = 0.5; 
        kMinOutput = -0.5;
        maxRPM = 1000;

        m_pidController = intakeDeployMotor.getPIDController();

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

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
    public void extendIntake()
    {
        m_deployTarget = 1000;
    }
    public void retractIntake()
    {
        m_deployTarget = 0;
    }
    public void periodic(){
        updateDashboard();
        m_pidController.setReference(m_deployTarget, CANSparkMax.ControlType.kPosition);
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
