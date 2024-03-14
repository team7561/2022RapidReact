package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.drivetrain.DT_AutoArcadeDrive;
import frc.robot.commands.shooter.SH_Shoot;
import frc.robot.commands.shooter.SH_Stop;
import frc.robot.subsystems.*;

/** */
public class ShootPreloaded extends SequentialCommandGroup {

  public ShootPreloaded(Shooter shooter, Conveyor conveyor, Drivetrain drivetrain) {
    addCommands(
      new ParallelCommandGroup(new TimerCommand(2), new SH_Shoot(shooter)),
      new SH_Stop(shooter),
      new DT_AutoArcadeDrive(drivetrain, -1, 0, 0.2, 2)
    );
  }
  
}
