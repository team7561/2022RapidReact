package frc.robot.commands.drivetrain;

import frc.robot.subsystems.*;
import frc.robot.SwerveMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DT_Auto_Cargo_Align extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  double m_speed;
  Boolean hubSpotted = false;
  Timer timer = new Timer();
  double m_time;

  public DT_Auto_Cargo_Align(Drivetrain drivetrain, double speed, double time) {
    m_subsystem = drivetrain;
    m_speed = speed;
    m_time = time;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Starting cargo track auto");
    timer.start();
  }

  @Override
  public void execute() {
    double twist  = SmartDashboard.getNumber("ball_x_coord", 0);

    m_subsystem.setSwerveVectorNoGyro(twist/1000, 0, 0.4);
  }

  @Override
  public void end(boolean interrupted) {
        m_subsystem.setSwerveVectorNoGyro(0, 0, 0.4);
    }

  @Override
  public boolean isFinished() {
    return timer.get()>m_time;
  }
}
