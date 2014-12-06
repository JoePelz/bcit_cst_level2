/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateInput;
import gui.shapes.GateNot;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class UnclockedDLatch extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;
    
    private GateOr norTop    = new GateOr(190, 100, 2);
    private GateOr norBottom = new GateOr(190, 200, 2);
    private GateNot not = new GateNot(90, 210, 2);

    private GateInput inputD = new GateInput(30, 90, 2, GateState.OFF);
    private GatePin pin1 = new GatePin(280, 100, 2);
    private GatePin pin2 = new GatePin(280, 200, 2);
    private GatePin pin3 = new GatePin(170, 130, 2);
    private GatePin pin4 = new GatePin(170, 170, 2);
    private GatePin pin5 = new GatePin(170, 110, 2);
    private GatePin pin6 = new GatePin(170, 190, 2);
    private GatePin pin7 = new GatePin(280, 130, 2);
    private GatePin pin8 = new GatePin(280, 170, 2);
    private GatePin pin9 = new GatePin(50, 90, 2);
    private GatePin pinNotQ = new GatePin(350, 100, 2);
    private GatePin pinQ = new GatePin(350, 200, 2);
    
    public UnclockedDLatch() {

        norTop.setVariation(GateOr.NOR);
        norBottom.setVariation(GateOr.NOR);

        gates.add(norBottom);
        gates.add(norTop);
        gates.add(not);
        gates.add(inputD);
        gates.add(pin1);
        gates.add(pin2);
        gates.add(pin3);
        gates.add(pin4);
        gates.add(pin5);
        gates.add(pin6);
        gates.add(pin7);
        gates.add(pin8);
        gates.add(pin9);
        gates.add(pinNotQ);
        gates.add(pinQ);
        
        //add labels
        inputD.setLabel("D");
        pinNotQ.setLabel("Q Not");
        pinNotQ.setLabelSide(GatePin.NW);
        pinQ.setLabel("Q");
        pinQ.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(inputD, -1, pin9, 0);
        Gate.connect(pin9, -1, norTop, 0);
        Gate.connect(pin5, -1, norTop, 1);
        Gate.connect(pin9, -1, not, 0, Link.VH);

        Gate.connect(pin6, -1, norBottom, 0);
        Gate.connect(not, -1, norBottom, 1);

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
        
        calcCircuit(20);
    }
}