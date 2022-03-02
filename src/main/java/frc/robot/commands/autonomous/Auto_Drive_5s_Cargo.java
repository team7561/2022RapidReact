package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Drive_5s_Cargo extends SequentialCommandGroup  {

    public Auto_Drive_5s_Cargo(Drivetrain drivetrain, LEDController ledController) {
        addCommands(
            new LED_Select_Random_Colour(ledController),
            new DT_Drive_Reset_Gyro(drivetrain),
            new ParallelCommandGroup(
                new DT_Auto_Cargo_Align(drivetrain, 0.15, 7),
                new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
            ));
        //addSequential(new cmdTurnToHeading(90));
    }
}

