/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LimeLightController;

public class SH_ShootAtTarget extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Shooter m_subsystem;
    private final LimeLightController m_vision_subsystem;
    private double m_targetAngle, m_hood_encoder;
    private Timer timer;

    /**
     * 
     * Creates a new SH_Extend.
     * 
     * @param subsystem
     */
    public SH_ShootAtTarget(Shooter subsystem, LimeLightController vision_subsystem) {
    m_subsystem = subsystem;
    m_vision_subsystem = vision_subsystem;
    addRequirements(subsystem);
    timer = new Timer();
  }

  @Override
  public void initialize() {
      System.out.println("Starting turn to vision angle");
      System.out.println("Turn to vision angle called");
      m_vision_subsystem.turnOnLED();
      timer.start();
      SmartDashboard.putBoolean("Turn to Vision Angle is finished: ", false); 
  }

  @Override
  public void execute() {
    m_subsystem.set_RPM(100, 100);
  }

  @Override
  public boolean isFinished()
  { 
    boolean isAtAngle = Math.abs(m_hood_encoder) == m_targetAngle;
    if (isAtAngle=true)
    {
        return true;
    }
    return isAtAngle;
  }
}
