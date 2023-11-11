package frc.robot;
import edu.wpi.first.wpilibj.Counter;

public class LIDARSensor {
    private Counter counter;
   
    private static final int CALIBRATION_OFFSET = -18;

    private int printedWarningCount = 5;

    public void LIDARSensorInit(int port) {
        counter = new Counter(port);
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    public double getDistance() {
        double cm;
        /* If we haven't seen the first rising to falling pulse, then we have no measurement.
         * This happens when there is no LIDAR-Lite plugged in, btw.
         */
        if (counter.get() < 1) {
            if (printedWarningCount-- > 0) {
                System.out.println("LidarLitePWM: waiting for distance measurement");
            }
            return 0;
        }
        /* getPeriod returns time in seconds. The hardware resolution is microseconds.
         * The LIDAR-Lite unit sends a high signal for 10 microseconds per cm of distance.
         */
        cm = (counter.getPeriod() * 1000000.0 / 10.0) + CALIBRATION_OFFSET;
        return cm;
    }
}