package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Constants;
import frc.robot.LimeLightControllerMode;

public class LimeLightController extends SubsystemBase {
	public int ledState;
    public Servo servo_L, servo_R;
    private double m_angle;
	public boolean useAngle = false;
	public LimeLightControllerMode m_mode = LimeLightControllerMode.LIMELIGHTCONTROLLER_HUBTRACK;

	public LimeLightController()
	{
		servo_L = new Servo(0);
        servo_R = new Servo(1);
        m_angle = 0.4;
		turnOnLED();
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
		//NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("stream").setNumber(2);
	}
	public void turnOffLED()
	{
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
		//NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("ledMode").setNumber(1);
		
	}
	public void periodic()
	{
		if(m_mode == LimeLightControllerMode.LIMELIGHTCONTROLLER_IDLE){
			m_angle = 0;
			if(get_ta() != 0){
				m_mode = LimeLightControllerMode.LIMELIGHTCONTROLLER_HUBTRACK;
			}
		}

		if(m_mode == LimeLightControllerMode.LIMELIGHTCONTROLLER_HUBTRACK){
			if(get_ta() == 0){
				m_mode = LimeLightControllerMode.LIMELIGHTCONTROLLER_IDLE;
			}

			if(get_ty() < -0.001 && m_angle > 0){
				//m_angle += 0.00008 * get_ty();
				m_angle += 0.00012 * get_ty();
			}
			if(get_ty() > 0.001 && m_angle < 1){
				//m_angle += 0.00008 * get_ty();
				m_angle += 0.00012 * get_ty();
			}
		}
		// m_angle = SmartDashboard.getNumber("Vis Angle", 0);

		servo_L.set(1 - m_angle);
		servo_R.set(m_angle);

		SmartDashboard.putNumber("ta", get_ta());
		SmartDashboard.putNumber("tx", get_tx());
		SmartDashboard.putNumber("ty", get_ty());
		SmartDashboard.putNumber("Vis Dist", (1.79 - Constants.LIMELIGHT_HEIGHT)/Math.tan((m_angle * 4.57 - 0.34)));
		SmartDashboard.putString("visionMode", m_mode.name());
		SmartDashboard.putNumber("m_angle", m_angle);
		SmartDashboard.putNumber("get t_y", get_ty());
	}

	public void setAngle(double angle){
		m_angle = angle;
	}
	public void turnOnLED()
	{
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
		//NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("ledMode").setNumber(3);
		//NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("camMode").setNumber(0);
	}
	public void blinkLED()
	{
		
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(2);
		//NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("ledMode").setNumber(2);

	}
	public double get_tx()
	{
		
		return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
		//return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("tx").getDouble(0);
	}
	public double get_ty()
	{
		return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
		//return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("targetPitch").getDouble(0);
	}
	public double get_ta()
	{
		return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
		//return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("targetArea").getDouble(0);
	}
	/*public boolean has_target()
	{
		return NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight").getEntry("tv").getBoolean(false);
	}*/
}
