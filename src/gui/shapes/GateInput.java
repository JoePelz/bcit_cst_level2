package gui.shapes;


/**
 * This class is an input wire to a circuit. 
 * Clicking it switches on and off.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GateInput extends GatePin {
    
    /**
     * Constructor, makes a new GateInput at the given position 
     * and state (ON/OFF)
     * 
     * @param x The x position for the gate
     * @param y The y position for the gate
     * @param thickness The stroke thickness for the gate
     * @param enabled The initial state (ON, OFF)
     */
    public GateInput(int x, int y, int thickness, GateState enabled) {
        super(x, y, thickness);
        setState(enabled);
    }
    
    @Override
    public String toString() {
        return "Gate Input: " + super.toString();
    }

    /**
     * Toggle the input on or off.
     * 
     * @param clickX The mouse X position on click.
     * @param clickY The mouse Y position on click.
     */
    public void perform( int clickX, int clickY) {
        if (getState() == GateState.ON) {
            setState(GateState.OFF);
        } else {
            setState(GateState.ON);
        }
    }
}
