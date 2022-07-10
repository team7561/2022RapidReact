package frc.robot.commands.injector;

import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InjectorMode;

public class INJ_Index_Ball extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;

  public INJ_Index_Ball(Injector subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_subsystem.resetEncoder();

  }

  @Override
  public void execute() {
    m_subsystem.setMode(InjectorMode.INJECTOR_FORWARD);
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.resetEncoder();
    m_subsystem.setMode(InjectorMode.INJECTOR_STOP);

  }

  @Override
  public boolean isFinished() {
    return m_subsystem.getEncoderCount()< -10;
  }
}
