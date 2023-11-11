package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Speeds;

public class IntakeHopper extends SubsystemBase {

    VictorSP intakeHopperMotor;
    DigitalInput intakeLimitSwitch;
    DoubleSolenoid hopperSolenoid;
    Boolean hasBall;

    public IntakeHopper()
    {
        intakeHopperMotor = new VictorSP(Ports.INTAKE_CHANNEL);
        hopperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Ports.INTAKE_SOLENOID_CHANNEL_A,Ports.INTAKE_SOLENOID_CHANNEL_B);
    }

    //set speed of both intake motors
    private void intakeSpeed (double speed)
    {
        intakeHopperMotor.set(speed);
    }

    //Get the Ball
    public void grabBall()
    {
        intakeSpeed(Speeds.GET_BALL_SPEED);
        updateDashboard();
    }

    //For keep in the Ball while driving
    public void keepBall()
    {
        intakeSpeed(Speeds.KEEP_BALL_SPEED);
    }
    public void extendHopper()
    {
        hopperSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void retractHopper()
    {
        hopperSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public boolean isExtended()
    {
        return hopperSolenoid.get() == DoubleSolenoid.Value.kForward;
    }
    //Ejects the Ball fast
    public void ejectBall()
    {
        intakeSpeed(Speeds.EJECT_BALL_SPEED);
    }

   /* //Ejects the Ball slow
    public void ejectBallSlow()
    {
        intakeSpeed(-0.4);
    }
   */
    //Stops intake
    public void stop()
    {
        intakeSpeed(Speeds.STOP_BALL_SPEED);
    }


    public void updateDashboard()
    {
        if (Constants.DEBUG_INTAKE)
        {
            SmartDashboard.putNumber("Intake Power", intakeHopperMotor.get());
        }
    }




}
