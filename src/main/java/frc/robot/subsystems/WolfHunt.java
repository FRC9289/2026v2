package frc.robot.subsystems;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import java.util.ArrayList;

public class WolfHunt extends SubsystemBase {
    private Drivetrain drivetrain;
    private ArrayList<Pose2d> coords;

    public WolfHunt() {
        drivetrain = Drivetrain.getInstance();
        coords = new ArrayList<>();
        coords.add(new Pose2d(0, 3.06705, new Rotation2d(0)));
    }
}
