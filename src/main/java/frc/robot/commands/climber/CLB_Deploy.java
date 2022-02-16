package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.IntakeMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CLB_Deploy extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Climber m_climber;
  private final Intake m_intake;

  public CLB_Deploy(Climber subsystem, Intake intake) {
    m_climber = subsystem;
    m_intake = intake;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(m_intake.getMode() == IntakeMode.INTAKE_DEPLOYED){
      System.out.println("Deploy Command Set");
      m_climber.climbDeploy(); 
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
