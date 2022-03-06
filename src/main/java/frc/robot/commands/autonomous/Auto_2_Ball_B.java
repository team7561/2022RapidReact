package frc.robot.commands.autonomous;

import frc.robot.commands.TimerCommand;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;

public class Auto_2_Ball_B extends SequentialCommandGroup  {

    public Auto_2_Ball_B(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, LimeLightController visionController) {
        addCommands(
            new DT_Drive_Reset_Gyro(drivetrain),
            new LED_Select_Random_Colour(ledController),
            new INT_Deploy(intake),
            new INT_Grabbing_Start(intake),
            new DT_DriveVectorTime(drivetrain, 0, 0, 0.2, 1.7),
            new TimerCommand(1),
            new ParallelDeadlineGroup(
                new TimerCommand(0.2),
                new INJ_Forward(injector)
            ),
            new INJ_Stop(injector),
            new INT_Grabbing_Stop(intake),
            new INT_Retract(intake),
            new SH_Perfect_Shot(shooter),
            new SH_Shooting_Start(shooter),
            new DT_TurnToAbsoluteAngle(drivetrain, 0.1, 180),
            new DT_DriveVectorTime(drivetrain, 0, 0, 0.15, 1.2),
            new SH_Get_To_Speed(shooter),
            new ParallelCommandGroup(
                new TimerCommand(0.5),
                new INJ_Forward(injector)
            ),
            new SH_Get_To_Speed(shooter),
            new ParallelDeadlineGroup(
                new TimerCommand(0.7),
                new INJ_Forward(injector)
            ),
            new INT_Retract(intake),
            new SH_Shooting_Stop(shooter),
            new INJ_Stop(injector)
        );
        //addSequential(new cmdTurnToHeading(90));
    }
}

