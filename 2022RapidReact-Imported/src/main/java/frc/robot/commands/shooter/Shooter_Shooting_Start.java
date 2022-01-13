package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Constants;

public class Shooter_Shooting_Start extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;

  public Shooter_Shooting_Start(Shooter subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    SmartDashboard.putNumber("LED Value", Constants.BLINKIN_COLOUR_WAVE_RAINBOW);
  }

  @Override
  public void execute() {
      m_subsystem.start();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
