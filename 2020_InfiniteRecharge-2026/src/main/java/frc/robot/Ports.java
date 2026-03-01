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
    public static int CLIMB_DEPLOY_A_CANID = 21;
    public static int CLIMB_DEPLOY_B_CANID = 22;
    public static int CLIMB_WINCH_A_CANID = 23;
    public static int CLIMB_WINCH_B_CANID = 24;
    public static int INJECTOR_CANID = 41;

    // PWM control channels
    public static int INTAKE_CHANNEL = 0;
    public static int LED_CONTROLLER_CHANNEL = 1;
    public static int COLOUR_WHEEL_ROTATE_CHANNEL = 2;

    // DIO ports
    public static int CLIMBER_HOOK_DEPLOY_LIMIT_SWITCH_CHANNEL = 0;
    public static int WINCH_LIMIT_SWITCH = 1;

    // Analog Channels

    // PCM ports
    public static int INTAKE_SOLENOID_CHANNEL_A = 0;
    public static int INTAKE_SOLENOID_CHANNEL_B = 1;
    public static int CPM_SOLENOID_CHANNEL_A = 2;
    public static int CPM_SOLENOID_CHANNEL_B = 3;
    public static int SHOOTER_SOLENOID_CHANNEL_A = 4;
    public static int SHOOTER_SOLENOID_CHANNEL_B = 5;

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