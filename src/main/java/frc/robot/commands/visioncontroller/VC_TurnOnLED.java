package frc.robot.commands.visioncontroller;

import frc.robot.subsystems.VisionController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class VC_TurnOnLED extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final VisionController m_subsystem;

  public VC_TurnOnLED(VisionController subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.turnOnLED();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}