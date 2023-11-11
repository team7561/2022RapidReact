package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.autoBalance;
import frc.robot.commands.Arm.*;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;

public class Auto_Balance extends SequentialCommandGroup {

  public Auto_Balance(
      Drivetrain drivetrain, LED_Controller ledController, Intake m_intake, Arm m_arm) {
    autoBalance m_autoBalance = new autoBalance();
    addCommands(
        new ParallelDeadlineGroup(new TimerCommand(1), new Arm_Extend(m_arm)),
        new ParallelCommandGroup(
            new DT_AutoArcadeDriveNoTime(drivetrain, 1, -m_autoBalance.autoBalanceRoutine())),
        new LED_Select_Random_Colour(ledController));

    // ParallelCommandGroup finishes when they're all finished.
    // ParallelDeadlineGroup finishes when the first command finishes.
  }
}
