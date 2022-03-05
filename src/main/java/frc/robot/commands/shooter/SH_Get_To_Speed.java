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
    double m_timeout;

    Timer timer = new Timer();

    /**
     * Creates a new SH_Retract.
     *  @param subsystem
     */
    public SH_Get_To_Speed(Shooter subsystem, double timeout) {
        m_subsystem = subsystem;
        m_timeout = timeout;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_subsystem.start();
        SmartDashboard.putNumber("LED Value", Constants.BLINKIN_LIGHTCHASE);
        timer.start();
    }

    @Override
    public void execute() {        
    }

    @Override
    public void end(boolean interrupted) {
        
        SmartDashboard.putNumber("LED Value", Constants.BLINKIN_BLUE);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > m_timeout || m_subsystem.atSetpoint();
    }
}
