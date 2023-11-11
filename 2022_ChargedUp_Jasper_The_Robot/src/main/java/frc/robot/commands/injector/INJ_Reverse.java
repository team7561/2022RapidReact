package frc.robot.commands.injector;

import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InjectorMode;

public class INJ_Reverse extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;

  public INJ_Reverse(Injector subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.setMode(InjectorMode.INJECTOR_REVERSE);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
