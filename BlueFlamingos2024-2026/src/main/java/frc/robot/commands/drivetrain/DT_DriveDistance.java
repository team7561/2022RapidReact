package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;


/**
* An example command that uses an example subsystem.
*/
public class DT_DriveDistance extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_subsystem;
    private final double m_speed;
    private final double m_distance;
    private final boolean m_inverted;
    private double m_difference; //difference between final distance and distance robot travelled
    
    
    /**
    * Creates a new ExampleCommand.
    *
    * @param subsystem The subsystem used by this command.
    */
    
    public DT_DriveDistance(Drivetrain subsystem, double speed, double distance){
        m_subsystem = subsystem;
        m_speed = speed;
        m_distance = distance;
        m_inverted = m_distance < 0;
        SmartDashboard.putBoolean("Inverted", m_inverted);
        SmartDashboard.putNumber("m_distance", m_distance);
        addRequirements(subsystem);
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_subsystem.resetEncoders();
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        m_difference = m_distance - m_subsystem.getRightEncoder();
        SmartDashboard.putNumber("m difference", m_difference);
        if (m_inverted)
        {
            if (m_difference > m_distance/5){
                m_subsystem.drive(m_speed, -m_speed); 
            }
            else{
                m_subsystem.drive(m_speed/2, -m_speed/2);
            }
            m_subsystem.updateDashboard();
        }
        else {
            if (m_difference < -m_distance/5){
                m_subsystem.drive(-m_speed, m_speed); 
            }
            else{
                m_subsystem.drive(-m_speed/2, m_speed/2);
            }
            
        }
        m_subsystem.updateDashboard();
    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (m_inverted)
        {
            m_subsystem.drive(0.01, -0.01);
        }
        else {
            m_subsystem.drive(-0.01, 0.01);
        }
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (Math.abs(m_difference) < 10);
    }
}
