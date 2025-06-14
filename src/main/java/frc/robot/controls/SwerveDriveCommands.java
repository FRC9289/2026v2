package frc.robot.controls;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.utils.Constants.JoystickConstants;
import frc.robot.subsystems.Drivetrain;

public class SwerveDriveCommands extends Command {
  private Drivetrain drivetrain = Drivetrain.getInstance();
  private boolean fieldOriented = true;
  private double frontSpeed;
  private double sideSpeed;
  private double turnSpeed;

  /** Creates a new SwerveDrive. */
  public SwerveDriveCommands(double fs, double ss, double ts) {
    addRequirements(drivetrain);
    this.frontSpeed = fs;
    this.sideSpeed = ss; 
    this.turnSpeed = ts;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.drivetrain.swerveDrive(
        frontSpeed,
        sideSpeed,
        turnSpeed,
        fieldOriented,
        new Translation2d(),
        true);
  }
}