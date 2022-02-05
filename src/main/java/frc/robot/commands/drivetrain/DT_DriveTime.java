package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

public class DT_DriveTime extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  Timer timer;
  double startTime;

  public DT_DriveTime(Drivetrain drivetrain) {
    m_subsystem = drivetrain;
    timer = new Timer();
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Starting auto");
    timer.start();
    m_subsystem.resetGyro();
  }

  @Override
  public void execute() {
    
    if (timer.get() < 1.5){
      m_subsystem.setSwerveVector(0, 0, 0.15);
    }
    else if (timer.get() < 3){
      m_subsystem.setSwerveVector(0, 270, 0.15);
    }
    else if (timer.get() < 4.5){
      m_subsystem.setSwerveVector(0, 180, 0.15);
    }
    else if (timer.get() < 6){
      m_subsystem.setSwerveVector(0, 90, 0.15);
    }
    System.out.println("Auto Drive");
  }

  public void drive(double leftSpeed, double rightSpeed) {
    m_subsystem.moduleD.setVelocity(leftSpeed);
    m_subsystem.moduleC.setVelocity(-rightSpeed);
    m_subsystem.moduleA.setVelocity(leftSpeed);
    m_subsystem.moduleB.setVelocity(-rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    drive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return timer.get()>6;
  }
}
