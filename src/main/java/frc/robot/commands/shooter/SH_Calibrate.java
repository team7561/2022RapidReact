/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SH_Calibrate extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private Shooter m_Shooter;
  double a_speed, b_speed;
  //private final ExampleSubsystem m_subsystem;

  public SH_Calibrate(Shooter subsystem) {
    a_speed = SmartDashboard.getNumber("motorAValue", 0.1);
    b_speed = SmartDashboard.getNumber("motorBValue", 0.1);
    addRequirements(subsystem);
    m_Shooter = subsystem;
  }

  @Override
  public void initialize() {
  }
  
  @Override
  public void execute() {

    m_Shooter.setMotorA(a_speed);
    m_Shooter.setMotorB(b_speed);

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
