  /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.autonomous.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.NetworkButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

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
  SendableChooser<Command> mAutoChooser = new SendableChooser<>();

//  private final OnboardVisionController onboardVisionController = new OnboardVisionController();
  private final PiVisionController piVisionController = new PiVisionController();
  
  private final LimeLightController limeLightController = new LimeLightController();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  //HID
  private Joystick  wheel = new Joystick(4); //Logitech Extreme 3D Pro Joysick Controller
  private XboxController xboxController = new XboxController(1);
  private final LEDController leds = new LEDController();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(new DT_WheelSwerveDrive(drivetrain, () -> wheel.getX(), () -> 1-wheel.getY(), () -> 0.7-wheel.getTwist()*0.7, () -> (wheel.getThrottle()+1)/2));
    //shooter.setDefaultCommand(new SH_Stop(shooter));//new SH_Stop(shooter));
    climber.setDefaultCommand(new CLB_StopWinch(climber));
    leds.setDefaultCommand(new LED_Teleop(leds, drivetrain, shooter, limeLightController));
    //CameraServer.startAutomaticCapture(); //Commented out as this is part of OnboardVisionController

    LiveWindow.disableAllTelemetry();

    SmartDashboard.putNumber("Left Vibrate", 0);
    SmartDashboard.putNumber("Right Vibrate", 0);
    
    mAutoChooser.setDefaultOption("Auto_Shoot_Spin_Pickup_Shoot", new Auto_Shoot_Spin_Pickup_Shoot(shooter, injector, drivetrain, leds, intake));
    mAutoChooser.addOption("90 degrees turn", new Auto_Turn_90_Degrees(drivetrain, leds));
    mAutoChooser.addOption("5 second tracking cargo", new Auto_Drive_5s_Cargo(drivetrain, leds));
    mAutoChooser.addOption("1 Ball", new Auto_Shoot_Ball(shooter, injector, drivetrain,leds, intake, limeLightController));
    mAutoChooser.addOption("2 Balls (No tracking)", new Auto_Shoot_2_Balls_No_Tracking(shooter, injector, drivetrain,leds, intake, limeLightController));
    mAutoChooser.addOption("2 Balls  (Tracking)", new Auto_Shoot_2_Balls_Tracking(shooter, injector, drivetrain,leds, intake, limeLightController));
    mAutoChooser.addOption("3 Balls", new Auto_Shoot_3_Balls(shooter, injector, drivetrain,leds, intake));
    mAutoChooser.addOption("3 Balls (Luke)", new Auto_3_Ball(drivetrain, intake, shooter, injector, leds, limeLightController));
    mAutoChooser.addOption("2 Balls A", new Auto_2_Ball_A(drivetrain, intake, shooter, injector, leds, limeLightController));
    mAutoChooser.addOption("2 Balls B", new Auto_2_Ball_B(drivetrain, intake, shooter, injector, leds, limeLightController));
    mAutoChooser.addOption("Auto_Injector_Detect_Ball", new Auto_Injector_Detect_Ball(shooter, injector, drivetrain, leds, intake));
    SmartDashboard.putData("Auto", mAutoChooser);
  }

  private void configureButtonBindings() {
    final JoystickButton trigger = new JoystickButton(wheel, 1);
    final JoystickButton button_2 = new JoystickButton(wheel, 2);
    final JoystickButton button_3 = new JoystickButton(wheel, 3);
    final JoystickButton button_4 = new JoystickButton(wheel, 4);
    final JoystickButton button_5 = new JoystickButton(wheel, 5);
    final JoystickButton button_6 = new JoystickButton(wheel, 6);
    final JoystickButton button_7 = new JoystickButton(wheel, 7);
    final JoystickButton button_8 = new JoystickButton(wheel, 8);
    final JoystickButton button_9 = new JoystickButton(wheel, 9);
    final JoystickButton button_10 = new JoystickButton(wheel, 10);
    final JoystickButton button_11 = new JoystickButton(wheel, 11);
    final JoystickButton button_12 = new JoystickButton(wheel, 12);
    final JoystickButton button_13 = new JoystickButton(wheel, 13);
    final JoystickButton button_14 = new JoystickButton(wheel, 14);
    final JoystickButton button_15 = new JoystickButton(wheel, 15);
    final JoystickButton button_16 = new JoystickButton(wheel, 16);

    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);

    final JoystickButton button_LB = new JoystickButton(xboxController, 5);
    final JoystickButton button_RB = new JoystickButton(xboxController, 6);

    final JoystickButton back = new JoystickButton(xboxController, 7);
    final JoystickButton start = new JoystickButton(xboxController, 8);
    final JoystickButton left_joystick_button = new JoystickButton(xboxController, 9);
    final JoystickButton right_joystick_button = new JoystickButton(xboxController, 10);
    final JoystickAnalogButton button_LT = new JoystickAnalogButton(xboxController, 2);
    final JoystickAnalogButton button_RT = new JoystickAnalogButton(xboxController, 3);
  
    final DPadButton xbox_dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton xbox_dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton xbox_dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton xbox_dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);

    final POVButton joystick_dpad_Up = new POVButton(wheel, 0);
    final POVButton joystick_dpad_Down = new POVButton(wheel, 180);
    final POVButton joystick_dpad_Left = new POVButton(wheel, 270);
    final POVButton joystick_dpad_Right = new POVButton(wheel, 90);

    SmartDashboard.putData("Save swerve steer offsets", new DT_Save_Offsets());
    SmartDashboard.putData("Drive manual align", new DT_ManualAlign(drivetrain));
   
    //trigger.whenPressed(new DT_ArcadeDrive(drivetrain, 0.4, 0.4, 0.5),true);
    
    button_5.whenPressed(new INT_Grabbing_Start(intake), true);
    button_5.whenReleased(new INT_Grabbing_Stop(intake), true);
    button_6.whenPressed(new INT_EjectBall(intake), true);
    button_6.whenReleased(new INT_Grabbing_Stop(intake), true);

    button_7.whenPressed(new INT_Toggle(intake), true);

    trigger.whenPressed(new INJ_Reverse_Index_Ball(injector), true);
    button_2.whenPressed(new INJ_Index_Ball(injector), true);

    button_13.whenPressed(new SH_Far_Shot(shooter), true);
    button_14.whenPressed(new SH_Close_Shot(shooter), true); 
    button_15.whenPressed(new SH_Perfect_Shot(shooter), true);

    button_9.whenPressed(new SH_Shooting_Stop(shooter, limeLightController), true);
    button_10.whenPressed(new SH_Shooting_Start(shooter, limeLightController), true);

    xbox_dpad_Up.whenPressed(new CLB_ReverseWinch(climber, intake, shooter), true);
    xbox_dpad_Up.whenReleased(new CLB_StopWinch(climber), true);
    xbox_dpad_Down.whenPressed(new CLB_StartWinch(climber, intake, shooter), true);
    xbox_dpad_Down.whenReleased(new CLB_StopWinch(climber), true);
    xbox_dpad_Left.whenPressed(new CLB_Deploy(climber, intake, shooter), true);
    xbox_dpad_Left.whenReleased(new CLB_StopWinch(climber), true);
    xbox_dpad_Right.whenPressed(new CLB_ReverseWinchSlow(climber, intake, shooter), true);
    xbox_dpad_Right.whenReleased(new CLB_StopWinch(climber), true);

        
    joystick_dpad_Up.whenPressed(new CLB_ReverseWinch(climber, intake, shooter), true);
    joystick_dpad_Up.whenReleased(new CLB_StopWinch(climber), true);
    joystick_dpad_Down.whenPressed(new CLB_StartWinch(climber, intake, shooter), true);
    joystick_dpad_Down.whenReleased(new CLB_StopWinch(climber), true);
    joystick_dpad_Left.whenPressed(new CLB_Deploy(climber, intake, shooter), true);
    joystick_dpad_Left.whenReleased(new CLB_StopWinch(climber), true);
    joystick_dpad_Right.whenPressed(new CLB_ReverseWinchSlow(climber, intake, shooter), true);
    joystick_dpad_Right.whenReleased(new CLB_StopWinch(climber), true);

    left_joystick_button.whenPressed(new INT_Toggle(intake));
    right_joystick_button.whenPressed(new INT_Toggle(intake));
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
    return mAutoChooser.getSelected();
      //return new Auto_Shoot_Ball(shooter, injector, drivetrain,leds, intake);
  }

  public void updateVibration()
  {
    xboxController.setRumble(RumbleType.kLeftRumble, SmartDashboard.getNumber("Left Vibrate", 0));
    xboxController.setRumble(RumbleType.kRightRumble, SmartDashboard.getNumber("Right Vibrate", 0));
  }
  public void cancelVibration()
  {
    xboxController.setRumble(RumbleType.kLeftRumble, 0);
    xboxController.setRumble(RumbleType.kRightRumble, 0);
  }
}
