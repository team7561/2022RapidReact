package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import frc.robot.GripPipelineContours;
import java.util.concurrent.atomic.AtomicBoolean;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class OnboardVisionController extends SubsystemBase implements Runnable {
    private GripPipelineContours gripPipelineContours = new GripPipelineContours();
    private boolean performVisionTracking = true;
    private UsbCamera camera = null;
    private Mat sourceMat = new Mat();
    private Boolean started = false;
    private AtomicBoolean stopRequested = new AtomicBoolean(false);

    public OnboardVisionController(){
        try {
            camera = CameraServer.startAutomaticCapture();

            configureCamera();

            //this.start();

        } catch (Exception e) {
            System.out.println("Exception constructing camera in RobotVision");
            e.printStackTrace();
            camera = null;  // we need to test the camera is NOT null before trying to use it
            SmartDashboard.putNumber("ball_x",0); // default Peg x is directly in front
        }
    }

    public void startTracking() {
        performVisionTracking = true;
    }

    public void stopTracking(){
        performVisionTracking = false;
    }

    public void processImageInPipeline()
    {
        if (camera == null)
            return;

        if (performVisionTracking) {
            sourceMat.empty(); 
            CvSink cvSink = CameraServer.getVideo();
            //CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);
            cvSink.grabFrame(sourceMat);

            // Put a rectangle on the image
            //Imgproc.rectangle(sourceMat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
            // Give the output stream a new image to display
            //outputStream.putFrame(sourceMat);
           
            gripPipelineContours.process(sourceMat);

            SmartDashboard.putNumber("Num Contours",gripPipelineContours.filterContoursOutput().size());
            
            double ball_x = 0;
            if (!gripPipelineContours.filterContoursOutput().isEmpty()) {
                    Rect ballRect = null;
                    ballRect = Imgproc.boundingRect(gripPipelineContours.filterContoursOutput().get(0));
                    ball_x = ballRect.x + (ballRect.width/2);
                    
                    SmartDashboard.putNumber("ball_x_coord", ball_x);
                    
                    ball_x = (ball_x-150)/16;
            }
            SmartDashboard.putNumber("ball_x",ball_x);
            sourceMat.release();
        }
    }

    private void configureCamera() {
        camera.setResolution(320, 240);
        //camera.setResolution(176, 144);
        //camera.setResolution(160, 120);
        camera.setBrightness(50);
        camera.setExposureManual(50);
        camera.setWhiteBalanceManual(0);
    }

    public void periodic(){
        boolean redAlliance = (DriverStation.getAlliance() == Alliance.Red);
        gripPipelineContours.redCargo = redAlliance;

        if (!started) {
            started = true;
            Thread thread = new Thread(this);
            thread.setPriority( Thread.MIN_PRIORITY + 1); // or  Thread.NORM_PRIORITY - 2
            thread.start();
        }
    }

    public void run() {
        while (!stopRequested.get()) {

            try{
            processImageInPipeline();
            System.out.println("test");
            }
            catch(Exception e)
            {
                System.out.println(e.getStackTrace().toString());
            }
        }
    }
    
}