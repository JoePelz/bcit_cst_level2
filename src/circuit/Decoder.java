/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GateNot;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Decoder extends Circuit {
    private GateAnd and00 = new GateAnd(200,  50, 2);
    private GateAnd and01 = new GateAnd(200, 100, 2);
    private GateAnd and10 = new GateAnd(200, 150, 2);
    private GateAnd and11 = new GateAnd(200, 200, 2);

    private GateNot not00a = new GateNot(140,  40, 2);
    private GateNot not00b = new GateNot( 90,  60, 2);
    private GateNot not01  = new GateNot(140,  90, 2);
    private GateNot not10  = new GateNot(140,  160, 2);

    private GatePin output00 = new GatePin(300,  50, 2);
    private GatePin output01 = new GatePin(300, 100, 2);
    private GatePin output10 = new GatePin(300, 150, 2);
    private GatePin output11 = new GatePin(300, 200, 2);

    private GatePin nodeA0 = new GatePin(30,  40, 2);
    private GatePin nodeB0 = new GatePin(50,  60, 2);

    private GateInput inputA = new GateInput(-50, 40, 2, GateState.ON);
    private GateInput inputB = new GateInput(-50, 60, 2, GateState.OFF);
    
    public Decoder() {
        gates.add(and00);
        gates.add(and01);
        gates.add(and10);
        gates.add(and11);
        gates.add(not00a);
        gates.add(not00b);
        gates.add(not01);
        gates.add(not10);

        gates.add(output00);
        output00.setLabel("00");
        gates.add(output01);
        output01.setLabel("01");
        gates.add(output10);
        output10.setLabel("10");
        gates.add(output11);
        output11.setLabel("11");
        gates.add(nodeA0);
        gates.add(nodeB0);

        gates.add(inputA);
        gates.add(inputB);
        inputA.setLabel("Input A");
        inputB.setLabel("Input B");

        //connect outputs
        Gate.connect(and00, 0, output00, 0);
        Gate.connect(and01, 0, output01, 0);
        Gate.connect(and10, 0, output10, 0);
        Gate.connect(and11, 0, output11, 0);
        
        //connect and gate inputs
        Gate.connect(not00a, 0, and00, 0);
        Gate.connect(nodeA0, 0, not00a, 0);
        Gate.connect(nodeB0, 0, not00b, 0);
        Gate.connect(not00b, 0, and00, 1);
        
        Gate.connect(not01, 0, and01, 0);
        Gate.connect(nodeA0, 0, not01, 0, Link.VH);
        Gate.connect(nodeB0, 0, and01, 1, Link.VH);

        Gate.connect(nodeA0, 0, and10, 0, Link.VH);
        Gate.connect(nodeB0, 0, not10, 0, Link.VH);
        Gate.connect(not10, 0, and10, 1);
        
        Gate.connect(nodeA0, 0, and11, 0, Link.VH);
        Gate.connect(nodeB0, 0, and11, 1, Link.VH);
        
        //spread out inputs
        Gate.connect(inputA, 0, nodeA0, 0);
        Gate.connect(inputB, 0, nodeB0, 0);

//        Gate.connect(nodeA0, -1, nodeA1, -1);
//        Gate.connect(nodeB0, -1, nodeB1, -1);
//        Gate.connect(nodeA1, -1, nodeA2, -1);
//        Gate.connect(nodeB1, -1, nodeB2, -1);
//        Gate.connect(nodeA2, -1, nodeA3, -1);
//        Gate.connect(nodeB2, -1, nodeB3, -1);
    }
}
