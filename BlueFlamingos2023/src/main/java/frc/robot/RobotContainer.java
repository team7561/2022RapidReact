package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Arm.*;
import frc.robot.commands.Elevator.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.LED_Controller.LED_Set_Colour_Mode;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

  public final Intake m_intake = new Intake();
  public final Drivetrain m_drivetrain = new Drivetrain();
  public final Elevator m_elevator = new Elevator();
  public final Arm m_Arm = new Arm();
  public final LED_Controller m_Led_Controller = new LED_Controller();

// Joysticks
//private final XboxController xboxController1 = new XboxController(1);
private final Joystick joystick1 = new Joystick(0);
private final XboxController xboxController = new XboxController(1);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
    // Smartdashboard Subsystems


    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    //SmartDashboard.putData("Intake_Grab", new Intake_Open(m_intake));
    //SmartDashboard.putData("Intake_Release", new Intak-e_Close( m_intake ));
    //SmartDashboard.putData("Elevator_Raise", new Elevator_Raise());
    //SmartDashboard.putData("Elevator_Lower", new Elevator_Lower( m_elevator ));

    CameraServer.startAutomaticCapture();
    configureButtonBindings();

  
    m_chooser.setDefaultOption("Drive forwards", new DT_AutoArcadeDrive(m_drivetrain, 0, 1, 0.2, 1));
    
    m_drivetrain.setDefaultCommand(new DT_ArcadeDrive(m_drivetrain, () -> joystick1.getX(), () -> joystick1.getY(), () -> joystick1.getThrottle(), () -> joystick1.getTrigger()));
    m_elevator.setDefaultCommand(new Elevator_Analog_Control(m_elevator, () -> xboxController.getRightY()));
    m_Arm.setDefaultCommand(new Arm_Analog_Control(m_Arm, () -> xboxController.getLeftY()));
    

  
    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }


public void init()
{
  m_elevator.stop();
  m_Arm.stop();
}
private void configureButtonBindings() {

  final JoystickButton joystickButton1 = new JoystickButton(joystick1, 1); 
  final JoystickButton joystickButton2 = new JoystickButton(joystick1, 2); 
  final JoystickButton joystickButton3 = new JoystickButton(joystick1, 3);    
  final JoystickButton joystickButton4 = new JoystickButton(joystick1, 4);    
  final JoystickButton joystickButton5 = new JoystickButton(joystick1, 5);    
  final JoystickButton joystickButton7 = new JoystickButton(joystick1, 7);    
  //final JoystickButton joystickButton8 = new JoystickButton(joystick1, 8);    
  //final JoystickButton joystickButton9 = new JoystickButton(joystick1, 9);        
  final JoystickButton joystickButton10 = new JoystickButton(joystick1, 10);        
  //final JoystickButton joystickButton11 = new JoystickButton(joystick1, 11);        
  final JoystickButton joystickButton12 = new JoystickButton(joystick1, 12);        


  final JoystickButton button_A = new JoystickButton(xboxController, 1);
  final JoystickButton button_B = new JoystickButton(xboxController, 2);
  final JoystickButton button_X = new JoystickButton(xboxController, 3);
  final JoystickButton button_Y = new JoystickButton(xboxController, 4);

  final JoystickButton button_LB = new JoystickButton(xboxController, 5);
  final JoystickButton button_RB = new JoystickButton(xboxController, 6);

  final JoystickButton back = new JoystickButton(xboxController, 7);
  final JoystickButton start = new JoystickButton(xboxController, 8);
  //final JoystickButton left_joystick_button = new JoystickButton(xboxController, 9);
  //final JoystickButton right_joystick_button = new JoystickButton(xboxController, 10);
  final JoystickAnalogButton button_LT = new JoystickAnalogButton(xboxController, 2);
  final JoystickAnalogButton button_RT = new JoystickAnalogButton(xboxController, 3);

  //final POVButton xbox_dpad_Up = new POVButton(xboxController, 180);
  //final POVButton xbox_dpad_Down = new POVButton(xboxController, 0);
  final POVButton xbox_dpad_Left = new POVButton(xboxController, 90);
  final POVButton xbox_dpad_Right = new POVButton(xboxController, 270);
//  final POVButton xbox_dpad_Up = new POVButton(xboxController, 180);
 // final DPadButton xbox_dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
 // final DPadButton xbox_dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
 // final DPadButton xbox_dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);



//  button_RT.onTrue(new Intake_Close(m_intake));
      
joystickButton10.onTrue(new DT_SetSpin(m_drivetrain, 0.1));
joystickButton12.onTrue(new DT_SetSpin(m_drivetrain, 1));

back.onTrue(new LED_Set_Colour_Mode(m_Led_Controller, Constants.BLINKIN_YELLOW)); 
start.onTrue(new LED_Set_Colour_Mode(m_Led_Controller, Constants.BLINKIN_VIOLET)); 
button_X.onTrue(new LED_Set_Colour_Mode(m_Led_Controller, Constants.BLINKIN_COLOUR_WAVE_RAINBOW)); 
button_Y.onTrue(new LED_Set_Colour_Mode(m_Led_Controller, Constants.BLINKIN_RAINBOWGLITTER)); 

/*   button_LB.onTrue(new Intake_Reverse(m_intake));
  button_LB.onFalse(new Intake_Stop(m_intake));
  button_RB.onTrue(new Intake_Grab(m_intake));
  button_RB.onFalse(new Intake_Stop(m_intake));
*/
  /*button_LB.onTrue(new Intake_Close(m_intake));
  button_RB.onTrue(new Intake_Open(m_intake));

  button_LT.onTrue(new Intake_Close(m_intake));
  button_RT.onTrue(new Intake_Open(m_intake));*/

  button_LB.onTrue(new Intake_Toggle(m_intake));
  button_RB.onTrue(new Intake_Toggle(m_intake));

  button_LT.onTrue(new Intake_Toggle(m_intake));
  button_RT.onTrue(new Intake_Toggle(m_intake));

  button_A.onTrue(new Arm_Toggle(m_Arm));
  button_B.onTrue(new Arm_Toggle(m_Arm));
/*
  button_A.onTrue(new Elevator_Raise(m_elevator));
  button_A.onFalse(new Elevator_Stop(m_elevator));
  button_B.onTrue(new Elevator_Lower(m_elevator));
  button_B.onFalse(new Elevator_Stop(m_elevator));*/

  xbox_dpad_Left.onTrue(new Arm_Extend(m_Arm));
  xbox_dpad_Right.onTrue(new Arm_Retract(m_Arm));

}

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
  

}

