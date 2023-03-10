package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    DoubleSolenoid arm_extension;
    CANSparkMax armMotor;
    boolean extend = false;
    double armSpeed = 0;
    public Arm() {

        arm_extension = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 1, 3);
        armMotor = new CANSparkMax(60, MotorType.kBrushed);
    }
    
    @Override
    public void periodic() {
        armMotor.set(armSpeed);

        if(extend)
        {
            arm_extension.set(Value.kForward);
        }
        else {
            arm_extension.set(Value.kReverse);
        }

        SmartDashboard.putNumber("Arm Speed", armMotor.get());
    }
    public void setSpeed(double speed)
    {
        armSpeed = speed; 
    }
    public void extend()
    {
        extend = true;
    }
    public void retract()
    {
        extend = false;
    }
    public void toggle()
    {
        extend = !extend;
    }

    public void lower()
    {
        armSpeed = 0.9;
    }
    public void raise()
    {
        armSpeed = -0.9;
    }
    public void stop()
    {
        armSpeed = 0;
    }

}
