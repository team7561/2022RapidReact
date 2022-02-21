package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;
import frc.robot.InjectorMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import frc.robot.GripPipeline;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OnboardVisionController /*extends Thread*/ extends SubsystemBase {
    private GripPipeline gripPipeline = new GripPipeline();
    public boolean balls;
    private long processCount = 0;
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
    private double getFinalPosition() {
        Rect leftTape = null;
        double leftMiddle = 0;

        Rect rightTape = null;
        double rightMiddle = 0;

        double finalPosition = 0;

        double powerIncrement = 0;

        //This is the middle of the distance between the tapes
        double centreOfDistanceBetweenTapes;

        //assumes that contour 0 is right hand tape
        rightTape = Imgproc.boundingRect(gripPipeline.filterContoursOutput().get(0));
        //SmartDashboard.putNumber("rightTape.x", rightTape.x);


        setBallY(rightTape.y);

        //SmartDashboard.putNumber("rightTape.width", rightTape.width);
        rightMiddle = rightTape.x + (rightTape.width/2);
            SmartDashboard.putNumber("rightMiddle",rightMiddle);

        if (gripPipeline.filterContoursOutput().size()==2){
            //Assumes we have two tapes, and that contour 1 is the left tape
            leftTape = Imgproc.boundingRect(gripPipeline.filterContoursOutput().get(1));
            //SmartDashboard.putNumber("leftTape.x", leftTape.x);
            //SmartDashboard.putNumber("leftTape.width", leftTape.width);
            leftMiddle = leftTape.x + (leftTape.width/2);
                SmartDashboard.putNumber("leftMiddle", leftMiddle);


            //final position for two tapes
            centreOfDistanceBetweenTapes = (rightMiddle - leftMiddle) / 2;
            //SmartDashboard.putNumber("centreOfDistanceBetweenTapes",centreOfDistanceBetweenTapes);
            finalPosition = rightMiddle-centreOfDistanceBetweenTapes;
                SmartDashboard.putBoolean("Two contours",true);

        }
        else{

            finalPosition = (rightTape.x + (rightTape.width / 2));
            // scale back to a number between -1 to 1, and then divides by 3 for a power cap, hence 240
                SmartDashboard.putBoolean("Two contours",false);


        }

        //SmartDashboard.putNumber("Final middle X", finalPosition);

        finalPosition = finalPosition-80;


        powerIncrement = finalPosition / 240;

            SmartDashboard.putNumber("scaled finalPosition", finalPosition);
            SmartDashboard.putNumber("powerIncrement", powerIncrement);
        return powerIncrement;
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
        // if we have no camera we can't process images
        if (camera == null)
            return;

        if (performVisionTracking) {

            // get camera image
            sourceMat.empty();  // we re-use the same Map to process the source image
            CvSink cvSink = CameraServer.getVideo();
            cvSink.grabFrame(sourceMat);

            // create an instance of the GRIP gripPipeline
            gripPipeline.process(sourceMat);

            System.out.println("Inside thread [contours:" + gripPipeline.filterContoursOutput().size() + "] [processCount:" + processCount++ + "]");

            //process image through gripPipeline

            if (!gripPipeline.filterContoursOutput().isEmpty()) {
                // process contours
                double finalPosition = getFinalPosition();

                setBallX(finalPosition);
                System.out.println("Peg X = " + getBallX());
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
        camera.setBrightness(0);
        camera.setExposureManual(0);
        camera.setWhiteBalanceManual(0);
    }
    public void setMode(InjectorMode mode){
        m_mode = mode;
    }

    public InjectorMode getMode(){
        return m_mode;
    }


    public void periodic(){
        
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INJECTOR)
        {
        }
    }
}