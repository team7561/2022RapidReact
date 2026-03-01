package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Coordinate {
    final double x;
    final double z;
    public Coordinate(double x, double z)
    {
        this.x = x;
        this.z = z;
    }
    public static double getHeading(Coordinate origin, Coordinate destination)
    {
        double deltaX = destination.x - origin.x;
        double deltaZ = destination.z - origin.z;
        double result =  Math.toDegrees(Math.atan2(deltaZ, deltaX));
        SmartDashboard.putNumber("Coordinate Destination X", destination.x);
        SmartDashboard.putNumber("Coordinate Origin X", origin.x);
        SmartDashboard.putNumber("Coordinate delta X", deltaX);
        SmartDashboard.putNumber("Coordinate delta Z", deltaZ);
        SmartDashboard.putNumber("Coordinate Destination Z", destination.x);
        SmartDashboard.putNumber("Coordinate Origin Z", origin.z);
        SmartDashboard.putNumber("Coordinate Heading", result);
        return result;
    }
    public static double getDistance(Coordinate origin, Coordinate destination)
    {
        double deltaX = destination.x - origin.x;
        double deltaZ = destination.z - origin.z;
        return Math.sqrt(deltaZ * deltaZ + deltaX * deltaX);
    }
    public static Coordinate getApproachCoordinate(Coordinate destination, double bearing, double distance)
    {
        double radians = Math.toRadians(bearing);
        return new Coordinate(destination.getX()+distance*Math.cos(radians), destination.getZ()-distance*Math.sin(radians));
    }

    public double getX() { return x; }
    public double getZ() { return z; }
}
