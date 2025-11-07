package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;
import frc.robot.utils.Constants.ModuleIDs;
import frc.robot.utils.WolfSparkMax;

public class Elevator extends SubsystemBase {
    private static final Elevator elevator = new Elevator();
    private PIDController pid;
    private WolfSparkMax m1;
    private WolfSparkMax m2;
    private double pos;

    public Elevator() {
        this.pid = new PIDController(1, 0, 0);
        this.pid.setTolerance(500, Double.POSITIVE_INFINITY);
        m1 = new WolfSparkMax(ModuleIDs.e[0], false, true, 45, false);
        m2 = new WolfSparkMax(ModuleIDs.e[1], false, true, 45, true);
    }

    public void move(double speed) {
        m1.set(speed);
        m2.set(speed);
    }

    public double getPosition() {
        return Math.abs(m1.getEncoder().getPosition());
    }

    @Override
    public void periodic() {
        move(MathUtil.clamp(pid.calculate(getPosition(), pos), -1, 1));
    }

    public static Elevator get() {
        return elevator;
    }

    public PIDController PID() {
        return pid;
    }

    public void Pos(double pos) {
        this.pos = pos;
    }
}
// Wolfram121