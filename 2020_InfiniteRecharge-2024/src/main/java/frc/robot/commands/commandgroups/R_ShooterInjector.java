package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.commands.shooter.*;
import frc.robot.subsystems.Injector;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.injector.*;

public class R_ShooterInjector extends ParallelCommandGroup {

  public R_ShooterInjector(Shooter shooter, Injector injector) {
        addCommands(
        // Injector Transfer ball
        new Injector_Transfer_Ball(injector),

        // Shoot At Speed
        new Shooter_ShootAtSpeed(shooter)
        );
  }

}
