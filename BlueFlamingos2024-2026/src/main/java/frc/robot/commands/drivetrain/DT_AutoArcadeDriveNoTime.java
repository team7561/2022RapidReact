package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DT_AutoArcadeDriveNoTime extends Command {
  private final Drivetrain m_drivetrain;
  private double m_y, m_speed;

  public DT_AutoArcadeDriveNoTime(Drivetrain subsystem, double y, double speed) {

    m_drivetrain = subsystem;
    m_y = y;
    m_speed = speed;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(0, m_y, m_speed, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {

    return false;
  }
}
