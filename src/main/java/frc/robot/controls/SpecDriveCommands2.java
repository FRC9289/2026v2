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
        specDrive.PID().reset();
    }

    @Override
    public void execute() {
        specDrive.Pos(pos * 180);
    }

    @Override
    public boolean isFinished() {
        return this.pos == specDrive.getMeasurement();
    }

    @Override
    public void end(boolean interrupted) {
        specDrive.PID().reset();
    }
}