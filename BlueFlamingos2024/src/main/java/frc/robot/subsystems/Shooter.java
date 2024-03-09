package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends SubsystemBase {
  
  private CANSparkMax m_shooter_motor_A, m_shooter_motor_B;
  private final double speedFast = -1;
  private final double speedSlow = -1;

  public Shooter() {

    m_shooter_motor_A = new CANSparkMax(Ports.Shooter_A_ID, MotorType.kBrushless);
    m_shooter_motor_A.restoreFactoryDefaults();
    m_shooter_motor_B = new CANSparkMax(Ports.Shooter_B_ID, MotorType.kBrushless);
    m_shooter_motor_B.restoreFactoryDefaults();
    m_shooter_motor_B.setInverted(true);
  }

  public void shootFast() {
    m_shooter_motor_A.set(speedFast);
    m_shooter_motor_B.set(speedFast);
  }

  public void shootSlow() {
    m_shooter_motor_A.set(speedSlow);
    m_shooter_motor_B.set(speedSlow);
  }
  public void shootSpeed(double speed)
  {
    m_shooter_motor_A.set(speed);
    m_shooter_motor_B.set(speed);
  }

  public void stop() {
    m_shooter_motor_A.set(0.0);
    m_shooter_motor_B.set(0.0);
  }
  public void periodic() {
    updateDashboard();
  
    if (Constants.AUTO_MODE) {
      
      if (m_shooter_motor_A.getOutputCurrent() > 25)
      {
        System.out.println(m_shooter_motor_A.getOutputCurrent());
        SmartDashboard.putBoolean("Holding Note", false);
      }
      if (SmartDashboard.getBoolean("Holding Note", true)) {
        shootFast();
      }
      else {
        stop();
      }
    }
  }

  public void updateDashboard()
  {
    SmartDashboard.putNumber("Shooter A Current", m_shooter_motor_A.getOutputCurrent());
    SmartDashboard.putNumber("Shooter B Current", m_shooter_motor_B.getOutputCurrent());
    SmartDashboard.putNumber("Shooter A Temp", m_shooter_motor_A.getMotorTemperature());
    SmartDashboard.putNumber("Shooter B Temp", m_shooter_motor_B.getMotorTemperature());
    SmartDashboard.putNumber("Shooter Speed", m_shooter_motor_A.get());
  }
}