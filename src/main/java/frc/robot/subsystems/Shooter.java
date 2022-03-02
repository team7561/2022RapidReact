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
    boolean shooting, RPMcontrol, autoSetpointControl;

    private SparkMaxPIDController m_ApidController;
    private SparkMaxPIDController m_BpidController;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
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
        kP = 0.007; 
        kI = 0.0000015;
        kD = 0.000002;
        kIz = 500; // Error process value must be within before I is used.
        kFF = 0; 
        m_setpoint = 0;
        m_hood_setpoint = 0.2;
        kMaxOutput = 0.85; 
        kMinOutput = -0.85;
        maxRPM = 4500;

        // set PID coefficients
        m_ApidController.setP(kP);
        m_ApidController.setI(kI);
        m_ApidController.setD(kD);
        m_ApidController.setIZone(kIz);
        m_ApidController.setFF(kFF);
        m_ApidController.setOutputRange(kMinOutput, kMaxOutput);

        // set PID coefficients
        m_BpidController.setP(kP);
        m_BpidController.setI(kI);
        m_BpidController.setD(kD);
        m_BpidController.setIZone(kIz);
        m_BpidController.setFF(kFF);
        m_BpidController.setOutputRange(kMinOutput, kMaxOutput);

        m_shooterMultiplier = 0;
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
        RPMcontrol = false;
        m_shooterMultiplier = 0;
    }
    public void start()
    {
        RPMcontrol = true;
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
        m_Atarget = a;
        m_Btarget = b;
        SmartDashboard.putNumber("Shooter A Setpoint", m_Atarget);
        SmartDashboard.putNumber("Shooter B Setpoint", m_Btarget);
    }
    public void periodic(){
        m_angle = SmartDashboard.getNumber("ShooterHood", 0.2);
        shooterServoA.set(1-m_angle);
        shooterServoB.set(m_angle);

        m_Atarget = SmartDashboard.getNumber("Shooter A Setpoint", 0);
        m_Btarget = SmartDashboard.getNumber("Shooter B Setpoint", 0);
        if (shooting)
        {
            if (RPMcontrol)
            {
                m_ApidController.setReference(m_Atarget * m_shooterMultiplier, CANSparkMax.ControlType.kVelocity);
                m_BpidController.setReference(m_Btarget * m_shooterMultiplier, CANSparkMax.ControlType.kVelocity);
            }
            else
            {
                shooterMotorA.set(0);
                shooterMotorB.set(0);

            }
        }
        //shooterHood.set(m_hood_setpoint);
        updateDashboard();
        if (autoSetpointControl)
        {
            double distance = 5;
            set_RPM(1000+distance*100, 700+distance*100);
            setHood(distance/10);
        }
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_SHOOTER)
        {
            SmartDashboard.putNumber("Shooter A Voltage", shooterMotorA.get());
            SmartDashboard.putNumber("Shooter A Speed", -shooterMotorA.getEncoder().getVelocity());
            SmartDashboard.putNumber("Shooter B Voltage", shooterMotorB.get());
            SmartDashboard.putNumber("Shooter B Speed", -shooterMotorB.getEncoder().getVelocity());
            SmartDashboard.putNumber("Shooter A Setpoint", m_Atarget);
            SmartDashboard.putNumber("Shooter B Setpoint", m_Btarget);
            SmartDashboard.putNumber("ShooterHood", m_angle);
        }
    }
}
