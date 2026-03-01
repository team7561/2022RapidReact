package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autonomous.Auto1;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.autonomous.Slalom;
import frc.robot.commands.drivetrain.DT_ArcadeDrive;
import frc.robot.commands.drivetrain.DT_ResetDrivePose;
import frc.robot.commands.drivetrain.DT_TurnToVisionAngle;
import frc.robot.commands.injector.Injector_Reverse;
import frc.robot.commands.injector.Injector_Stop;
import frc.robot.commands.injector.Injector_Transfer_Ball;
import frc.robot.commands.intakehopper.Intake_EjectBall;
import frc.robot.commands.intakehopper.Intake_ExtendHopper;
import frc.robot.commands.intakehopper.Intake_GrabBall;
import frc.robot.commands.intakehopper.Intake_Grabbing_Stop;
import frc.robot.commands.intakehopper.Intake_RetractHopper;
import frc.robot.commands.intakehopper.Intake_ToggleHopper;
import frc.robot.commands.shooter.Shooter_Auto_Hood;
import frc.robot.commands.shooter.Shooter_Auto_Vision_Speed;
import frc.robot.commands.shooter.Shooter_Extend;
import frc.robot.commands.shooter.Shooter_Reset_Hood;
import frc.robot.commands.shooter.Shooter_Retract;
import frc.robot.commands.shooter.Shooter_Set_Hood_Setpoint;
import frc.robot.commands.shooter.Shooter_ShootAtSpeed;
import frc.robot.commands.shooter.Shooter_Shooting_Stop;
import frc.robot.commands.shooter.Shooter_Stop_Hood;
import frc.robot.commands.visioncontroller.VC_TurnOnLED;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeHopper;
import frc.robot.subsystems.Injector;
import frc.robot.subsystems.LEDController;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionController;

public class RobotContainer {

  // Subsystems
  private final IntakeHopper m_intakeHopper = new IntakeHopper();
  private final Drivetrain m_drivetrain = new Drivetrain();
  public final Shooter m_shooter = new Shooter();
  private final Injector m_injector = new Injector();
  private final VisionController m_visionController = new VisionController();
  private final LEDController m_ledcontroller = new LEDController();

  // HID
  private final Joystick joystick = new Joystick(0);       // Logitech Extreme 3D Pro
  private final XboxController xboxController = new XboxController(2); // Logitech F310 (XInput)

  // Trajectory config (if still used elsewhere)
  @SuppressWarnings("unused")
  private final TrajectoryConfig m_trajConfig =
      new TrajectoryConfig(Constants.AUTO_MAX_VELOCITY, Constants.AUTO_MAX_ACCEL);

  // Autonomous choosers
  @SuppressWarnings("unused")
  private final SendableChooser<List<Pose2d>> m_autoChooser = new SendableChooser<>();
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    // Default commands
    m_drivetrain.setDefaultCommand(
        new DT_ArcadeDrive(
            m_drivetrain,
            () -> joystick.getX(),
            () -> joystick.getY(),
            () -> joystick.getThrottle()));

    m_shooter.setDefaultCommand(new Shooter_Shooting_Stop(m_shooter));
    m_intakeHopper.setDefaultCommand(new Intake_Grabbing_Stop(m_intakeHopper));
    m_injector.setDefaultCommand(new Injector_Stop(m_injector));
    m_visionController.setDefaultCommand(new VC_TurnOnLED(m_visionController));

