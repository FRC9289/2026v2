package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpecDrive;

public class SpecDriveCommands extends Command {
    private SpecDrive module;
    private int pos;

    public SpecDriveCommands(int pos) {
        this.module = SpecDrive.get();
        this.pos = pos;
        addRequirements(module);
    }

    @Override
    public void initialize() {
        module.PID().reset();
    }

    @Override
    public void execute() {
        if (this.pos > 180) {
            this.pos -= 360;
        }
        module.Pos(this.pos);
    }

    @Override
    public boolean isFinished() {
        return module.PID().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        module.PID().reset();
    }
}