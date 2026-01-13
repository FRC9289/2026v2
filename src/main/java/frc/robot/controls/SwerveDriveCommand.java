package frc.robot.controls;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.utils.Constants.JoystickConstants;
import edu.wpi.first.wpilibj.Joystick;

public class SwerveDriveCommand extends Command {
    private final Drivetrain drivetrain = Drivetrain.get();
    private Pose2d targetPose;
    private Command pathFollowCommand;
    private Pose2d[] ends = {
        new Pose2d(1.0, 2.0, Rotation2d.fromDegrees(180))
    };

    private static final double DEADZONE = 0.05;
    private double x;
    private double y;
    private double rot;
    private int end;
    private boolean manual;

    public SwerveDriveCommand(double x, double y, double rot, Joystick wolfByte, boolean manual) {
        this.x = x * Math.max((-RobotContainer.controller3D.getRawAxis(JoystickConstants.Slider) + 1) / 2.0, 0.001);
        this.y = y * Math.max((-RobotContainer.controller3D.getRawAxis(JoystickConstants.Slider) + 1) / 2.0, 0.001);
        this.rot = rot * Math.max((-RobotContainer.controller3D.getRawAxis(JoystickConstants.Slider) + 1) / 2.0, 0.001);
        
        for (int i = 3; i <= 9; i++) {
            if (wolfByte.getRawButton(0)) {
                end = i;
            }
        }

        this.manual = manual;

        addRequirements(drivetrain);
    }

    public void setTargetPose(Pose2d pose) {
        this.targetPose = pose;
        schedulePathFollow();
    }

    @Override
    public void execute() {
        if (isManualInput()) {
            if (this.pathFollowCommand != null && this.pathFollowCommand.isScheduled()) {
                this.pathFollowCommand.cancel();
            }

            drivetrain.swerveDrive(
                this.x,
                this.y,
                this.rot,
                true,
                drivetrain.getPose().getTranslation(),
                true
            );
        } else {
            if (this.targetPose != null && (this.pathFollowCommand == null || !this.pathFollowCommand.isScheduled())) {
                schedulePathFollow();
            }
        }
    }

    private boolean isManualInput() {
        return (Math.abs(this.x) > DEADZONE || Math.abs(this.y) > DEADZONE || Math.abs(this.rot) > DEADZONE) || this.manual;
    }

    private void schedulePathFollow() {
        if (this.targetPose == null) {
            return;
        }

        this.pathFollowCommand = AutoBuilder.pathfindToPose(ends[end], PathConstraints.unlimitedConstraints(12.0)
        );

        this.pathFollowCommand.schedule();
    }

    @Override
    public void end(boolean interrupted) {
        if (this.pathFollowCommand != null && this.pathFollowCommand.isScheduled()) {
            this.pathFollowCommand.cancel();
        }
        drivetrain.stopModules();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
//Wolfram121