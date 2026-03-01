package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Ports;

import java.util.Random;

public class LEDController extends SubsystemBase {

    private final Spark blinkin;
    private final Random rand = new Random();

    public LEDController() {
        blinkin = new Spark(Ports.LED_CONTROLLER_CHANNEL);

        // Default LED pattern value
        SmartDashboard.putNumber("LED Value", 0.83);
    }

    @Override
    public void periodic() {
        double value = SmartDashboard.getNumber("LED Value", 0.83);
        blinkin.set(value);

        // Keep dashboard in sync with actual output
        SmartDashboard.putNumber("LED Value", value);
    }

    public void setValue(double value) {
        SmartDashboard.putNumber("LED Value", value);
    }

    public void setRandomValue() {
        // Blinkin patterns range from -1.0 to +1.0
        double pattern = -1.0 + (2.0 * rand.nextDouble());
        SmartDashboard.putNumber("LED Value", pattern);
    }
}