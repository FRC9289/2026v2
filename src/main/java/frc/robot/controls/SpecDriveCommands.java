package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpecDrive;

public class SpecDriveCommands extends Command {
    private int pos;
    private SpecDrive specDrive;

    public SpecDriveCommands(int pos) {
        this.specDrive = SpecDrive.getInstance();
        addRequirements(specDrive);
        this.pos = pos;
    }

    @Override
    public void initialize() {
        specDrive.PID().reset();
    }

    @Override
    public void execute() {
        if (this.pos > 180) {
            this.pos -= 360;
        }
        specDrive.Pos(this.pos);
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