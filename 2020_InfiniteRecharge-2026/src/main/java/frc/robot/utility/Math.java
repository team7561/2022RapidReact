package frc.robot.utility;

public class Math {
    public static double clamp(double input)
    {
        if (input > 1)
        {
            return 1;
        }
        if (input < -1)
        {
            return -1;
        }
        return input;
    }
}