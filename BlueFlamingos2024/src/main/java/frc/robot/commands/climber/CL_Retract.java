package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;

public class CL_Retract extends Command {
  private final Climber m_climber;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CL_Retract(Climber climber) {
    m_climber = climber;
    
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climber.extend();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}