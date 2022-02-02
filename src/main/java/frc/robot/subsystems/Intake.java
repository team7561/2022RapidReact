package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAnalogSensor.Mode;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;


public class Intake extends SubsystemBase{
    double m_deployTarget;

    private CANSparkMax intakeMotor;
    private CANSparkMax intakeDeployMotor;

    public boolean armUp = false;
    public boolean armUpRequested = true;
    public double intakeSpeed = 0;
    public double intakeDeploySpeed = 0;

    public double m_hood_position, m_hood_setpoint;

    public Intake(){
        intakeMotor = new CANSparkMax(Ports.CAN_ID_INTAKE, MotorType.kBrushless);
        intakeDeployMotor = new CANSparkMax(Ports.CAN_ID_INTAKE_DEPLOY, MotorType.kBrushless);
        
        intakeMotor.restoreFactoryDefaults();
        intakeDeployMotor.restoreFactoryDefaults();

        intakeMotor.setIdleMode(IdleMode.kCoast);
        intakeDeployMotor.setIdleMode(IdleMode.kBrake);
        
        intakeMotor.setSmartCurrentLimit(20);
        intakeDeployMotor.setSmartCurrentLimit(35);

        SmartDashboard.putNumber("intakeMotor", 0.1);
        SmartDashboard.putNumber("intakeDeployMotor", 0.1);


    }
    public void stop()
    {
        intakeMotor.set(0);
    }
    public void grabBall()
    {
        intakeSpeed = Speeds.GET_BALL_SPEED;
    }
    public void ejectBall()
    {
        intakeSpeed = Speeds.EJECT_BALL_SPEED;
    }
    public void extendIntake()
    {
        armUpRequested = false;
        intakeDeploySpeed = Speeds.INTAKE_DEPLOY_DOWN_SPEED;
        //intakeDeployMotor.set(Speeds.INTAKE_DEPLOY_DOWN_SPEED);
        m_deployTarget = 1000;
    }
    public void stopDeploy()
    {
        intakeDeploySpeed = Speeds.INTAKE_DEPLOY_STOP_SPEED;
        //intakeDeployMotor.set(Speeds.INTAKE_DEPLOY_STOP_SPEED);
    }
    public void retractIntake()
    {
        armUpRequested = true;
        intakeDeploySpeed = Speeds.INTAKE_DEPLOY_UP_SPEED;
        //intakeDeployMotor.set(Speeds.INTAKE_DEPLOY_UP_SPEED);
        m_deployTarget = 0;
    }
    public void toggleIntake()
    {
        armUpRequested = !armUpRequested;
    }
    public boolean isDone()
    {
        return armUp == armUpRequested;
    }
    public void periodic(){
        updateDashboard();
        intakeMotor.set(intakeSpeed);
        if (armUp!=armUpRequested)
        {
            if (armUpRequested)
            {
                intakeDeploySpeed = Speeds.INTAKE_DEPLOY_UP_SPEED;
            }
            else {
                intakeDeploySpeed = Speeds.INTAKE_DEPLOY_DOWN_SPEED;
            }
        }
        if (intakeDeployMotor.getOutputCurrent()>Constants.INTAKE_DEPLOY_CURRENT_LIMIT)
        {
            System.out.println("Reached the end");
            intakeDeploySpeed = 0;
            armUp = armUpRequested;
        }
        intakeDeployMotor.set(intakeDeploySpeed);
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INTAKE)
        {
            SmartDashboard.putNumber("Intake Speed", intakeMotor.get());
            SmartDashboard.putNumber("Intake Deploy Speed", intakeDeployMotor.get());
            SmartDashboard.putNumber("Intake Deploy Velocity", intakeDeployMotor.getAnalog(Mode.kRelative).getVelocity());
            SmartDashboard.putNumber("Intake Deploy Current", intakeDeployMotor.getOutputCurrent());
        }
    }

}
