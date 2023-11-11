package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.IntakeMode;
import frc.robot.subsystems.Shooter;

public class CLB_StartWinch extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_subsystem;
  private final Shooter m_shooter;
  private final Intake m_intake;

  public CLB_StartWinch(Climber subsystem, Intake intake, Shooter shooter) {
    m_subsystem = subsystem;
    m_intake = intake;
    m_shooter = shooter;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_intake.setMode(IntakeMode.INTAKE_DEPLOY_REQUESTED);
  }

  @Override
  public void execute() {
      System.out.println("Winch Command Set");
      m_subsystem.climb();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
