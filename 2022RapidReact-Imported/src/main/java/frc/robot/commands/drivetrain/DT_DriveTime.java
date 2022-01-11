package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * An example command that uses an example subsystem.
 */
public class DT_DriveTime extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  Timer timer;
  double startTime;

  /**
   * Creates a new ExampleCommand.
   *
   * @param drivetrain The subsystem used by this command.
   */
  public DT_DriveTime(Drivetrain drivetrain) {
    m_subsystem = drivetrain;
    timer = new Timer();
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Starting auto eat my ass");
    timer.start();
    m_subsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if (timer.get() < 1.5){
      m_subsystem.setSwerveVector(0.5, 0, 0.15);
    }
    else if (timer.get() < 3){
      m_subsystem.setSwerveVector(0.5, 270, 0.15);
    }
    else if (timer.get() < 4.5){
      m_subsystem.setSwerveVector(0.5, 180, 0.15);
    }
    else if (timer.get() < 6){
      m_subsystem.setSwerveVector(0.5, 90, 0.15);
    }
    System.out.println("Auto Drive");
    m_subsystem.updateDashboard();
  }

  public void drive(double leftSpeed, double rightSpeed) {
    m_subsystem.moduleD.setVelocity(leftSpeed);
    m_subsystem.moduleC.setVelocity(-rightSpeed);
    m_subsystem.moduleA.setVelocity(leftSpeed);
    m_subsystem.moduleB.setVelocity(-rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get()>6;
  }
}
