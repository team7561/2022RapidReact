package frc.robot.commands.autonomous.state;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class Stop extends Command {
    private final Conveyor m_conveyor;
    private final Climber m_climber;
    private final Drivetrain m_drivetrain;
    private final Shooter m_shooter;

public Stop(Climber climber, Conveyor conveyor, Drivetrain drivetrain, Shooter shooter) {
    m_climber = climber;
    m_conveyor = conveyor;
    m_drivetrain = drivetrain;
    m_shooter = shooter;

    addRequirements(conveyor, drivetrain, climber, shooter);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climber.stop();
    m_conveyor.stop();
    m_drivetrain.stop();
    m_shooter.stop();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
