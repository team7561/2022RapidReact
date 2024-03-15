package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.autonomous.ShootPreloaded;
import frc.robot.commands.climber.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.drivetrain.DT_ArcadeDrive;
//import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.states.ToggleClimbingMode;
import frc.robot.commands.states.ToggleConveyorMode;
import frc.robot.subsystems.*;

public class RobotContainer {
  Intake intake = new Intake();
  Climber climber = new Climber();
  Shooter shooter = new Shooter();
  Conveyor conveyor = new Conveyor();
  Drivetrain drivetrain = new Drivetrain();
  //LED_Controller led_Controller = new LED_Controller();
  Vision_Controller vision_Controller = new Vision_Controller();

  Joystick joystick = new Joystick(0);
  XboxController xboxController = new XboxController(2);

  public RobotContainer() {
    configureBindings();
    CameraServer.startAutomaticCapture();
    drivetrain.setDefaultCommand( new DT_ArcadeDrive(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getThrottle()));
    //drivetrain.setDefaultCommand( new DT_ArcadeDrive(drivetrain, () -> xboxController.getLeftX(), () -> xboxController.getLeftY(), () -> 0.1));
  }

  private void configureBindings()
  {
    configureBindingsJoystick(); 
  }

  /*private void configureBindingsXbox()
  {
    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    //final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);

    final JoystickButton button_LB = new JoystickButton(xboxController, 5);
    final JoystickButton button_RB = new JoystickButton(xboxController, 6);

    final JoystickButton back = new JoystickButton(xboxController, 7);
    final JoystickButton start = new JoystickButton(xboxController, 8);
    //final JoystickButton left_joystick_button = new JoystickButton(xboxController, 9);
    //final JoystickButton right_joystick_button = new JoystickButton(xboxController, 10);


    button_LB.onTrue(new CO_GoUp(conveyor));
    button_LB.onFalse(new CO_Stop(conveyor));
    button_RB.onTrue(new CO_GoDown(conveyor));
    button_RB.onFalse(new CO_Stop(conveyor));
    
    button_A.onTrue(new SH_Shoot(shooter));
    button_A.onFalse(new SH_Stop(shooter));
    back.onTrue(new CL_Extend(climber));
    start.onTrue(new CL_Retract(climber));
    button_X.onTrue(new CL_Lower(climber));
    button_X.onFalse(new CL_Stop(climber));
    button_Y.onTrue(new CL_Climb(climber));
    button_Y.onFalse(new CL_Stop(climber));

  }*/

  private void configureBindingsJoystick() {

  
    /*final DPadButton xbox_dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton xbox_dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton xbox_dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton xbox_dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);*/
    
    JoystickButton triggerButton = new JoystickButton(joystick, 1);
    JoystickButton thumbButton = new JoystickButton(joystick, 2);
    JoystickButton button3 = new JoystickButton(joystick, 3);
    JoystickButton button5 = new JoystickButton(joystick, 5);
    JoystickButton button6 = new JoystickButton(joystick, 6);
    JoystickButton button7 = new JoystickButton(joystick, 7);
    JoystickButton button8 = new JoystickButton(joystick, 8);
    JoystickButton button9 = new JoystickButton(joystick, 9);
    JoystickButton button10 = new JoystickButton(joystick, 10);
    
    JoystickButton button11 = new JoystickButton(joystick, 11);
    JoystickButton button12 = new JoystickButton(joystick, 12);

    triggerButton.onTrue(new CO_GoUp(conveyor));
    triggerButton.onFalse(new CO_Stop(conveyor));
    thumbButton.onTrue(new CO_GoDown(conveyor));
    thumbButton.onFalse(new CO_Stop(conveyor));
    /*triggerButton.onTrue(new IN_Grab(intake));
    triggerButton.onFalse(new IN_Stop(intake));
    thumbButton.onTrue(new IN_Reverse(intake));
    thumbButton.onFalse(new IN_Stop(intake));*/
    button3.onTrue(new ToggleConveyorMode());
    button6.onTrue(new CO_GoUp(conveyor));
    button6.onFalse(new CO_Stop(conveyor));
    button5.onTrue(new CO_GoDown(conveyor));
    button5.onFalse(new CO_Stop(conveyor));
    button7.onTrue(new ToggleConveyorMode());
    button8.onTrue(new ToggleClimbingMode());
    button9.onTrue(new CL_Extend(climber));
    button10.onTrue(new CL_Retract(climber));
    button11.onTrue(new CL_Lower(climber));
    button11.onFalse(new CL_Stop(climber));
    button12.onTrue(new CL_Climb(climber));
    button12.onFalse(new CL_Stop(climber));
  }

  public Command getAutonomousCommand() {
    return new ShootPreloaded(shooter, conveyor, drivetrain);
  }
}
