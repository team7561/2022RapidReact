package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private DoubleSolenoid intakeOpenSolenoid;
  private AnalogInput ultrasonicSensor;

  public Intake() {

    intakeOpenSolenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 0, 2);
    addChild("intakeDeploySolenoid", intakeOpenSolenoid);
    intakeOpenSolenoid.set(Value.kForward);

    ultrasonicSensor = new AnalogInput(3);
  }

  @Override
  public void periodic() {
    updateDashboard();
    /*if (ultrasonicSensor.getAverageVoltage()>3)
    {
        intakeOpenSolenoid.set(Value.kReverse);
    }*/

  }

  public void open() {
    intakeOpenSolenoid.set(Value.kForward);
  }

  public void close() {
    intakeOpenSolenoid.set(Value.kReverse);
  }

  public void toggle() {
    if (intakeOpenSolenoid.get().equals(Value.kForward)) {
      intakeOpenSolenoid.set(Value.kReverse);
    } else {
      intakeOpenSolenoid.set(Value.kForward);
    }
  }

  public void updateDashboard() {
    SmartDashboard.putBoolean(
        "Intake Open", intakeOpenSolenoid.get().compareTo(Value.kForward) == 1);
    SmartDashboard.putNumber("Ultrasonic Distance", ultrasonicSensor.getAverageVoltage());
  }
}
