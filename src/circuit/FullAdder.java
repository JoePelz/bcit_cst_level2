package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class FullAdder extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;
    
    private Font font = new Font("Consolas", Font.PLAIN, 10);

    private GateOr gxor  = new GateOr(100, 100, 2);
    private GateOr gxor2 = new GateOr(230, 110, 2);
    private GateAnd ga    = new GateAnd(100, 190, 2);
    private GateAnd ga2   = new GateAnd(230, 160, 2);
    private GatePin pin11 = new GatePin(300, 160, 2);
    private GatePin pin12 = new GatePin(300, 170, 2);
    private GateOr gCarry = new GateOr(320, 180, 2);
    
    private GateInput input1 = new GateInput(30, 90, 2, GateState.ON);
    private GatePin pin2 = new GatePin(80, 90, 2);
    private GatePin pin3 = new GatePin(80, 180, 2);
    private GateInput input2 = new GateInput(30, 110, 2, GateState.ON);
    private GatePin pin5 = new GatePin(60, 110, 2);
    private GatePin pin6 = new GatePin(60, 200, 2);
    private GateInput cIn= new GateInput(30, 70, 2, GateState.ON);
    private GatePin cInP = new GatePin(200, 70, 2);
    private GatePin pin7 = new GatePin(200, 120, 2);
    private GatePin pin8 = new GatePin(200, 150, 2);

    private GatePin pin9 = new GatePin(180, 100, 2);
    private GatePin pin10 = new GatePin(180, 170, 2);
    private GatePin sum  = new GatePin(410, 110, 2);
    private GatePin cOut = new GatePin(410, 180, 2);
    
    public FullAdder() {

        gxor.setVariation(GateOr.XOR);
        gxor2.setVariation(GateOr.XOR);

        gates.add(ga);
        gates.add(ga2);
        gates.add(gxor);
        gates.add(gxor2);
        gates.add(gCarry);
        gates.add(input1);
        gates.add(pin2);
        gates.add(pin3);
        gates.add(input2);
        gates.add(pin5);
        gates.add(pin6);
        gates.add(cIn);
        gates.add(cInP);
        gates.add(pin7);
        gates.add(pin8);
        gates.add(pin9);
        gates.add(pin10);
        gates.add(pin11);
        gates.add(pin12);
        gates.add(sum);
        gates.add(cOut);
        
        //add labels
        cIn   .setLabel("C-In");
        input1.setLabel("I1");
        input2.setLabel("I2");
        sum.setLabel("Sum");
        sum.setLabelSide(GatePin.SW);
        cOut.setLabel("C-Out");
        cOut.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(input1, 0, pin2, 0);
        Gate.connect(pin2, 0, pin3, 0);
        Gate.connect(input2, 0, pin5, 0);
        Gate.connect(pin5, 0, pin6, 0);
        Gate.connect(pin3, 0, ga, 0);
        Gate.connect(pin6, 0, ga, 1);
        Gate.connect(pin2, -1, gxor, -1);
        Gate.connect(pin5, -1, gxor, -1);
        Gate.connect(cIn,  -1, cInP, -1);
        Gate.connect(cInP,  -1, pin7, -1);
        Gate.connect(pin7, -1, gxor2, 1);
        Gate.connect(pin7, -1, pin8, -1);
        Gate.connect(pin8, -1, ga2,  -1);
        Gate.connect(gxor,  0, pin9, 0);
        Gate.connect(pin9, -1, gxor2, 0);
        Gate.connect(pin9, -1, pin10, -1);
        Gate.connect(pin10, -1, ga2, 1);
        Gate.connect(gxor2,  0, sum, 0);
        Gate.connect(ga2,   -1, pin11, -1);
        Gate.connect(pin11, -1, pin12, -1);
        Gate.connect(pin12, -1, gCarry, -1);
        Gate.connect(ga,    -1, gCarry, -1);
        Gate.connect(gCarry, 0, cOut, 0);

        calcCircuit(20);
    }
    
    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setFont(font);
        
        //draw stuff.
        for (Gate gate : gates) {
            gate.drawFill(g);
            gate.drawStroke(g);
        }
    }
    
}
