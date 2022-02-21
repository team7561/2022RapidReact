package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.led_controller.LED_Select_Random_Colour;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;

public class Auto1 extends SequentialCommandGroup  {

    public Auto1(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, LimeLightController visionController) {
        addCommands(
            new ParallelDeadlineGroup(new TimerCommand(4),
                new DT_TurnToAngle(drivetrain, 0.4, 0.4),
                new SH_Shooting_Start(shooter),
                new INT_Deploy(intake),
                new LED_Select_Random_Colour(ledController)
                ),
            new ParallelDeadlineGroup(new TimerCommand(3),
                new SH_Extend(shooter),
                new LED_Select_Random_Colour(ledController)
                ),
        
        new ParallelDeadlineGroup(new TimerCommand(0.5),
            new INJ_Index_Ball(injector),
            new INT_Deploy(intake),
            new LED_Select_Random_Colour(ledController)
        ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INT_Deploy(intake),
            new LED_Select_Random_Colour(ledController)
            ),
        new ParallelDeadlineGroup(new TimerCommand(0.5),
            new INT_Grabbing_Start(intake),
            new LED_Select_Random_Colour(ledController)
                ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INT_Deploy(intake),
            new LED_Select_Random_Colour(ledController)
            ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INT_Deploy(intake),
            new LED_Select_Random_Colour(ledController)
            ),
        //new DT_DriveDistance(drivetrain, 0.5, 1),
        //new DT_TurnToVisionAngle(drivetrain, visionController, () -> 0.4),
        new INT_Retract(intake),
        new DT_DriveVectorTime(drivetrain, 0, 0.3, 15, 2),

        new ParallelCommandGroup(
            new DT_Drive_Stop(drivetrain),
            new SH_Stop(shooter),
            new INJ_Stop(injector),
            new LED_Select_Random_Colour(ledController)
            )
        );
        //addSequential(new cmdTurnToHeading(90));
    
        
    }
}

