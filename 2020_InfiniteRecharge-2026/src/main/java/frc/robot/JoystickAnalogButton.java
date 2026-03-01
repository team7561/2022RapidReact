package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class JoystickAnalogButton extends Trigger {

    @SuppressWarnings("unused")
    private final GenericHID joystick;
    @SuppressWarnings("unused")
    private int axisNumber;
    private double threshold = 0.5;

    public JoystickAnalogButton(GenericHID joystick, int axisNumber) {
        this(joystick, axisNumber, 0.5);
    }

    public JoystickAnalogButton(GenericHID joystick, int axisNumber, double threshold) {
        super(() -> {
            double value = joystick.getRawAxis(axisNumber);

            // Positive threshold → trigger when axis > threshold
            // Negative threshold → trigger when axis < threshold
            if (threshold < 0) {
                return value < threshold;
            } else {
                return value > threshold;
            }
        });

        this.joystick = joystick;
        this.axisNumber = axisNumber;
        this.threshold = threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }
}