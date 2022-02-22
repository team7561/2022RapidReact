package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.InjectorMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import frc.robot.GripPipelineContours;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OnboardVisionController /*extends Thread*/ extends SubsystemBase {
    private GripPipelineContours gripPipelineContours = new GripPipelineContours();
    public boolean balls;
    private double centreX = 0.0;
    private double ballY = 0.0;
    private final Object imgLock = new Object();  // to lock access to te centreX variable

    private boolean performVisionTracking = false;
    private UsbCamera camera = null;  // it will remain null if we have no camera plugged into the USB ports
    public InjectorMode m_mode = InjectorMode.INJECTOR_STOP;
    private Mat sourceMat = new Mat();

    public OnboardVisionController(){
        try {
            camera = CameraServer.startAutomaticCapture();

            configureCamera();

            //this.start();

        } catch (Exception e) {
            System.out.println("Exception constructing camera in RobotVision");
            e.printStackTrace();
            camera = null;  // we need to test the camera is NOT null before trying to use it
            setBallX(0);  // default Peg x is directly in front
        }

    }
    private double getPosition() {
        Rect ballRect = null;
        double ball_x = 0;

        //assumes that contour 0 is right hand tape
        ballRect = Imgproc.boundingRect(gripPipelineContours.filterContoursOutput().get(0));

        setBallY(ballRect.y);

        //SmartDashboard.putNumber("rightTape.width", rightTape.width);
        ball_x = ballRect.x + (ballRect.width/2);
        SmartDashboard.putNumber("ball_x",ball_x);


        return (ball_x-160)/160;
    }

    /**
     * Using the Camera, take an image and process using the GRIP gripPipeline
     * To find the contours of the tape around the peg.
     * <p>
     * return double X co-ordinate that is the centre between the tapes.
     */
    public void startTracking() {
        //configureVisionCameraSettingsForTracking();
        performVisionTracking = true;
    }

    /**
     * Stops the vision thread
     */
    public void stopTracking(){
        // stop the vision thread from performing the expensive processing of the video images
        performVisionTracking = false;
    }

    public boolean isPerformVisionTracking() {
        return performVisionTracking;
    }

    public double getBallX(){
        synchronized (imgLock) {
            return centreX;
        }
    }

    private void setBallX(double x)
    {
        synchronized (imgLock) {
            centreX = x;
        }
    }

    public double getBallY() {
        return ballY;
    }

    private void setBallY(double ballY) {
        this.ballY = ballY;
    }
    public void processImageInPipeline()
    {
        if (camera == null)
            return;

        if (performVisionTracking) {

            sourceMat.empty(); 
            CvSink cvSink = CameraServer.getVideo();
            cvSink.grabFrame(sourceMat);
           
            // create an instance of the GRIP gripPipeline
            gripPipelineContours.process(sourceMat);

            //process image through gripPipeline
            SmartDashboard.putNumber("Num Contours",gripPipelineContours.filterContoursOutput().size());
            if (!gripPipelineContours.filterContoursOutput().isEmpty()) {
                // process contours
                double finalPosition = getPosition();
                setBallX(finalPosition);
            }
            else
            {
                setBallX(0);
            }

            // release any resources held in the source Map as these can be expensive
            sourceMat.release();  // todo: after testing that a memory leak occurs when we do not  release, remove the comment block
        }
    }

    private void configureCamera() {

        camera.setResolution(320, 240);
        camera.setBrightness(50);
        camera.setExposureManual(50);
        camera.setWhiteBalanceManual(0);
    }
    public void setMode(InjectorMode mode){
        m_mode = mode;
    }

    public InjectorMode getMode(){
        return m_mode;
    }


    public void periodic(){
        processImageInPipeline();
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INJECTOR)
        {
        }
    }
}