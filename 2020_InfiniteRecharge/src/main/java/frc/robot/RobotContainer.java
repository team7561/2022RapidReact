/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.trajectory.constraint.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

//import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intakehopper.*;
import frc.robot.commands.shooter.*;
import frc.robot.commands.visioncontroller.*;
import frc.robot.autonomous.Auto1;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.autonomous.*;
import frc.robot.subsystems.*;

public class RobotContainer {
  //private final Climber m_climber = new Climber();
  private final IntakeHopper m_intakeHopper = new IntakeHopper();
  private final Drivetrain m_drivetrain = new Drivetrain();
  public final Shooter m_shooter = new Shooter();
  private final Injector m_injector = new Injector();
  private final VisionController m_visionController = new VisionController();
  private final LEDController m_ledcontroller = new LEDController();

  //HID
  private Joystick joystick = new Joystick(0); //Logitech Extreme 3D Pro Joysick Controller
  private XboxController xboxController = new XboxController(1); //Logitech Gamepad F310 (Xbox Controller)
  
  TrajectoryConfig m_trajConfig = new TrajectoryConfig(Constants.AUTO_MAX_VELOCITY, Constants.AUTO_MAX_ACCEL);

  SendableChooser<List<Pose2d>> m_autoChooser = new SendableChooser<List<Pose2d>>();

