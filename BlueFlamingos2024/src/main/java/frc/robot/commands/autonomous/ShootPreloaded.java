package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.drivetrain.DT_AutoArcadeDrive;
import frc.robot.commands.intake.IN_Grab;
import frc.robot.commands.shooter.SH_Shoot_Time;
import frc.robot.commands.shooter.SH_Stop;
import frc.robot.subsystems.*;

public class ShootPreloaded extends SequentialCommandGroup {

  public ShootPreloaded(Shooter shooter, Conveyor conveyor, Drivetrain drivetrain, Intake intake) {
    addCommands(
      new ParallelCommandGroup(new SH_Shoot_Time(shooter, 3), new CO_Stop_Time(conveyor, 3), Commands.print("Step 1")),
      new ParallelCommandGroup(new SH_Shoot_Time(shooter, 2), new CO_GoUp_Time(conveyor, 2), Commands.print("Step 2")),
      new ParallelDeadlineGroup(new TimerCommand(1), new SH_Stop(shooter), new CO_Stop_Time(conveyor, 1), Commands.print("Step 3")), 
      new ParallelDeadlineGroup(new DT_AutoArcadeDrive(drivetrain, 0, -1, 0.25, 1.5), new IN_Grab(intake), new CO_GoUp_Time(conveyor, 1.5), Commands.print("Step 4")),
      new ParallelDeadlineGroup(new DT_AutoArcadeDrive(drivetrain, 0, 1, 0.25, 1.5), new CO_GoDown_Time(conveyor, 0.5), Commands.print("Step 5")),
      new ParallelCommandGroup(new SH_Shoot_Time(shooter, 3), new CO_Stop_Time(conveyor, 3), Commands.print("Step 6")),
      new ParallelCommandGroup(new SH_Shoot_Time(shooter, 2), new CO_GoUp_Time(conveyor, 2), Commands.print("Step 7"))
    );
  }  
}