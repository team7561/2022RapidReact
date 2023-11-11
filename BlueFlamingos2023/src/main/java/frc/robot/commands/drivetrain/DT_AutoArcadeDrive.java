package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DT_AutoArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private double m_x, m_y, m_speed;
  private Timer timer;
  private double m_time;

  public DT_AutoArcadeDrive(Drivetrain subsystem, double x, double y, double speed, double time) {

    m_drivetrain = subsystem;
    m_x = x;
    m_y = y;
    m_speed = speed;
    m_time = time;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = new Timer();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_x, m_y, m_speed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > m_time;
  }

  @Override
  public boolean runsWhenDisabled() {

    return false;
  }
}
