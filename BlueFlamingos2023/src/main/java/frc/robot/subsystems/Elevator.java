package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Elevator extends SubsystemBase {

private CANSparkMax elevatorWinchMotorA;
private CANSparkMax elevatorWinchMotorB;
private MotorControllerGroup elevatorWinchGroup;
private DoubleSolenoid elevatorGripper;
private DoubleSolenoid elevatorExtensionSolenoid;

private DigitalInput limitSwitchUpper;

private double elevatorSpeed = 0;
    public Elevator() {
elevatorWinchMotorA = new CANSparkMax(30, MotorType.kBrushless);
 
elevatorWinchMotorA.restoreFactoryDefaults();  
elevatorWinchMotorA.setInverted(false);
elevatorWinchMotorA.setIdleMode(IdleMode.kCoast);
elevatorWinchMotorA.burnFlash();

limitSwitchUpper = new DigitalInput(1);
  

elevatorWinchMotorB = new CANSparkMax(31, MotorType.kBrushless);
 
elevatorWinchMotorB.restoreFactoryDefaults();  
elevatorWinchMotorB.setInverted(false);
elevatorWinchMotorB.setIdleMode(IdleMode.kCoast);
elevatorWinchMotorB.burnFlash();


elevatorWinchMotorB.follow(elevatorWinchMotorA); 

elevatorWinchGroup = new MotorControllerGroup(elevatorWinchMotorA, elevatorWinchMotorB);

elevatorGripper = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 2, 3);

addChild("elevatorGripper", elevatorGripper);
 
elevatorExtensionSolenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 4, 5);
 addChild("elevatorExtensionSolenoid", elevatorExtensionSolenoid);
 
    }

    @Override
    public void periodic() {
        elevatorWinchGroup.set(elevatorSpeed);
        SmartDashboard.putBoolean("Elevator Upper Limit", limitSwitchUpper.get());
        SmartDashboard.putNumber("Elevator Speed", elevatorWinchGroup.get());
    }
    
    public void lift()
    {
        elevatorSpeed =  0.5;
    }
    public void lower()
    {
        elevatorSpeed = -0.5;
    }
    public void stop()
    {
        elevatorSpeed = 0;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

