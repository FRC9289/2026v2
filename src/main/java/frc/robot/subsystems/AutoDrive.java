package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import com.pathplanner.lib.path.PathPlannerPath;

public class AutoDrive extends SubsystemBase {
    private static final AutoDrive auto = new AutoDrive();

    private final Pose2d goal = new Pose2d(3.0, -1.0, Rotation2d.fromDegrees(90.0));
    private final Pose2d hang = new Pose2d(1.0, 1.0, Rotation2d.fromDegrees(180.0));
    private final Pose2d[] coral = {
        new Pose2d(0, -1, Rotation2d.fromDegrees(0.0)),
        new Pose2d(-.5, -1.5, Rotation2d.fromDegrees(45)),
        new Pose2d(-1, -2, Rotation2d.fromDegrees(90)),
        new Pose2d(-.5, -2.5, Rotation2d.fromDegrees(135)),
        new Pose2d(0, -3, Rotation2d.fromDegrees(180)),
        new Pose2d(.5, -2.5, Rotation2d.fromDegrees(225)),
        new Pose2d(1, -2, Rotation2d.fromDegrees(270)),
        new Pose2d(.5, -1.5, Rotation2d.fromDegrees(315))
    };
    public AutoDrive() {}

    @Override
    public void periodic() {

    }
}