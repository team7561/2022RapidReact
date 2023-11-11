/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PowerDistribution;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private static final String DEFAULT_AUTO = "Default";
  double curr_angle, target_angle;

  private RobotContainer m_robotContainer;
  Timer matchTimer = new Timer();
  NetworkTable table;
  String autoMode;

  public PowerDistribution pdp;
  boolean invertedDrive;
  double speedControl;
  boolean debug;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    debug = true;
    m_chooser.setDefaultOption("Default Auto", DEFAULT_AUTO);
    invertedDrive = false;
    speedControl = 0.5;
    setM_robotContainer(new RobotContainer());

    //table = NetworkTable.getTable("GRIP/myContoursReport"); 
    CameraServer.startAutomaticCapture();
  }

  public RobotContainer getM_robotContainer() {
    return m_robotContainer;
  }

  public void setM_robotContainer(RobotContainer m_robotContainer) {
    this.m_robotContainer = m_robotContainer;
  }

  @Override
  public void robotPeriodic() {   
    CommandScheduler.getInstance().run();     
    }    
    /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Empty as scheduler takes care of this
  }                                                                                                                                
  @Override                                                                                                                                         
  public void teleopInit() {         
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }     
    m_robotContainer.m_shooter.stop();                                                                                                                              
  }                                                                         
  @Override                                     
  public void teleopPeriodic() {                                                                                                                                   
    // Unused as scheduler takes care of this.
  }                                                                                                                                        
                                                                                              
  @Override                                                                                                                                         
  public void testInit() { 
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();                                                                                                                                       
  }                                          
  @Override                                                                                                                                         
  public void testPeriodic() {         
    // Unused                                                                                                                                
  }
}