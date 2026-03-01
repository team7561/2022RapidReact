package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * Sets drivetrain sensors to a default state.
 * <p>
 * Executes once before robot autonomous movement.
 */
public class DT_InitDrivePose extends InstantCommand {
    
    private Drivetrain m_drivetrain;
    private double m_x, m_y;

    /**
     * Creates a new ResetDrivePose.
     * @param dt The robot's drivetrain instance to be reset.
     * @see ResetDrivePose
     */
    public DT_InitDrivePose(Drivetrain dt, double x, double y) {
        super();
        m_drivetrain = dt;
        m_x = x;
        m_y = y;
    }

    /**
     * Executes the reset exactly once and within a single cycle.
     * 
     * @see edu.wpi.first.wpilibj2.command.InstantCommand
     */
    @Override
    public void execute() {
        m_drivetrain.setPose(m_x, m_y);
    }

}