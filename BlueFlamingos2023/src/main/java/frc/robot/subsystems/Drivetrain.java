package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {

private CANSparkMax leftMotorA;
private CANSparkMax leftMotorB;
private CANSparkMax rightMotorA;
private CANSparkMax rightMotorB;

public Drivetrain() {

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

    }
    public void arcadeDrive(double x, double y, double speed, boolean inverted) {
        double left = (-y + x)*speed;
        double right = (y + x)*speed;
      
        left = Math.min(left, 1);
        right = Math.min(right, 1);

        left = Math.max(left, -1);
        right = Math.max(right, -1);

        if (inverted) {
            set(left, right);
        }
        else
        {
            set(left, right);
        }
        SmartDashboard.putNumber("Left", left);
        SmartDashboard.putNumber("Right", right);
    }
    public void set(double left, double right) {
        leftMotorA.set(left);
        leftMotorA.set(left);
        rightMotorA.set(right);
        rightMotorB.set(right);
      }
      public void updateDashboard() {
        SmartDashboard.putNumber("Drive Left A Current",leftMotorA.getOutputCurrent());
        SmartDashboard.putNumber("Drive Left B Current",leftMotorB.getOutputCurrent());
        SmartDashboard.putNumber("Drive Right A Current",rightMotorA.getOutputCurrent());
        SmartDashboard.putNumber("Drive Right B Current",rightMotorB.getOutputCurrent());

      }
}