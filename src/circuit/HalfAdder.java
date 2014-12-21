package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;


/**
 * This class creates a half adder circuit, 
 * capable of adding 1-bit without any carry-in.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class HalfAdder extends Circuit {
    /** unique id for serialization. */
    private static final long serialVersionUID = 442544548172570492L;

    private GateOr go = new GateOr(170, 100, 2);
    private GateAnd ga = new GateAnd(170, 180, 2);
    
    private GateInput input1 = new GateInput(30, 90, 2, GateState.ON);
    private GatePin pin2 = new GatePin(110, 90, 2);
    private GateInput input2 = new GateInput(30, 110, 2, GateState.ON);
    private GatePin pin5 = new GatePin(90, 110, 2);
    private GatePin pin7 = new GatePin(300, 100, 2);
    private GatePin pin8 = new GatePin(300, 180, 2);

    /**
     * Constructor, set default values for gates  
     * and make connections.
     */
    public HalfAdder() {
        
        go.setVariation(GateOr.XOR);
        
        gates.add(ga);
        gates.add(go);
        gates.add(input1);
        gates.add(pin2);
        gates.add(input2);
        gates.add(pin5);
        gates.add(pin7);
        gates.add(pin8);
        
        //add labels
        input1.setLabel("I1");
        input2.setLabel("I2");
        pin7.setLabel("Sum");
        pin7.setLabelSide(GatePin.SW);
        pin8.setLabel("CarryOut");
        pin8.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(input1, 0, pin2, 0);
        Gate.connect(input2, 0, pin5, 0);
        Gate.connect(pin2, 0, ga, 0, Link.VH);
        Gate.connect(pin5, 0, ga, 1, Link.VH);
        Gate.connect(pin2, -1, go, -1);
        Gate.connect(pin5, -1, go, -1);
        Gate.connect(go, 0, pin7, 0);
        Gate.connect(ga, 0, pin8, 0);
    }
}
