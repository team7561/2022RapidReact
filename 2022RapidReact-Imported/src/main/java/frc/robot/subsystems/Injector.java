package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

import frc.robot.Ports;

public class Injector extends SubsystemBase{
    double m_deployTarget;

    CANSparkMax injectorMotor;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Injector(){
        injectorMotor = new CANSparkMax(Ports.CAN_ID_INJECTOR, MotorType.kBrushless);
        
        injectorMotor.restoreFactoryDefaults();
        injectorMotor.setIdleMode(IdleMode.kCoast);
        injectorMotor.setSmartCurrentLimit(20);
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
    {
        if (Constants.DEBUG_INJECTOR)
        {
        SmartDashboard.putNumber("Injector Speed", injectorMotor.get());
        }
    }
}
