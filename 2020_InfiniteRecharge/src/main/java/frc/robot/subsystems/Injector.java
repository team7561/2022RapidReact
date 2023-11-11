package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;

public class Injector extends SubsystemBase {

    CANSparkMax injectorMotor;
    Boolean hasBall;
    double setpoint;

    public Injector()
    {
        injectorMotor = new CANSparkMax(Ports.INJECTOR_CANID, MotorType.kBrushless);
        injectorMotor.setSmartCurrentLimit(20);
        injectorMotor.setIdleMode(IdleMode.kCoast);
        setpoint = 0;
    }

    //set speed of both intake motors
    private void setSpeed (double speed)
    {
        setpoint = speed;
    }

    public void transferBall()
    {
        setSpeed(Speeds.INJECTOR_TRANSFER_SPEED);
    }
    //Stops injector
    public void stop()
    {
        setSpeed(Speeds.INJECTOR_STOP_SPEED);
    }
    //Reverses injector
    public void reverse()
    {
        setSpeed(Speeds.INJECTOR_BACKFEED_SPEED);
    }


    public void periodic()
    {
        injectorMotor.set(setpoint);
        updateDashboard();
    }
    public void updateDashboard()
    {
        if (Constants.DEBUG_INJECTOR)
        {
            SmartDashboard.putNumber("Injector Power", injectorMotor.get());
        }
    }

}
