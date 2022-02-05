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

public class Auto_Drive_Off_Line extends SequentialCommandGroup  {

    public Auto_Drive_Off_Line(Drivetrain drivetrain, Intake intake, Shooter shooter, Injector injector, LEDController ledController, VisionController visionController) {
        addCommands(
        new LED_Select_Random_Colour(ledController),
        new DT_DriveVectorTime(drivetrain, 0, 0.3, 15, 2)
        );
        //addSequential(new cmdTurnToHeading(90));
    
        
    }
}

