package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.math.geometry.Pose2d;

public class DT_SetPose extends InstantCommand {
    
    private Drivetrain m_drivetrain;
    private Pose2d m_pose2d;

    public DT_SetPose(Drivetrain dt, Pose2d pose2d) {
        super();
        m_drivetrain = dt;
        m_pose2d = pose2d;
        
    }

    @Override
    public void execute() {
        m_drivetrain.setPose(m_pose2d);
    }

}