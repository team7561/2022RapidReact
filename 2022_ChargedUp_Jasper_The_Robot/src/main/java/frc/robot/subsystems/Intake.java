package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAnalogSensor.Mode;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;
import frc.robot.IntakeMode;


public class Intake extends SubsystemBase{
    private CANSparkMax intakeMotor;
    private CANSparkMax intakeDeployMotor;

    public boolean armUp = false;
    public boolean armUpRequested = true;
    public boolean intakeRequested = false;
    public boolean reverse = false;
    public double intakeSpeed = 0;
    public double intakeDeploySpeed = 0;
    Timer timer;

    public IntakeMode m_mode = IntakeMode.INTAKE_RETRACT_REQUESTED;

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

        timer = new Timer();

        timer.reset();
        timer.start();
    }

    public IntakeMode getMode(){
        return m_mode;
    }

    public void setMode(IntakeMode mode){
        m_mode = mode;
    }

    public void resetTimer(){
        timer.reset();
        timer.start();
    }

    public void stop()
    {
        intakeRequested = false;
        reverse = false;
        intakeSpeed = 0;
    }
    public void hold()
    {
        intakeDeploySpeed = Speeds.INTAKE_DEPLOY_HOLD_DOWN_SPEED;
    }
    public void grabBall()
    {
        if(!armUp){
            intakeSpeed = Speeds.GET_BALL_SPEED;
        }
        else {
            intakeMotor.set(0);
        }
    }
    public void ejectBall()
    {
        if(!armUp){
            intakeSpeed = Speeds.EJECT_BALL_SPEED;
        }
        else {
            intakeMotor.set(0);
        }
    }
    public void extendIntake()
    {
        armUpRequested = false;
        intakeDeploySpeed = Speeds.INTAKE_DEPLOY_DOWN_SPEED;
        //intakeDeployMotor.set(Speeds.INTAKE_DEPLOY_DOWN_SPEED);
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
    }
    public void toggleIntake()
    {
        intakeMotor.set(0);
        armUpRequested = !armUpRequested;
    }
    public boolean isDone()
    {
        return armUp == armUpRequested;
    }
    public void periodic(){
        //updateDashboard();
        SmartDashboard.putString("Intake Mode", getMode().name());

        if(getMode() == IntakeMode.INTAKE_RETRACT_REQUESTED){
            intakeDeploySpeed = Speeds.INTAKE_DEPLOY_UP_SPEED;
            intakeRequested = false;
            reverse = false;
            stop();
            if(intakeDeployMotor.getOutputCurrent() > Constants.INTAKE_DEPLOY_CURRENT_LIMIT + 2 && timer.get() > 0.5){
                System.out.println("Intake Retracted");
                setMode(IntakeMode.INTAKE_RETRACTED);
            }
        }

        else if(getMode() == IntakeMode.INTAKE_RETRACTED){
            intakeDeploySpeed = Speeds.INTAKE_DEPLOY_HOLD_UP_SPEED;
            stop();
        }

        if(getMode() == IntakeMode.INTAKE_DEPLOY_REQUESTED){
            intakeDeploySpeed = Speeds.INTAKE_DEPLOY_DOWN_SPEED;
            if(intakeRequested){
                grabBall();
            } else {
                stop();
            }
            if(intakeDeployMotor.getOutputCurrent() > Constants.INTAKE_DEPLOY_CURRENT_LIMIT && timer.get() > 0.5){
                System.out.println("Intake Deployed");
                setMode(IntakeMode.INTAKE_DEPLOYED);
            }
        }

        else if(getMode() == IntakeMode.INTAKE_DEPLOYED){
            intakeDeploySpeed = Speeds.INTAKE_DEPLOY_HOLD_DOWN_SPEED;
            if(intakeRequested){
                if (!reverse) {
                    grabBall();
                }
                else {
                    ejectBall();
                }
            } else {
                stop();
            }
        }

        intakeMotor.set(intakeSpeed);
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