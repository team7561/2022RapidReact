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

public class Auto_Shoot_3_Balls extends SequentialCommandGroup  {

    public Auto_Shoot_3_Balls(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake) {
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
            new INJ_Stop(injector),
            new DT_TurnToAngle(drivetrain,0.18,90),
            new INT_Deploy(intake),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
            
        new ParallelCommandGroup(
            new INJ_Stop(injector),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 2.5),
            //new DT_Auto_Cargo_Align(drivetrain, 0.4, 2.5),
            new LED_Select_Random_Colour(ledController),
            new INT_Grabbing_Start(intake)
        ),
            
        new ParallelCommandGroup(
            new DT_TurnToAngle(drivetrain,0.25, -35),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INT_Grabbing_Stop(intake),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 0.45),
            new LED_Select_Random_Colour(ledController)
        ),    
        // Shoot Ball 2
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Forward(injector),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        new ParallelCommandGroup(
            new DT_TurnToAngle(drivetrain,0.25, 180),
            new INJ_Stop(injector),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        new ParallelCommandGroup(
            new TimerCommand(2),
            new INT_Grabbing_Start(intake),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 1),
            new LED_Select_Random_Colour(ledController)
        ),    
        new ParallelDeadlineGroup(
            new TimerCommand(2),
            new DT_TurnToAngle(drivetrain,0.2, 0),
            new INT_Grabbing_Stop(intake),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        new ParallelCommandGroup(
            new TimerCommand(2),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 1),
            new LED_Select_Random_Colour(ledController)
        ),    
        
        // Shoot Ball 3
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Forward(injector),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        new ParallelDeadlineGroup(
            new TimerCommand(2),
            new DT_TurnToAngle(drivetrain,0.25, 135),
            new INJ_Stop(injector),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        new ParallelCommandGroup(
            new TimerCommand(2),
            new INT_Grabbing_Start(intake),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 1),
            new LED_Select_Random_Colour(ledController)
        ),    
        new ParallelCommandGroup(
            new DT_TurnToAngle(drivetrain,0.25, 0),
            new INT_Grabbing_Stop(intake),
            new LED_Select_Random_Colour(ledController)
        ),
        new ParallelCommandGroup(
            new TimerCommand(2),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 1),
            new LED_Select_Random_Colour(ledController)
        ),    
        // Shoot Ball 4
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