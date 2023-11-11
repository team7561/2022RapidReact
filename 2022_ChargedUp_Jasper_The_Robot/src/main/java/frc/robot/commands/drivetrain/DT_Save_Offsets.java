package frc.robot.commands.drivetrain;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DT_Save_Offsets extends CommandBase {

  public DT_Save_Offsets() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Preferences.setDouble("A Offset", SmartDashboard.getNumber("A_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE));
    Preferences.setDouble("B Offset", SmartDashboard.getNumber("B_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE));
    Preferences.setDouble("C Offset", SmartDashboard.getNumber("C_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE));
    Preferences.setDouble("D Offset", SmartDashboard.getNumber("D_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Offsets saved");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
