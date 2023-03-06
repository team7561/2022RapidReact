package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {

private DoubleSolenoid intakeOpenSolenoid;
private AnalogInput ultrasonicSensor;
private Spark intakeSparkPWM;

private double speed;
public Intake() {

    intakeOpenSolenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 0, 2);
    addChild("intakeDeploySolenoid", intakeOpenSolenoid);
    

    ultrasonicSensor = new AnalogInput(0);    

    intakeSparkPWM = new Spark(1);

}

@Override
    public void periodic() {
        speed = 0;

        intakeSparkPWM.set(speed);
        updateDashboard();
    }

    public void open()
    {
        intakeOpenSolenoid.set(Value.kForward);
    }
    public void close()
    {
        speed = 0;
        intakeOpenSolenoid.set(Value.kReverse);
    }

    public void grab() {
        speed = -0.15;
    }
    public void reverse() {
        speed = 0.15;
    }
    public void stop() {
        speed = 0;
    }
    public void grabFast() {
        speed = -0.3;
    }

    public void reverseGrab() {
        speed = 0.3;
    }
    public void updateDashboard()
    {
        SmartDashboard.putBoolean("Intake Open", intakeOpenSolenoid.get().compareTo(Value.kForward)==1);
        SmartDashboard.putNumber("Intake Speed", speed);
        SmartDashboard.putNumber("Ultrasonic Distance", ultrasonicSensor.getAverageVoltage());
    }
}

