package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {

  private CANSparkMax elevatorWinchMotorA;
  private CANSparkMax elevatorWinchMotorB;
  private MotorControllerGroup elevatorWinchGroup;

  private double elevatorSpeed = 0;

  public Elevator() {
    elevatorWinchMotorA = new CANSparkMax(30, MotorType.kBrushless);

    elevatorWinchMotorA.restoreFactoryDefaults();
    elevatorWinchMotorA.setInverted(false);
    elevatorWinchMotorA.setIdleMode(IdleMode.kCoast);
    elevatorWinchMotorA.setSmartCurrentLimit(10);
    elevatorWinchMotorA.burnFlash();

    elevatorWinchMotorB = new CANSparkMax(31, MotorType.kBrushless);

    elevatorWinchMotorB.restoreFactoryDefaults();
    elevatorWinchMotorB.setInverted(false);
    elevatorWinchMotorB.setIdleMode(IdleMode.kCoast);
    elevatorWinchMotorB.setSmartCurrentLimit(10);
    elevatorWinchMotorB.burnFlash();

    elevatorWinchMotorB.follow(elevatorWinchMotorA);

    elevatorWinchGroup = new MotorControllerGroup(elevatorWinchMotorA, elevatorWinchMotorB);
  }

  @Override
  public void periodic() {
    if (Math.abs(elevatorSpeed) > 0.05) {
      elevatorWinchGroup.set(elevatorSpeed * 0.85);
    } else {
      elevatorWinchGroup.set(0);
    }
    // elevatorWinchGroup.set(elevatorSpeed);
    // SmartDashboard.putBoolean("Elevator Upper Limit", limitSwitchUpper.get());
    updateDashboard();
  }

  public void setSpeed(double speed) {
    elevatorSpeed = speed;
  }

  public void lift() {
    elevatorSpeed = 0.1;
  }

  public void lower() {
    elevatorSpeed = -0.1;
  }

  public void stop() {
    elevatorSpeed = 0;
  }

  public void updateDashboard() {
    SmartDashboard.putNumber("Elevator Speed", elevatorSpeed);
    SmartDashboard.putNumber("Elevator Current A", elevatorWinchMotorA.getOutputCurrent());
    SmartDashboard.putNumber("Elevator Current B", elevatorWinchMotorB.getOutputCurrent());
  }
}
