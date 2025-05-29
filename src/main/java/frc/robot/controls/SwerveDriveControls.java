package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.utils.Constants.JoystickConstants;
import frc.robot.commands.SwerveDriveCommands;
import frc.robot.subsystems.Drivetrain;

public class SwerveDriveControls extends Command {
  private Drivetrain drivetrain = Drivetrain.getInstance();
  private boolean fieldOriented = true;
  private SwerveDriveCommands cmd;

  /** Creates a new SwerveDrive. */
  public SwerveDriveControls() {
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double slider = (-RobotContainer.controller3D.getRawAxis(JoystickConstants.Slider) + 1) / 2.0;
    if (slider == 0)  {
      slider = 0.001;
    }

    double frontSpeed = RobotContainer.controller3D.getRawAxis(JoystickConstants.X) * slider;
    double sideSpeed = RobotContainer.controller3D.getRawAxis(JoystickConstants.Y) * slider;
    double turnSpeed = RobotContainer.controller3D.getRawAxis(JoystickConstants.Rot) * slider;

    cmd = new SwerveDriveCommands(
        frontSpeed,
        sideSpeed,
        turnSpeed,
        fieldOriented);
    cmd.execute();
  }
}