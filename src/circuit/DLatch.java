/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateInput;
import gui.shapes.GateNor;
import gui.shapes.GateNot;
import gui.shapes.GatePin;
import gui.shapes.GateState;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class DLatch extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;
    
    private Font font = new Font("Consolas", Font.PLAIN, 15);

    private GateNor norTop    = new GateNor(10, 10, 2);
    private GateNor norBottom = new GateNor(10, 10, 2);
    private GateNot not = new GateNot(10, 10, 2);

    private GateInput inputD = new GateInput(10, 10, 2, GateState.OFF);
    private GatePin pin1 = new GatePin(10, 10, 2);
    private GatePin pin2 = new GatePin(10, 10, 2);
    private GatePin pin3 = new GatePin(10, 10, 2);
    private GatePin pin4 = new GatePin(10, 10, 2);
    private GatePin pin5 = new GatePin(10, 10, 2);
    private GatePin pin6 = new GatePin(10, 10, 2);
    private GatePin pin7 = new GatePin(10, 10, 2);
    private GatePin pin8 = new GatePin(10, 10, 2);
    private GatePin pin9 = new GatePin(10, 10, 2);
    private GatePin pin10 = new GatePin(10, 10, 2);
    private GatePin pinNotQ = new GatePin(10, 10, 2);
    private GatePin pinQ = new GatePin(10, 10, 2);
    
    public DLatch() {
        Dimension size = new Dimension(800, 500);
        setPreferredSize(size);
        setMaximumSize(size);

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
        gates.add(pin10);
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
        Gate.connect(pin9, -1, pin10, 0);
        Gate.connect(pin10, -1, not, 0);

        Gate.connect(pin6, -1, norBottom, 0);
        Gate.connect(not, -1, norBottom, 1);

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

        calcCircuit(20);
    }
    
    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setFont(font.deriveFont((float)pd(0.03)));
        
        norTop.setPosition(w(0.52), h(0.2));
        norTop.setScaleX(w(0.17));
        norTop.setScaleY(h(0.3));
        norBottom.setPosition(w(0.52), h(0.8));
        norBottom.setScaleX(w(0.17));
        norBottom.setScaleY(h(0.3));
        not.setPosition(w(0.25), h(0.87));
        not.setScaleX(w(0.17));
        not.setScaleY(h(0.3));

        inputD.setPosition(w(0.05), h(0.13));
        pin9.setPosition(w(0.15), h(0.13));
        pin10.setPosition(w(0.15), h(0.87));
        
        pin1.setPosition(w(0.83), h(0.2));
        pin2.setPosition(w(0.83), h(0.8));
        pin7.setPosition(w(0.83), h(0.4));
        pin8.setPosition(w(0.83), h(0.6));
        pin3.setPosition(w(0.46), h(0.4));
        pin4.setPosition(w(0.46), h(0.6));
        pin5.setPosition(w(0.46), h(0.27));
        pin6.setPosition(w(0.46), h(0.73));
        
        pinNotQ.setPosition(w(0.95), h(0.2));
        pinQ.setPosition(w(0.95), h(0.8));
        
        
        //draw stuff.
        int thickness = p(0.008);
        for (Gate gate : gates) {
            gate.setThickness(thickness);
            gate.drawFill(g);
            gate.drawStroke(g);
        }
        
        // Debug text
        /*
        g.setColor(Color.BLUE);
        for(Gate gate : gates) {
            Point p = gate.getPosition();
            if (gate.getState() == GateState.ON)
                g.drawString("1", p.x, p.y - 15);
            else if (gate.getState() == GateState.OFF)
                g.drawString("0", p.x, p.y - 15);
            else if (gate.getState() == GateState.NULL)
                g.drawString("n/a", p.x, p.y - 15);
        }
        // */
    }
    
}