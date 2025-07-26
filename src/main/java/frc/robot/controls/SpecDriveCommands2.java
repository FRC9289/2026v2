package frc.robot.controls;

import frc.robot.subsystems.SpecDrive;

public class SpecDriveCommands2 extends SpecDriveCommands {
    private int pos;
    private SpecDrive specDrive;

    public SpecDriveCommands2(int pos) {
        super(pos);
    }

    @Override
    public void execute() {
        specDrive.Pos(pos * 180);
    }
}