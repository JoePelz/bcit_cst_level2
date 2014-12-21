package circuit;

import gui.shapes.Gate;
import gui.shapes.GateInput;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;


/**
 * This class is an SR latch, a simple memory circuit.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class SRLatch extends Circuit {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = 442544548172570492L;
    
    private GateOr norTop    = new GateOr(150, 100, 2);
    private GateOr norBottom = new GateOr(150, 200, 2);

    private GateInput inputS = new GateInput(30,  90, 2, GateState.OFF);
    private GateInput inputR = new GateInput(30, 210, 2, GateState.ON);
    private GatePin pin1 = new GatePin(230, 100, 2);
    private GatePin pin7 = new GatePin(230, 130, 2);
    private GatePin pin8 = new GatePin(230, 170, 2);
    private GatePin pin2 = new GatePin(230, 200, 2);
    private GatePin pin5 = new GatePin(100, 110, 2);
    private GatePin pin3 = new GatePin(100, 130, 2);
    private GatePin pin4 = new GatePin(100, 170, 2);
    private GatePin pin6 = new GatePin(100, 190, 2);
    private GatePin pinNotQ = new GatePin(310, 100, 2);
    private GatePin pinQ = new GatePin(310, 200, 2);

    /**
     * Constructor, set default values for gates  
     * and make connections.
     */
    public SRLatch() {
        
        norTop.setVariation(GateOr.NOR);
        norBottom.setVariation(GateOr.NOR);

        gates.add(norBottom);
        gates.add(norTop);
        gates.add(inputS);
        gates.add(inputR);
        gates.add(pin1);
        gates.add(pin2);
        gates.add(pin3);
        gates.add(pin4);
        gates.add(pin5);
        gates.add(pin6);
        gates.add(pin7);
        gates.add(pin8);
        gates.add(pinNotQ);
        gates.add(pinQ);
        
        //add labels
        inputS.setLabel("S");
        inputR.setLabel("R");
//        pin1.setLabel("1");
//        pin2.setLabel("2");
//        pin3.setLabel("3");
//        pin4.setLabel("4");
//        pin5.setLabel("5");
//        pin6.setLabel("6");
//        pin7.setLabel("7");
//        pin8.setLabel("8");
        pinNotQ.setLabel("Q Not");
        pinNotQ.setLabelSide(GatePin.NW);
        pinQ.setLabel("Q");
        pinQ.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(inputS, -1, norTop, 0);
        Gate.connect(pin5, -1, norTop, 1);

        Gate.connect(pin6, -1, norBottom, 0);
        Gate.connect(inputR, -1, norBottom, 1);

        Gate.connect(norTop, -1, pin1, 0);
        Gate.connect(pin1, -1, pinNotQ, 0);
        Gate.connect(norBottom, -1, pin2, 0);
        Gate.connect(pin2, -1, pinQ, 0);

        Gate.connect(pin1, -1, pin7, 0);
        Gate.connect(pin2, -1, pin8, 0);
        Gate.connect(pin8, -1, pin3, 0, Link.STRAIGHT);
        Gate.connect(pin7, -1, pin4, 0, Link.STRAIGHT);
        Gate.connect(pin4, -1, pin6, 0);
        Gate.connect(pin3, -1, pin5, 0);
    }
}
