package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class DT_DriveVectorTime extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_subsystem;
    private final double m_twist, m_angle, m_speed, m_time;
    Timer timer;
    

    
    public DT_DriveVectorTime(Drivetrain subsystem, double twist, double angle, double speed, double time){
        m_subsystem = subsystem;
        m_twist = twist;
        m_angle = angle;
        m_speed = speed;
        m_time = time;
        addRequirements(subsystem);
    }
    
    @Override
    public void initialize() {
        timer.start();
    }
    
    @Override
    public void execute() {
        m_subsystem.setSwerveVector(m_twist, m_angle, m_speed);
    }
    
    @Override
    public void end(boolean interrupted) {
        m_subsystem.setSwerveVector(m_twist, m_angle, 0);
    }
    
    @Override
    public boolean isFinished() {
        return (timer.get()<m_time);
    }
}
