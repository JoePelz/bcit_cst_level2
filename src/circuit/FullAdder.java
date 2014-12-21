package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;


/**
 * This is a Full Adder, performing a 1-bit addition with carry-in.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class FullAdder extends Circuit {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = 442544548172570492L;
    
    private GateOr    fa_gxor   = new GateOr(100, 100, 2);
    private GateOr    fa_gxor2  = new GateOr(230, 110, 2);
    private GateAnd   fa_ga     = new GateAnd(100, 190, 2);
    private GateAnd   fa_ga2    = new GateAnd(230, 160, 2);
    private GateOr    fa_gCarry = new GateOr(320, 180, 2);
                      
    private GateInput fa_input1 = new GateInput(30, 90, 2, GateState.ON);
    private GatePin   fa_pin2   = new GatePin(80, 90, 2);
    private GateInput fa_input2 = new GateInput(30, 110, 2, GateState.ON);
    private GatePin   fa_pin5   = new GatePin(60, 110, 2);
    private GateInput fa_cIn    = new GateInput(30, 70, 2, GateState.ON);
    private GatePin   fa_pin7   = new GatePin(200, 120, 2);
                      
    private GatePin   fa_pin9   = new GatePin(180, 100, 2);
    private GatePin   fa_sum    = new GatePin(410, 110, 2);
    private GatePin   fa_cOut   = new GatePin(410, 180, 2);

    /**
     * Constructor, set default values for gates  
     * and make connections.
     */
    public FullAdder() {

        fa_gxor.setVariation(GateOr.XOR);
        fa_gxor2.setVariation(GateOr.XOR);

        gates.add(fa_ga);
        gates.add(fa_ga2);
        gates.add(fa_gxor);
        gates.add(fa_gxor2);
        gates.add(fa_gCarry);
        gates.add(fa_input1);
        gates.add(fa_pin2);
        gates.add(fa_input2);
        gates.add(fa_pin5);
        gates.add(fa_cIn);
        gates.add(fa_pin7);
        gates.add(fa_pin9);
        gates.add(fa_sum);
        gates.add(fa_cOut);
        
        //add labels
        fa_cIn   .setLabel("C-In");
        fa_input1.setLabel("I1");
        fa_input2.setLabel("I2");
        fa_sum.setLabel("Sum");
        fa_sum.setLabelSide(GatePin.SW);
        fa_cOut.setLabel("C-Out");
        fa_cOut.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(fa_input1, 0, fa_pin2, 0);
        Gate.connect(fa_input2, 0, fa_pin5, 0);
        Gate.connect(fa_pin2, 0,   fa_ga, 0, Link.VH);
        Gate.connect(fa_pin5, 0,   fa_ga, 1, Link.VH);
        Gate.connect(fa_pin2, -1,  fa_gxor, -1);
        Gate.connect(fa_pin5, -1,  fa_gxor, -1);
        Gate.connect(fa_cIn,  -1,  fa_pin7, -1, Link.HV);
        Gate.connect(fa_pin7, -1,  fa_gxor2, 1);
        Gate.connect(fa_pin7, -1,  fa_ga2,  -1, Link.VH);
        Gate.connect(fa_gxor,  0,  fa_pin9, 0);
        Gate.connect(fa_pin9, -1,  fa_gxor2, 0);
        Gate.connect(fa_pin9, -1,  fa_ga2, 1, Link.VH);
        Gate.connect(fa_gxor2,  0, fa_sum, 0);
        Gate.connect(fa_ga2,   -1, fa_gCarry, -1);
        Gate.connect(fa_ga,    -1, fa_gCarry, -1);
        Gate.connect(fa_gCarry, 0, fa_cOut, 0);
    }
}
