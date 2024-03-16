package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.utility.Coordinate;

public class Drivetrain extends SubsystemBase {

  private BuiltInAccelerometer mRioAccel;

  public Coordinate currentCoordinate;
  private ADXRS450_Gyro gyro;
  private boolean intakeForwards;
  private CANSparkMax leftMotorA;
  private CANSparkMax leftMotorB;
  private CANSparkMax rightMotorA;
  private CANSparkMax rightMotorB;
  private RelativeEncoder leftMotorA_Encoder, leftMotorB_Encoder, rightMotorA_Encoder, rightMotorB_Encoder;
  private double spin = 1;

  public Drivetrain() {

    gyro = new ADXRS450_Gyro();
    intakeForwards = false;
    mRioAccel = new BuiltInAccelerometer();

    leftMotorA = new CANSparkMax(Ports.Drivetrain_FL_ID, MotorType.kBrushless);
    leftMotorA.restoreFactoryDefaults();
    leftMotorA.setIdleMode(IdleMode.kCoast);
    leftMotorA.burnFlash();
    leftMotorA_Encoder = leftMotorA.getEncoder();

    leftMotorB = new CANSparkMax(Ports.Drivetrain_BL_ID, MotorType.kBrushless);
    leftMotorB.restoreFactoryDefaults();
    leftMotorB.setIdleMode(IdleMode.kCoast);
    leftMotorB_Encoder = leftMotorB.getEncoder();

    rightMotorA = new CANSparkMax(Ports.Drivetrain_FR_ID, MotorType.kBrushless);
    rightMotorA.restoreFactoryDefaults();
    rightMotorA.setIdleMode(IdleMode.kCoast);
    rightMotorA_Encoder = rightMotorA.getEncoder();

    rightMotorB = new CANSparkMax(Ports.Drivetrain_BR_ID, MotorType.kBrushless);
    rightMotorB.restoreFactoryDefaults();
    rightMotorB.setIdleMode(IdleMode.kCoast);
    rightMotorB.burnFlash();
    rightMotorB_Encoder = rightMotorB.getEncoder();

    //gyro.calibrate();
  }

  @Override
  public void periodic() {
    updateDashboard();
    if (Constants.AUTO_MODE)
    {
      intakeForwards = SmartDashboard.getBoolean("Holding Note", true);
    }
  }
  
  public double getGyroRotation() {
    return gyro.getAngle();
  }

  public void resetEncoders() {
    leftMotorA_Encoder.setPosition(0);
    leftMotorB_Encoder.setPosition(0);
    rightMotorA_Encoder.setPosition(0);
    rightMotorB_Encoder.setPosition(0);
  }

  public void setBrake() {
    leftMotorA.setIdleMode(IdleMode.kBrake);
    leftMotorB.setIdleMode(IdleMode.kBrake);
    rightMotorA.setIdleMode(IdleMode.kBrake);
    rightMotorB.setIdleMode(IdleMode.kBrake);
  }

  public void setCoast() {
    leftMotorA.setIdleMode(IdleMode.kCoast);
    leftMotorB.setIdleMode(IdleMode.kCoast);
    rightMotorA.setIdleMode(IdleMode.kCoast);
    rightMotorB.setIdleMode(IdleMode.kCoast);
  }

  public void stop() {
    set(0, 0);
  }
  public double getLeftEncoder() {
    return 0.5*(leftMotorA_Encoder.getPosition()+leftMotorB_Encoder.getPosition());
  }
  public double getRightEncoder() {
    return 0.6*(rightMotorA_Encoder.getPosition() + rightMotorB_Encoder.getPosition());
  }
  public void drive(double left, double right) {
    //set(right, left);
    if (intakeForwards)
    {
      //set(right, left);
    }
    else 
    {
      //set(left,right);
    }
    if (SmartDashboard.getBoolean("Cylinder Extended?", false))
    {
      set(right/5, left/5);
    }
    else 
    {
      set(left,right);
    }
  }
  public void arcadeDrive(double x, double y, double speed, boolean inverted) {
    x = x * spin;
    double left = (-y + x) * speed;
    double right = (y + x) * speed;

    left = Math.min(left, 1);
    right = Math.min(right, 1);

    left = Math.max(left, -1);
    right = Math.max(right, -1);

    if (inverted) {
      drive(left, right);
    } else {
      drive(left, right);
    }
    SmartDashboard.putNumber("Left", left);
    SmartDashboard.putNumber("Right", right);
  }

  public void setSpin(double speed) {
    spin = speed;
  }

  public double getPitch() {
    return Math.atan2(
            (-mRioAccel.getX()),
            Math.sqrt(mRioAccel.getY() * mRioAccel.getY() + mRioAccel.getZ() * mRioAccel.getZ()))
        * 57.3;
  }

  public double getRoll() {
    return Math.atan2(mRioAccel.getY(), mRioAccel.getZ()) * 57.3;
  }

  // returns the magnititude of the robot's tilt calculated by the root of
  // pitch^2 + roll^2, used to compensate for diagonally mounted rio
  public double getTilt() {
    if ((getPitch() + getRoll()) >= 0) {
      return Math.sqrt(getPitch() * getPitch() + getRoll() * getRoll());
    } else {
      return -Math.sqrt(getPitch() * getPitch() + getRoll() * getRoll());
    }
  }

  public void set(double left, double right) {
    leftMotorA.set(left);
    leftMotorA.set(left);
    rightMotorA.set(right);
    rightMotorB.set(right);
  }

  public void updateDashboard() {
    SmartDashboard.putNumber("Drive Left A Current", leftMotorA.getOutputCurrent());
    SmartDashboard.putNumber("Drive Left B Current", leftMotorB.getOutputCurrent());
    SmartDashboard.putNumber("Drive Right A Current", rightMotorA.getOutputCurrent());
    SmartDashboard.putNumber("Drive Right B Current", rightMotorB.getOutputCurrent());

    SmartDashboard.putNumber("Robot Angle", getGyroRotation());
    SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
    SmartDashboard.putNumber("Right Encoder", getRightEncoder());
   
    /*SmartDashboard.putNumber("Accel X", mRioAccel.getX());
    SmartDashboard.putNumber("Accel Y", mRioAccel.getY());
    SmartDashboard.putNumber("Accel Z", mRioAccel.getZ());
    SmartDashboard.putNumber("DT Tilt", getTilt());*/

  }
}
