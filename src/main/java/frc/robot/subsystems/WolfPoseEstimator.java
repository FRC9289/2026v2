package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.EstimatedRobotPose;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

import java.util.Optional;

public class WolfPoseEstimator extends SubsystemBase {

    private static final WolfPoseEstimator wolfPoseEstimator = new WolfPoseEstimator();
    private Drivetrain drivetrain = Drivetrain.get();

    private final PhotonCamera frontCamera = new PhotonCamera("frontCam");
    private final PhotonCamera rearCamera = new PhotonCamera("rearCam");

    private final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    private final Transform3d frontCamTransform = new Transform3d(
        new Translation3d(0.5, 0.0, 0.0), new Rotation3d(0, 0, 0));

    private final Transform3d rearCamTransform = new Transform3d(
        new Translation3d(-0.5, 0.0, 0.0), new Rotation3d(0, 0, Math.PI));

    private final PhotonPoseEstimator frontEstimator = new PhotonPoseEstimator(
        fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, frontCamTransform);

    private final PhotonPoseEstimator rearEstimator = new PhotonPoseEstimator(
        fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, rearCamTransform);

    private Pose2d pose = new Pose2d();

    public WolfPoseEstimator() {
        frontEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
        rearEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    }

    @Override
    public void periodic() {
        Optional<EstimatedRobotPose> frontPose = frontEstimator.update(frontCamera.getLatestResult());
        Optional<EstimatedRobotPose> rearPose = rearEstimator.update(rearCamera.getLatestResult());

        if (frontPose.isPresent() && rearPose.isPresent()) {
            Pose2d front = frontPose.get().estimatedPose.toPose2d();
            Pose2d rear = rearPose.get().estimatedPose.toPose2d();

            double avgX = (front.getX() + rear.getX()) / 2.0;
            double avgY = (front.getY() + rear.getY()) / 2.0;
            double avgRot = (front.getRotation().getRadians() + rear.getRotation().getRadians()) / 2.0;

            pose = new Pose2d(avgX, avgY, new edu.wpi.first.math.geometry.Rotation2d(avgRot));
        } else if (frontPose.isPresent()) {
            pose = frontPose.get().estimatedPose.toPose2d();
        } else if (rearPose.isPresent()) {
            pose = rearPose.get().estimatedPose.toPose2d();
        }

        drivetrain.getPoseEstimator().addVisionMeasurement(pose, Timer.getFPGATimestamp());

        SmartDashboard.putNumber("PhotonX", pose.getX());
        SmartDashboard.putNumber("PhotonY", pose.getY());
        SmartDashboard.putNumber("PhotonRot", pose.getRotation().getDegrees());
    }

    public Pose2d getPose2d() {
        return pose;
    }

    public static WolfPoseEstimator get() {
        return wolfPoseEstimator;
    }
}