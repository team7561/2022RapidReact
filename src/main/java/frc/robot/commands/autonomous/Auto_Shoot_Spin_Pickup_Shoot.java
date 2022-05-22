package frc.robot.commands.autonomous;

import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.INT_Deploy;
import frc.robot.commands.intake.INT_Grabbing_Start;
import frc.robot.commands.intake.INT_Grabbing_Stop;
import frc.robot.commands.intake.INT_Retract;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.TimerCommand;
import frc.robot.Constants;
import frc.robot.SwerveMode;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Shoot_Spin_Pickup_Shoot extends SequentialCommandGroup  {

    public Auto_Shoot_Spin_Pickup_Shoot(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake) {
        
        // ParallelCommandGroup finishes when they're all finished.
        // ParallelDeadlineGroup finishes when the first command finishes.

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
                new SH_Get_To_Speed(shooter)
            ),
            // Shoot Preloaded ball
            new ParallelDeadlineGroup(
                new TimerCommand(1),
                new INJ_Forward(injector),
                //new INJ_Index_Ball(injector), // Or index ball if this command works to be quicker
                new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
            ),
            // Turn the robot 180 deg
            new ParallelCommandGroup(
                new DT_TurnToRelativeAngle(drivetrain, 0.13, 180),
                new INJ_Stop(injector)
            ),
            // Deploy the intake
            new ParallelDeadlineGroup(
                new INT_Deploy(intake)
            ),
            // Drive forward for 0.45 seconds
            new ParallelCommandGroup(
                new DT_DriveVectorTime(drivetrain,0, 0, 0.2, 2),
                new INT_Grabbing_Start(intake)
            ),
            // Turn the robot 180 deg
            new SequentialCommandGroup(
                new DT_TurnToAbsoluteAngle(drivetrain, 0.13, 0),
                new INT_Grabbing_Stop(intake)
            ),
            // Drive forward for 0.45 seconds
            new ParallelCommandGroup(
                new DT_DriveVectorTime(drivetrain,0, 0, 0.1, 1),
                new INT_Retract(intake)
            ),
            // Shoot recently acquired ball
            new ParallelDeadlineGroup(
                new TimerCommand(1),
                new INJ_Forward(injector),
                //new INJ_Index_Ball(injector), // Or index ball if this command works to be quicker
                new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
            ),
            new ParallelDeadlineGroup(
                new TimerCommand(1),
                new SH_Stop(shooter),
                new INJ_Stop(injector)
            )
        );
    }
}