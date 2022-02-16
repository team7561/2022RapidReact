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
import frc.robot.subsystems.Injector;
import frc.robot.Constants;
import frc.robot.InjectorMode;
import frc.robot.subsystems.Shooter;

public class SH_Auto_Shoot_Ball extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Shooter m_subsystem;
    private final Injector m_injector;
    private boolean shooterSpeedAcheived, ballShot;
    private double m_setpointA, m_setpointB;

    /**
     * Creates a new SH_Retract.
     *  @param subsystem
     */
    public SH_Auto_Shoot_Ball(Shooter subsystem, Injector injector, double setpointA, double setpointB) {
        shooterSpeedAcheived = false;
        ballShot = false;
        m_subsystem = subsystem;
        m_setpointA = setpointA;
        m_setpointB = setpointB;
        m_injector = injector;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_subsystem.start();
        m_subsystem.set_RPM(m_setpointA, m_setpointB);
        m_injector.setMode(InjectorMode.INJECTOR_INDEX_BALL);
    }

    @Override
    public void execute() {
        if(
            Math.abs(SmartDashboard.getNumber("Shooter A Speed", 0) + SmartDashboard.getNumber("Shooter A Setpoint", 0)) < Constants.SHOOTER_TOLERANCE &&
            Math.abs(SmartDashboard.getNumber("Shooter B Speed", 0) + SmartDashboard.getNumber("Shooter B Setpoint", 0)) < Constants.SHOOTER_TOLERANCE
        ){
            shooterSpeedAcheived = true;
        } 
        
        if(shooterSpeedAcheived && !m_injector.balls){
            ballShot = true;
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.stop();
        m_subsystem.set_RPM(0, 0);
        m_injector.setMode(InjectorMode.INJECTOR_STOP);
    }

    @Override
    public boolean isFinished() {
        return ballShot;
    }
}
