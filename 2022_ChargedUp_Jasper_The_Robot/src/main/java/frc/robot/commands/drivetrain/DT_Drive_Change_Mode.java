package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SwerveMode;



public class DT_Drive_Change_Mode extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_subsystem;
    private final SwerveMode m_mode;
    

    
    public DT_Drive_Change_Mode(Drivetrain subsystem, SwerveMode mode){
        m_subsystem = subsystem;
        m_mode = mode;
        addRequirements(subsystem);
    }
    
    @Override
    public void initialize() {
        m_subsystem.setMode(m_mode);
    }
    
    @Override
    public void execute() {
    }
    
    @Override
    public void end(boolean interrupted) {
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
