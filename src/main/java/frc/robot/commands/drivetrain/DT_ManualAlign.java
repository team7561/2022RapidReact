package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import frc.robot.SwerveMode;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DT_ManualAlign extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_drivetrain;

  public DT_ManualAlign(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    SmartDashboard.putNumber("Drive fixed angle", 0);
    m_drivetrain.setMode(SwerveMode.ROBOTCENTRICSWERVE);
  }

  @Override
  public void execute() {
    m_drivetrain.setTargetAngle(SmartDashboard.getNumber("Drive fixed angle", 0));
    m_drivetrain.moduleA.setTargetAngle(90);
    drive(0.1, 0.1);
  }

  public void drive(double leftSpeed, double rightSpeed) {
    m_drivetrain.moduleD.setVelocity(leftSpeed);
    m_drivetrain.moduleC.setVelocity(-rightSpeed);
    m_drivetrain.moduleA.setVelocity(leftSpeed);
    m_drivetrain.moduleB.setVelocity(-rightSpeed);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
