package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TelemetryPublisher extends SubsystemBase {
    private final NetworkTable telemetryTable;
    private final NetworkTableEntry value1;
    private final NetworkTableEntry value2;
    private final NetworkTableEntry value3;
    private final NetworkTableEntry value4;
    private final NetworkTableEntry statusText;

    public TelemetryPublisher() {
        telemetryTable = NetworkTableInstance.getDefault().getTable("BotTelemetry");

        value1 = telemetryTable.getEntry("value1");
        value2 = telemetryTable.getEntry("value2");
        value3 = telemetryTable.getEntry("value3");
        value4 = telemetryTable.getEntry("value4");
        statusText = telemetryTable.getEntry("status");
    }

    @Override
    public void periodic() {
        value1.setDouble(Math.random());           // Example value
        value2.setDouble(Math.random() * 10);
        value3.setDouble(-5.3);
        value4.setDouble(42.0);
        statusText.setString("System nominal");    // Example status
    }
}