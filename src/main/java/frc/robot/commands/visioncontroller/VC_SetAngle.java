/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.visioncontroller;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionController;
import java.util.function.DoubleSupplier;


public class VC_SetAngle extends CommandBase {
  private final VisionController m_subsystem;
  private double m_angle;

  public VC_SetAngle(VisionController subsystem, double angle) {
    m_subsystem = subsystem;
    m_angle = angle;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    //m_subsystem.start();
    System.out.println("Set LimelightController Angle");
    //m_subsystem.setAngle(m_angle);

  }
  @Override
  public void execute() {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
