package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LimeLightController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SH_Shooting_Stop extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private final LimeLightController m_lc;

  public SH_Shooting_Stop(Shooter subsystem, LimeLightController lc) {
    m_subsystem = subsystem;
    m_lc = lc;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      m_lc.turnOffLED();
      m_subsystem.stop();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
