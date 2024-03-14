package frc.robot.commands.states;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ToggleConveyorMode extends Command {

  public ToggleConveyorMode() {
  }

  @Override
  public void initialize() {
    SmartDashboard.putBoolean("Holding Note", !SmartDashboard.getBoolean("Holding Note", false));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

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
