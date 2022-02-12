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


public class SH_JoystickControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private final DoubleSupplier m_R_joyY, m_L_joyY;

  /**
   * Creates a new SH_Retract.
   *  @param subsystem
   */
  public SH_JoystickControl(Shooter subsystem, DoubleSupplier L_joyY, DoubleSupplier R_joyY) {
    m_subsystem = subsystem;
    m_L_joyY = L_joyY;
    m_R_joyY = R_joyY;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    //m_subsystem.start();
    System.out.println("Set speed setpoint");
  }
  @Override
  public void execute() {
    SmartDashboard.putNumber("Left: ", m_L_joyY.getAsDouble());
    SmartDashboard.putNumber("Right: ", m_R_joyY.getAsDouble());
    m_subsystem.set_voltage(m_L_joyY.getAsDouble(), m_R_joyY.getAsDouble());
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
