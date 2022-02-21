package frc.robot.commands.limelight_controller;

import frc.robot.subsystems.LimeLightController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LL_TurnOnLED extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LimeLightController m_subsystem;

  public LL_TurnOnLED(LimeLightController subsystem) {
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
