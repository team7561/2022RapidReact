package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Climber extends SubsystemBase {
    TalonFX climberMotorA;
    TalonFX climberMotorB;
    VictorSPX climberDeployMotor;

    public Climber()
    {
        climberMotorA = new TalonFX(Ports.CLIMB_WINCH_A_CANID);
        climberMotorB = new TalonFX(Ports.CLIMB_WINCH_B_CANID);
        climberMotorA.configFactoryDefault();
        climberMotorB.configFactoryDefault();
        climberMotorB.follow(climberMotorA);
        climberMotorA.setNeutralMode(NeutralMode.Brake);
        /*climberMotorA.configContinuousCurrentLimit(10, 0);
        talon.configPeakCurrentLimit(15, 0);
        talon.configPeakCurrentDuration(100, 0);
        talon.enableCurrentLimit(true);*/

        climberDeployMotor = new VictorSPX(Ports.CLIMB_DEPLOY_B_CANID); //22

        climberDeployMotor.configOpenloopRamp(1);

        climberDeployMotor.configFactoryDefault();

        climberDeployMotor.setNeutralMode(NeutralMode.Coast);

    }
    private void setWinchSpeed(double speed)
    {
        climberMotorA.set(ControlMode.PercentOutput, speed);
    }
    public void climb()
    {
        setWinchSpeed(Speeds.CLIMBER_LIFT_SPEED);
    }
    public void climbReverse()
    {
        setWinchSpeed(Speeds.CLIMBER_LOWER_SPEED);
    }
    public void raiseHook()
    {
        climberDeployMotor.configOpenloopRamp(0.5);
        climberDeployMotor.set(ControlMode.PercentOutput, Speeds.CLIMBER_HOOK_RAISE_SPEED);
    }
    public void lowerHook()
    {
        climberDeployMotor.configOpenloopRamp(0.5);
        climberDeployMotor.set(ControlMode.PercentOutput, Speeds.CLIMBER_HOOK_LOWER_SPEED);
    }
    public void stopClimbing()
    {
        setWinchSpeed(Speeds.CLIMBER_STOP_SPEED);
    }
    public void stop()
    {
        
        setWinchSpeed(Speeds.CLIMBER_STOP_SPEED);
        climberDeployMotor.configOpenloopRamp(0.0);
        climberDeployMotor.set(ControlMode.PercentOutput, 0); 
       }
    public void updateDashboard()
    {
        if (Constants.DEBUG_CLIMBER)
            {
            SmartDashboard.putNumber("Climber Motor Speed", climberMotorA.getMotorOutputPercent());
            SmartDashboard.putNumber("Climber Motor Current", climberMotorA.getStatorCurrent());
            //SmartDashboard.putNumber("Climber Deploy Motor A Current", climberDeployMotor.getStatorCurrent());
            //SmartDashboard.putNumber("Climber Deploy Motor B Current", climberDeployMotorB.getStatorCurrent());
            SmartDashboard.putNumber("Climber Deploy Motor Speed", climberDeployMotor.getMotorOutputPercent());
            //SmartDashboard.putNumber("Climber Deploy Motor B Speed", climberDeployMotorB.getMotorOutputPercent());
        }
    }
}