package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;

public class Auto_Drive_Off_Line extends SequentialCommandGroup  {

    public Auto_Drive_Off_Line(Drivetrain drivetrain) {
        addCommands(
        //new LED_Select_Random_Colour(ledController),
        new DT_DriveVectorTime(drivetrain, 0, 0, 0.3, 1),
        new DT_TurnToRelativeAngle(drivetrain, 0.1, 90),
        new DT_DriveVectorTime(drivetrain, 0, 0, 0.3, 1),
        new DT_TurnToRelativeAngle(drivetrain, 0.1, 90),
        new DT_DriveVectorTime(drivetrain, 0, 0, 0.3, 1),
        new DT_TurnToRelativeAngle(drivetrain, 0.1, 90),
        new DT_DriveVectorTime(drivetrain, 0, 0, 0.3, 1),
        new DT_TurnToRelativeAngle(drivetrain, 0.1, 90)
        );
        //addSequential(new cmdTurnToHeading(90));  
    }
}

