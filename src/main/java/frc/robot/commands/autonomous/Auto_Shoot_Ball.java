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

public class Auto_Shoot_Ball extends SequentialCommandGroup  {

    public Auto_Shoot_Ball(Shooter shooter, Injector injector) {
        addCommands(
        //new LED_Select_Random_Colour(ledController),
        new SH_Auto_Shoot_Ball(shooter, injector, 500, 500)
        //addSequential(new cmdTurnToHeading(90));  
        );
    }
}