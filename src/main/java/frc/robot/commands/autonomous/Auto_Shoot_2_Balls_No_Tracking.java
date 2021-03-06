package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.injector.INJ_Stop;
import frc.robot.commands.intake.INT_Deploy;
import frc.robot.commands.intake.INT_Grabbing_Start;
import frc.robot.commands.intake.INT_Grabbing_Stop;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.TimerCommand;
import frc.robot.Constants;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Shoot_2_Balls_No_Tracking extends SequentialCommandGroup  {

    public Auto_Shoot_2_Balls_No_Tracking(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake, LimeLightController visionController) {
        addCommands(
        new LED_Select_Random_Colour(ledController),
        new DT_Drive_Reset_Gyro(drivetrain),
        new ParallelCommandGroup(
            //new DT_TurnToAngle(drivetrain,0.4,30),
            new LED_Select_Random_Colour(ledController),
            new SH_Perfect_Shot(shooter)
            ),
        new ParallelCommandGroup(
                new TimerCommand(4),
                new SH_Shooting_Start(shooter, visionController),
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
            new DT_TurnToRelativeAngle(drivetrain,0.18,90),
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
            new DT_TurnToRelativeAngle(drivetrain,0.25, -35),
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