    configureButtonBindings();
    configureAutonomousChooser();
  }

  private void configureAutonomousChooser() {
    m_chooser.setDefaultOption("None", null);
    m_chooser.addOption("Slalom", new Slalom(m_drivetrain));

    SmartDashboard.putData("Auto Mode", m_chooser);
    Shuffleboard.getTab("Autonomous").add(m_chooser);
  }

  @SuppressWarnings("unused")
  private void configureButtonBindings() {
    System.out.println("Configure button bindings");

    // SmartDashboard shooter controls
    SmartDashboard.putData("Reset Hood Position", new Shooter_Reset_Hood(m_shooter));
    SmartDashboard.putData("Hood Manual", new Shooter_Auto_Hood(m_shooter, false));
    SmartDashboard.putData("Hood Close", new Shooter_Set_Hood_Setpoint(m_shooter, 0));
    SmartDashboard.putData("Hood Far", new Shooter_Set_Hood_Setpoint(m_shooter, 2));
    SmartDashboard.putData("Hood Vision False", new Shooter_Auto_Vision_Speed(m_shooter, false));

    // Joystick buttons
    final JoystickButton trigger   = new JoystickButton(joystick, 1);
    final JoystickButton thumb     = new JoystickButton(joystick, 2);
    final JoystickButton button_3  = new JoystickButton(joystick, 3);
    final JoystickButton button_4  = new JoystickButton(joystick, 4);
    final JoystickButton button_5  = new JoystickButton(joystick, 5);
    final JoystickButton button_6  = new JoystickButton(joystick, 6);
    final JoystickButton button_7  = new JoystickButton(joystick, 7);
    final JoystickButton button_8  = new JoystickButton(joystick, 8);
    final JoystickButton button_9  = new JoystickButton(joystick, 9);
    final JoystickButton button_10 = new JoystickButton(joystick, 10);
    final JoystickButton button_11 = new JoystickButton(joystick, 11);
    final JoystickButton button_12 = new JoystickButton(joystick, 12);

    // Joystick bindings
    trigger.onTrue(new Intake_GrabBall(m_intakeHopper));
    trigger.onFalse(new Intake_Grabbing_Stop(m_intakeHopper));

    thumb.onTrue(new Intake_EjectBall(m_intakeHopper));
    thumb.onFalse(new Intake_Grabbing_Stop(m_intakeHopper));

    button_3.onTrue(
        new DT_TurnToVisionAngle(
            m_drivetrain,
            m_visionController,
            () -> (joystick.getThrottle() + 1) / 2.0).withTimeout(5));

    button_4.onTrue(new DT_ResetDrivePose(m_drivetrain));

    button_5.onTrue(new Shooter_ShootAtSpeed(m_shooter));
    button_6.onTrue(new Shooter_Shooting_Stop(m_shooter));

    button_7.onTrue(new Intake_RetractHopper(m_intakeHopper));
    button_8.onTrue(new Intake_ExtendHopper(m_intakeHopper));

    button_11.onTrue(new Shooter_Retract(m_shooter));
    button_11.onFalse(new Shooter_Stop_Hood(m_shooter));

    button_12.onTrue(new Shooter_Extend(m_shooter));
    button_12.onFalse(new Shooter_Stop_Hood(m_shooter));

    // Xbox buttons
    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);
    final JoystickButton button_RB = new JoystickButton(xboxController, 6);
    final JoystickButton start = new JoystickButton(xboxController, 8);

    final JoystickAnalogButton RT = new JoystickAnalogButton(xboxController, 3);

    final DPadButton dpad_Up = new DPadButton(xboxController, DPadButton.Direction.UP);
    final DPadButton dpad_Down = new DPadButton(xboxController, DPadButton.Direction.DOWN);
    final DPadButton dpad_Left = new DPadButton(xboxController, DPadButton.Direction.LEFT);
    final DPadButton dpad_Right = new DPadButton(xboxController, DPadButton.Direction.RIGHT);

    // Xbox bindings
    button_A.onTrue(new Injector_Transfer_Ball(m_injector));
    button_A.onFalse(new Injector_Stop(m_injector));

    button_B.onTrue(new Injector_Reverse(m_injector));
    button_B.onFalse(new Injector_Stop(m_injector));

    button_X.onTrue(new Shooter_Retract(m_shooter));
    button_X.onFalse(new Shooter_Stop_Hood(m_shooter));

    button_Y.onTrue(new Shooter_Extend(m_shooter));
    button_Y.onFalse(new Shooter_Stop_Hood(m_shooter));

    start.onTrue(new LED_Select_Random_Colour(m_ledcontroller));
    button_RB.onTrue(new Intake_ToggleHopper(m_intakeHopper));

    // RT, D-pad currently unused in your snippet
  }

  public Command getAutonomousCommand() {
    // If you want to use the chooser instead:
    // return m_chooser.getSelected();
    return new Auto1(
        m_drivetrain,
        m_intakeHopper,
        m_shooter,
        m_injector,
        m_ledcontroller,
        m_visionController);
  }
}