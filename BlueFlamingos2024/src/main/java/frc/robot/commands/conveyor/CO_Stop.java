package frc.robot.commands.conveyor;

import frc.robot.subsystems.Conveyor;
import edu.wpi.first.wpilibj2.command.Command;

public class CO_Stop extends Command {
  private final Conveyor m_conveyor;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public CO_Stop(Conveyor conveyor) {
    m_conveyor = conveyor;
    
    addRequirements(conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_conveyor.stop();
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