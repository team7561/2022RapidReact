package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.drivetrain.DT_ArcadeDrive;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.SH_Shoot;
import frc.robot.commands.shooter.SH_Stop;
import frc.robot.subsystems.*;

public class RobotContainer {
  Intake intake = new Intake();
  Climber climber = new Climber();
  Shooter shooter = new Shooter();
  Conveyor conveyor = new Conveyor();
  Drivetrain drivetrain = new Drivetrain();
  LED_Controller led_Controller = new LED_Controller();
  Vision_Controller vision_Controller = new Vision_Controller();

  Joystick joystick = new Joystick(0);
  
  public RobotContainer() {
    configureBindings();
    drivetrain.setDefaultCommand( new DT_ArcadeDrive(drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getThrottle()
    ));
  }

  private void configureBindings() {
    JoystickButton triggerButton = new JoystickButton(joystick, 1);
    JoystickButton thumbButton = new JoystickButton(joystick, 2);
    JoystickButton button3 = new JoystickButton(joystick, 3);
    JoystickButton button5 = new JoystickButton(joystick, 5);
    JoystickButton button6 = new JoystickButton(joystick, 6);

    triggerButton.onTrue(new IN_Grab(intake));
    triggerButton.onFalse(new IN_Stop(intake));
    thumbButton.onTrue(new IN_Reverse(intake));
    thumbButton.onFalse(new IN_Stop(intake));
    button3.onTrue(new SH_Shoot(shooter));
    button3.onFalse(new SH_Stop(shooter));
    button5.onTrue(new CO_GoUp(conveyor));
    button5.onFalse(new CO_Stop(conveyor));
    button6.onTrue(new CO_GoDown(conveyor));
    button6.onFalse(new CO_Stop(conveyor));
    

  }

  public Command getAutonomousCommand() {
    
    return Commands.print("No autonomous command configured");
  }
}
