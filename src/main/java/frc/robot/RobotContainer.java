  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.Injector_Reverse;
import frc.robot.commands.injector.Injector_Stop;
import frc.robot.commands.injector.Injector_Transfer_Ball;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot sho
 * uld be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  //private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Injector injector = new Injector();
  private final Climber climber = new Climber();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  //HID
  private Joystick joystick = new Joystick(0); //Logitech Extreme 3D Pro Joysick Controller
  private XboxController xboxController = new XboxController(1); //Logitech Extreme 3D Pro Joysick Controller
    

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(new DT_SwerveDrive(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getTwist(), () -> (joystick.getThrottle()+1)/2));
    //shooter.setDefaultCommand(new Shooter_Set_Speed_Setpoints(shooter, 0, 0));//new Shooter_Stop(shooter));
    intake.setDefaultCommand(new Intake_Grabbing_Stop(intake));
    climber.setDefaultCommand(new Climb_StopWinch(climber));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton trigger = new JoystickButton(joystick, 1);
    final JoystickButton button_2 = new JoystickButton(joystick, 2);
    final JoystickButton button_3 = new JoystickButton(joystick, 3);
    final JoystickButton button_4 = new JoystickButton(joystick, 4);
    final JoystickButton button_5 = new JoystickButton(joystick, 5);
    final JoystickButton button_6 = new JoystickButton(joystick, 6);
    final JoystickButton button_7 = new JoystickButton(joystick, 7);
    final JoystickButton button_8 = new JoystickButton(joystick, 8);
    final JoystickButton button_9 = new JoystickButton(joystick, 9);
    final JoystickButton button_10 = new JoystickButton(joystick, 10);
    final JoystickButton button_11 = new JoystickButton(joystick, 11);
    final JoystickButton button_12 = new JoystickButton(joystick, 12);

    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);

    final JoystickButton button_LB = new JoystickButton(xboxController, 5);
    final JoystickButton button_RB = new JoystickButton(xboxController, 6);

    //final JoystickButton back = new JoystickButton(xboxController, 7);
    //final JoystickButton start = new JoystickButton(xboxController, 8);
    //final JoystickButton left_joystick_button = new JoystickButton(xboxController, 9);
    //final JoystickButton right_joystick_button = new JoystickButton(xboxController, 10);
    //final JoystickAnalogButton LT = new JoystickAnalogButton(xboxController, 2);
    //final JoystickAnalogButton RT = new JoystickAnalogButton(xboxController, 3);
  
    final DPadButton dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);
    
    //trigger.whenPressed(new DT_ArcadeDrive(drivetrain, 0.4, 0.4, 0.5),true);
    
    trigger.whenPressed(new Intake_GrabBall(intake), true);
    trigger.whenReleased(new Intake_Grabbing_Stop(intake), true);
    button_2.whenPressed(new Intake_EjectBall(intake), true);
    button_2.whenReleased(new Intake_Grabbing_Stop(intake), true);

    button_3.whenPressed(new Intake_Toggle(intake), true);
    //button_3.whenPressed(new Intake_Extend(intake), true);
    //button_3.whenReleased(new Intake_Deploy_Stop(intake), true);
    //button_4.whenPressed(new Intake_Retract(intake), true);
    //button_4.whenReleased(new Intake_Deploy_Stop(intake), true);
    
    button_5.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.BALL_TRACK),true);
    button_6.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.HUB_TRACK),true);

    button_7.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.CRAB),true);
    button_8.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.SPIN),true);
    button_9.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.ULTIMATESWERVE),true);
    button_10.whenPressed(new DT_ManualAlign(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getTwist(), () -> (joystick.getThrottle()+1)/2),true);
    button_11.whenPressed(new DT_Drive_ResetEncoders(drivetrain),true);
    button_12.whenPressed(new DT_Drive_Reset_Gyro(drivetrain),true);

    
    dpad_Up.whenPressed(new Climb_StartWinch(climber), true);
    dpad_Up.whenReleased(new Climb_StopWinch(climber), true);
    dpad_Down.whenPressed(new Climb_ReverseWinch(climber), true);
    dpad_Down.whenReleased(new Climb_StopWinch(climber), true);
    dpad_Left.whenPressed(new Climb_Deploy(climber), true);
    dpad_Left.whenReleased(new Climb_StopWinch(climber), true);
    button_X.whenPressed(new Injector_Transfer_Ball(injector), true);
    button_X.whenReleased(new Injector_Stop(injector), true);
    button_Y.whenPressed(new Injector_Reverse(injector), true);
    button_Y.whenReleased(new Injector_Stop(injector), true);
/*
    button_B.whenPressed(new Shooter_Set_Speed_Setpoints(shooter, 1600, -1600), true);
    button_B.whenReleased(new Shooter_Stop(shooter), true);
    button_X.whenPressed(new Shooter_Set_Speed_Setpoints(shooter, 1400, -1400), true);
    button_X.whenReleased(new Shooter_Stop(shooter), true);
    button_Y.whenPressed(new Shooter_Set_Speed_Setpoints(shooter, 1200, -1200), true);
    button_Y.whenReleased(new Shooter_Stop(shooter), true);
    button_LB.whenPressed(new Shooter_Extend(shooter), true);
    button_RB.whenPressed(new Shooter_Retract(shooter), true);
    */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   *
   * 
   */
    public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
      return new DT_DriveTime(drivetrain);
  }
}
