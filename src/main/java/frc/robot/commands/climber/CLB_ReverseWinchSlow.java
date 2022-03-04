package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.IntakeMode;

public class CLB_ReverseWinchSlow extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_subsystem;
  private final Intake m_intake;

  public CLB_ReverseWinchSlow(Climber subsystem, Intake intake) {
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
      System.out.println("Reverse Command Set");
      m_subsystem.climbReverseSlow();
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
