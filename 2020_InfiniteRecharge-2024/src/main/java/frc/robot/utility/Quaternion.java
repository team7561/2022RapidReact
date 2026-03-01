package frc.robot.utility;
import java.lang.Math;

public class Quaternion {
    double r, i, j, k;
    public Quaternion(double r, double i, double j, double k) {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
    }
    public EulerAngles convertToEuler()
    {
        double roll = 0;
        double pitch = 0;
        double yaw = 0;

        return new EulerAngles(roll, pitch, yaw);
    }
    public String toString()
    {
        return "(" + r +"+"+i+"i+"+j+"j+"+k+"k)";
    }
    public double magnitude()
    {
        return Math.sqrt(r*r+i+i+j*j+k*k);
    }
    public Quaternion normalise() {
        double m = magnitude();
        return new Quaternion(r/m, i/m, j/m, k/m);
    }
    public Double toYaw()
    {
        return Math.atan2(2 * (r * k + i * k), 1 - 2 *j*j+k*k);
    }
    public Double toPitch()
    {
        double sinp = 2 * r * j - k * i;
        if (sinp >= 1)
            return 90.0;
        if (sinp <= -1)
            return -90.0;
        return Math.toDegrees(Math.asin(sinp));
    }
    public Double toRoll()
    {
        return Math.atan2(2 * (r * i + j * k), 1 - 2 *i*i+j*j);
    }
    public EulerAngles toEuler()
    {
        return new EulerAngles(this.toYaw(), this.toPitch(), this.toRoll());
    }
    public static Quaternion multiply(Quaternion one, Quaternion two)
    {
        double r = one.r * two.r - one.i * two.i - one.j * two.j - one.k * two.k;
        double i = one.r * two.i + one.i * two.r + one.j * two.k - one.k * two.j;
        double j = one.r * two.j - one.i * two.k + one.j * two.r - one.k * two.i;
        double k = one.r * two.k + one.i * two.j - one.j * two.i + one.k * two.r;
        return new Quaternion(r, i, j, k);
    }
    public Quaternion conjugate()
    {
        return new Quaternion(r, -i, -j, -k);
    }
}