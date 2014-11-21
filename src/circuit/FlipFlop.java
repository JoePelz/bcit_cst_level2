package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateEdgeTrigger;
import gui.shapes.GateInput;
import gui.shapes.GateNot;
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
public class FlipFlop extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;
    
    private Font font = new Font("Consolas", Font.PLAIN, 15);

    private GateOr norTop     = new GateOr(190, 100, 2);
    private GateOr norBottom  = new GateOr(190, 200, 2);
    private GateAnd andTop    = new GateAnd(100, 90, 2);
    private GateAnd andBottom = new GateAnd(100, 210, 2);
    private GateNot not = new GateNot(10, 220, 2);
    private GateEdgeTrigger get = new GateEdgeTrigger(-90, 140, 2);

    private GateInput inputD = new GateInput(-100, 80, 2, GateState.OFF);
    private GatePin pin1 = new GatePin(280, 100, 2);
    private GatePin pin2 = new GatePin(280, 200, 2);
    private GatePin pin3 = new GatePin(170, 130, 2);
    private GatePin pin4 = new GatePin(170, 170, 2);
    private GatePin pin5 = new GatePin(170, 110, 2);
    private GatePin pin6 = new GatePin(170, 190, 2);
    private GatePin pin7 = new GatePin(280, 130, 2);
    private GatePin pin8 = new GatePin(280, 170, 2);
    private GatePin pin9 = new GatePin(-10, 80, 2);
    private GatePin pin10 = new GatePin(-10, 220, 2);
    
    private GatePin pinNotQ = new GatePin(350, 100, 2);
    private GatePin pinQ = new GatePin(350, 200, 2);

    private GateInput inputClock = new GateInput(-110, 140, 2, GateState.OFF);
    private GatePin pinClock1 = new GatePin(80, 150, 2);
    private GatePin pinClock2 = new GatePin(80, 100, 2);
    private GatePin pinClock3 = new GatePin(80, 200, 2);
    
    public FlipFlop() {

        norTop.setVariation(GateOr.NOR);
        norBottom.setVariation(GateOr.NOR);

        andTop.setName("andTop");
        andBottom.setName("andBottom");

        gates.add(norBottom);
        gates.add(norTop);
        gates.add(andTop);
        gates.add(andBottom);
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
        gates.add(pin10);
        gates.add(pinNotQ);
        gates.add(pinQ);
        gates.add(inputClock);
        gates.add(pinClock1);
        gates.add(pinClock2);
        gates.add(pinClock3);
        gates.add(get);
        
        //add labels
        inputD.setLabel("D");
        inputClock.setLabel("Clock");
        pinNotQ.setLabel("Q Not");
        pinNotQ.setLabelSide(GatePin.NW);
        pinQ.setLabel("Q");
        pinQ.setLabelSide(GatePin.NW);
        
        //make connections
        Gate.connect(inputD, -1, pin9, 0);
        Gate.connect(pin9, -1, andTop, 0);
        Gate.connect(andTop, -1, norTop, 0);
        Gate.connect(pin5, -1, norTop, 1);
        Gate.connect(pin9, -1, pin10, 0);
        Gate.connect(pin10, -1, not, 0);

        Gate.connect(pin6, -1, norBottom, 0);
        Gate.connect(not, -1, andBottom, 1);
        Gate.connect(andBottom, -1, norBottom, 1);

        Gate.connect(norTop, -1, pin1, 0);
        Gate.connect(pin1, -1, pinNotQ, 0);
        Gate.connect(norBottom, -1, pin2, 0);
        Gate.connect(pin2, -1, pinQ, 0);

        Gate.connect(pin1, -1, pin7, 0);
        Gate.connect(pin2, -1, pin8, 0);
        Gate.connect(pin8, -1, pin3, 0);
        Gate.connect(pin7, -1, pin4, 0);
        Gate.connect(pin4, -1, pin6, 0);
        Gate.connect(pin3, -1, pin5, 0);

        Gate.connect(inputClock, -1, get, -1);
        Gate.connect(get, -1, pinClock1, -1);
        Gate.connect(pinClock1, -1, pinClock2, -1);
        Gate.connect(pinClock1, -1, pinClock3, -1);
        Gate.connect(pinClock2, -1, andTop, -1);
        Gate.connect(pinClock3, -1, andBottom, -1);
        
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