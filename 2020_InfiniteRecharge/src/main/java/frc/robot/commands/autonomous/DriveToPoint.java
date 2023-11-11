package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.autonomous.Coordinate;
import frc.robot.autonomous.ViveMeasurements;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.utility.Math.clamp;

public class DriveToPoint extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_drivetrain;

    final Coordinate destination;
    final double bearing;
    final boolean forwardOnly;
    boolean finished = false;

    public DriveToPoint(Drivetrain drivetrain, Coordinate destination, double bearing) {
        m_drivetrain = drivetrain;
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = false;
    }
    public DriveToPoint(Drivetrain drivetrain, Coordinate destination, double bearing, boolean forwardOnly) {
        m_drivetrain = drivetrain;
        this.destination = destination;
        this.bearing = bearing;
        this.forwardOnly = forwardOnly;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
          
    }
        @Override
    public void execute() {
        final double currentHeading = ViveMeasurements.get_Y_rot()+270;
        final Coordinate currentLocation = ViveMeasurements.getLocation();
        double distance = 9;

        double offsetDistance = 0.1 * distance;
        if (distance < 0.2)
            offsetDistance = 0;

        Coordinate targetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  offsetDistance);
        double targetHeading = Coordinate.getHeading(currentLocation, targetLocation);
        double headingError = (currentHeading - targetHeading + 900) % 360 - 180;

        Coordinate alternateTargetLocation = Coordinate.getApproachCoordinate(destination,  bearing+270,  -offsetDistance);
        final double alternateTargetHeading = Coordinate.getHeading(currentLocation, alternateTargetLocation);
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

        if(!ViveMeasurements.isValidCooardinates(currentLocation)) {
            m_drivetrain.drive(0, 0);
            finished = false;
        }
    
         
        if (distance < Constants.DISTANCE_TOLERANCE) {
            m_drivetrain.drive(0, 0);
            finished = true;
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
        distance = Coordinate.getDistance(currentLocation, destination);

        if (distance < Constants.SLOW_DOWN_DISTANCE) {
            // change to slow speed if close to target
            speed = slow_speed;
        }
        if (reverse)
            speed = -speed;
        //speed *= 0.9;
        m_drivetrain.drive(left * speed, right * speed);
    }
    public void updateDashboard(boolean debug, ViveMeasurements viveMeasurements)
    {
        SmartDashboard.putNumber("DriveToPoint_Distance", Coordinate.getDistance(ViveMeasurements.getLocation(), destination));
        SmartDashboard.putNumber("DriveToPoint_Heading", Coordinate.getHeading(ViveMeasurements.getLocation(), destination));
    }
    
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}