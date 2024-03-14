package frc.robot.subsystems;

import frc.robot.Ports;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {
  //private CANSparkMax m_conveyor;
  private CANSparkMax m_conveyor;
  private final double speed = 0.4;
  private boolean m_holding_note;
  Timer conveyorTimer = new Timer();

    public Conveyor() {
        m_conveyor = new CANSparkMax(Ports.Conveyor_ID, MotorType.kBrushless);
        m_conveyor.restoreFactoryDefaults();
        m_conveyor.setIdleMode(IdleMode.kBrake);
        m_holding_note = false;
        SmartDashboard.putBoolean("Holding Note", m_holding_note);

    }

    public void goUp() {
        m_conveyor.set(-speed);
    }

    public void goUpSlow() {
        m_conveyor.set(-speed/2);
    }

    public void goDown() {
        m_conveyor.set(speed);
    }

    public void stop() {
        m_conveyor.set(0.0);
    }

    public void periodic()
    {
        updateDashboard();
        m_holding_note = SmartDashboard.getBoolean("Holding Note", false);
        if (!m_holding_note)
        {
            conveyorTimer.reset();
            goUp();
        }
        else {
            conveyorTimer.start();
            if (conveyorTimer.get()>0.1)
            {
                stop();
            }
            else
            {
                goUpSlow();
            }
        }

        /*if (SmartDashboard.getBoolean("Robot Shooting?", false))
        {
          stop();
        }*/
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("Conveyor Speed", m_conveyor.get());
        SmartDashboard.putNumber("Conveyor Current", m_conveyor.getOutputCurrent());
        SmartDashboard.putNumber("Conveyor Velocity", m_conveyor.getEncoder().getVelocity());
    }
}