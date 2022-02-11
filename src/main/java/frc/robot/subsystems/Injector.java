package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;

import frc.robot.Ports;
import frc.robot.Speeds;

public class Injector extends SubsystemBase{
    double m_deployTarget;

    CANSparkMax injectorMotor;

    public double m_hood_position, m_hood_setpoint;

    public Injector(){
        injectorMotor = new CANSparkMax(Ports.CAN_ID_INJECTOR, MotorType.kBrushless);
        
        injectorMotor.restoreFactoryDefaults();
        injectorMotor.setIdleMode(IdleMode.kCoast);
        injectorMotor.setSmartCurrentLimit(20);
        resetEncoder();
    }
    public void transferBall()
    {
        injectorMotor.set(Speeds.INJECTOR_TRANSFER_SPEED);
    }
    public void forward()
    {
        injectorMotor.set(Speeds.INJECTOR_TRANSFER_SPEED);
    }
    public void reverse()
    {
        injectorMotor.set(Speeds.INJECTOR_REVERSE_SPEED);
    }
    public void stop()
    {
        injectorMotor.set(Speeds.INJECTOR_STOP_SPEED);
    }
    public void periodic(){
        updateDashboard();
    }
    public void resetEncoder()
    {
        injectorMotor.getEncoder().setPosition(0);
    }
    public double getEncoderCount()
    {
        return injectorMotor.getEncoder().getPosition();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INJECTOR)
        {
            SmartDashboard.putNumber("Injector Speed", injectorMotor.get());
            SmartDashboard.putNumber("Injector Encoder Count", getEncoderCount());
        }
    }
}
