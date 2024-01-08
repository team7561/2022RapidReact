package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Random;

public class LED_Controller extends SubsystemBase {
  private Spark led_controller;

  public LED_Controller() {

    led_controller = new Spark(9);
    SmartDashboard.putNumber("LED Pattern", -0.89);
  }

  @Override
  public void periodic() {
    led_controller.set(SmartDashboard.getNumber("LED Pattern", -0.89));
  }

  public void setValue(double value) {
    SmartDashboard.putNumber("LED Pattern", value);
  }

  public void setRandomValue() {
    Random rand = new Random();
    double pattern = rand.nextInt(100) * 1.0 / 100 - 0.99;
    System.out.println(pattern);
    SmartDashboard.putNumber("LED Pattern", pattern);
  }
}
