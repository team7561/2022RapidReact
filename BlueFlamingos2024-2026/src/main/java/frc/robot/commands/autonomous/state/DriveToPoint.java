package frc.robot.commands.autonomous.state;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utility.Coordinate;

import static frc.robot.utility.Math_Utility.clamp;

public class DriveToPoint extends Command {
    final Coordinate destination;
    final double bearing;
    final boolean forwardOnly;
    Drivetrain m_drivetrain;

    public DriveToPoint(Drivetrain drivetrain, Coordinate destination, double bearing) {
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = false;
        m_drivetrain = drivetrain;
    }
    public DriveToPoint(Drivetrain drivetrain, Coordinate destination, double bearing, boolean forwardOnly) {
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = forwardOnly;
        m_drivetrain = drivetrain;
    }
    @Override
    public void execute() {
        final double currentHeading = 0;//robot.viveMeasurements.get_Y_rot()+270;
        /*if(!robot.viveMeasurements.isValidCooardinates(currentLocation)) {
            //robot.drivetrain.drive(0, 0);
            return false;
        }*/

        final double distance = Coordinate.getDistance(m_drivetrain.currentCoordinate, destination);
        double offsetDistance = 0.1 * distance;
        if (distance < 0.2)
            offsetDistance = 0;

        Coordinate targetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  offsetDistance);
        double targetHeading = Coordinate.getHeading(m_drivetrain.currentCoordinate, targetLocation);
        double headingError = (currentHeading - targetHeading + 900) % 360 - 180;

        Coordinate alternateTargetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  -offsetDistance);
        final double alternateTargetHeading = Coordinate.getHeading(m_drivetrain.currentCoordinate, alternateTargetLocation);
        double alternateHeadingError = (currentHeading - alternateTargetHeading + 900) % 360 - 180;
        // 180 - alternateHeadingError
        alternateHeadingError = (180 - alternateHeadingError + 900) % 360 - 180;

        boolean reverse = false;
        System.out.println("Heading errors " + headingError + ", " + alternateHeadingError);
        if (Math.abs(alternateHeadingError) < Math.abs(headingError) && !forwardOnly) {
            reverse = true;
            System.out.println("Reversing ");
            targetLocation = alternateTargetLocation;
            targetHeading = alternateTargetHeading;
            headingError = alternateHeadingError;
        }

        SmartDashboard.putNumber("Target Location X", targetLocation.getX());
        SmartDashboard.putNumber("Target Location Z", targetLocation.getZ());
        SmartDashboard.putNumber("Target Location Heading", targetHeading);

        SmartDashboard.putNumber("Destination Location X", destination.getX());
        SmartDashboard.putNumber("Destination Location Z", destination.getZ());
        SmartDashboard.putNumber("Destination Location Heading", bearing);

        double speed = Constants.AUTO_DRIVE_SPEED;
        double slow_speed= Constants.AUTO_DRIVE_SLOW_SPEED;

        SmartDashboard.putNumber("DriveToPoint Distance", distance);
        SmartDashboard.putNumber("DriveToPoint destinationHeading", targetHeading);
        SmartDashboard.putNumber("DriveToPoint currentHeading", currentHeading);
        SmartDashboard.putNumber("DriveToPoint headingError", headingError);

        final double headingDivisor = 30;
        double left = 0.4 - headingError / headingDivisor;
        double right = 0.4 + headingError / headingDivisor;
        left = clamp(left);
        right = clamp(right);
        // At >= 60 degrees, we have left = 1, right = -1
        // At <= -60 degrees, we have left = -1, right = 1

        if (distance < Constants.SLOW_DOWN_DISTANCE) {
            // change to slow speed if close to target
            speed = slow_speed;
        }
        if (reverse)
            speed = -speed;

        m_drivetrain.drive(left * speed, right * speed);

    }
    public void updateDashboard(boolean debug, Robot robot)
    {
        //SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(robot.viveMeasurements.getLocation(), destination));
        //SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(robot.viveMeasurements.getLocation(), destination));
    }

    @Override
    public boolean isFinished() {
    
    final double distance = Coordinate.getDistance(m_drivetrain.currentCoordinate, destination);
    return distance < Constants.DISTANCE_TOLERANCE;

}



}