package frc.robot.utils;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Preferences;

public class WolfSparkMax extends SparkMax {
    protected double target;

    public WolfSparkMax(int ID, boolean brush, boolean brake, int lim, boolean inverted) {
        super(ID, brush ? MotorType.kBrushed : MotorType.kBrushless);
        System.out.println("" + ID);

        SparkMaxConfig config = new SparkMaxConfig();
        config
            .inverted(inverted)
            .idleMode(brake ? IdleMode.kBrake : IdleMode.kCoast)
            .smartCurrentLimit(lim);

        super.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        String key = "Spark " + this.getDeviceId() + " Flashes";
        Preferences.setDouble(key, Preferences.getDouble(key, 0) + 1);
    }

    public WolfSparkMax(int ID, boolean brush, boolean brake, int lim, boolean inverted, double kP, double kI, double kD, double minOutput, double maxOutput) {
        super(ID, brush ? MotorType.kBrushed : MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config
            .inverted(inverted)
            .idleMode(brake ? IdleMode.kBrake : IdleMode.kCoast);
        config.closedLoop.pid(kP, kI, kD);

        super.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        String key = "Spark " + this.getDeviceId() + " Flashes";
        Preferences.setDouble(key, Preferences.getDouble(key, 0) + 1);
    }
}