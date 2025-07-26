package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpecDrive extends SubsystemBase {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private static final SpecDrive specDrive = new SpecDrive();
    private PIDController pid;
    private double yaw;
    private double pos;

    public SpecDrive() {
        this.pid = new PIDController(0.1, 0, 0);
        this.pid.enableContinuousInput(-180, 180);
    }


    public double getMeasurement() {
        // Get the gyro yaw and ensure it is within -180 to 180
        yaw = drivetrain.getGyro().getYaw().getValueAsDouble();
        if (yaw > 180) {
            yaw -= 360;
        } else if (yaw < -180) {
            yaw += 360;
        }
        return yaw;
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

    @Override
    public void periodic() {
        drivetrain.swerveDrive(
            0.0,
            0.0,
            0.01 * pid.calculate(getMeasurement(), this.pos),
            true,
            new Translation2d(),
            true
        );
    }

    public void Pos(double pos) {
        this.pos = pos;
    }

    public static SpecDrive getInstance() {
        return specDrive;
    }

    public PIDController PID() {
        return pid;
    }
}