package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.intake.*;
import frc.robot.commands.injector.*;
import frc.robot.subsystems.*;

public class AutoTesting extends SequentialCommandGroup  {

    public AutoTesting(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, LimeLightController visionController) {
        addCommands(
        new LED_Select_Random_Colour(ledController),
        new INT_Deploy(intake),
        new INT_Grabbing_Start(intake),
        new INJ_Detect_Ball_Intaken(injector, 5),
        new INJ_Forward(injector),
        new TimerCommand(0.15),
        new INJ_Stop(injector),
        new INT_Grabbing_Stop(intake)
        );
        //addSequential(new cmdTurnToHeading(90));
    }
}

