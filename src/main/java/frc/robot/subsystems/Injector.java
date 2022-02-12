package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;
import frc.robot.InjectorMode;

public class Injector extends SubsystemBase{
    double m_deployTarget;
    CANSparkMax injectorMotor;
    public double m_hood_position, m_hood_setpoint;

    public InjectorMode m_mode = InjectorMode.INJECTOR_STOP;

    public Injector(){
        injectorMotor = new CANSparkMax(Ports.CAN_ID_INJECTOR, MotorType.kBrushless);
        
        injectorMotor.restoreFactoryDefaults();
        injectorMotor.setIdleMode(IdleMode.kCoast);
        injectorMotor.setSmartCurrentLimit(20);
        resetEncoder();
    }

    public void setMode(InjectorMode mode){
        m_mode = mode;
    }

    public InjectorMode getMode(){
        return m_mode;
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
        injectorMotor.set(Speeds.INJ_Reverse_SPEED);
    }
    public void stop()
    {
        injectorMotor.set(Speeds.INJ_stop_SPEED);
    }
    public void periodic(){
        if(m_mode == InjectorMode.INJECTOR_FORWARD) {
            forward();
        }
  
        if(m_mode == InjectorMode.INJECTOR_REVERSE) {
            reverse();
        }
  
        if(m_mode == InjectorMode.INJECTOR_INDEX_BALL){
            if(getEncoderCount() < Constants.INJECTOR_CARGO_INDEX_PULSE_COUNT){
                forward();
            } else {
                setMode(InjectorMode.INJECTOR_STOP);
            }
        }
  
        if(m_mode == InjectorMode.INJECTOR_REVERSE_INDEX_BALL){
            if(getEncoderCount() > -Constants.INJECTOR_CARGO_INDEX_PULSE_COUNT){
                forward();
            } else {
                setMode(InjectorMode.INJECTOR_STOP);
            }
        }
  
        if(m_mode == InjectorMode.INJECTOR_STOP){
            stop();
        }
        
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