package frc.robot.commands.drivetrain;

import frc.robot.Constants;
import frc.robot.SwerveMode;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class DT_TurnToAngle extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private final double m_speed;
  private double m_angleDifference;
  private final double m_targetAngle;
  
  private SwerveMode orig_mode;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DT_TurnToAngle(Drivetrain subsystem, double speed, double targetAngle){
    m_subsystem = subsystem;
    m_speed = speed;
    m_targetAngle = (targetAngle + m_subsystem.readGyro()) % 360;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_angleDifference = m_targetAngle - m_subsystem.readGyro();
    System.out.println(m_angleDifference);
    //Error in appropriate direction and magnitude (-180 to 180)
    if (Math.abs(m_angleDifference) > 180){
      m_angleDifference = (360 - Math.abs(m_angleDifference)) * -Math.signum(m_angleDifference);
    }

    //Command doesn't use target angle input in SPIN mode
    m_subsystem.setSwerveVector(-m_speed * 8 *  m_angleDifference / 180, 0, 0);
    m_subsystem.updateDashboard();
   }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {        
        return (Math.abs(m_angleDifference) <= Constants.ANGLE_TOLERANCE);
  }
}
