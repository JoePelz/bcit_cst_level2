package gui.shapes;

/**
 * This class represents the state, or signal in a wire or gate.
 * A given gate can be ON, OFF, or be UNKNOWN.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public enum GateState {
    /** The gate is ON, HIGH, 1, ENABLED, or TRIGGERED. */
    ON,
    /** The gate is OFF, LOW, 0, DISABLED, or INACTIVE. */
    OFF,
    /** The gate's state is not known at this time. */
    UNKNOWN
}
