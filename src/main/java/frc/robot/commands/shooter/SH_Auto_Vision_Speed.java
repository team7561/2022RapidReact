
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionController;

public class SH_Auto_Vision_Speed extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;
  private final VisionController m_visionController;


  /**
   * Creates a new SH_Retract.
   *  @param subsystem
   */
  public SH_Auto_Vision_Speed(Shooter shooter, VisionController visionController) {
    m_shooter = shooter;
    m_visionController = visionController;
    addRequirements(shooter, visionController);
  }

  @Override
  public void initialize() {
    System.out.println("Starting Auto Shooting");
  }

  @Override
  public void execute() {
    double speed = m_visionController.calcSetpoint();
    double spin = m_visionController.calcSpin();
    m_shooter.set_RPM(speed+spin, speed-spin);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
