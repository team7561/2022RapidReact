package frc.robot.commands.intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.IntakeMode;

public class INT_Toggle extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake m_subsystem;

  public INT_Toggle(Intake subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.stop();
      m_subsystem.resetTimer();
      if(m_subsystem.getMode() == IntakeMode.INTAKE_DEPLOYED || m_subsystem.getMode() == IntakeMode.INTAKE_DEPLOY_REQUESTED){
        m_subsystem.setMode(IntakeMode.INTAKE_RETRACT_REQUESTED);
      }

      else if(m_subsystem.getMode() == IntakeMode.INTAKE_RETRACTED || m_subsystem.getMode() == IntakeMode.INTAKE_RETRACT_REQUESTED){
        m_subsystem.setMode(IntakeMode.INTAKE_DEPLOY_REQUESTED);
      }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
