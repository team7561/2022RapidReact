package frc.robot.commands.drivetrain;

import frc.robot.subsystems.*;
import frc.robot.SwerveMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DT_Auto_Hub_Align extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private final LimeLightController m_lc;
  Boolean hubSpotted = false;
  SwerveMode m_origMode;

  public DT_Auto_Hub_Align(Drivetrain drivetrain, LimeLightController lc) {
    m_subsystem = drivetrain;
    m_lc = lc;
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    m_origMode = m_subsystem.getMode();
  }

  @Override
  public void execute() {
          m_subsystem.setMode(SwerveMode.HUB_TRACK);
  }

  @Override
  public void end(boolean interrupted) {
      m_subsystem.setMode(m_origMode);
        
    }

  @Override
  public boolean isFinished() {
    return (m_lc.get_ta() != 0 && Math.abs(m_lc.get_tx()) < 10);
  }
}
