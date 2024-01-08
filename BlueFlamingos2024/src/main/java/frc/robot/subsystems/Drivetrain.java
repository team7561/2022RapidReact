package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private BuiltInAccelerometer mRioAccel;

  private CANSparkMax leftMotorA;
  private CANSparkMax leftMotorB;
  private CANSparkMax rightMotorA;
  private CANSparkMax rightMotorB;
  private double spin = 1;

  public Drivetrain() {

    mRioAccel = new BuiltInAccelerometer();

    leftMotorA = new CANSparkMax(1, MotorType.kBrushless);
    leftMotorA.restoreFactoryDefaults();
    leftMotorA.setIdleMode(IdleMode.kCoast);
    leftMotorA.burnFlash();

    leftMotorB = new CANSparkMax(2, MotorType.kBrushless);
    leftMotorB.restoreFactoryDefaults();
    leftMotorB.setIdleMode(IdleMode.kCoast);

    rightMotorA = new CANSparkMax(4, MotorType.kBrushless);
    rightMotorA.restoreFactoryDefaults();
    rightMotorA.setIdleMode(IdleMode.kCoast);

    rightMotorB = new CANSparkMax(5, MotorType.kBrushless);
    rightMotorB.restoreFactoryDefaults();
    rightMotorB.setIdleMode(IdleMode.kCoast);
    rightMotorB.burnFlash();
  }

  @Override
  public void periodic() {
    updateDashboard();
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
      set(left, right);
    } else {
      set(left, right);
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

    /*SmartDashboard.putNumber("Accel X", mRioAccel.getX());
    SmartDashboard.putNumber("Accel Y", mRioAccel.getY());
    SmartDashboard.putNumber("Accel Z", mRioAccel.getZ());
    SmartDashboard.putNumber("DT Tilt", getTilt());*/

  }
}
