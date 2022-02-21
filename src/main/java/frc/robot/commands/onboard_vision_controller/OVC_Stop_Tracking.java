package frc.robot.commands.onboard_vision_controller;

import frc.robot.subsystems.OnboardVisionController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OVC_Stop_Tracking extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final OnboardVisionController m_subsystem;

  public OVC_Stop_Tracking(OnboardVisionController subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_subsystem.stopTracking();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
