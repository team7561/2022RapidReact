package frc.robot.commands.drivetrain;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class DT_Drive_Reset_Gyro extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain m_subsystem;    

    
    public DT_Drive_Reset_Gyro(Drivetrain subsystem){
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }
    
    @Override
    public void initialize() {
        m_subsystem.resetGyro();
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
