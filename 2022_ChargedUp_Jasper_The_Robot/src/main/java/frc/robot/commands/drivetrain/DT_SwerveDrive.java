package frc.robot.commands.drivetrain;

import frc.robot.SwerveMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

public class DT_SwerveDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain m_subsystem;
  private DoubleSupplier m_x, m_y, m_twist, m_speed;
  private double target_angle, m_power;

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
    double x_power = 0;
    if (Math.abs(m_x.getAsDouble()) > 0.05)
    {
      x_power = m_x.getAsDouble();
    }

    double y_power = 0;
    if (Math.abs(m_y.getAsDouble()) > 0.05)
    {
      y_power = m_y.getAsDouble();
    }

    //Joystick input in magnitude/direction from
    m_power = Math.sqrt(Math.pow(x_power , 2) + Math.pow(y_power , 2)) * m_speed.getAsDouble();
    target_angle = Math.atan2(y_power, x_power) + Math.PI;
    target_angle = target_angle * 360 / (2 * Math.PI);

    
    double twist = 0;
    
    if (m_twist.getAsDouble() > 0.1)
    {
      twist = (0.5*Math.sin((Math.PI*m_twist.getAsDouble())-Math.PI/2))+0.5;
      //twist = m_twist.getAsDouble();
    }
    if (m_twist.getAsDouble() < -0.1)
    {
      twist = -((0.5*Math.sin((Math.PI*m_twist.getAsDouble())-Math.PI/2))+0.5);
      //twist = m_twist.getAsDouble();
    }
    
    //Math.pow(m_twist.getAsDouble(), 1.5);//.getAsDouble()*m_twist.getAsDouble()*Math.signum(m_twist.getAsDouble());
    //Mode specific code
    if (m_subsystem.getMode() == SwerveMode.SPIN){
      m_power = twist * m_speed.getAsDouble();
      m_subsystem.setTargetAngle(target_angle);
      drive(m_power, m_power);
    }
    
    if (m_subsystem.getMode() == SwerveMode.BALL_TRACK){
      //double ballTwist = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("ballCam").getEntry("targetYaw").getDouble(0);
      double ballTwist = SmartDashboard.getNumber("ball_x",0);
      m_subsystem.setSwerveVector(ballTwist * 0.005, target_angle + 180, -m_power * m_speed.getAsDouble());
      System.out.println(ballTwist);
    }
    
    if (m_subsystem.getMode() == SwerveMode.HUB_TRACK){
      //Photonvision: double targetTwist = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("targetYaw").getDouble(0);
      double targetTwist = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      m_subsystem.setSwerveVector(targetTwist * 0.0045, target_angle + 180, -m_power * m_speed.getAsDouble());
      System.out.println(targetTwist);
    }

    if (m_subsystem.getMode() == SwerveMode.ULTIMATESWERVE || m_subsystem.getMode() == SwerveMode.ROBOTCENTRICSWERVE){
      double swTwist = 0;
      double swPower = 0;

      //Filtering Inputs for central deadzone in twist input
      swTwist = twist * 0.3;

      //Filtering Inputs for central deadzone in translation input
      swPower = -m_power * m_speed.getAsDouble();
      
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
      swTwist = twist * 0.6;
      

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


  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
