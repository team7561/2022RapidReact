package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DT_Auto_Cargo_Align;
import frc.robot.commands.drivetrain.DT_DriveVectorTime;
import frc.robot.commands.drivetrain.DT_TurnToAngle;
import frc.robot.commands.injector.INJ_Forward;
import frc.robot.commands.injector.INJ_Index_Ball;
import frc.robot.commands.injector.INJ_Stop;
import frc.robot.commands.intake.INT_Deploy;
import frc.robot.commands.intake.INT_Grabbing_Start;
import frc.robot.commands.intake.INT_Grabbing_Stop;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TimerCommand;
import frc.robot.Constants;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Shoot_Ball extends SequentialCommandGroup  {

    public Auto_Shoot_Ball(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake) {
        addCommands(
        new LED_Select_Random_Colour(ledController),
        new ParallelCommandGroup(
            //new DT_TurnToAngle(drivetrain,0.4,30),
            new LED_Select_Random_Colour(ledController),
            new SH_Perfect_Shot(shooter)
            ),
        new ParallelCommandGroup(
                new TimerCommand(4),
                new SH_Shooting_Start(shooter),
                new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        // Pre loaded ball
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Forward(injector),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Stop(injector),
            new SH_Stop(shooter),
            new INT_Grabbing_Start(intake),
            new LED_Select_Random_Colour(ledController)
        )

            
        //addSequential(new cmdTurnToHeading(90));  
        );
    }
}