/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
    public static int EJECT_TIME = 2;
    public static int INTAKE_TIME = 2;
    
    public static int INTAKE_DEPLOY_CURRENT_LIMIT = 35;

    public static int INJECTOR_CARGO_INDEX_PULSE_COUNT = -10;

    public static double DISTANCE_TOLERANCE = 0.08;
    public static double SLOW_DOWN_DISTANCE = 0.4;
    public static double AUTO_DRIVE_SPEED = 0.2;
    public static double AUTO_DRIVE_SLOW_SPEED = 0.15;
    public static double TURNING_THRESHOLD = 60;
    public static double ANGLE_TOLERANCE = 4;

    public static double SHOOTER_TOLERANCE = 50;
    
    public static double SWERVE_A_OFFSET_ANGLE = -0.09;
    public static double SWERVE_B_OFFSET_ANGLE = 0.13;
    public static double SWERVE_C_OFFSET_ANGLE = 0.59;
    public static double SWERVE_D_OFFSET_ANGLE = -0.90;

    public static int SWERVE_A_ENCODER_PORT = 0;
    public static int SWERVE_B_ENCODER_PORT = 3;
    public static int SWERVE_D_ENCODER_PORT = 2;
    public static int SWERVE_C_ENCODER_PORT = 1;

    public static boolean DEBUG_INTAKE = true;
    public static boolean DEBUG_SHOOTER = true;
    public static boolean DEBUG_CLIMBER = true;
    public static boolean DEBUG_INJECTOR = true;
        
    public static double BLINKIN_RAINBOW                = -0.91;
    public static double BLINKIN_RAINBOWGLITTER         = -0.89; 
    public static double BLINKIN_RED                    = 0.61;
    public static double BLINKIN_GREEN                  = 0.77;
    public static double BLINKIN_YELLOW                 = 0.69; 
    public static double BLINKIN_BLUE                   = 0.83; 
    public static double BLINKIN_LIGHTCHASE             = -0.29;
    public static double BLINKIN_COLOUR_WAVE_RAINBOW    = -0.45;


    public static double HIGH_HUB_HEIGHT = 2.3;
    public static double LIMELIGHT_HEIGHT = 0.55;
}