
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shooter_Auto_Vision_Speed extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private final boolean m_auto_mode;


  /**
   * Creates a new Shooter_Retract.
   *  @param subsystem
   */
  public Shooter_Auto_Vision_Speed(Shooter subsystem, boolean auto_mode) {
    m_subsystem = subsystem;
    m_auto_mode = auto_mode;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    if (m_auto_mode)
    {
      m_subsystem.start_auto_vision_speed();
    }
    else {
      m_subsystem.stop_auto_vision_speed();
    }    
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
