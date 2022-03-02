package frc.robot.commands.drivetrain;

import frc.robot.subsystems.*;
import frc.robot.SwerveMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DT_Auto_Hub_Align extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private final LimeLightController m_lc;
  Boolean hubSpotted = false;

  public DT_Auto_Hub_Align(Drivetrain drivetrain, LimeLightController lc) {
    m_subsystem = drivetrain;
    m_lc = lc;
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    System.out.println("Starting auto");
  }

  @Override
  public void execute() {
      if(m_lc.get_ta() == 0){
          m_subsystem.setSwerveVector(0.05, 0, 0);
      } else {
          m_subsystem.setMode(SwerveMode.HUB_TRACK);
      }
  }

  @Override
  public void end(boolean interrupted) {
        m_subsystem.setSwerveVector(0, 0, 0);
    }

  @Override
  public boolean isFinished() {
    return (m_lc.get_ta() != 0 && Math.abs(m_lc.get_tx()) < 10);
  }
}
