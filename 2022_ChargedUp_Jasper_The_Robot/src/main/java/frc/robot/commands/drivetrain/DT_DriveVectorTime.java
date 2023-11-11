package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class DT_DriveVectorTime extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_subsystem;
    private final double m_twist, m_angle, m_speed, m_time;
    double m_targetAngle;
    double m_angleDifference;
    Timer timer;
    

    
    public DT_DriveVectorTime(Drivetrain subsystem, double twist, double angle, double speed, double time){
        m_subsystem = subsystem;
        m_twist = twist;
        m_angle = angle;
        m_speed = speed;
        m_time = time;
        timer = new Timer();
        addRequirements(subsystem);
    }
    
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        m_targetAngle = m_subsystem.readGyro();
    }
    
    @Override
    public void execute() {
        m_angleDifference = m_targetAngle - m_subsystem.readGyro();
        System.out.println(m_angleDifference);
        //Error in appropriate direction and magnitude (-180 to 180)
        if (Math.abs(m_angleDifference) > 180){
          m_angleDifference = (360 - Math.abs(m_angleDifference)) * -Math.signum(m_angleDifference);
        }
    
        //Command doesn't use target angle input in SPIN mode
        m_subsystem.updateDashboard();
        m_subsystem.setSwerveVectorNoGyro(-m_angleDifference * 0.01, (m_angle + 90) % 360, m_speed);
        // 🤣
    }
    
    @Override
    public void end(boolean interrupted) {
        System.out.println("Command ended");
        stop();
    }
    
  public void stop() {
    m_subsystem.moduleD.setVelocity(0);
    m_subsystem.moduleC.setVelocity(0);
    m_subsystem.moduleA.setVelocity(0);
    m_subsystem.moduleB.setVelocity(0);
  }
    @Override
    public boolean isFinished() {
        System.out.println(timer.get());
        return (timer.get()>m_time);
    }
}
