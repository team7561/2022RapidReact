package frc.robot.commands.drivetrain;

import frc.robot.subsystems.*;
import frc.robot.IntakeMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

public class DT_Auto_Intake_Ball extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private final OnboardVisionController m_obvc;
  private final Intake m_intake;
  Timer timer;
  double startTime;
  double m_endTime;

  public DT_Auto_Intake_Ball(Drivetrain drivetrain, Intake intake, OnboardVisionController obvc, double endTime) {
    m_subsystem = drivetrain;
    timer = new Timer();
    m_endTime = endTime;
    m_obvc = obvc;
    m_intake = intake;
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Starting auto");
    timer.start();
    m_intake.setMode(IntakeMode.INTAKE_DEPLOY_REQUESTED);  
    obvc.startTracking();
  }

  @Override
  public void execute() {
    if (Math.abs(ball_x) < 10){
        m_subsystem.setSwerveVectorNoGyro(0.02 * Math.signum(ball_x), 0, 0.1);
    } else {
        m_subsystem.setSwerveVectorNoGyro(0, 0, 0.1);
    }

    m_intake.reverse = false;
    m_intake.intakeRequested = true;
  }

  @Override
  public void end(boolean interrupted) {
    drive(0, 0);
    m_intake.setMode(IntakeMode.INTAKE_RETRACT_REQUESTED);  
    m_intake.intakeRequested = false;
    m_intake.stop()
    obvc.stopTracking();
  }

  @Override
  public boolean isFinished() {
    return timer.get()>m_endTime;
  }
}
