package frc.robot;

public class Constants {
    public static boolean DEBUG_DRIVETRAIN = true;
    public static boolean DEBUG_CLIMBER = false;
    public static boolean DEBUG_SHOOTER = true;
    public static boolean DEBUG_INTAKE = false;
    public static boolean DEBUG_INJECTOR = false;
    public static boolean DEBUG_COLOUR_SENSOR = false;
    public static boolean DEBUG = false;

    public static int EJECT_TIME = 2;
    public static int INTAKE_TIME = 2;
    public static int POWERCELL_STALL_CURRENT = 2;

    public static double DISTANCE_TOLERANCE = 0.08;
    public static double SLOW_DOWN_DISTANCE = 0.4;
    public static double AUTO_DRIVE_SPEED = 0.2;
    public static double AUTO_DRIVE_SLOW_SPEED = 0.15;
    public static double TURNING_THRESHOLD = 60;
    public static double ANGLE_TOLERANCE = 0.12;
    public static double HOOD_TOLERANCE = 0.075;
    public static double HOOD_SLOW_TOLERANCE = 0.3;

    // Auto Constants
    public static double kRamseteB = 2;
    public static double kRamseteZeta = 0.7;

    // Auto drivetrain
    public static final double DRIVE_TRACK_WIDTH = 0.916;
    public static final double AUTO_MAX_VELOCITY = 1;
    public static final double AUTO_MAX_ACCEL = 0.5;
    public static final double AUTO_MAX_CENTRIPETAL_ACCEL = 1;

    public static double ksVolts = 0.344;
    public static double kvVoltSecondsPerMeter = 1.16;
    public static double kaVoltSecondsSquaredPerMeter = 0.0495;
    public static double kPDriveVel = 0.000692;
    public static double kMaxSpeedMetersPerSecond = 0.5;
    public static double kMaxAccelerationMetersPerSecondSquared = 0.25;

    // 5.95:1
    // 42 pulses per rev
    public static final double DRIVE_GEAR_RATIO = 1/5.95;
    
    public static double BLINKIN_RAINBOW                = -0.91;
    public static double BLINKIN_RAINBOWGLITTER         = -0.89; 
    public static double BLINKIN_RED                    = 0.61;
    public static double BLINKIN_GREEN                  = 0.77;
    public static double BLINKIN_YELLOW                 = 0.69; 
    public static double BLINKIN_BLUE                   = 0.83; 
    public static double BLINKIN_LIGHTCHASE             = -0.29;
    public static double BLINKIN_COLOUR_WAVE_RAINBOW    = -0.45;
}