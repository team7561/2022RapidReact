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
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Shoot_Ball extends SequentialCommandGroup  {

    public Auto_Shoot_Ball(Shooter shooter, Injector injector, Drivetrain drivetrain, LEDController ledController, Intake intake, LimeLightController visionController) {
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