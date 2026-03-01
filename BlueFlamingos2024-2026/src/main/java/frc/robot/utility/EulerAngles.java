package frc.robot.utility;
import java.lang.Math;
public class EulerAngles {
    double roll;
    double pitch;
    double yaw;
    public EulerAngles(double roll, double pitch, double yaw)
    {
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    public Quaternion convertToQuaternion()
    {
        double r = 0;
        double i= 0;
        double j = 0;
        double k = 0;

        return new Quaternion(r, i, j, k);
    }
    public Quaternion toQuaternion()
    {
        double roll_half = Math.toRadians(roll)/2;
        double yaw_half = Math.toRadians(yaw)/2;
        double pitch_half = Math.toRadians(pitch)/2;

        double r = Math.cos(roll_half)*Math.cos(pitch_half)*Math.cos(yaw_half)+Math.sin(roll_half)*Math.sin(pitch_half)*Math.sin(yaw_half);
        double i = Math.sin(roll_half)*Math.cos(pitch_half)*Math.cos(yaw_half)-Math.cos(roll_half)*Math.sin(pitch_half)*Math.sin(yaw_half);
        double j = Math.cos(roll_half)*Math.sin(pitch_half)*Math.cos(yaw_half)+Math.sin(roll_half)*Math.cos(pitch_half)*Math.sin(yaw_half);
        double k = Math.cos(roll_half)*Math.cos(pitch_half)*Math.sin(yaw_half)-Math.sin(roll_half)*Math.sin(pitch_half)*Math.cos(yaw_half);

        return new Quaternion(r, i, j, k);
    }
}
