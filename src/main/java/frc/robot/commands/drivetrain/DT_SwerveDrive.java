package frc.robot.commands.drivetrain;

import frc.robot.SwerveMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DT_SwerveDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private DoubleSupplier m_x, m_y, m_twist, m_speed;
  private double abs_x, abs_y, target_angle, m_power, current_angle = 0;

  public DT_SwerveDrive(Drivetrain drivetrain, DoubleSupplier x, DoubleSupplier y, DoubleSupplier twist, DoubleSupplier speed) {
    m_subsystem = drivetrain;
    m_x = x;
    m_y = y;
    m_speed = speed;
    m_twist = twist;
    
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //Set module offsets
    setOffsets();

    //Joystick input in magnitude/direction from
    m_power = Math.sqrt(Math.pow(m_x.getAsDouble() , 2) + Math.pow(m_y.getAsDouble() , 2)) * m_speed.getAsDouble();
    target_angle = Math.atan2(m_y.getAsDouble(), m_x.getAsDouble()) + Math.PI;
    target_angle = target_angle * 360 / (2 * Math.PI);

    //Mode specific code
    if (m_subsystem.getMode() == SwerveMode.SPIN){
      m_power = m_twist.getAsDouble() * m_speed.getAsDouble();
      m_subsystem.setTargetAngle(target_angle);
      drive(m_power, m_power);
    }
    
    if (m_subsystem.getMode() == SwerveMode.BALL_TRACK){
      //double ballTwist = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("ballCam").getEntry("targetYaw").getDouble(0);
      double ballTwist = SmartDashboard.getNumber("ball_x",0);
      m_subsystem.setSwerveVector(ballTwist * 0.0085, target_angle + 180, -m_power * m_speed.getAsDouble());
      System.out.println(ballTwist);
    }
    
    if (m_subsystem.getMode() == SwerveMode.HUB_TRACK){
      double targetTwist = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("targetYaw").getDouble(0);
      m_subsystem.setSwerveVector(targetTwist * 0.0045, target_angle + 180, -m_power * m_speed.getAsDouble());
      System.out.println(targetTwist);
    }

    if (m_subsystem.getMode() == SwerveMode.ULTIMATESWERVE || m_subsystem.getMode() == SwerveMode.ROBOTCENTRICSWERVE){
      double swTwist = 0;
      double swPower = 0;

      //Filtering Inputs for central deadzone in twist input
      if (Math.abs(m_twist.getAsDouble()) > 0.01){
        swTwist = m_twist.getAsDouble() * 0.3;
      }

      //Filtering Inputs for central deadzone in translatoin input
      if(Math.abs(m_power * m_speed.getAsDouble()) > 0.01 ){
        swPower = -m_power * m_speed.getAsDouble();
      } 

      if (swPower != 0 || swTwist != 0){
        if (m_subsystem.getMode() == SwerveMode.ROBOTCENTRICSWERVE){
          m_subsystem.setSwerveVectorNoGyro(swTwist, target_angle + 180, swPower);
        } else {
          m_subsystem.setSwerveVector(swTwist, target_angle + 180, swPower);
        }
      }
      else {
        m_subsystem.stop();
      }
    }

    if (m_subsystem.getMode() == SwerveMode.ULTIMATEDEFENCE){
      double swTwist = 0;
      double swPower = 0;

      //Filtering Inputs for central deadzone in twist input
      if (Math.abs(m_twist.getAsDouble()) > 0.01){
        swTwist = m_twist.getAsDouble() * 0.6;
      }

      //Filtering Inputs for central deadzone in translatoin input
      if(Math.abs(m_power * m_speed.getAsDouble()) > 0.01 ){
        swPower = -m_power * m_speed.getAsDouble() * 0.4;
      } 

      m_subsystem.setSwerveVectorNoGyro(swTwist, target_angle + 180, swPower);
    }
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

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
