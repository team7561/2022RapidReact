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
    m_targetAngle = targetAngle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    orig_mode = m_subsystem.getMode();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     // m_subsystem.turnToAngle(m_speed);
     m_subsystem.setMode(SwerveMode.SPIN);
     m_angleDifference = m_targetAngle - m_subsystem.readGyro();
     if ((m_angleDifference > m_targetAngle/2) && (m_angleDifference < 180)) {
      m_subsystem.setSpeed(m_speed);
     }
     if ((m_angleDifference > m_targetAngle/2) && (m_angleDifference > 180)) {
      m_subsystem.setSpeed(m_speed/2);
     }
     if ((m_angleDifference > m_targetAngle/4) && (m_angleDifference < 180)) {
      m_subsystem.setSpeed(m_speed/4);
     }
     if ((m_angleDifference > m_targetAngle/4) && (m_angleDifference > 180)) {
      m_subsystem.setSpeed(m_speed/4);
     }
     else{
      if (m_angleDifference > 180){
        m_subsystem.setSpeed(-m_speed);
      }
      else if (m_angleDifference < 180){
        m_subsystem.setSpeed(-m_speed);
      }
     }
     m_subsystem.updateDashboard();
   }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setMode(orig_mode);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {        
        return (m_angleDifference <= Constants.ANGLE_TOLERANCE);
  }
}
