package frc.robot.commands.autonomous;

import frc.robot.commands.TimerCommand;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;

public class Auto_2_Ball_A extends SequentialCommandGroup  {

    public Auto_2_Ball_A(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, LimeLightController visionController) {
        addCommands(
            new DT_Drive_Reset_Gyro(drivetrain),
            new INT_Deploy(intake),
            new SH_Perfect_Shot(shooter),
            new SH_Shooting_Start(shooter, visionController),
            new LED_Select_Random_Colour(ledController),
            new DT_TurnToAbsoluteAngle(drivetrain, 0.1, 135),
            new DT_DriveVectorTime(drivetrain, 0, 0, 0.25, 1.4),
            new TimerCommand(0.3),
            new INT_Grabbing_Start(intake),
            new TimerCommand(0.8),
            new ParallelDeadlineGroup(
                new TimerCommand(0.2),
                new INJ_Forward(injector)
            ),
            new INJ_Stop(injector),
            new INT_Grabbing_Stop(intake),
            new INT_Retract(intake),
            new SH_Shooting_Stop(shooter, visionController)
        );
        //addSequential(new cmdTurnToHeading(90));
    }
}

