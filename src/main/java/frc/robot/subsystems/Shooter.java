package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;


public class Shooter extends SubsystemBase{
    double m_Atarget, m_Btarget, m_angle, m_shooterMultiplier;

    CANSparkMax shooterMotorA;
    CANSparkMax shooterMotorB;
    Servo shooterServoA, shooterServoB; 
    public boolean shooting, RPMcontrol, autoSetpointControl;

    private SparkMaxPIDController m_ApidController;
    private SparkMaxPIDController m_BpidController;

    public double kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double kP_shooterMotorA, kI_shooterMotorA, kD_shooterMotorA;
    public double kP_shooterMotorB, kI_shooterMotorB, kD_shooterMotorB;
    public double m_hood_position, m_hood_setpoint;

    public Shooter(){
        shooting = true;
        RPMcontrol = true;
        autoSetpointControl = false;
        shooterServoA = new Servo(Ports.PWM_SHOOTER_HOOD_LEFT_SERVO);
        shooterServoB = new Servo(Ports.PWM_SHOOTER_HOOD_RIGHT_SERVO);
        shooterMotorA = new CANSparkMax(Ports.CAN_ID_SHOOTER_A, MotorType.kBrushless);
        shooterMotorB = new CANSparkMax(Ports.CAN_ID_SHOOTER_B, MotorType.kBrushless);
        //shooterHood = new Servo(Ports.PWM_SHOOTER_HOOD_SERVO);
        
        m_ApidController = shooterMotorA.getPIDController();
        m_BpidController = shooterMotorB.getPIDController();
        shooterMotorA.restoreFactoryDefaults();
        shooterMotorB.restoreFactoryDefaults();

        shooterMotorA.setIdleMode(IdleMode.kCoast);
        shooterMotorB.setIdleMode(IdleMode.kCoast);
        
        shooterMotorA.setSmartCurrentLimit(45);
        shooterMotorB.setSmartCurrentLimit(45);

        // PID coefficients
        kP_shooterMotorA = 0.00172; 
        kI_shooterMotorA = 0.00000;
        kD_shooterMotorA = 0.0036;
        kP_shooterMotorB = 0.0015; 
        kI_shooterMotorB = 0.0000;
        kD_shooterMotorB = 0.015 ;
        kIz = 400; // Error process value must be within before I is used.
        kFF = 0.0002; 
        m_setpoint = 0;
        m_hood_setpoint = 0.2;
        kMaxOutput = 0.85; 
        kMinOutput = -0.85;
        maxRPM = 4500;

        // set PID coefficients
        m_ApidController.setP(kP_shooterMotorA);
        m_ApidController.setI(kI_shooterMotorA);
        m_ApidController.setD(kD_shooterMotorA);
        m_ApidController.setIZone(kIz);
        m_ApidController.setFF(kFF);
        m_ApidController.setOutputRange(kMinOutput, kMaxOutput);

        // set PID coefficients
        m_BpidController.setP(kP_shooterMotorB);
        m_BpidController.setI(kI_shooterMotorB);
        m_BpidController.setD(kD_shooterMotorB);
        m_BpidController.setIZone(kIz);
        m_BpidController.setFF(kFF);
        m_BpidController.setOutputRange(kMinOutput, kMaxOutput);

        m_shooterMultiplier = 0;


/*
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP_shooterMotorA);
        SmartDashboard.putNumber("I Gain", kI_shooterMotorA);
        SmartDashboard.putNumber("D Gain", kD_shooterMotorA);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

*/

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP_shooterMotorA);
        SmartDashboard.putNumber("I Gain", kI_shooterMotorA);
        SmartDashboard.putNumber("D Gain", kD_shooterMotorA);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);



    }

    public void setMotorA(double target){
        m_Atarget = target;
    }
    public void setMotorB(double target){
        m_Btarget = target;
    }

    public void setHood(double angle){
        m_angle = angle;
        SmartDashboard.putNumber("ShooterHood", m_angle);
    }

    public void start_auto_hood()
    {
        autoSetpointControl = true;
    }
    public void stop_auto_hood()
    {
        autoSetpointControl = false;
    }
    public void fullyRetractHood()
    {
        m_angle = 0;
    }
    public void fullyExtendHood()
    {
        m_angle = 1;
    }
    public void stop()
    {
        shooting = false;
        m_shooterMultiplier = 0;
    }
    public void start()
    {
        shooting = true;
        m_shooterMultiplier = -1;
    }
    public boolean hood_at_setpoint()
    {
        return false;
    }
    public int getVelocity()
    {
        return 0;
    }
    public void start_auto_vision_speed()
    {

    }
    public void stop_auto_vision_speed()
    {

    }
    public void set_voltage(double a, double b)
    {
        RPMcontrol = false;
        m_Atarget = a;
        m_Btarget = b;
        SmartDashboard.putNumber("Shooter A Setpoint", m_Atarget);
        SmartDashboard.putNumber("Shooter B Setpoint", m_Btarget);
    }
    public void set_RPM(double a, double b)
    {
        RPMcontrol = true;
        m_Atarget = 2*a;
        m_Btarget = 2*b;
        SmartDashboard.putNumber("Shooter A Setpoint", m_Atarget);
        SmartDashboard.putNumber("Shooter B Setpoint", m_Btarget);
    }

    public boolean atSetpoint()
    {
        return  -shooterMotorA.getEncoder().getVelocity() > 0 && 
        Math.abs(-shooterMotorA.getEncoder().getVelocity() - m_Atarget) < Constants.SHOOTER_TOLERANCE &&
        Math.abs((-shooterMotorB.getEncoder().getVelocity()) - m_Btarget) < Constants.SHOOTER_TOLERANCE;
    }

    public void periodic(){
        m_angle = SmartDashboard.getNumber("ShooterHood", 0.58);
        shooterServoA.set(1-m_angle);
        shooterServoB.set(m_angle);

        m_Atarget = SmartDashboard.getNumber("Shooter A Setpoint", 0);
        m_Btarget = SmartDashboard.getNumber("Shooter B Setpoint", 0);

        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        /*
        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP_shooterMotorA)) { m_ApidController.setP(p); kP_shooterMotorA = p; }
        if((i != kI_shooterMotorA)) { m_ApidController.setI(i); kI_shooterMotorA = i; }
        if((d != kD_shooterMotorA)) { m_ApidController.setD(d); kD_shooterMotorA = d; }
        if((iz != kIz)) { m_ApidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_ApidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_ApidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
        }   
        */    
        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP_shooterMotorA)) { m_ApidController.setP(p); kP_shooterMotorA = p; }
        if((i != kI_shooterMotorA)) { m_ApidController.setI(i); kI_shooterMotorA = i; }
        if((d != kD_shooterMotorA)) { m_ApidController.setD(d); kD_shooterMotorA = d; }
        if((iz != kIz)) { m_ApidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_ApidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
            m_ApidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max; 
        }

        
        if (shooting)
        {
            if (RPMcontrol && (m_Atarget > 25 || m_Btarget > 25))
            {
                m_ApidController.setReference(m_Atarget * m_shooterMultiplier, CANSparkMax.ControlType.kVelocity);
                m_BpidController.setReference(m_Btarget * m_shooterMultiplier, CANSparkMax.ControlType.kVelocity);
            }
        }
            else
            {
                shooterMotorA.set(0);
                shooterMotorB.set(0);
            }
        
        SmartDashboard.putBoolean("Shooter At Setpoint", atSetpoint());
        //shooterHood.set(m_hood_setpoint);
        updateDashboard();
    }
    public void updateDashboard()
    {
            
        if (Constants.DEBUG_SHOOTER)
        {
            SmartDashboard.putNumber("Shooter A Voltage", shooterMotorA.getAppliedOutput());
            SmartDashboard.putNumber("Shooter A Speed", -shooterMotorA.getEncoder().getVelocity());
            SmartDashboard.putNumber("Shooter A Temp", shooterMotorA.getMotorTemperature());
            SmartDashboard.putNumber("Shooter A Current", shooterMotorA.getOutputCurrent());

            SmartDashboard.putNumber("Shooter B Voltage", shooterMotorB.getAppliedOutput());
            SmartDashboard.putNumber("Shooter B Speed", -shooterMotorB.getEncoder().getVelocity());
            SmartDashboard.putNumber("Shooter B Current", shooterMotorB.getOutputCurrent());
            SmartDashboard.putNumber("Shooter B Temp", shooterMotorB.getMotorTemperature());
            
            SmartDashboard.putNumber("Shooter A Setpoint", m_Atarget);
            SmartDashboard.putNumber("Shooter B Setpoint", m_Btarget);
            SmartDashboard.putNumber("ShooterHood", m_angle);
        }
    }
}