  // Create SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    m_drivetrain.setDefaultCommand( new DT_ArcadeDrive(m_drivetrain, () -> joystick.getX(), () -> joystick.getY(), () -> joystick.getThrottle()
    ));
    //m_drivetrain.setDefaultCommand( new ArcadeDrive(m_drivetrain, () -> 0, () -> 0));
    m_shooter.setDefaultCommand( new Shooter_Shooting_Stop(m_shooter));
    /*m_climber.setDefaultCommand( new Climb_StopWinch(m_climber));*/
    m_intakeHopper.setDefaultCommand( new Intake_Grabbing_Stop(m_intakeHopper));
    m_injector.setDefaultCommand( new Injector_Stop(m_injector));
    m_visionController.setDefaultCommand( new VC_TurnOnLED(m_visionController));
    
    // Configure the button bindings
    configureButtonBindings();



    // set up autonomous trajectories
     m_chooser.setDefaultOption("None", null);
     m_chooser.addOption("Slalom", new Slalom(m_drivetrain)); 

     SmartDashboard.putData(m_chooser);
    
    // Put the chooser on the dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);
  }
  
  private void configureButtonBindings() {
    System.out.println("Configure button bindings");
    //creating the buttons for the Joystick Controller

    SmartDashboard.putData("Reset Hood Position", new Shooter_Reset_Hood(m_shooter));
    SmartDashboard.putData("Hood Manual", new Shooter_Auto_Hood(m_shooter, false));
    SmartDashboard.putData("Hood Close", new Shooter_Set_Hood_Setpoint(m_shooter, 0));
    SmartDashboard.putData("Hood Far", new Shooter_Set_Hood_Setpoint(m_shooter, 2));
    SmartDashboard.putData("Hood Vision False", new Shooter_Auto_Vision_Speed(m_shooter, false));

    final JoystickButton trigger =   new JoystickButton(joystick, 1);
    final JoystickButton thumb   =   new JoystickButton(joystick, 2);
    final JoystickButton button_3 =  new JoystickButton(joystick, 3);
    final JoystickButton button_4 =  new JoystickButton(joystick, 4);
    final JoystickButton button_5 =  new JoystickButton(joystick, 5);
    final JoystickButton button_6 =  new JoystickButton(joystick, 6);
    final JoystickButton button_7 =  new JoystickButton(joystick, 7);
    final JoystickButton button_8 =  new JoystickButton(joystick, 8);
    final JoystickButton button_9 =  new JoystickButton(joystick, 9);
    final JoystickButton button_10 = new JoystickButton(joystick, 10);
    final JoystickButton button_11 = new JoystickButton(joystick, 11);
    final JoystickButton button_12 = new JoystickButton(joystick, 12);

    //binding buttons to commands for the Joystick Controller
    trigger.onTrue(new Intake_GrabBall(m_intakeHopper)); //spins intake while held and not interuptable by other driver
    trigger.onFalse(new Intake_Grabbing_Stop(m_intakeHopper)); //spins intake while held
    thumb.onTrue(new Intake_EjectBall(m_intakeHopper)); // Eject the ball
    thumb.onFalse(new Intake_Grabbing_Stop(m_intakeHopper)); // Stop grabbing the ball
    button_3.onTrue(new DT_TurnToVisionAngle(m_drivetrain, m_visionController, () -> (joystick.getThrottle()+1)/2).withTimeout(5)); // Turns the drivetrain to the right vision angle
    button_4.onTrue(new DT_ResetDrivePose(m_drivetrain)); // Start Winch
    //button_4.onTrue(new Climb_StartWinch(m_climber)); // Start Winch
    button_5.onTrue(new Shooter_ShootAtSpeed(m_shooter));  // Shoot at speed
    button_6.onTrue(new Shooter_Shooting_Stop(m_shooter)); // Stop shooting
    
    button_7.onTrue(new Intake_RetractHopper(m_intakeHopper)); // Extend Hopper
    button_8.onTrue(new Intake_ExtendHopper(m_intakeHopper)); // Reatract Hopper

    /*button_9.onTrue(new Climb_StartWinch(m_climber)); // Stop grabbing
    button_9.onFalse(new Climb_StopWinch(m_climber)); // Stop the Winch
    button_10.onTrue(new Climb_RaiseHook(m_climber)); // Raise the Hook
    button_10.onFalse(new Climb_StopWinch(m_climber)); // Stop the Winch*/
    button_11.onTrue(new Shooter_Retract(m_shooter));  // Extend the Shooter Hood
    button_11.onFalse(new Shooter_Stop_Hood(m_shooter)); // Stop the Shooter Hood
    button_12.onTrue(new Shooter_Extend(m_shooter)); // Retract the Shooter Hood
    button_12.onFalse(new Shooter_Stop_Hood(m_shooter)); // Stop the Shooter Hood
    
    //creating the buttons for the Xbox Controller
    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);

    //final JoystickButton button_LB = new JoystickButton(xboxController, 5);
    final JoystickButton button_RB = new JoystickButton(xboxController, 6);

    //final JoystickButton back = new JoystickButton(xboxController, 7);
    final JoystickButton start = new JoystickButton(xboxController, 8);
    //final JoystickButton left_joooooooooooooystick_button = new JoystickButton(xboxController, 9);
    //final JoystickButton right_joystick_button = new JoystickButton(xboxController, 10);
    //final JoystickAnalogButton LT = new JoystickAnalogButton(xboxController, 2);
    final JoystickAnalogButton RT = new JoystickAnalogButton(xboxController, 3);
  
    final DPadButton dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);
    //binding buttons to commands for the Xbox Controller
    
    button_A.onTrue(new Injector_Transfer_Ball(m_injector));
    button_A.onFalse(new Injector_Stop(m_injector));
    button_B.onTrue(new Injector_Reverse(m_injector));
    button_B.onFalse(new Injector_Stop(m_injector));
    button_X.onTrue(new Shooter_Retract(m_shooter));
    button_X.onFalse(new Shooter_Stop_Hood(m_shooter)); // Stop the Shooter Hood
    button_Y.onTrue(new Shooter_Extend(m_shooter));
    button_Y.onFalse(new Shooter_Stop_Hood(m_shooter)); // Stop the Shooter Hood
    //button_LB.onTrue(new R_ShooterInjector(m_shooter, m_injector));
    start.onTrue(new LED_Select_Random_Colour(m_ledcontroller));
    button_RB.onTrue(new Intake_ToggleHopper(m_intakeHopper));

   /*RT.onTrue(new Climb_ReverseWinch(m_climber));
    RT.onFalse(new Climb_StopWinch(m_climber));*/

  }
  

  public Command getAutonomousCommand() {
    return new Auto1(m_drivetrain, m_intakeHopper, m_shooter, m_injector, m_ledcontroller, m_visionController);
  }
  /*
  TrajectoryConfig config = new TrajectoryConfig(0.1, 0.1);
  config.setKinematics(m_drivetrain.getKinematics());
    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      Arrays.asList(new Pose2d(), new Pose2d(1.0, 0, new Rotation2d())),
      config
  );
  RamseteCommand command = new RamseteCommand(
    trajectory,
    m_drivetrain::getPose,
    new RamseteController(2, .7),
    m_drivetrain.getFeedforward(),
    m_drivetrain.getKinematics(),
    m_drivetrain::getWheelSpeeds,
    m_drivetrain.getLeftPIDController(),
    m_drivetrain.getRightPIDController(),
    m_drivetrain::setOutputVolts,
    m_drivetrain
  );*/
  //return new DT_InitDrivePose(m_drivetrain, 0,0).andThen(command.andThen(() -> m_drivetrain.setOutputVolts(0, 0)));

 // return m_chooser.getSelected();
  //}  
}