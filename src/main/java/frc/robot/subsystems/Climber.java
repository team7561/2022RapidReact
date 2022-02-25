package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;


public class Climber extends SubsystemBase{

    TalonFX climberMotorA, climberMotorB;

    public Climber(){
        climberMotorA = new TalonFX(Ports.CAN_ID_CLIMBER_A);
        climberMotorB = new TalonFX(Ports.CAN_ID_CLIMBER_B);
        
        climberMotorA.configFactoryDefault();
        climberMotorB.configFactoryDefault();
        
        climberMotorA.configOpenloopRamp(0.5);
        climberMotorB.configOpenloopRamp(0.5);

        climberMotorA.setNeutralMode(NeutralMode.Brake);
        climberMotorB.setNeutralMode(NeutralMode.Brake);

    }
    public void climb()
    {
        System.out.println("Climb");
        climberMotorA.set(TalonFXControlMode.PercentOutput, Speeds.CLIMBER_LIFT_SPEED);
        climberMotorB.set(TalonFXControlMode.PercentOutput, -Speeds.CLIMBER_LIFT_SPEED);
    }
    public void climbDeploy()
    {
        System.out.println("Deploy Climb");
        climberMotorA.set(TalonFXControlMode.PercentOutput, Speeds.CLIMBER_DEPLOY_SPEED);
        climberMotorB.set(TalonFXControlMode.PercentOutput, -Speeds.CLIMBER_DEPLOY_SPEED);
    }
    public void climbReverse()
    {
        System.out.println("Reverse Climb");
        climberMotorA.set(TalonFXControlMode.PercentOutput, Speeds.CLIMBER_REVERSE_SPEED);
        climberMotorB.set(TalonFXControlMode.PercentOutput, -Speeds.CLIMBER_REVERSE_SPEED);
    }
    public void stop()
    {
        //System.out.println("Stop Climbing");
        climberMotorA.configOpenloopRamp(0);
        climberMotorB.configOpenloopRamp(0);
        climberMotorA.set(TalonFXControlMode.PercentOutput, Speeds.CLIMBER_STOP_SPEED);
        climberMotorB.set(TalonFXControlMode.PercentOutput, -Speeds.CLIMBER_STOP_SPEED);
    }
    public void periodic(){
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_CLIMBER)
        {
            SmartDashboard.putNumber("Climber A Speed", climberMotorA.getMotorOutputPercent());
            SmartDashboard.putNumber("Climber B Speed", climberMotorB.getMotorOutputPercent());
        }
    }
}
