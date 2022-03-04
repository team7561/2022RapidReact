package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.TimerCommand;
import frc.robot.Constants;
import frc.robot.SwerveMode;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Shoot_2_Balls_Tracking extends SequentialCommandGroup  {

    public Auto_Shoot_2_Balls_Tracking(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake, LimeLightController lc) {
        addCommands(
        // Set robot up with right setpoint, and reset gyro for gyro drift
        new ParallelCommandGroup(
            //new DT_TurnToAngle(drivetrain,0.4,30), //If robot didn't start pointing towards goal
            new LED_Select_Random_Colour(ledController),
            new DT_Drive_Reset_Gyro(drivetrain),
            new SH_Perfect_Shot(shooter)
            ),
        // Shooter has up to 3 seconds to get to setpoint
        new ParallelCommandGroup(
            new SH_Get_To_Speed(shooter,3), 
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        // Shoot Preloaded ball
        new ParallelDeadlineGroup(
            new TimerCommand(1),
            new INJ_Forward(injector),
            //new INJ_Index_Ball(injector), // Or index ball if this command works to be quicker
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        // Turn around to get the next ball
        new ParallelCommandGroup(
            new INJ_Stop(injector),
            new DT_TurnToRelativeAngle(drivetrain,0.18,90),
            new INT_Deploy(intake),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        // Move forward, tracking the ball until injector has detected a ball, or 2.5 seconds
        new ParallelDeadlineGroup(
            new INJ_Detect_Ball_Intaken(injector, 2.5), 
            new DT_Auto_Cargo_Align(drivetrain, 0.4, 2.5),
            new LED_Select_Random_Colour(ledController),
            new INT_Grabbing_Start(intake)
        ),
        // Turn back to roughly face target
        new ParallelCommandGroup(
            new DT_TurnToRelativeAngle(drivetrain,0.25, -35),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        ),
        // Change to hub tracking mode
        new DT_Drive_Change_Mode(drivetrain, SwerveMode.HUB_TRACK),
        // Drive forward
        new ParallelCommandGroup(
            new INT_Grabbing_Stop(intake),
            new DT_DriveVectorTime(drivetrain,0, 0, 0.4, 0.45),
            new LED_Select_Random_Colour(ledController)
        ),   
        // Shoot Ball 2
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Forward(injector),
            //new INJ_Index_Ball(injector), // Or index ball if this command works to be quicker
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        // Stop subsytems and get ready for teleop
        new ParallelCommandGroup(
            new TimerCommand(1),
            new INJ_Stop(injector),
            new SH_Stop(shooter),
            new DT_Drive_Change_Mode(drivetrain, SwerveMode.ULTIMATESWERVE),
            new INT_Grabbing_Stop(intake),
            new LED_Select_Random_Colour(ledController)
        )  
        );
    }
}