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


public class Climber extends SubsystemBase{

    CANSparkMax climberMotorA, climberMotorB;

    public Climber(){
        climberMotorA = new CANSparkMax(Ports.CAN_ID_CLIMBER_A, MotorType.kBrushless);
        climberMotorB = new CANSparkMax(Ports.CAN_ID_CLIMBER_B, MotorType.kBrushless);
        
        climberMotorA.restoreFactoryDefaults();
        climberMotorB.restoreFactoryDefaults();

        climberMotorA.setIdleMode(IdleMode.kBrake);
        climberMotorB.setIdleMode(IdleMode.kBrake);
        
        climberMotorA.setSmartCurrentLimit(5);
        climberMotorB.setSmartCurrentLimit(5);

    }
    public void climb()
    {
        climberMotorA.set(Speeds.CLIMBER_LIFT_SPEED);
        climberMotorB.set(Speeds.CLIMBER_LIFT_SPEED);
    }
    public void climbDeploy()
    {
        climberMotorA.set(Speeds.CLIMBER_DEPLOY_SPEED);
        climberMotorB.set(Speeds.CLIMBER_DEPLOY_SPEED);
    }
    public void climbReverse()
    {
        climberMotorA.set(Speeds.CLIMBER_REVERSE_SPEED);
        climberMotorB.set(Speeds.CLIMBER_REVERSE_SPEED);
    }
    public void stop()
    {
        climberMotorA.set(Speeds.CLIMBER_STOP_SPEED);
        climberMotorB.set(Speeds.CLIMBER_STOP_SPEED);
    }
    public void periodic(){
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_CLIMBER)
        {
            SmartDashboard.putNumber("Climber A Speed", climberMotorA.get());
            SmartDashboard.putNumber("Climber B Speed", climberMotorB.get());
        }
    }
}
