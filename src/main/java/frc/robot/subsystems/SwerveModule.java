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
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;

public class SwerveModule extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    double m_offset;
    double m_angle;
    double m_steering_target;
    double m_steering_sp;
    double currentAngle;

    String m_pos;

    Boolean m_inverted;
    Boolean m_steering, m_driving;

    CANSparkMax m_driveMotor;
    CANSparkMax m_steeringMotor;

    CANPIDController m_driving_pidController;
    CANEncoder m_steering_encoder;

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
        m_steeringMotor = new CANSparkMax(steerChannel, MotorType.kBrushless);

        absolute_encoder_source = new DigitalInput(encoderPort);
        absolute_encoder = new DutyCycle(absolute_encoder_source);

        //m_steering_encoder = m_steeringMotor.getAlternateEncoder();


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

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber(m_pos+"Encoder", absolute_encoder.getOutput());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber(m_pos+"_Offset_Angle",getAngleOffset());
        SmartDashboard.putNumber(m_pos+"Encoder", absolute_encoder.getOutput());
        currentAngle = SmartDashboard.getNumber(m_pos+"_Angle", 0)-m_offset*360;
        if (currentAngle < 0)
        {
            currentAngle += 360;
        }
        double error = (m_steering_target-currentAngle)%360;
        double dir = 1;


        if (error < 1){
            dir = -0.1;
        }

        if (error > 1)
        {
            dir = 0.1;
        }        

        if (Math.abs(error) > 180){
            dir *= -1;
            dir *= (360 - Math.abs(error))/4.6;
            SmartDashboard.putNumber(m_pos + "_Error", 360 - Math.abs(error));
        } else {
            dir *= Math.abs(error)/4.6;
            SmartDashboard.putNumber(m_pos + "_Error", Math.abs(error));
        }

        if (m_steering)
        {
            m_steeringMotor.set(dir); 
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
        double error = (m_steering_target-currentAngle)%360;
        if(error > 180){
            error -= 360;
        }
        else if(error < -180){
            error += 360;
        }
        return error;
    }

    public double getPulses()
    {
        return absolute_encoder.getOutput();
    }

    public double getAngle()
    {
        return absolute_encoder.getOutput()*360;
    }

    public void resetEncoders()
    {
        m_driveMotor.getEncoder().setPosition(0);
        m_steeringMotor.getEncoder().setPosition(0);
    }

    public void setTargetAngle(double angle){
        m_steering_target = angle;
    }

    public double getSpeed(){
        //Gets speed in ms^-1
        return m_driveMotor.getEncoder().getVelocity();
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
        //m_steeringMotor.set(speed);
    }
    public void setVelocity(double velocity){
        if (Math.abs(getAngleError()) < 60){
            if (m_inverted)
            {
                driving_m_setpoint = -velocity;
            }
            else 
            {
                driving_m_setpoint = velocity;
            }
        } else {
            driving_m_setpoint = 0;
        }
    }

    public void setToZeroAngle(){
        //Sets current encoder value to zero
        setTargetAngle(0);
    }

    public void stop(){
        m_driveMotor.set(0);

        m_steering = false;
        m_steeringMotor.set(0);
    }

    public void setAngleOffset(double angleOffset){
        //
        m_offset = angleOffset;
    }

    public double getAngleOffset(){
        return m_offset;
    }
    public void updateDashboard()
    {
        /*SmartDashboard.putNumber(m_pos+"_DrivingSP", driving_m_setpoint);
        SmartDashboard.putNumber(m_pos+"_Speed", getSpeed());
        SmartDashboard.putNumber(m_pos+"_RawDrivespeed", getRawSpeed());
        SmartDashboard.putNumber(m_pos+"_RawSteerSpeed", getSteerSpeed());*/
        SmartDashboard.putNumber(m_pos+"_Angle", getAngle());
        /*SmartDashboard.putNumber(m_pos+"_CurrentAngle", currentAngle);
        SmartDashboard.putNumber(m_pos+"_PV", m_steeringMotor.getEncoder().getPosition());
        SmartDashboard.putNumber(m_pos+"_SP", steering_m_setpoint);
        SmartDashboard.putNumber(m_pos+"_EncoderOutput", absolute_encoder.getOutput());
        SmartDashboard.putNumber(m_pos+"_m_steering_target", m_steering_target);*/
        

    }
    
}
