package frc.robot.subsystems;

import frc.robot.Ports;

import java.util.Random;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDController extends SubsystemBase{

    PWM blinkin;
    public LEDController() {
        blinkin = new PWM(Ports.PWM_LED_CONTROLLER_CHANNEL);
        SmartDashboard.putNumber("LED Value", -0.31);
    }

    public void periodic(){
        blinkin.setSpeed(SmartDashboard.getNumber("LED Value", 0.83));
        SmartDashboard.putNumber("LED Value", blinkin.getSpeed());
    }    
    public void setValue(double value){
        SmartDashboard.putNumber("LED Value", value);
    }    
    public void setRandomValue(){
        Random rand = new Random();
        double pattern = rand.nextInt(100)*1.0/100-0.99;
        System.out.println(pattern);
        SmartDashboard.putNumber("LED Value", pattern);
    }
    
}
