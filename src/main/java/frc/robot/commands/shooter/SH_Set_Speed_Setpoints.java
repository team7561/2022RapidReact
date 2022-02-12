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
import java.util.function.DoubleSupplier;


public class SH_Set_Speed_Setpoints extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private final double m_setpointA, m_setpointB;

  /**
   * Creates a new SH_Retract.
   *  @param subsystem
   */
  public SH_Set_Speed_Setpoints(Shooter subsystem, double setpointA, double setpointB) {
    m_subsystem = subsystem;
    m_setpointA = setpointA;
    m_setpointB = setpointB;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    //m_subsystem.start();
    System.out.println("Set speed setpoint");
  }
  @Override
  public void execute() {
    m_subsystem.set_RPM(m_setpointA, m_setpointB);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
