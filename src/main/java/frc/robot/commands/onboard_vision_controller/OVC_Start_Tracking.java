package frc.robot.commands.onboard_vision_controller;

import frc.robot.subsystems.LimeLightController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OVC_Start_Tracking extends CommandBase {
  private final LimeLightController m_subsystem;

  public OVC_Start_Tracking(LimeLightController subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.turnOffLED();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
