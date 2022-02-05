package frc.robot.commands.injector;

import frc.robot.Constants;
import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Injector_Reverse_Index_Ball extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;

  public Injector_Reverse_Index_Ball(Injector subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_subsystem.resetEncoder();
  }

  @Override
  public void execute() {
      m_subsystem.reverse();
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return m_subsystem.getEncoderCount() > -Constants.INJECTOR_CARGO_INDEX_PULSE_COUNT;
  }
}
