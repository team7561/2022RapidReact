/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shooter_Stop extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  /**
   * 
   * Creates a new Shooter_Extend.
   * @param subsystem
   */
  public Shooter_Stop(Shooter subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }
  @Override
  public void initialize()
  {
    System.out.println("Stopping Shooter.");
  }
  @Override
  public void execute() {
    m_subsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
