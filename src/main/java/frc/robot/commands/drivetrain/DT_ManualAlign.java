package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command that uses an example subsystem.
 */
public class DT_ManualAlign extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;

  public DT_ManualAlign(Drivetrain drivetrain, DoubleSupplier x, DoubleSupplier y, DoubleSupplier twist, DoubleSupplier speed) {
    m_subsystem = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    setOffsets();
    m_subsystem.setTargetAngle(90);
    m_subsystem.updateDashboard();
    drive(0.1, 0.1);
  }

  public void drive(double leftSpeed, double rightSpeed) {
    m_subsystem.moduleD.setVelocity(leftSpeed);
    m_subsystem.moduleC.setVelocity(-rightSpeed);
    m_subsystem.moduleA.setVelocity(leftSpeed);
    m_subsystem.moduleB.setVelocity(-rightSpeed);
  }

  public void setOffsets(){
    if (m_subsystem.moduleD.getAngleOffset() != SmartDashboard.getNumber("BL_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE))
    {
        m_subsystem.moduleD.setAngleOffset(SmartDashboard.getNumber("BL_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleC.getAngleOffset() != SmartDashboard.getNumber("BR_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE))
    {
        m_subsystem.moduleC.setAngleOffset(SmartDashboard.getNumber("BR_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleA.getAngleOffset() != SmartDashboard.getNumber("FL_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE))
    {
      m_subsystem.moduleA.setAngleOffset(SmartDashboard.getNumber("FL_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleB.getAngleOffset() != SmartDashboard.getNumber("FR_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE))
    {
      m_subsystem.moduleB.setAngleOffset(SmartDashboard.getNumber("FR_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
