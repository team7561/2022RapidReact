package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.commands.TimerCommand;

public class Auto_Injector_Detect_Ball extends SequentialCommandGroup  {

    public Auto_Injector_Detect_Ball(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake) {
        addCommands(
        // Set robot up with right setpoint, and reset gyro for drift
        new ParallelCommandGroup(
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOWGLITTER),
            new DT_Drive_Reset_Gyro(drivetrain),
            new SH_Perfect_Shot(shooter)
            ),
        //new DT_TurnToAngle(drivetrain,0.4,30), //Add this if robot isn't starting pointing to goal
        
        // Shooter has up to 3 seconds to get to setpoint
        new ParallelCommandGroup(
            new INT_Deploy(intake)
        ),
        // Shoot Preloaded ball
        new ParallelDeadlineGroup(
            new INJ_Detect_Ball_Intaken(injector, 10),
            new INT_Grabbing_Start(intake),
            //new INJ_Index_Ball(injector), // Or index ball if this command works to be quicker
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        ),
        // Turn 90 degrees to get ready to pick up the next ball
        new ParallelCommandGroup(
            new INT_Grabbing_Stop(intake),
            new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_LIGHTCHASE)
        )
        );
        //addSequential(new cmdTurnToHeading(90));
    }
}

