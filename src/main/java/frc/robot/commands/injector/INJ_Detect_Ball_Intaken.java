package frc.robot.commands.injector;

import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class INJ_Detect_Ball_Intaken extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;
  double m_count, m_timeout;
  Timer timer;

  public INJ_Detect_Ball_Intaken(Injector subsystem, double timeout) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
    timer = new Timer();
    m_timeout = timeout;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    m_subsystem.resetEncoder();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.resetEncoder();
  }

  @Override
  public boolean isFinished() {
    return timer.get() > m_timeout || m_subsystem.getEncoderCount() < -0.2;
  }
}
