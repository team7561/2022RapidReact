package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.injector.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.*;
import frc.robot.subsystems.*;

public class Auto_Do_Nothing extends SequentialCommandGroup  {

    public Auto_Do_Nothing(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, VisionController visionController) {
        addCommands(new LED_Select_Random_Colour(ledController));
        //addSequential(new cmdTurnToHeading(90));
    
        
    }
}

