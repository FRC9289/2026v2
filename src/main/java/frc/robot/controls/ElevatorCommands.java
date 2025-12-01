package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.Joystick;

public class ElevatorCommands extends Command {
    private Elevator module;
    private int pos;

    public ElevatorCommands(Joystick wolfByte) {
        this.module = Elevator.get();
        
        if (wolfByte.getRawButton(0)) {
            this.pos = 5000;
        } else if (wolfByte.getRawButton(1)) {
            this.pos = 10000;
        } else if (wolfByte.getRawButton(2)) {
            this.pos = 20000;
        }

        addRequirements(module);
    }

    @Override
    public void initialize() {
        module.PID().reset();
    }

    @Override
    public void execute() {
        if (this.pos == 1) {
            this.pos = 5000;
        } else if (this.pos == 2) {
            this.pos = 10000;
        } else if (this.pos == 3) {
            this.pos = 20000;
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