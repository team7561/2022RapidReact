package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;

public class Auto_Score extends SequentialCommandGroup {

  public Auto_Score(Drivetrain drivetrain, LED_Controller ledController, Intake intake) {
    addCommands(new ParallelCommandGroup(new DT_AutoArcadeDrive(drivetrain, 0, 1, 0.2, 1)));

    // ParallelCommandGroup finishes when they're all finished.
    // ParallelDeadlineGroup finishes when the first command finishes.
  }
}
