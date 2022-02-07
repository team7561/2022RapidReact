package frc.robot.commands.injector;

import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Injector_Forward extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;

  public Injector_Forward(Injector subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.forward();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}