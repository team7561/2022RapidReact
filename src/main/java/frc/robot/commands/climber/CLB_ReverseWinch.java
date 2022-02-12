package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.IntakeMode;

public class CLB_ReverseWinch extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_subsystem;
  private final Intake m_intake;

  public CLB_ReverseWinch(Climber subsystem, Intake intake) {
    m_subsystem = subsystem;
    m_intake = intake;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(m_intake.getMode() == IntakeMode.INTAKE_DEPLOYED){
      m_subsystem.climbReverse();
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
