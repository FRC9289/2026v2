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
        if (pos > 180) {
            pos -= 360;
        }
        specDrive.setSetpoint(pos);
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