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
//import frc.robot.LIDARSensor;

public class Injector extends SubsystemBase{
    double m_deployTarget;
    CANSparkMax injectorMotor;
    //public double m_distance;
    public boolean balls;
    //public LIDARSensor lidarSensor;
    public InjectorMode m_mode = InjectorMode.INJECTOR_STOP;

    public Injector(){
        injectorMotor = new CANSparkMax(Ports.CAN_ID_INJECTOR, MotorType.kBrushless);
        
        //lidarSensor = new LIDARSensor();
        //lidarSensor.LIDARSensorInit(Ports.DIO_PORT_LIDAR);

        injectorMotor.restoreFactoryDefaults();
        injectorMotor.setIdleMode(IdleMode.kBrake);
        injectorMotor.setSmartCurrentLimit(20);
        resetEncoder();
        balls = false;
    }

    public void setMode(InjectorMode mode){
        m_mode = mode;
    }

    public InjectorMode getMode(){
        return m_mode;
    }

    public double getSpeed(){
        return injectorMotor.getEncoder().getVelocity();
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
        //m_distance = lidarSensor.getDistance();
        if(m_mode == InjectorMode.INJECTOR_FORWARD) {
            balls = true;
            forward();
        }
  
        if(m_mode == InjectorMode.INJECTOR_REVERSE) {
            balls = true;
            reverse();
        }
        /*if(m_mode == InjectorMode.INJECTOR_PREPARE_BALL) {
            balls = true;
            if (m_distance > 5)  {
                forward();
            }
            else    {
                stop();
            }
        }*/
  
        if(m_mode == InjectorMode.INJECTOR_INDEX_BALL){
            if(
                Math.abs(SmartDashboard.getNumber("Shooter A Speed", 0) - SmartDashboard.getNumber("Shooter A Setpoint", 0)) < Constants.SHOOTER_TOLERANCE &&
                Math.abs(SmartDashboard.getNumber("Shooter B Speed", 0) - SmartDashboard.getNumber("Shooter B Setpoint", 0)) < Constants.SHOOTER_TOLERANCE
            ){
                balls = true;
                forward();
            } else {
                balls = false;
                stop();
            }
        }
  
        if(m_mode == InjectorMode.INJECTOR_STOP){
            balls = false;
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
            SmartDashboard.putString("Injector Mode", m_mode.name());
            //SmartDashboard.putNumber("Injector Distance", m_distance);
        }
    }
}