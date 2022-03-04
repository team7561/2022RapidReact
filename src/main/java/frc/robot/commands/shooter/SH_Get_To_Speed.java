/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class SH_Get_To_Speed extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private boolean shooterSpeedAcheived;
    double m_timeout;

    Timer timer = new Timer();

    /**
     * Creates a new SH_Retract.
     *  @param subsystem
     */
    public SH_Get_To_Speed(Shooter subsystem, double timeout) {
        shooterSpeedAcheived = false;
        m_subsystem = subsystem;
        m_timeout = timeout;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_subsystem.start();
        timer.start();
    }

    @Override
    public void execute() {
        if(
            Math.abs(SmartDashboard.getNumber("Shooter A Speed", 0) - SmartDashboard.getNumber("Shooter A Setpoint", 0)) < Constants.SHOOTER_TOLERANCE &&
            Math.abs(SmartDashboard.getNumber("Shooter B Speed", 0) - SmartDashboard.getNumber("Shooter B Setpoint", 0)) < Constants.SHOOTER_TOLERANCE
        ){
            shooterSpeedAcheived = true;
        } 
        
        
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.stop();
        m_subsystem.set_voltage(0,0);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > m_timeout || shooterSpeedAcheived;
    }
}
