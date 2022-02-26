package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.LED_Select_Random_Colour;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard;

public class Auto_Chooser extends SequentialCommandGroup  {

    public Auto_Chooser() {
        mAutoChooser = new SendableChooser<>();
        mAutoChooser.setDefaultOption("Do Nothing", StartingPosition.LEFT_HAB_2);
        mAutoChooser.addOption("Drive Off Line", StartingPosition.RIGHT_HAB_2);
        mAutoChooser.addOption("Drive and Shoot", StartingPosition.RIGHT_HAB_1);
        mAutoChooser.addOption("Left HAB 1", StartingPosition.LEFT_HAB_1);
        mAutoChooser.addOption("Center HAB 1", StartingPosition.CENTER_HAB_1);
    
        SmartDashboard.putData("Auto", mAutoChooser);
    }
}

