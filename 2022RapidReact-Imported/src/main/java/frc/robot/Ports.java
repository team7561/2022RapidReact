package frc.robot;

public class Ports {

    // CAN Bus Node IDs
    public static int CAN_ID_DRIVING_A = 1;
    public static int CAN_ID_STEERING_A = 2;
    public static int CAN_ID_DRIVING_B = 3;
    public static int CAN_ID_STEERING_B = 4;
    public static int CAN_ID_DRIVING_D = 5;
    public static int CAN_ID_STEERING_D = 6;
    public static int CAN_ID_DRIVING_C = 7;
    public static int CAN_ID_STEERING_C = 8;

    public static int CAN_ID_CLIMBER_A = 10;
    public static int CAN_ID_CLIMBER_B = 11;
    public static int CAN_ID_CLIMBER_DEPLOY = 12;

    public static int CAN_ID_SHOOTER_A = 20;
    public static int CAN_ID_SHOOTER_B = 21;

    public static int CAN_ID_INTAKE_DEPLOY = 31;
    public static int CAN_ID_INTAKE = 32;
    
    public static int CAN_ID_INJECTOR = 41;

    // PWM control channels
    public static int PWM_SHOOTER_HOOD_SERVO = 0;
    public static int PWM_LED_CONTROLLER_CHANNEL = 6;

    // DIO ports
    public static int LIMIT_ARM_LOWER = 0;
    public static int LIMIT_ARM_UPPER = 1;
    public static int LIMIT_LIFT_UPPER = 2;
    public static int LIMIT_CLIMB_LOWER = 3;
}