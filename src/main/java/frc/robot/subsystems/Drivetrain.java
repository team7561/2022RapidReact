/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.SwerveMode;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class Drivetrain extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    public SwerveModule moduleA, moduleB, moduleD, moduleC;

    Translation2d moduleA_location, moduleB_location, moduleC_location, moduleD_location;
    SwerveDriveKinematics m_kinemtics;
    SwerveDriveOdometry m_odometry;
    Pose2d m_pose;
    Field2d m_field2d;

    public Timer timer;
    double angleA, angleB, angleD, angleC;
    double m_x, m_y;
    double prevGyro = 0;
    SwerveMode m_mode;
    
    public static final ADXRS450_Gyro imu = new ADXRS450_Gyro();

    public Drivetrain() {
        
        m_mode = SwerveMode.ULTIMATESWERVE;

        Preferences.initDouble("A Offset", Constants.SWERVE_A_OFFSET_ANGLE);
        Preferences.initDouble("B Offset", Constants.SWERVE_B_OFFSET_ANGLE);
        Preferences.initDouble("C Offset", Constants.SWERVE_C_OFFSET_ANGLE);
        Preferences.initDouble("D Offset", Constants.SWERVE_D_OFFSET_ANGLE);

        SmartDashboard.putNumber("A_Offset_Angle", Preferences.getDouble("A Offset", 0));
        SmartDashboard.putNumber("B_Offset_Angle", Preferences.getDouble("B Offset", 0));
        SmartDashboard.putNumber("C_Offset_Angle", Preferences.getDouble("C Offset", 0));
        SmartDashboard.putNumber("D_Offset_Angle", Preferences.getDouble("D Offset", 0));  

        moduleA = new SwerveModule(Preferences.getDouble("A Offset",0), Constants.SWERVE_A_ENCODER_PORT, Ports.CAN_ID_DRIVING_A, Ports.CAN_ID_STEERING_A, "A");
        moduleB = new SwerveModule(Preferences.getDouble("B Offset",0), Constants.SWERVE_B_ENCODER_PORT, Ports.CAN_ID_DRIVING_B, Ports.CAN_ID_STEERING_B, "B");
        moduleD = new SwerveModule(Preferences.getDouble("D Offset",0), Constants.SWERVE_D_ENCODER_PORT, Ports.CAN_ID_DRIVING_D, Ports.CAN_ID_STEERING_D, "D");
        moduleC = new SwerveModule(Preferences.getDouble("C Offset",0), Constants.SWERVE_C_ENCODER_PORT, Ports.CAN_ID_DRIVING_C, Ports.CAN_ID_STEERING_C, "C");
        
        moduleA_location = new Translation2d(-0.2, 0.2); //FL
        moduleB_location = new Translation2d(0.2, 0.2); //FR
        moduleD_location = new Translation2d(0.2, -0.2); //BL
        moduleC_location = new Translation2d(0.2, -0.2); //BR
        
        m_kinemtics = new SwerveDriveKinematics(moduleA_location, moduleB_location, moduleC_location, moduleD_location);
        m_pose = new Pose2d(5, 13.5, new Rotation2d());
        m_field2d = new Field2d();

        m_odometry = new SwerveDriveOdometry(m_kinemtics, imu.getRotation2d(), m_pose);

        resetEncoders();
        m_x = 0;
        m_y = 0;
        imu.calibrate();
        imu.reset();

        timer = new Timer();
        timer.reset();
        timer.start();
    }

    @Override
    public void periodic() {
        updateOffsets();
        if(prevGyro != imu.getAngle()){
            prevGyro = imu.getAngle();
        } else {
            DriverStation.reportError("Robot Gyro Failed!", false);
        }
        
        updateDashboard();
        SmartDashboard.putString("Drivetrain Mode", m_mode.name());

        m_pose = m_odometry.update(imu.getRotation2d(), moduleA.getState(), moduleB.getState(), moduleC.getState(), moduleD.getState());
        m_field2d.setRobotPose(m_pose);
    }

	public void stop() {
        moduleA.stop();
        moduleB.stop();
        moduleD.stop();
        moduleC.stop();
    }

	public void resetEncoders() {
        moduleA.resetEncoders();
        moduleB.resetEncoders();
        moduleD.resetEncoders();
        moduleC.resetEncoders();
	}

    public double getAvgEncoderCount(){
        return (moduleA.getPulses() + moduleB.getPulses() + moduleC.getPulses()  + moduleD.getPulses()) / 4;
    }

	public void resetPose() {
        m_x = 0;
        m_y = 0;
	}
	public void setPose(double x, double y) {
        m_x = x;
        m_y = y;
	}
	public void setPose(Pose2d pose) {
        m_x = pose.getX();
        m_y = pose.getY();
	}

    public void updateOffsets(){
        if (moduleD.getAngleOffset() != SmartDashboard.getNumber("D_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE))
        {
          moduleD.setAngleOffset(SmartDashboard.getNumber("D_Offset_Angle", Constants.SWERVE_D_OFFSET_ANGLE));
        }
        if (moduleC.getAngleOffset() != SmartDashboard.getNumber("C_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE))
        {
          moduleC.setAngleOffset(SmartDashboard.getNumber("C_Offset_Angle", Constants.SWERVE_C_OFFSET_ANGLE));
        }
        if (moduleA.getAngleOffset() != SmartDashboard.getNumber("A_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE))
        {
          moduleA.setAngleOffset(SmartDashboard.getNumber("A_Offset_Angle", Constants.SWERVE_A_OFFSET_ANGLE));
        }
        if (moduleB.getAngleOffset() != SmartDashboard.getNumber("B_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE))
        {
          moduleB.setAngleOffset(SmartDashboard.getNumber("B_Offset_Angle", Constants.SWERVE_B_OFFSET_ANGLE));
        }
    }

    public void drive(DoubleSupplier m_x,DoubleSupplier m_y,DoubleSupplier m_twist,DoubleSupplier m_speed, boolean fieldCentric)
    {
        ChassisSpeeds speeds = 
        new ChassisSpeeds(m_x.getAsDouble() * m_speed.getAsDouble(), m_y.getAsDouble() * m_speed.getAsDouble(), m_twist.getAsDouble()); 
        SwerveModuleState[] swerveStates = m_kinemtics.toSwerveModuleStates(speeds);
        // SwerveDriveKinematics.desaturateWheelSpeeds(swerveStates, 1);
        moduleA.setDesiredState(swerveStates[0]);
        moduleB.setDesiredState(swerveStates[0]);
        moduleC.setDesiredState(swerveStates[0]);
        moduleD.setDesiredState(swerveStates[0]);
        SmartDashboard.putNumber("Module A State Angle", swerveStates[0].angle.getDegrees());
        SmartDashboard.putNumber("Module B State Angle", swerveStates[1].angle.getDegrees());
        SmartDashboard.putNumber("Module C State Angle", swerveStates[2].angle.getDegrees());
        SmartDashboard.putNumber("Module D State Angle", swerveStates[3].angle.getDegrees());

        SmartDashboard.putNumber("Module A State Speed", swerveStates[0].speedMetersPerSecond);
        SmartDashboard.putNumber("Module B State Speed", swerveStates[1].speedMetersPerSecond);
        SmartDashboard.putNumber("Module C State Speed", swerveStates[2].speedMetersPerSecond);
        SmartDashboard.putNumber("Module D State Speed", swerveStates[3].speedMetersPerSecond);
    }

	public double readGyro() {
		return imu.getAngle();
	}

    public SwerveMode getMode()
    {
        return m_mode;
    }

    public boolean isStill(){
        if(moduleA.getSpeed() == 0 && moduleB.getSpeed() == 0 && moduleC.getSpeed() == 0 &&  moduleD.getSpeed() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setMode(SwerveMode mode)
    {
        m_mode = mode;
    }

    public void resetGyro(){
        imu.reset();
        timer.reset();
        timer.start();
        SmartDashboard.putNumber("LED Value", 0.77);
    }

    public void updateDashboard()
    {
        moduleA.updateDashboard();
        moduleB.updateDashboard();
        moduleD.updateDashboard();
        moduleC.updateDashboard();
        SmartDashboard.putNumber("Game Time", DriverStation.getMatchTime());
        SmartDashboard.putNumber("Gyro Angle", imu.getAngle());
        SmartDashboard.putNumber("Avg Encoder count", getAvgEncoderCount());
        
        SmartDashboard.putData("Gyro", imu);
        SmartDashboard.putData("Field 2d", m_field2d);

        SmartDashboard.putNumber("Pose X", m_pose.getX());
        SmartDashboard.putNumber("Pose Y", m_pose.getY());
        SmartDashboard.putNumber("Pose Angle", m_pose.getRotation().getDegrees());

    }

}
