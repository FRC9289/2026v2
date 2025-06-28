package frc.robot.subsystems;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class SpecDrive extends PIDSubsystem {
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private static final SpecDrive specDrive = new SpecDrive();

    public SpecDrive() {
        super(new PIDController(0.1, 0, 0));
        getController().setTolerance(1.0);

    }

    @Override
    protected double getMeasurement() {
        return drivetrain.getGyro().getYaw().getValueAsDouble();
    }

    @Override
    public void useOutput(double output, double setpoint) {
        double turn = output * 0.01;
        drivetrain.swerveDrive(
            0,
            0,
            turn,
            true,
            null,
            true);
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
