package frc.robot.commands;

import frc.robot.subsystems.LEDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.LED_Mode;
import frc.robot.SwerveMode;

/**
 * An example command that uses an example subsystem.
 */
public class LED_Teleop extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LEDController m_subsystem;
  private final Drivetrain m_drivetrain;
  private final Shooter m_shooter;
  private final LimeLightController m_lc;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LED_Teleop(LEDController subsystem, Drivetrain drivetrain, Shooter shooter, LimeLightController lc) {
    m_subsystem = subsystem;
    m_drivetrain = drivetrain;
    m_shooter = shooter;
    m_lc = lc;
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
      
    SmartDashboard.putNumber("Left Vibrate", 0);
    SmartDashboard.putNumber("Right Vibrate", 0);
      if (m_drivetrain.isStill() && !m_shooter.shooting){
          SmartDashboard.putNumber("LED Value", -0.20);
      }
      else if (m_drivetrain.getMode() == SwerveMode.ROBOTCENTRICSWERVE){
        if (m_lc.get_ta() != 0 && m_shooter.atSetpoint() && Math.abs(m_lc.get_tx()) < 4){
            SmartDashboard.putNumber("LED Value", 0.77);
            SmartDashboard.putNumber("Left Vibrate", 0.5);
        }
        else if (m_lc.get_ta() != 0){
            SmartDashboard.putNumber("LED Value", -0.99);
            SmartDashboard.putNumber("Left Vibrate", 1);
        }

        else if (m_shooter.atSetpoint()){
            SmartDashboard.putNumber("LED Value", -0.09);
        }
        else{
            SmartDashboard.putNumber("LED Value", -0.11); 
        }
    }

    else if (m_drivetrain.getMode() == SwerveMode.ULTIMATESWERVE){
        if (m_drivetrain.timer.get() < 1){
            SmartDashboard.putNumber("LED Value", 0.85);
        }
        else if (m_lc.get_ta() != 0 && m_shooter.atSetpoint() && Math.abs(m_lc.get_tx()) < 4){
            SmartDashboard.putNumber("LED Value", 0.77);
            SmartDashboard.putNumber("Right Vibrate", 0.5);
        }
        else if (m_lc.get_ta() != 0){
            SmartDashboard.putNumber("LED Value", -0.99);
        }

        else if (m_shooter.atSetpoint()){
            SmartDashboard.putNumber("LED Value", -0.15);
        }
        else{
            SmartDashboard.putNumber("LED Value", -0.17);    
        }
      } else{
        if (m_lc.get_ta() != 0 && m_shooter.atSetpoint() && Math.abs(m_lc.get_tx()) < 4){
            SmartDashboard.putNumber("LED Value", 0.77);
        }
        else if (m_lc.get_ta() != 0){
            SmartDashboard.putNumber("LED Value", -0.99);
        }
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
