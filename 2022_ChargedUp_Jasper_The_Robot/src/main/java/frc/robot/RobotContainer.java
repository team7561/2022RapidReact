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
  private Joystick  joystick = new Joystick(0); //Logitech Extreme 3D Pro Joysick Controller
  private XboxController xboxController = new XboxController(1);
  private final LEDController leds = new LEDController();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(new DT_SwerveDrive(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getTwist()*0.7, () -> (joystick.getThrottle()+1)/2));
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

    final POVButton joystick_dpad_Up = new POVButton(joystick, 0);
    final POVButton joystick_dpad_Down = new POVButton(joystick, 180);
    final POVButton joystick_dpad_Left = new POVButton(joystick, 270);
    final POVButton joystick_dpad_Right = new POVButton(joystick, 90);

    SmartDashboard.putData("Save swerve steer offsets", new DT_Save_Offsets());
    SmartDashboard.putData("Drive manual align", new DT_ManualAlign(drivetrain));
   
    //trigger.onTrue(new DT_ArcadeDrive(drivetrain, 0.4, 0.4, 0.5),true);
    
    trigger.onTrue(new INT_Grabbing_Start(intake));
    trigger.onFalse(new INT_Grabbing_Stop(intake));
    button_2.onTrue(new INT_EjectBall(intake));
    button_2.onFalse(new INT_Grabbing_Stop(intake));

    button_3.onTrue(new INT_Toggle(intake));

    button_4.onTrue(new DT_Drive_Change_Mode(drivetrain, SwerveMode.HUB_TRACK));

    button_6.onTrue(new SH_Perfect_Shot(shooter));
    
    button_7.onTrue(new DT_Drive_Change_Mode(drivetrain, SwerveMode.ROBOTCENTRICSWERVE));
    button_8.onTrue(new DT_Drive_Change_Mode(drivetrain, SwerveMode.SPIN));
    button_9.onTrue(new DT_Drive_Change_Mode(drivetrain, SwerveMode.ULTIMATESWERVE));
    button_10.onTrue(new DT_Drive_Change_Mode(drivetrain, SwerveMode.ULTIMATEDEFENCE));
    button_11.onTrue(new DT_Drive_ResetEncoders(drivetrain));
    button_12.onTrue(new DT_Drive_Reset_Gyro(drivetrain));

    button_LT.onTrue(new INJ_Reverse_Index_Ball(injector));
    button_RT.onTrue(new INJ_Index_Ball(injector));
    button_LB.onTrue(new INJ_Reverse(injector));
    button_LB.onFalse(new INJ_Stop(injector));
    button_RB.onTrue(new INJ_Forward(injector));
    button_RB.onFalse(new INJ_Stop(injector));

    button_Y.onTrue(new SH_Far_Shot(shooter));
    button_A.onTrue(new SH_Close_Shot(shooter)); 
    button_X.onTrue(new SH_Perfect_Shot(shooter));

    back.onTrue(new SH_Shooting_Stop(shooter, limeLightController));
    start.onTrue(new SH_Shooting_Start(shooter, limeLightController));

    xbox_dpad_Up.onTrue(new CLB_ReverseWinch(climber, intake, shooter));
    xbox_dpad_Up.onFalse(new CLB_StopWinch(climber));
    xbox_dpad_Down.onTrue(new CLB_StartWinch(climber, intake, shooter));
    xbox_dpad_Down.onFalse(new CLB_StopWinch(climber));
    xbox_dpad_Left.onTrue(new CLB_Deploy(climber, intake, shooter));
    xbox_dpad_Left.onFalse(new CLB_StopWinch(climber));
    xbox_dpad_Right.onTrue(new CLB_ReverseWinchSlow(climber, intake, shooter));
    xbox_dpad_Right.onFalse(new CLB_StopWinch(climber));

        
    joystick_dpad_Up.onTrue(new CLB_ReverseWinch(climber, intake, shooter));
    joystick_dpad_Up.onFalse(new CLB_StopWinch(climber));
    joystick_dpad_Down.onTrue(new CLB_StartWinch(climber, intake, shooter));
    joystick_dpad_Down.onFalse(new CLB_StopWinch(climber));
    joystick_dpad_Left.onTrue(new CLB_Deploy(climber, intake, shooter));
    joystick_dpad_Left.onFalse(new CLB_StopWinch(climber));
    joystick_dpad_Right.onTrue(new CLB_ReverseWinchSlow(climber, intake, shooter));
    joystick_dpad_Right.onFalse(new CLB_StopWinch(climber));

    left_joystick_button.onTrue(new INT_Toggle(intake));
    right_joystick_button.onTrue(new INT_Toggle(intake));
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