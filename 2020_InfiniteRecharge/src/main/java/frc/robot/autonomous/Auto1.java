package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.LED_Controller.LED_Select_Random_Colour;
import frc.robot.commands.drivetrain.DT_DriveDistance;
import frc.robot.commands.drivetrain.DT_Drive_Stop;
import frc.robot.commands.drivetrain.DT_TurnToVisionAngle;
import frc.robot.commands.injector.Injector_Stop;
import frc.robot.commands.injector.Injector_Transfer_Ball;
import frc.robot.commands.intakehopper.Intake_ExtendHopper;
import frc.robot.commands.intakehopper.Intake_GrabBall;
import frc.robot.commands.intakehopper.Intake_RetractHopper;
import frc.robot.commands.shooter.Shooter_ShootAtSpeed;
import frc.robot.commands.shooter.Shooter_Stop;
import frc.robot.commands.shooter.Shooter_Extend;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Injector;
import frc.robot.subsystems.IntakeHopper;
import frc.robot.subsystems.LEDController;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionController;

public class Auto1 extends SequentialCommandGroup  {

    public Auto1(Drivetrain drivetrain, IntakeHopper intakeHopper, Shooter shooter, Injector injector, LEDController ledController, VisionController visionController) {
        addCommands(
            new ParallelDeadlineGroup(new TimerCommand(4),
                new DT_TurnToVisionAngle(drivetrain, visionController, () -> 0.4),
                new Shooter_ShootAtSpeed(shooter, 1900, false, false),
                new Intake_ExtendHopper(intakeHopper),
                new LED_Select_Random_Colour(ledController)
                ),
            new ParallelDeadlineGroup(new TimerCommand(3),
                new Shooter_Extend(shooter),
                new LED_Select_Random_Colour(ledController)
                ),
        
        new ParallelDeadlineGroup(new TimerCommand(0.5),
            new Injector_Transfer_Ball(injector),
            new Intake_RetractHopper(intakeHopper),
            new LED_Select_Random_Colour(ledController)
        ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new Intake_ExtendHopper(intakeHopper),
            new LED_Select_Random_Colour(ledController)
            ),
        new ParallelDeadlineGroup(new TimerCommand(0.5),
            new Intake_GrabBall(intakeHopper),
            new LED_Select_Random_Colour(ledController)
                ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new Intake_RetractHopper(intakeHopper),
            new LED_Select_Random_Colour(ledController)
            ),
        new ParallelCommandGroup(
            new TimerCommand(1),
            new Intake_ExtendHopper(intakeHopper),
            new LED_Select_Random_Colour(ledController)
            ),
        //new DT_DriveDistance(drivetrain, 0.5, 1),
        //new DT_TurnToVisionAngle(drivetrain, visionController, () -> 0.4),
        new Intake_RetractHopper(intakeHopper),
        new DT_DriveDistance(drivetrain, 0.3, 15),

        new ParallelCommandGroup(
            new DT_Drive_Stop(drivetrain),
            new Shooter_Stop(shooter),
            new Injector_Stop(injector),
            new LED_Select_Random_Colour(ledController)
            )
        );
        //addSequential(new cmdTurnToHeading(90));
    
        
    }
}

