package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.utility.Lidar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeMotorFront, intakeMotorRear;

  Timer intakeTimer = new Timer();
  Lidar lidar;
  DigitalSource lidarSource;
  boolean intakeAtSpeed, intakeLostSpeed = false;
  DigitalInput limitSwitch = new DigitalInput(1);
  
  public Intake() {
    intakeMotorFront = new CANSparkMax(Ports.Intake_Front_ID, MotorType.kBrushless);
    intakeMotorRear = new CANSparkMax(Ports.Intake_Rear_ID, MotorType.kBrushless);
    intakeMotorFront.setOpenLoopRampRate(100);
    intakeMotorRear.setOpenLoopRampRate(100);
    lidarSource = new DigitalInput(0);
    lidar = new Lidar(lidarSource);
  }

  public void grab() {
    intakeMotorFront.set(0.5);
    intakeMotorRear.set(0.5);
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
    if (SmartDashboard.getBoolean("Holding Note", true))
    {
      intakeAtSpeed = false;
      intakeLostSpeed = false;
    }
    /*if (intakeAtSpeed && intakeMotorFront.getEncoder().getVelocity()<2100)
    {
      intakeLostSpeed   = true;
      System.out.println("Intake Lost Speed");
    }
    else {
      intakeLostSpeed = false;
    }
    if (intakeMotorFront.getEncoder().getVelocity()>2200)
    {
      intakeAtSpeed = true;
    }
    else
    {
      intakeAtSpeed = false;
    }*/

    if (Constants.AUTO_MODE) {
      if (SmartDashboard.getBoolean("Holding Note", true)) {
        stop();
      }
      else {
        grab();
      }
    }

    if (limitSwitch.get())
    {
      intakeTimer.start();
    }
    if (intakeTimer.get()>0.2)
    {
      SmartDashboard.putBoolean("Holding Note", true);
      intakeTimer.reset();
      intakeTimer.stop();
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
    SmartDashboard.putNumber("Intake Front RPM", intakeMotorFront.getEncoder().getVelocity());
    SmartDashboard.putNumber("Intake Rear RPM", intakeMotorRear.getEncoder().getVelocity());
    SmartDashboard.putNumber("Intake Front Current", intakeMotorFront.getOutputCurrent());
    SmartDashboard.putNumber("Intake Rear Current", intakeMotorRear.getOutputCurrent());
    SmartDashboard.putBoolean("Intake Lost Speed", intakeLostSpeed);
    SmartDashboard.putBoolean("Intake At Speed", intakeAtSpeed);
    SmartDashboard.putNumber("LIDAR Distance", lidar.getDistance());
    SmartDashboard.putBoolean("Conveyor Limit Switch", limitSwitch.get());
    SmartDashboard.putNumber("Intake Limit Timer", intakeTimer.get());
    SmartDashboard.putBoolean("Robot Climbing Indicator", SmartDashboard.getBoolean("Holding Note", true));
      

  }
}
