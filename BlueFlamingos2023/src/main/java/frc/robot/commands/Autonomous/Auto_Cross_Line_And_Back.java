package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;

public class Auto_Cross_Line_And_Back extends SequentialCommandGroup {

  public Auto_Cross_Line_And_Back(Drivetrain m_drivetrain) {
    addCommands(
        new SequentialCommandGroup(
            new DT_AutoArcadeDrive(m_drivetrain, 0, 1, 0.1, 5),
            new DT_AutoArcadeDrive(m_drivetrain, 0, -1, 0.1, 5)));
  }
}
