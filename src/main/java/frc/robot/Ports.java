package frc.robot;

public class Ports {

    // CAN Bus Node IDs
    public static int CAN_ID_DRIVING_A = 8;
    public static int CAN_ID_STEERING_A = 3;
    public static int CAN_ID_DRIVING_B = 1;
    public static int CAN_ID_STEERING_B = 2;
    public static int CAN_ID_DRIVING_D = 7;
    public static int CAN_ID_STEERING_D = 4;
    public static int CAN_ID_DRIVING_C = 5;
    public static int CAN_ID_STEERING_C = 6;

    public static int CAN_ID_CLIMBER_A = 10;
    public static int CAN_ID_CLIMBER_B = 11;
    public static int CAN_ID_CLIMBER_DEPLOY = 12;

    public static int CAN_ID_SHOOTER_A = 21;
    public static int CAN_ID_SHOOTER_B = 22;

    public static int CAN_ID_INTAKE_DEPLOY = 31;
    public static int CAN_ID_INTAKE = 20;
    
    public static int CAN_ID_INJECTOR = 41;

    // PWM control channels
    //0 and 1 for LimeLight
    public static int PWM_SHOOTER_HOOD_LEFT_SERVO = 2;
    public static int PWM_SHOOTER_HOOD_RIGHT_SERVO = 3;
    public static int PWM_LED_CONTROLLER_CHANNEL = 4;

    // DIO ports
    public static int LIMIT_ARM_LOWER = 0;
    public static int LIMIT_ARM_UPPER = 1;
    public static int LIMIT_LIFT_UPPER = 2;
    public static int LIMIT_CLIMB_LOWER = 3;

    public static int LIMELIGHT_SERVO_L = 0;
    public static int LIMELIGHT_SERVO_R = 1;
}