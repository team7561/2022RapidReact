package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.subsystems.Climber;
import frc.robot.commands.climber.*;


public class Slow_DrawerSlides extends ParallelCommandGroup {
    /**
     * Creates a new ComplexAuto.
     *
     * @param drive The drive subsystem this command will run on
     * @param hatch The hatch subsystem this command will run on
     */
    public Slow_DrawerSlides(Climber climber) {
          addCommands(
          // Climb
          new Climb_StartWinch(climber),
  
          // Lower Draw Slides
          new Climb_LowerHook(climber)
          );
    }
  
  }