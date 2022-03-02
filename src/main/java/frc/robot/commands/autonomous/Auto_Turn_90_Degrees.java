package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.commands.LED_Set_Colour_Mode;

public class Auto_Turn_90_Degrees extends SequentialCommandGroup  {

    public Auto_Turn_90_Degrees(Drivetrain drivetrain, LEDController ledController) {
        addCommands(
        new LED_Select_Random_Colour(ledController),
        new DT_Drive_Reset_Gyro(drivetrain),
        new ParallelCommandGroup(
                new DT_TurnToRelativeAngle(drivetrain,0.18,90),
                new LED_Set_Colour_Mode(ledController, Constants.BLINKIN_RAINBOW)
        )
            
        //addSequential(new cmdTurnToHeading(90));  
        );
    }
}

