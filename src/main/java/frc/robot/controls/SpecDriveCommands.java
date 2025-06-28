package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpecDrive;

public class SpecDriveCommands extends Command {
    private int pos;
    private SpecDrive specDrive;

    public SpecDriveCommands(int pos) {
        this.specDrive = specDrive.getInstance();
        addRequirements(specDrive);
        this.pos = pos;
    }

    @Override
    public void initialize() {
        specDrive.getController().reset();
    }

    @Override
    public void execute() {
        double targetAngle = 0;

        switch (pos) {
            case 0: targetAngle = 0; break;
            case 1: targetAngle = 45; break;
            case 2: targetAngle = 90; break;
            case 3: targetAngle = 135; break;
            case 4: targetAngle = 180; break;
            case 5: targetAngle = 225; break;
            case 6: targetAngle = 270; break;
            case 7: targetAngle = 315; break;
        }

        specDrive.setSetpoint(targetAngle);
    }

    @Override
    public boolean isFinished() {
        return specDrive.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        specDrive.getController().reset();
    }
}