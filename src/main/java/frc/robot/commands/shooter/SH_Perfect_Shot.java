/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SH_Perfect_Shot extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;

  /**
   * Creates a new SH_Retract.
   *  @param subsystem
   */
  public SH_Perfect_Shot(Shooter subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    //m_subsystem.start();
    System.out.println("Set speed setpoint");
  }
  
  @Override
  public void execute() {
    m_subsystem.stop_auto_hood();
    m_subsystem.set_RPM(1167, 1185);
    m_subsystem.setHood(0.58);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
