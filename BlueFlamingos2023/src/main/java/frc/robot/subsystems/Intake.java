package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake extends SubsystemBase {

private DoubleSolenoid intakeOpenSolenoid;
private Ultrasonic ultrasonic1;
private CANSparkMax intakeMotorLeft, intakeMotorRight;


public Intake() {
    intakeOpenSolenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 0, 1);
    addChild("intakeDeploySolenoid", intakeOpenSolenoid);
    

    ultrasonic1 = new Ultrasonic(5, 6);
    addChild("Ultrasonic 1", ultrasonic1);
    

    intakeMotorLeft = new CANSparkMax(21, MotorType.kBrushless);
    intakeMotorRight = new CANSparkMax(22, MotorType.kBrushless);
    
    intakeMotorLeft.restoreFactoryDefaults();  
    intakeMotorLeft.setInverted(false);
    intakeMotorLeft.setIdleMode(IdleMode.kCoast);
    intakeMotorLeft.burnFlash();
    intakeMotorRight.restoreFactoryDefaults();  
    intakeMotorRight.setInverted(false);
    intakeMotorRight.setIdleMode(IdleMode.kCoast);
    intakeMotorRight.burnFlash();

}
    @Override
    public void periodic() {

    }

    public void open()
    {
        intakeOpenSolenoid.set(Value.kForward);
    }
    public void close()
    {
        intakeOpenSolenoid.set(Value.kReverse);
    }

    public void grab() {
        intakeMotorLeft.set(-0.1);
        intakeMotorRight.set(-0.1);
    }
    public void reverse() {
        intakeMotorLeft.set(0.15);
        intakeMotorRight.set(0.1);
    }
    public void stop() {
        intakeMotorLeft.set(0);
        intakeMotorRight.set(0);
    }
    public void grabFast() {
        intakeMotorLeft.set(-0.3);
        intakeMotorRight.set(-0.3);
    }

    public void reverseGrab() {
        intakeMotorLeft.set(-0.25);
        intakeMotorRight.set(0.25);
    }
}

