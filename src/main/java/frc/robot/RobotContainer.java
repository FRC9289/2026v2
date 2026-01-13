package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.subsystems.*;
import frc.robot.utils.Constants;
import frc.robot.utils.Constants.JoystickConstants;
import frc.robot.controls.*;

public class RobotContainer {
  public static final Joystick controller3D = new Joystick(0);
  public static final Joystick wolfByte = new Joystick(1);
  public static double pov;
  public static final JoystickButton resetHeading_Start = new JoystickButton(controller3D, Constants.JoystickConstants.BaseRM);
  private final Drivetrain drivetrain = Drivetrain.get();
  private final Elevator elevator = Elevator.get();
  // private final SpecDrive specDrive = SpecDrive.get();
  private final WolfSend wolfSend = WolfSend.get();
  private final WolfPoseEstimator wolfPoseEstimator = WolfPoseEstimator.get();
  private ParallelRaceGroup swerveStopCmd;
  SendableChooser<Command> auton_chooser;
  
  public RobotContainer() {
    CameraServer.startAutomaticCapture(0); // Start capturing from the first camera
    CameraServer.startAutomaticCapture(1); // Start capturing from the second camera

    //call to configureBindings() method
    configureBindings();

    // Register swerveStopCmd in Pathplanner to stop robot
    swerveStopCmd = new SwerveDriveCommand(0.0,0.0,0.0, wolfByte, false).withTimeout(3);
    NamedCommands.registerCommand("Swerve Stop", swerveStopCmd);

    //set up auton commands for the driver
    auton_chooser = new SendableChooser<>();
    auton_chooser.setDefaultOption("MidReefAuto", new PathPlannerAuto("MidReefAuto"));
    SmartDashboard.putData("Auton Chooser", auton_chooser);
  }

  private void configureBindings() {
    drivetrain.setDefaultCommand(new SwerveDriveCommand(controller3D.getRawAxis(JoystickConstants.X), controller3D.getRawAxis(JoystickConstants.Y), controller3D.getRawAxis(JoystickConstants.Rot), wolfByte, false));
    resetHeading_Start.onTrue(new InstantCommand(drivetrain::zeroHeading, drivetrain));

    elevator.setDefaultCommand(new ElevatorCommands(wolfByte));

    //Comment out before driving. Will only let robot turn.
    // pov = wolfByte.getPOV();
    // if (pov != -1) {
    //   specDrive.setDefaultCommand(new SpecDriveCommands(wolfByte.getPOV()));
    // }
    //specDrive.setDefaultCommand(new SpecDriveCommands2(wolfByte.getRawAxis(0)));
  }

  public Command getAutonomousCommand() {
    return auton_chooser.getSelected();
  }
}
//Wolfram121 + Elizar Zinsmeister + Bismuthe