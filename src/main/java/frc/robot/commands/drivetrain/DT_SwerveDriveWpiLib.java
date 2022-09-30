package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DT_SwerveDriveWpiLib extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private DoubleSupplier m_x, m_y, m_twist, m_speed;
  private double target_angle, m_power;

  public DT_SwerveDriveWpiLib(Drivetrain drivetrain, DoubleSupplier x, DoubleSupplier y, DoubleSupplier twist, DoubleSupplier speed) {
    m_subsystem = drivetrain;
    m_x = x;
    m_y = y;
    m_speed = speed;
    m_twist = twist;


    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_subsystem.drive(m_x, m_y, m_twist, m_speed, false);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
