package frc.robot.subsystems;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class SpecDrive extends PIDSubsystem {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private static final SpecDrive specDrive = new SpecDrive();
    private double yaw;

    public SpecDrive() {
        super(new PIDController(0.1, 0, 0));
        getController().setTolerance(1.0);

    }

    @Override
    protected double getMeasurement() {
        // Get the gyro yaw and ensure it is within -180 to 180
        double yaw = drivetrain.getGyro().getYaw().getValueAsDouble();
        if (yaw > 180) {
            yaw -= 360;
        } else if (yaw < -180) {
            yaw += 360;
        }
        return yaw;
    }

    @Override
    public void useOutput(double output, double setpoint) {
        // Calculate the shortest path to the target angle
        double diff = setpoint - drivetrain.getGyro().getYaw().getValueAsDouble();

        if (diff > 180) {
            diff -= 360;
        } else if (diff < -180) {
            diff += 360;
        }

        drivetrain.swerveDrive(
            0.0,
            0.0,
            output * 0.01,
            true,
            new Translation2d(),
            true
        );
    }
    
    public void rotate(double rot) {
        while (Math.abs(drivetrain.getGyro().getYaw().getValueAsDouble() - rot) > 1) {
            double turn = (rot - drivetrain.getGyro().getYaw().getValueAsDouble()) * 0.01;
            drivetrain.swerveDrive(
                0,
                0,
                turn,
                true,
                null,
                true);
        }
    }

    public static SpecDrive getInstance() {
        return specDrive;
    }
}
