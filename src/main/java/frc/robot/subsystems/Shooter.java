package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*
 * As per analysis of the shooter subsystem by Wolfram, the V required to operate 
 * the shooter (rotate the rubber materials) at a specific anglar acceleration can 
 * be modelled as the following:
 *      V = ((R*J)/(K_t))*accel + K_e * veloc
 *      for V = kS + kV*v+kA*a
 * 
 * kS must be experimented
 * kV = K_e
 * kA = ((R*J)/(K_t))
 * Where accel and veloc are the desired acceleration and velocity of the system
 */

public class Shooter extends SubsystemBase {


    private final int MOTOR_ID = -1;
    private final TalonFX shooter_motor;
    private final TalonFXConfigurator shooter_motor_config;

    // physics constants
    private static final double ROT_INERTIA = 0.00136;     //J
    private static final double RESISTANCE = 0.047;        //R
    private static final double TORQUE_CONSTANT = 0.0182;  //K_t
    private static final double BACKEMF_CONSTANT = 0.018;  //K_e


    // ff constants
    private final double kS = -1; // This is the amount of Voltage required to overcome static friction. we need to calculate from quasistatic ramp in sysid
    private final double kV = BACKEMF_CONSTANT; 
    private final double kA = (RESISTANCE * ROT_INERTIA) / TORQUE_CONSTANT;                              


    // pid constants
    private final double kP = 0.1; // finetuned
    /* integral PIDs r annoying so we js use PD control */
    private final double kD = 0.1; // finetuned


    private final SimpleMotorFeedforward ff_control = new SimpleMotorFeedforward(kS, kV, kA);
    private final PIDController fb_control = new PIDController(kP, 0, kD); // Only using PD

    // Limits
    private static final double MAX_VOLTAGE = 12.0;

    // current state of shooter
    private double omegaCmd = 0.0;
    private double omegaTarget = 0.0;

    public Shooter() {
        shooter_motor = new TalonFX(MOTOR_ID);
        shooter_motor_config = shooter_motor.getConfigurator();
    }
    public void setTargetAngularVelocity(double targetRadPerSec) {
        omegaTarget = targetRadPerSec;
    }

    public void stop() {
        omegaTarget = 0.0;
        omegaCmd = 0.0;
        shooter_motor.setVoltage(0.0);
    }

    public boolean atSpeed() {
        return Math.abs(omegaCmd - omegaTarget) < 2.0;
    }

    public double getCurrentVelocity() {
        double rotationsPerSecond = shooter_motor.getRotorVelocity().getValueAsDouble(); // RPS
        double radPerSecond = rotationsPerSecond * 2 * Math.PI; // rad/s
        return radPerSecond;
    }

    @Override
    public void periodic() {
        double omegaMeasured = getCurrentVelocity();

        double ff_volts = ff_control.calculateWithVelocities(omegaMeasured, omegaTarget);
        ff_volts = MathUtil.clamp(ff_volts, -MAX_VOLTAGE, MAX_VOLTAGE);

        double fb_volts = fb_control.calculate(omegaMeasured, omegaTarget);
        double total_volts = ff_volts+fb_volts;


        shooter_motor.setVoltage(total_volts);
    }
}

// bismuthe-83