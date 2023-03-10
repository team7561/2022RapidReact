package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.Arm.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.Constants;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.LED_Controller.LED_Set_Colour_Mode;

public class Auto_Score extends SequentialCommandGroup  {

    public Auto_Score(Drivetrain drivetrain, LED_Controller ledController, Intake intake) {
        
        // ParallelCommandGroup finishes when they're all finished.
        // ParallelDeadlineGroup finishes when the first command finishes.
    }
}