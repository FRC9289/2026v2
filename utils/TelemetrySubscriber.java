import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class TelemetrySubscriber {
    public static void main(String[] args) throws InterruptedException {
        NetworkTableInstance inst = NetworkTableInstance.create();
        inst.startClient4("TelemetryClient");
        inst.setServerTeam(XXXX); // Replace with your FRC team number
        inst.startDSClient();

        NetworkTable telemetryTable = inst.getTable("BotTelemetry");

        while (true) {
            double v1 = telemetryTable.getEntry("value1").getDouble(0.0);
            double v2 = telemetryTable.getEntry("value2").getDouble(0.0);
            double v3 = telemetryTable.getEntry("value3").getDouble(0.0);
            double v4 = telemetryTable.getEntry("value4").getDouble(0.0);
            String status = telemetryTable.getEntry("status").getString("N/A");

            System.out.printf("v1=%.4f, v2=%.4f, v3=%.4f, v4=%.4f, status=%s%n",
                              v1, v2, v3, v4, status);
            Thread.sleep(100);
        }
    }
}