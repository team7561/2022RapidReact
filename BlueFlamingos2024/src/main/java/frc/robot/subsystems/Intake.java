package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Ports;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeMotorFront, intakeMotorRear;

  public Intake() {
    intakeMotorFront = new CANSparkMax(Ports.Intake_Front_ID, MotorType.kBrushless);
    intakeMotorRear = new CANSparkMax(Ports.Intake_Rear_ID, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    updateDashboard();
  }

  public void grab() {
    intakeMotorFront.set(0.2);
    intakeMotorRear.set(0.2);
  }
  public void stop() {
    intakeMotorRear.set(0);
    intakeMotorFront.set(0);
  }
  
  public void reverse() {
    intakeMotorFront.set(-0.2);
    intakeMotorRear.set(-0.2);
  }

  public void updateDashboard() {
    SmartDashboard.putNumber("Intake Front Speed", intakeMotorFront.get());
    SmartDashboard.putNumber("Intake Rear Speed", intakeMotorRear.get());
    SmartDashboard.putNumber("Intake Front Current", intakeMotorFront.getOutputCurrent());
    SmartDashboard.putNumber("Intake Rear Current", intakeMotorRear.getOutputCurrent());
  }
}
