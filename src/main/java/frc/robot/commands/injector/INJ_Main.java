package frc.robot.commands.injector;

import frc.robot.Constants;
import frc.robot.subsystems.Injector;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.InjectorMode;

public class INJ_Main extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Injector m_subsystem;

  public INJ_Main(Injector subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
      if(m_subsystem.getMode() == InjectorMode.INJECTOR_FORWARD) {
          m_subsystem.forward();
      }

      if(m_subsystem.getMode() == InjectorMode.INJECTOR_REVERSE) {
          m_subsystem.reverse();
      }

      if(m_subsystem.getMode() == InjectorMode.INJECTOR_INDEX_BALL){
          if(m_subsystem.getEncoderCount() < Constants.INJECTOR_CARGO_INDEX_PULSE_COUNT){
              m_subsystem.forward();
          } else {
              m_subsystem.setMode(InjectorMode.INJECTOR_STOP);
          }
      }

      if(m_subsystem.getMode() == InjectorMode.INJECTOR_REVERSE_INDEX_BALL){
        if(m_subsystem.getEncoderCount() > -Constants.INJECTOR_CARGO_INDEX_PULSE_COUNT){
            m_subsystem.forward();
        } else {
            m_subsystem.setMode(InjectorMode.INJECTOR_STOP);
        }
    }

      if(m_subsystem.getMode() == InjectorMode.INJECTOR_STOP){
          m_subsystem.stop();
      }
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
