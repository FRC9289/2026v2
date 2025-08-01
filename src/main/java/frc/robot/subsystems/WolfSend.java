package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
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
    private final NetworkTableEntry POS_X;
    private final NetworkTableEntry POS_Y;
    private final NetworkTableEntry ANGLE;
    private final NetworkTableEntry statusText;

    private Drivetrain drivetrain = Drivetrain.getInstance();
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    SwerveModuleState[] states = new SwerveModuleState[4];
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
        POS_X = t.getEntry("POS_X");
        POS_Y = t.getEntry("POS_Y");
        ANGLE = t.getEntry("ANGLE");
        statusText = t.getEntry("status");
    }

    @Override
    public void periodic() {
        states = drivetrain.getModuleStates();
        positions = drivetrain.getModulePositions();
        LFD.setDouble(states[0].speedMetersPerSecond);
        LBD.setDouble(states[2].speedMetersPerSecond);
        RBD.setDouble(states[3].speedMetersPerSecond);
        RFD.setDouble(states[1].speedMetersPerSecond);
        LFR.setDouble(positions[0].angle.getDegrees());
        LBR.setDouble(positions[2].angle.getDegrees());
        RBR.setDouble(positions[3].angle.getDegrees());
        RFR.setDouble(positions[1].angle.getDegrees());
        POS_X.setDouble(drivetrain.getPose().getX());
        POS_Y.setDouble(drivetrain.getPose().getY());
        ANGLE.setDouble(drivetrain.getPose().getRotation().getDegrees());
        statusText.setString("Uknown Status");
    }

    public static WolfSend getInstance() {
        return wolfSend;
    }
}
// Recieve with WolfRecieve.java