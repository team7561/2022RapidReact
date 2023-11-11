/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shooter_Set_Hood_Setpoint extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  //private final Shooter m_subsystem;
  private final double m_setpoint;

  /**
   * Creates a new Shooter_Retract.
   *  @param subsystem
   */
  public Shooter_Set_Hood_Setpoint(Shooter subsystem, double setpoint) {
    //m_subsystem = subsystem;
    m_setpoint = setpoint;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Hood Set Point", m_setpoint);
  }


  @Override
  public boolean isFinished() {
    return true;
  }
}
