/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;

public class SwerveModule extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    double m_offset, m_angle, m_steering_target, m_steering_sp, currentAngle, moduleAngle, m_rawSteeringTarget;

    String m_pos;

    Boolean m_inverted;
    Boolean m_steering, m_driving;

    CANSparkMax m_driveMotor;
    CANSparkMax m_steeringMotor;

    SparkMaxPIDController m_driving_pidController;
    SparkMaxRelativeEncoder m_steering_encoder;

    DigitalSource absolute_encoder_source;
    DutyCycle absolute_encoder;

    public double steering_kP, steering_kI, steering_kD, steering_kIz, steering_kFF, steering_kMaxOutput, steering_kMinOutput, steering_maxRPM, steering_m_setpoint;
    public double driving_kP, driving_kI, driving_kD, driving_kIz, driving_kFF, driving_kMaxOutput, driving_kMinOutput, driving_maxRPM, driving_m_setpoint;

    /** Swerve Drive drive mode */
    public enum DriveMode {
        OPEN_LOOP,
        CLOSED_LOOP,
        TELEOP,
        TRAJECTORY,
        AZIMUTH
    }

    public SwerveModule(double angleOffset, int encoderPort, int driveChannel, int steerChannel, String pos) {
        setAngleOffset(angleOffset);
        SmartDashboard.putNumber(m_pos+"_Offset_Angle",getAngleOffset());
        m_angle = 0;
        m_driveMotor = new CANSparkMax(driveChannel, MotorType.kBrushless);
        m_driveMotor.getEncoder().setVelocityConversionFactor(2.08921623); // 24:11, 45:15, 4" wheel
        m_steeringMotor = new CANSparkMax(steerChannel, MotorType.kBrushless);

        absolute_encoder_source = new DigitalInput(encoderPort);
        absolute_encoder = new DutyCycle(absolute_encoder_source);

        m_driveMotor.restoreFactoryDefaults();
        m_steeringMotor.restoreFactoryDefaults();

        m_driveMotor.setIdleMode(IdleMode.kCoast);
        m_steeringMotor.setIdleMode(IdleMode.kBrake);
        
        m_driveMotor.setSmartCurrentLimit(15);
        m_steeringMotor.setSmartCurrentLimit(15);

        m_pos = pos;

        m_inverted = false;
        m_steering = true;
        m_driving = true;

        currentAngle = 0;

        // PID coefficients
        driving_kP = 0.01; 
        driving_kI = 0.00000;
        driving_kD = 0.00000; 
        driving_kFF = 0; 
        driving_m_setpoint = 0;
        driving_kMaxOutput = 0.2; 
        driving_kMinOutput = -0.2;
        driving_maxRPM = 4500;

        m_driving_pidController = m_driveMotor.getPIDController();
        
        // set PID coefficients
        m_driving_pidController.setP(driving_kP);
        m_driving_pidController.setI(driving_kI);
        m_driving_pidController.setD(driving_kD);
        m_driving_pidController.setIZone(driving_kIz);
        m_driving_pidController.setFF(driving_kFF);
        m_driving_pidController.setOutputRange(driving_kMinOutput, driving_kMaxOutput);

        
        SmartDashboard.putNumber(m_pos+"Encoder", absolute_encoder.getOutput());
    }

    @Override
    public void periodic() {
        if (Math.abs(SmartDashboard.getNumber(m_pos+"_Offset_Angle",getAngleOffset()) - getAngleOffset())< 0.01)
        {
            SmartDashboard.putNumber(m_pos+"_Offset_Angle",getAngleOffset());
        }
        SmartDashboard.putNumber(m_pos+"Encoder", absolute_encoder.getOutput());
        currentAngle = ((SmartDashboard.getNumber(m_pos+"_Angle", 0)-m_offset*360)+360)%360;

        //Error defines the magnitued and direction of the target angle from the current angle
        double error = m_steering_target-currentAngle;

        //dir is the motor power set to the steering motors
        double dir = 1;

        moduleAngle = currentAngle;
        //Alters magnitued and direction of error if it exceeds 180 degrees.
        if (Math.abs(error) > 180){
            error = (360 - Math.abs(error)) * -Math.signum(error);
            dir = -1;
            moduleAngle = (currentAngle + 180)%360; // Correct for being inverted
        }

        if (m_steering)
        {
            //Multiplies steering motor power by a function of the target drive power.
            //This smooths the steering power for when modules are inverted.
            m_steeringMotor.set((error * 0.1 / 4.6)* Math.pow(Math.abs(driving_m_setpoint), 0.25)); 
        }
        else
        {
            m_steeringMotor.set(0);
        }

        if (m_driving)
        {
            m_driveMotor.set(driving_m_setpoint);
        }
        else
        {
            m_driveMotor.set(0);
        }
    }

    public double getAngleError(){
        //Gets magnitude and direction of target angle from current angle (-180 to 180)
        double error = m_steering_target-currentAngle;

        if (Math.abs(error) > 180){
            error = (360 - Math.abs(error)) * -Math.signum(error);
        }

        return Math.abs(error);
    }

    public double getPulses()
    {
        return absolute_encoder.getOutput();
    }

    public double getAngle()
    {
        return absolute_encoder.getOutput()*360;
    }

    public SwerveModuleState getState()
    {
        //return new SwerveModuleState(getSpeed(), new Rotation2d(moduleAngle));
        return new SwerveModuleState(getSpeed(), new Rotation2d(moduleAngle));

    }
    public void resetEncoders()
    {
        m_driveMotor.getEncoder().setPosition(0);
        m_steeringMotor.getEncoder().setPosition(0);
    }

    public void setTargetAngle(double angle){
        m_rawSteeringTarget = angle;
        //Modules are inverted if the target angle is greater than 90 degrees.
        /*if(getAngleError() > 90){
            m_inverted = !m_inverted;
        }

        //If modules are inverted, the new zero is 180 degrees off.
        if(m_inverted){
            m_steering_target = (angle + 180) % 360;
        }

        else{
            m_steering_target = angle;
        }*/
        m_steering_target = angle;
    }

    public double getSpeed(){
        //Gets speed in rs^-1
        return m_driveMotor.getEncoder().getVelocity()/100;
    }

    public double getSteerSpeed(){
        return m_steeringMotor.get();
    }

    public double getRawSpeed(){
        return m_driveMotor.get();
    }

    public void setSpeed(double speed){
        if (Math.abs(getAngleError()) < 10){
            if (m_inverted)
            {
                m_driveMotor.set(-speed);
            }
            else 
            {
                m_driveMotor.set(speed);
            }
        } else {
            m_driveMotor.set(0);
        }
    }

    public void setVelocity(double velocity){
        if (m_inverted)
        {
            driving_m_setpoint = -velocity;
        }
        else 
        {
            driving_m_setpoint = velocity;
        }
    }

    public void setToZeroAngle(){
        setTargetAngle(0);  //Sets current encoder value to zero
    }

    public void stop(){
        driving_m_setpoint = 0;
        m_driveMotor.set(0);
    }

    public void setAngleOffset(double angleOffset){
        m_offset = angleOffset;
    }

    public double getAngleOffset(){
        return m_offset;
    }
   
    public void updateDashboard()
    {
        /*SmartDashboard.putNumber(m_pos+"_DrivingSP", driving_m_setpoint);
        SmartDashboard.putNumber(m_pos+"_Speed", getSpeed());
        SmartDashboard.putNumber(m_pos+"_RawSteerSpeed", getSteerSpeed());*/
        SmartDashboard.putNumber(m_pos+"_RawDrivespeed", getRawSpeed());
        SmartDashboard.putNumber(m_pos+"_Drivespeed", getSpeed());
        SmartDashboard.putNumber(m_pos+"_Angle", getAngle());
        SmartDashboard.putNumber(m_pos+"_AngleCorrected", moduleAngle);
        SmartDashboard.putNumber(m_pos+"_AngleTarget", m_steering_target);
        SmartDashboard.putNumber(m_pos+"_RawAngleTarget", m_rawSteeringTarget);
        SmartDashboard.putNumber(m_pos+"_AngleEncoderOutput", absolute_encoder.getOutput());
        SmartDashboard.putNumber(m_pos+"_DriveEncoderOutput", m_driveMotor.getEncoder().getPosition());
        SmartDashboard.putBoolean(m_pos+"_Inverted", m_inverted);
        /*SmartDashboard.putNumber(m_pos+"_CurrentAngle", currentAngle);
        SmartDashboard.putNumber(m_pos+"_PV", m_steeringMotor.getEncoder().getPosition());
        SmartDashboard.putNumber(m_pos+"_SP", steering_m_setpoint);
        SmartDashboard.putNumber(m_pos+"_m_steering_target", m_steering_target);*/
        

    }
    
}
