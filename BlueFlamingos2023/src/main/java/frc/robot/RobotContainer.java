package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Elevator.Elevator_Lower;
import frc.robot.commands.Elevator.Elevator_Raise;
import frc.robot.commands.Elevator.Elevator_Stop;
import frc.robot.commands.Intake.*;
import frc.robot.commands.drivetrain.DT_ArcadeDrive;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

// The robot's subsystems
    //public final Elevator m_elevator = new Elevator();
    public final Intake m_intake = new Intake();
    public final Drivetrain m_drivetrain = new Drivetrain();
    public final Elevator m_elevator = new Elevator();

// Joysticks
//private final XboxController xboxController1 = new XboxController(1);
private final Joystick joystick1 = new Joystick(0);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
    // Smartdashboard Subsystems


    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    SmartDashboard.putData("Intake_Grab", new Intake_Open(m_intake));
    SmartDashboard.putData("Intake_Release", new Intake_Close( m_intake ));
    //SmartDashboard.putData("Elevator_Raise", new Elevator_Raise());
    //SmartDashboard.putData("Elevator_Lower", new Elevator_Lower( m_elevator ));

    configureButtonBindings();

  
    m_chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());
    //m_drivetrain.setDefaultCommand(new DT_ArcadeDrive(m_drivetrain, () -> joystick1.getX(), () -> joystick1.getY(), () -> joystick1.getThrottle()));
  
    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

private void configureButtonBindings() {

final JoystickButton joystickButton1 = new JoystickButton(joystick1, 1); 
final JoystickButton joystickButton2 = new JoystickButton(joystick1, 2); 
final JoystickButton joystickButton3 = new JoystickButton(joystick1, 3);    
final JoystickButton joystickButton4 = new JoystickButton(joystick1, 4);    
final JoystickButton joystickButton5 = new JoystickButton(joystick1, 5);    
final JoystickButton joystickButton7 = new JoystickButton(joystick1, 7);    
final JoystickButton joystickButton8 = new JoystickButton(joystick1, 8);    
final JoystickButton joystickButton9 = new JoystickButton(joystick1, 9);        
//joystickButton1.onTrue(new Intake_Deploy(m_intake).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
//joystickButton2.onTrue(new Intake_Retract(m_intake).withInterruptBehavior(InterruptionBehavior.kCancelSelf));
    
joystickButton1.onTrue(new Intake_Grab(m_intake));
joystickButton2.onTrue(new Intake_Grab_Fast(m_intake));
joystickButton3.onTrue(new Intake_Stop(m_intake));
joystickButton4.onTrue(new Intake_Open(m_intake));
joystickButton5.onTrue(new Intake_Close(m_intake));
joystickButton7.whenPressed(new Elevator_Raise(m_elevator));
joystickButton8.whenPressed(new Elevator_Lower(m_elevator));
joystickButton9.whenPressed(new Elevator_Stop(m_elevator));

  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
  

}

