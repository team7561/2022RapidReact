package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeMotorFront, intakeMotorRear;

  public Intake() {
    intakeMotorFront = new CANSparkMax(Ports.Intake_Front_ID, MotorType.kBrushless);
    intakeMotorRear = new CANSparkMax(Ports.Intake_Rear_ID, MotorType.kBrushless);
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

  public void periodic() {
    updateDashboard();
    if (intakeMotorFront.getOutputCurrent() > 15)
    {
      SmartDashboard.putBoolean("Holding Note", true);
    }
    if (Constants.AUTO_MODE) {
      if (SmartDashboard.getBoolean("Holding Note", true)) {
        stop();
      }
      else {
        grab();
      }
    }
  }

  public void updateDashboard() {
    SmartDashboard.putNumber("Intake Front Speed", intakeMotorFront.get());
    SmartDashboard.putNumber("Intake Rear Speed", intakeMotorRear.get());
    SmartDashboard.putNumber("Intake Front Current", intakeMotorFront.getOutputCurrent());
    SmartDashboard.putNumber("Intake Rear Current", intakeMotorRear.getOutputCurrent());
  }
}
