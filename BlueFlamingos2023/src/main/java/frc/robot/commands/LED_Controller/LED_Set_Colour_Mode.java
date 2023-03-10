package frc.robot.commands.LED_Controller;

import frc.robot.subsystems.LED_Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class LED_Set_Colour_Mode extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LED_Controller m_subsystem;
  double m_number;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsy
   * stem used by this command.
   */
  public LED_Set_Colour_Mode(LED_Controller subsystem, double number) {
    m_subsystem = subsystem;
    m_number = number;
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
    m_subsystem.setValue(m_number);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
