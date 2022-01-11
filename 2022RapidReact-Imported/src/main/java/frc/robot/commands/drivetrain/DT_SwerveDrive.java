package frc.robot.commands.drivetrain;

import frc.robot.SwerveMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example command that uses an example subsystem.
 */
public class DT_SwerveDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private DoubleSupplier m_x, m_y, m_twist, m_speed;
  private double abs_x, abs_y, target_angle, m_power, current_angle = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param drivetrain The subsystem used by this command.
   */
  public DT_SwerveDrive(Drivetrain drivetrain, DoubleSupplier x, DoubleSupplier y, DoubleSupplier twist, DoubleSupplier speed) {
    m_subsystem = drivetrain;
    m_x = x;
    m_y = y;
    m_speed = speed;
    m_twist = twist;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    abs_x = Math.abs(m_x.getAsDouble());
    abs_y = Math.abs(m_y.getAsDouble());
    
    setOffsets();

    if (m_subsystem.getMode() == SwerveMode.SPIN){
      m_power = m_twist.getAsDouble() * m_speed.getAsDouble();
    } else {
      m_power = Math.sqrt(Math.pow(abs_x , 2) + Math.pow(abs_y , 2)) * m_speed.getAsDouble();
    }
    
    target_angle = Math.atan2(m_y.getAsDouble(), m_x.getAsDouble())+Math.PI;
    target_angle = target_angle * 360 / (2 * Math.PI);

    System.out.println(target_angle);

    SmartDashboard.putNumber("current_angle", current_angle);
    SmartDashboard.putNumber("measured_angle", m_subsystem.moduleD.getAngle());
    SmartDashboard.putNumber("target_angle", target_angle);

    if(m_subsystem.getMode() == SwerveMode.ULTIMATESWERVE){
      m_subsystem.setSwerveVector(m_twist.getAsDouble() * -0.4, target_angle, -m_power * m_speed.getAsDouble());
    }else{
      m_subsystem.setTargetAngle(target_angle);
      drive(m_power, m_power);
    }
    m_subsystem.updateDashboard();
  }

  public void drive(double leftSpeed, double rightSpeed) {
    m_subsystem.moduleD.setVelocity(leftSpeed);
    m_subsystem.moduleC.setVelocity(-rightSpeed);
    m_subsystem.moduleA.setVelocity(leftSpeed);
    m_subsystem.moduleB.setVelocity(-rightSpeed);
  }

  public void setOffsets(){
    if (m_subsystem.moduleD.getAngleOffset() != SmartDashboard.getNumber("D_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE))
    {
      m_subsystem.moduleD.setAngleOffset(SmartDashboard.getNumber("D_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleC.getAngleOffset() != SmartDashboard.getNumber("C_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE))
    {
      m_subsystem.moduleC.setAngleOffset(SmartDashboard.getNumber("C_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleA.getAngleOffset() != SmartDashboard.getNumber("A_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE))
    {
      m_subsystem.moduleA.setAngleOffset(SmartDashboard.getNumber("A_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE));
    }
    if (m_subsystem.moduleB.getAngleOffset() != SmartDashboard.getNumber("B_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE))
    {
      m_subsystem.moduleB.setAngleOffset(SmartDashboard.getNumber("B_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE));
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
