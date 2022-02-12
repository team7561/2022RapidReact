package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Constants;
import frc.robot.Ports;

public class VisionController extends SubsystemBase {
	public int ledState;
    public Servo servo_L, servo_R;
    private double m_angle_L, m_angle_R;
	public boolean useAngle = false;

	public VisionController()
	{
		servo_L = new Servo(0);
        servo_R = new Servo(1);
        m_angle_L = 0;
        m_angle_R = 90;
		turnOnLED();
		NetworkTableInstance.getDefault().getTable("photonvision").getEntry("stream").setNumber(2);
	}
	public void turnOffLED()
	{
		NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setNumber(1);
		//NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
	}
	public void periodic()
	{
        //servo_L.setAngle(m_angle_L);
        //servo_R.setAngle(m_angle_R);

		if(!useAngle){
				m_angle_L -= 4 * get_ty()/40;
				m_angle_R += 4 * get_ty()/40;
		}

		SmartDashboard.putNumber("ta", get_ta());
		SmartDashboard.putNumber("tx", get_tx());
		SmartDashboard.putNumber("ty", get_ty());
		SmartDashboard.putNumber("calcDistance", calcDistance());
		SmartDashboard.putNumber("calcSetpoint", calcSetpoint());
	}

	public void setAngle(double angle){
		m_angle_L = angle;
		m_angle_R = 90-angle;
	}
	public void turnOnLED()
	{
		NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setNumber(3);
		NetworkTableInstance.getDefault().getTable("photonvision").getEntry("camMode").setNumber(0);
	}
	public void blinkLED()
	{
		NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ledMode").setNumber(2);

	}
	public double get_tx()
	{
		return NetworkTableInstance.getDefault().getTable("photonvision").getEntry("tx").getDouble(0);
	}
	public double get_ty()
	{
		return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("targetPitch").getDouble(0);
	}
	public double get_ta()
	{
		return NetworkTableInstance.getDefault().getTable("photonvision").getEntry("ta").getDouble(0);
	}
	public boolean has_target()
	{
		return NetworkTableInstance.getDefault().getTable("photonvision").getEntry("tv").getBoolean(false);
	}
	public double getRange()
	{
		return get_ta()*100;
	}

	// Returns distance to target in metres
	public double calcDistance()
	{
		return (Constants.HIGH_HUB_HEIGHT-Constants.LIMELIGHT_HEIGHT)/Math.tan(m_angle_L);
	}

	public double calcSetpoint()
	{
		return calcDistance()*200;
	}
	public double calcSpin()
	{
		return 100;
	}
	public double calcAngle()
	{
		return 0.5;
	}
}
