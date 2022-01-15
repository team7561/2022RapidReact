/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class Shooter_Auto_Hood extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_subsystem;
  private final boolean m_auto_mode;
  private Timer timerFinished;

  /**
   * Creates a new Shooter_Retract.
   *  @param subsystem
   */
  public Shooter_Auto_Hood(Shooter subsystem, boolean auto_mode) {
    m_subsystem = subsystem;
    m_auto_mode = auto_mode;
    timerFinished = new Timer();
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    if (m_auto_mode)
    {
      m_subsystem.start_auto_hood();
    }
    else {
      m_subsystem.stop_auto_hood();
    }    
  }

  @Override
  public void execute() {
    System.out.println("Retracting Deflector");
  }

  @Override
  public void end(boolean interrupted) {
    //m_subsystem.stop_auto_hood();
  }

  @Override
  public boolean isFinished() {
    boolean isAtAngle = m_subsystem.hood_at_setpoint();
    if (isAtAngle)
    {
        timerFinished.start();
    }
    else {
        timerFinished.reset();
        timerFinished.stop();
    }
    if (timerFinished.get()>1)
    {
        return true;
    }
    return false;
  }
}
