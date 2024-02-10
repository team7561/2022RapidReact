package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import frc.robot.Ports;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conveyor extends SubsystemBase {
  //private CANSparkMax m_conveyor;
  private CANSparkMax m_conveyor;
  private final double speed = 0.3;

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

    public void stop() {
        m_conveyor.set(0.0);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Conveyor Speed", m_conveyor.get());
        SmartDashboard.putNumber("Conveyor Temp", m_conveyor.getMotorTemperature());
        SmartDashboard.putNumber("Conveyor Current", m_conveyor.getOutputCurrent());
        SmartDashboard.putNumber("Conveyor Velocity", m_conveyor.getEncoder().getVelocity());
    }
}