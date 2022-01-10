package frc.robot;

public class Ports {

    // CAN Bus Node IDs
    public static int DRIVE_LEFT_A_CANID = 1;
    public static int DRIVE_LEFT_B_CANID = 2;
    public static int DRIVE_LEFT_C_CANID = 3;
    public static int DRIVE_RIGHT_A_CANID = 4;
    public static int DRIVE_RIGHT_B_CANID = 5;
    public static int DRIVE_RIGHT_C_CANID = 6;
    public static int SHOOTER_A_CANID = 11;
    public static int SHOOTER_B_CANID = 12;
    public static int SHOOTER_HOOD_CANID = 13;
    public static int POWERCELL_INTAKE_CANID = 21;
    public static int CLIMB_ELEVATOR_A_CANID = 31;
    public static int CLIMB_ELEVATOR_B_CANID = 32;
    public static int CLIMB_VACUUM_CANID = 41;
    public static int ARM_CANID = 51;

    // PWM control channels
    public static int LED_CONTROLLER_CHANNEL = 6;
    public static int INTAKE_CHANNEL = 7;
    public static int LIFT_A_PWM = 0;
    public static int LIFT_B_PWM = 1;


    // DIO ports
    public static int LIMIT_ARM_LOWER = 0;
    public static int LIMIT_ARM_UPPER = 1;
    public static int LIMIT_LIFT_UPPER = 2;
    public static int LIMIT_CLIMB_LOWER = 3;

    public static int ENCODER_LIFT_A_CHANNEL = 4;
    public static int ENCODER_LIFT_B_CHANNEL = 5;
    public static int ENCODER_LEFT_A_CHANNEL = 6;
    public static int ENCODER_LEFT_B_CHANNEL = 7;
    public static int ENCODER_RIGHT_A_CHANNEL = 8;
    public static int ENCODER_RIGHT_B_CHANNEL = 9;

    // Analog Channels
    public static int POSITION_CHANNEL = 1;
    public static int ULTRASONIC_CHANNEL = 2;

    // PCM ports
    public static int INTAKE_SOLENOID_CHANNEL_A = 0;
    public static int INTAKE_SOLENOID_CHANNEL_B = 1;
    public static int CLIMBER_SOLENOID_CHANNEL_A = 2;
    public static int CLIMBER_SOLENOID_CHANNEL_B = 3;

    // PDP Slots
    public static int ARM_PDP_SLOT = 5;
    public static int CARGO_INTAKE_PDP_SLOT = 7;

    // Unknown below here
    public static int DRIVE_LEFT_A_PDP_SLOT = 1;
    public static int DRIVE_LEFT_B_PDP_SLOT = 2;
    public static int DRIVE_RIGHT_A_PDP_SLOT = 3;
    public static int DRIVE_RIGHT_B_PDP_SLOT = 4;
    public static int LIFT_A_PDP_SLOT = 5;
    public static int LIFT_B_PDP_SLOT = 6;
    public static int CLIMB_ELEVATOR_A_PDP_SLOT = 8;
    public static int CLIMB_ELEVATOR_B_PDP_SLOT = 9;
    public static int CLIMB_VACUUM_PDP_SLOT = 10;
}