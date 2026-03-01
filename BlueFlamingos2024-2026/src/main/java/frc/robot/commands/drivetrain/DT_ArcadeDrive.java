package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import java.util.function.DoubleSupplier;

public class DT_ArcadeDrive extends Command {
  private final Drivetrain m_drivetrain;
  private DoubleSupplier m_x, m_y, m_speed;
  
  public DT_ArcadeDrive(
      Drivetrain subsystem,
      DoubleSupplier x,
      DoubleSupplier y,
      DoubleSupplier speed) {

    m_drivetrain = subsystem;
    m_x = x;
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
      m_drivetrain.arcadeDrive(m_x.getAsDouble(), m_y.getAsDouble(), 0.5 * (1 + m_speed.getAsDouble()), false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

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
