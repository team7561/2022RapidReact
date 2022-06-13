package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.IntakeMode;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CLB_Deploy extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_climber;
  private final Intake m_intake;
  private final Shooter m_shooter;
  public CLB_Deploy(Climber subsystem, Intake intake, Shooter shooter) {
    m_climber = subsystem;
    m_intake = intake;
    m_shooter = shooter;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_intake.setMode(IntakeMode.INTAKE_DEPLOY_REQUESTED);
    m_shooter.stop();
  }

  @Override
  public void execute() {
      System.out.println("Deploy Command Set");
      m_climber.climbDeploy(); 

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
