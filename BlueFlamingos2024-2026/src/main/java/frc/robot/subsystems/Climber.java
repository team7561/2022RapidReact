package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Ports;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    
DoubleSolenoid climberSolenoid;
SparkMax climberA, climberB;
boolean robotClimbing;
SparkMaxConfig globalConfig = new SparkMaxConfig();

  public Climber() {
    
    climberSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Ports.Climber_Cylinder_A, Ports.Climber_Cylinder_B);
    climberA = new SparkMax(Ports.ClimberA_ID, MotorType.kBrushless);
    climberB = new SparkMax(Ports.ClimberB_ID, MotorType.kBrushless);
    globalConfig.smartCurrentLimit(10).idleMode(IdleMode.kBrake);
    climberA.configure(globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    climberB.configure(globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    retract();
    robotClimbing = true;
  }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climber Speed", climberA.get());
        SmartDashboard.putBoolean("Robot Climbing?", robotClimbing);
        SmartDashboard.putBoolean("Cylinder Extended?", climberSolenoid.get()==Value.kForward);
    }

    public void setSpeed(double speed) {
        climberA.set(speed);
        climberB.set(speed);
    }

    public void stop(){
        setSpeed(0);
    }

    public void raise() {
        if (climberSolenoid.get() == Value.kForward)
        {
            System.out.println("Not retracting, pneumatic cylinder is up");
        }
        else {
        setSpeed(0.7);
        robotClimbing = true;
        }
    }
    public void lower() {
        setSpeed(-0.2);
        robotClimbing = true;
    }

    public void extend() {
        climberSolenoid.set(Value.kForward);
        robotClimbing = false;
    }
    public void retract() {
        climberSolenoid.set(Value.kReverse);
        robotClimbing = true;
    }
}
