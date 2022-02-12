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
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.autonomous.*;
import frc.robot.commands.visioncontroller.*;
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
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Injector injector = new Injector();
  private final Climber climber = new Climber();
  private final VisionController visionController = new VisionController();
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
    //shooter.setDefaultCommand(new SH_Stop(shooter));//new SH_Stop(shooter));
    intake.setDefaultCommand(new INT_Main(intake));
    injector.setDefaultCommand(new INJ_Main(injector));
    climber.setDefaultCommand(new CLB_StopWinch(climber));
    visionController.setDefaultCommand(new VC_SetAngle(visionController, 65));
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
    final JoystickAnalogButton button_LT = new JoystickAnalogButton(xboxController, 2);
    final JoystickAnalogButton button_RT = new JoystickAnalogButton(xboxController, 3);
  
    final DPadButton dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);
    
    //trigger.whenPressed(new DT_ArcadeDrive(drivetrain, 0.4, 0.4, 0.5),true);
    
    trigger.whenPressed(new INT_Grabbing_Start(intake), true);
    trigger.whenReleased(new INT_Grabbing_Stop(intake), true);
    button_2.whenPressed(new INT_EjectBall(intake), true);
    button_2.whenReleased(new INT_Grabbing_Stop(intake), true);

    button_3.whenPressed(new INT_Toggle(intake), true);

    button_4.whenPressed(new SH_Extend(shooter), true);
    button_4.whenReleased(new SH_Retract(shooter), true);

    button_7.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.CRAB),true);
    button_8.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.SPIN),true);
    button_9.whenPressed(new DT_Drive_Change_Mode(drivetrain, SwerveMode.ULTIMATESWERVE),true);
    button_10.whenPressed(new DT_ManualAlign(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getTwist(), () -> (joystick.getThrottle()+1)/2),true);
    button_11.whenPressed(new DT_Drive_ResetEncoders(drivetrain),true);
    button_12.whenPressed(new DT_Drive_Reset_Gyro(drivetrain),true);

    dpad_Up.whenPressed(new CLB_StartWinch(climber, intake), true);
    dpad_Up.whenReleased(new CLB_StopWinch(climber), true);
    dpad_Down.whenPressed(new CLB_ReverseWinch(climber, intake), true);
    dpad_Down.whenReleased(new CLB_StopWinch(climber), true);
    dpad_Left.whenPressed(new CLB_Deploy(climber, intake), true);
    dpad_Left.whenReleased(new CLB_StopWinch(climber), true);
    dpad_Right.whenPressed(new CLB_Deploy(climber, intake), true);
    dpad_Right.whenReleased(new CLB_StopWinch(climber), true);

    button_A.whenPressed(new INJ_Index_Ball(injector), true);
    button_B.whenPressed(new INJ_Reverse_Index_Ball(injector), true);
    button_X.whenPressed(new INJ_Forward(injector), true);
    button_X.whenReleased(new INJ_Stop(injector), true);
    button_Y.whenPressed(new INJ_Reverse(injector), true);
    button_Y.whenReleased(new INJ_Stop(injector), true);

    button_LB.whenPressed(new SH_Set_Speed_Setpoints(shooter, 1600, -1600), true);
    button_LB.whenPressed(new SH_Extend(shooter), true);

    button_RB.whenPressed(new SH_Set_Speed_Setpoints(shooter, 1400, -1400), true);
    button_RB.whenPressed(new SH_Retract(shooter), true);
    
    button_Y.whenPressed(new SH_Set_Speed_Setpoints(shooter, 1200, -1200), true);
    button_Y.whenReleased(new SH_Stop(shooter), true);

    button_X.whenReleased(new SH_Stop(shooter), true);

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
      return new Auto_Drive_Off_Line(drivetrain);
  }
}
