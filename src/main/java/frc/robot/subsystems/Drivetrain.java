/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.SwerveMode;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;

public class Drivetrain extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    public SwerveModule moduleA, moduleB, moduleD, moduleC;

    Translation2d moduleA_location, moduleB_location, moduleC_location, moduleD_location;
    SwerveDriveKinematics m_kinemtics;
    SwerveDriveOdometry m_odometry;
    Pose2d m_pose;



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
        
        moduleA_location = new Translation2d(0.5, 0.5);
        moduleB_location = new Translation2d(0.5, 0.5);
        moduleC_location = new Translation2d(0.5, 0.5);
        moduleD_location = new Translation2d(0.5, 0.5);
        
        m_kinemtics = new SwerveDriveKinematics(
        moduleA_location, moduleB_location, moduleC_location, moduleD_location
        );
        m_pose = new Pose2d(5, 13.5, new Rotation2d());

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
        if(prevGyro != imu.getAngle()){
            prevGyro = imu.getAngle();
        } else {
            DriverStation.reportError("Robot Gyro Failed!", false);
        }
        
        updateDashboard();
        SmartDashboard.putString("Drivetrain Mode", m_mode.name());

        m_pose = m_odometry.update(imu.getRotation2d(), moduleA.getState(), moduleB.getState(), moduleC.getState(), moduleD.getState());
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

	public void setTargetAngle(double angle) {
        if (m_mode == SwerveMode.CAR)
        {
            moduleA.setTargetAngle(angle);
            moduleB.setTargetAngle(angle);
            moduleD.setTargetAngle(0);
            moduleC.setTargetAngle(0);
        }
        if (m_mode == SwerveMode.CAR_X)
        {
            moduleA.setTargetAngle(angle);
            moduleB.setTargetAngle(0);
            moduleD.setTargetAngle(angle);
            moduleC.setTargetAngle(0);
        }
        if (m_mode == SwerveMode.SPIN){
            moduleA.setTargetAngle(315);
            moduleB.setTargetAngle(45);
            moduleD.setTargetAngle(225);
            moduleC.setTargetAngle(135);
        }
        if (m_mode == SwerveMode.TANK)
        {
            moduleA.setTargetAngle(0);
            moduleB.setTargetAngle(0);
            moduleD.setTargetAngle(0);
            moduleC.setTargetAngle(0);
        }
        if (m_mode == SwerveMode.TANK_X)
        {
            moduleA.setTargetAngle(90);
            moduleB.setTargetAngle(90);
            moduleD.setTargetAngle(90);
            moduleC.setTargetAngle(90);
        }
        if (m_mode == SwerveMode.CRAB)
        {
            moduleA.setTargetAngle(angle);
            moduleB.setTargetAngle(angle);
            moduleD.setTargetAngle(angle);
            moduleC.setTargetAngle(angle);
        }
        if (m_mode == SwerveMode.CRAB_X)
        {
            moduleA.setTargetAngle(90+angle);
            moduleB.setTargetAngle(90+angle);
            moduleD.setTargetAngle(90+angle);
            moduleC.setTargetAngle(90+angle);
        }
        if (m_mode == SwerveMode.SNAKE)
        {
            moduleA.setTargetAngle(angle);
            moduleB.setTargetAngle(angle);
            moduleD.setTargetAngle(-angle);
            moduleC.setTargetAngle(-angle);
        }
        if (m_mode == SwerveMode.SNAKE_X)
        {
            moduleA.setTargetAngle(angle);
            moduleB.setTargetAngle(-angle);
            moduleD.setTargetAngle(angle);
            moduleC.setTargetAngle(-angle);
        }
    }
    public void setSpeed(double speed)
    {
        moduleA.setVelocity(speed);
        moduleB.setVelocity(speed);
        moduleC.setVelocity(speed);
        moduleD.setVelocity(speed);
    }


    public void setSwerveVector(double twist, double target_angle, double mag){
        setSwerveVectorNoGyro(twist, target_angle + imu.getAngle(), mag);
    }

    public void setSwerveVectorNoGyro(double twist, double target_angle, double mag){
        //x and y component of translation vector (offest with imu value).
        double x = mag * Math.cos((target_angle) * Math.PI/180);
        double y = mag * Math.sin((target_angle) * Math.PI/180);

        //Constants for individual modules (x component, y component, angle)
        double Ax = twist * Math.cos(3 * Math.PI/4) + x; 
        double Ay = twist * Math.sin(3 * Math.PI/4) + y;
        double Ao = (Math.atan2(Ay, Ax) * 180/Math.PI + 180) % 360;

        double Bx = twist * Math.cos(5 * Math.PI/4) + x; 
        double By = twist * Math.sin(5 * Math.PI/4) + y;
        double Bo = (Math.atan2(By, Bx) * 180/Math.PI + 180) % 360;

        double Cx = twist * Math.cos(7 * Math.PI/4) + x; 
        double Cy = twist * Math.sin(7 * Math.PI/4) + y;
        double Co = (Math.atan2(Cy, Cx) * 180/Math.PI + 180) % 360;

        double Dx = twist * Math.cos(Math.PI/4) + x; 
        double Dy = twist * Math.sin(Math.PI/4) + y;
        double Do = (Math.atan2(Dy, Dx) * 180/Math.PI + 180) % 360;

        //Set drive motors
        moduleA.setVelocity(
            Math.sqrt(
                Math.pow(Ax, 2) + Math.pow(Ay, 2)
                )
            );
    
        moduleB.setVelocity(
            -Math.sqrt(
                Math.pow(Bx, 2) + Math.pow(By, 2)
                )
            );

        moduleC.setVelocity(
            -Math.sqrt(
                Math.pow(Cx, 2) + Math.pow(Cy, 2)
                )
            );

        moduleD.setVelocity(
            Math.sqrt(
                Math.pow(Dx, 2) + Math.pow(Dy, 2)
                )
            );

        //Set module angle targets
        moduleA.setTargetAngle(Ao);
        moduleB.setTargetAngle(Bo);
        moduleD.setTargetAngle(Do);
        moduleC.setTargetAngle(Co);
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

    }

}
