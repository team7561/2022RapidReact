package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Conveyor extends SubsystemBase {
  private CANSparkMax m_conveyor;
  private final double speed = 0.5;

  public Conveyor() {
      m_conveyor = new CANSparkMax(Ports.Conveyor_ID, MotorType.kBrushless);
      m_conveyor.restoreFactoryDefaults();
      m_conveyor.setIdleMode(IdleMode.kBrake);
  }

  public void goUp() {
      m_conveyor.set(speed);
  }

  public void goDown() {
      m_conveyor.set(-speed);
  }

  public void end() {
      m_conveyor.set(0.0);
  }
}