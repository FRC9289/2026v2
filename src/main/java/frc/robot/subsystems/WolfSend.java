package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WolfSend extends SubsystemBase {
    private final NetworkTable t;
    private final NetworkTableEntry LFD;
    private final NetworkTableEntry LBD;
    private final NetworkTableEntry RBD;
    private final NetworkTableEntry RFD;
    private final NetworkTableEntry LFR;
    private final NetworkTableEntry LBR;
    private final NetworkTableEntry RBR;
    private final NetworkTableEntry RFR;
    private final NetworkTableEntry statusText;

    private Drivetrain drivetrain = Drivetrain.getInstance();
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    private static final WolfSend wolfSend = new WolfSend();

    public WolfSend() {
        t = NetworkTableInstance.getDefault().getTable("BotTelemetry");

        LFD = t.getEntry("LFD");
        LBD = t.getEntry("LBD");
        RBD = t.getEntry("RBD");
        RFD = t.getEntry("RFD");
        LFR = t.getEntry("LFR");
        LBR = t.getEntry("LBR");
        RBR = t.getEntry("RBR");
        RFR = t.getEntry("RFR");
        statusText = t.getEntry("status");
    }

    @Override
    public void periodic() {
        positions = drivetrain.getModulePositions();
        LFD.setDouble(positions[0].distanceMeters);
        LBD.setDouble(positions[2].distanceMeters);
        RBD.setDouble(positions[3].distanceMeters);
        RFD.setDouble(positions[1].distanceMeters);
        LFR.setDouble(positions[0].angle.getDegrees());
        LBR.setDouble(positions[2].angle.getDegrees());
        RBR.setDouble(positions[3].angle.getDegrees());
        RFR.setDouble(positions[1].angle.getDegrees());
        statusText.setString("System nominal");
    }

    public static WolfSend getInstance() {
        return wolfSend;
    }
}
// Recieve with WolfRecieve.java