package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DPadButton extends Trigger {

    @SuppressWarnings("unused")
    private final XboxController joystick;
    @SuppressWarnings("unused")
    private final Direction direction;

    public DPadButton(XboxController joystick, Direction direction) {
        super(() -> {
            int pov = joystick.getPOV();
            int angle = direction.angle;

            // Accept exact angle or ±45° diagonals
            return pov == angle
                || pov == (angle + 45) % 360
                || pov == (angle + 315) % 360;
        });

        this.joystick = joystick;
        this.direction = direction;
    }

    public enum Direction {
        UP(0),
        RIGHT(90),
        DOWN(180),
        LEFT(270);

        public final int angle;

        Direction(int angle) {
            this.angle = angle;
        }
    }
}