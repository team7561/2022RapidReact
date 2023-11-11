package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class PiVisionController extends SubsystemBase {
    private boolean performVisionTracking = true;
    public double ball_x;

    PhotonCamera camera = null;
    public PiVisionController(){
        try {
            camera = new PhotonCamera("photonvision");
        } catch (Exception e) {
            System.out.println("Exception constructing camera in RobotVision");
            e.printStackTrace();
            SmartDashboard.putNumber("ball_x",0); // default Peg x is directly in front
        }
    }

    public void startTracking() {
        performVisionTracking = true;
        camera.setDriverMode(true);

    }

    public void stopTracking(){
        performVisionTracking = false;
        camera.setDriverMode(false);
    }

    public void processImageInPipeline()
    {
        if (performVisionTracking) {

            var result = camera.getLatestResult();

            SmartDashboard.putBoolean("Num Contours", result.hasTargets());
            
            ball_x = 0;
            if (result.hasTargets()) {
                SmartDashboard.putNumber("ball_x", result.getBestTarget().getYaw());
            }
            else
            {
                SmartDashboard.putNumber("ball_x", 0);
            }
        }
    }

    public void periodic(){
        if (DriverStation.getAlliance() == Alliance.Red)
        {
            camera.setPipelineIndex(1);
        }
        else {
            camera.setPipelineIndex(2);
        }
        
    }
    
}