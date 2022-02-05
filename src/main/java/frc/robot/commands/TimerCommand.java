package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * An example command that uses an example subsystem.
 */
public class TimerCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Timer m_timer = new Timer();
  private final double m_time;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TimerCommand(double time) {
    m_time = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_timer.start();
      System.out.println("Timer started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Timer ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (m_timer.get()>m_time);
  }
}
