package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;


public class Shooter extends SubsystemBase{
    double m_Atarget, m_Btarget;

    CANSparkMax shooterMotorA;
    CANSparkMax shooterMotorB;
    CANSparkMax shooterHood;

    private CANPIDController m_ApidController;
    private CANPIDController m_BpidController;
    //Hood Controller

    //private CANEncoder m_AEncoder;
    //private CANEncoder m_BEncoder;
    // Hood Encoder

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, m_setpoint;
    public double m_hood_position, m_hood_setpoint;

    public Shooter(){
        // PID coefficients
        kP = 0.003; 
        kI = 0.000002;
        kD = 0.000004; 
        kIz = 500; // Error process value must be within before I is used.
        kFF = 0; 
        m_setpoint = 2200;
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
    }

    public void periodic(){
        m_ApidController.setReference(m_Atarget, ControlType.kVelocity);
        m_BpidController.setReference(m_Btarget, ControlType.kVelocity);
    }

    public void setMotorA(double target){
        m_Atarget = target;
    }

    public void setMotorB(double target){
        m_Btarget = target;
    }
}
