package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import frc.robot.Ports;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    
DoubleSolenoid climberSolenoid;
CANSparkMax climberA, climberB;

  public Climber() {
    
    climberSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Ports.Climber_Cylinder_A, Ports.Climber_Cylinder_B);
    climberA = new CANSparkMax(Ports.ClimberA_ID, MotorType.kBrushless);
    climberB = new CANSparkMax(Ports.ClimberB_ID, MotorType.kBrushless);
    climberA.setIdleMode(IdleMode.kBrake);
    climberB.setIdleMode(IdleMode.kBrake);
    climberA.setSmartCurrentLimit(10);
    climberB.setSmartCurrentLimit(10);
  }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climber Speed", climberA.get());
        SmartDashboard.putString("Climber A Serial", climberA.getSerialNumber().toString());
        SmartDashboard.putString("Climber B Serial", climberB.getSerialNumber().toString());
    }

    public void setSpeed(double speed) {
        climberA.set(speed);
        climberB.set(speed);
    }

    public void stop(){
        setSpeed(0);
    }

    public void raise() {
        setSpeed(0.35);
        retract();
    }
    public void lower() {
        setSpeed(-0.2);
    }


    public void extend() {
        climberSolenoid.set(Value.kForward);
    }
    public void retract() {
        climberSolenoid.set(Value.kReverse);
    }
}
