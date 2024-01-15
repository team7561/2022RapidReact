package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

public class Shooter extends SubsystemBase {
  
  private PWMVictorSPX m_shooterMotorA,m_shooterMotorB; 
  private CANSparkMax m_shooter_motor_A, m_shooter_motor_B;
  private final double speedFast = 1;
  private final double speedSlow = 0.3;

  public Shooter() {

    m_shooterMotorA = new PWMVictorSPX(0);
    m_shooterMotorB = new PWMVictorSPX(1);

    m_shooterMotorA.setInverted(false);
    m_shooterMotorB.setInverted(true);

    m_shooter_motor_A = new CANSparkMax(Ports.Shooter_A_ID, MotorType.kBrushless);
    m_shooter_motor_A.restoreFactoryDefaults();
    m_shooter_motor_B = new CANSparkMax(Ports.Shooter_B_ID, MotorType.kBrushless);
    m_shooter_motor_B.restoreFactoryDefaults();
    m_shooter_motor_B.setInverted(true);
  }

  public void shootFast() {
    m_shooter_motor_A.set(speedFast);
    m_shooter_motor_B.set(speedFast);

    m_shooterMotorA.set(speedFast);
    m_shooterMotorB.set(speedFast);
  }

  public void shootSlow() {
    m_shooter_motor_A.set(speedSlow);
    m_shooter_motor_B.set(speedSlow);
    
    m_shooterMotorA.set(speedSlow);
    m_shooterMotorB.set(speedSlow);
  }

  public void stop() {
    m_shooter_motor_A.set(0.0);
    m_shooter_motor_B.set(0.0);
    
    m_shooterMotorA.set(0);
    m_shooterMotorB.set(0);
  }
}