package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class CLB_StopWinch extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_subsystem;
  
  public CLB_StopWinch(Climber subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.stop();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
