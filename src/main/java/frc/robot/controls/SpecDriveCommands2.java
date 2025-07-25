package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpecDrive;

public class SpecDriveCommands2 extends Command {
    private int pos;
    private SpecDrive specDrive;

    public SpecDriveCommands2(int pos) {
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
        double target = (pos * 180);
        specDrive.setSetpoint(target);
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