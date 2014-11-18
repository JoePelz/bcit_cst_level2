/**
 * 
 */
package gui.shapes;


/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateInput extends GatePin {
    
    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GateInput(int x, int y, int thickness, GateState enabled) {
        super(x, y, thickness);
        setState(enabled);
    }
    
    @Override
    public String toString() {
        return "Gate Input: " + super.toString();
    }

    public void perform(int clickX, int clickY) {
        if (getState() == GateState.ON) {
            setState(GateState.OFF);
        } else {
            setState(GateState.ON);
        }
    }
}
