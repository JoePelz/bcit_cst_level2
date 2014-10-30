/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GatePin;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HalfAdder extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;

    private GateAnd ga = new GateAnd(10, 10, 2);
    
    private GatePin pin1 = new GatePin(10, 10, 2);
    private GatePin pin2 = new GatePin(10, 10, 2);
    private GatePin pin3 = new GatePin(10, 10, 2);
    private GatePin pin4 = new GatePin(10, 10, 2);
    private GatePin pin5 = new GatePin(10, 10, 2);
    private GatePin pin6 = new GatePin(10, 10, 2);
    private GatePin pin7 = new GatePin(10, 10, 2);
    private GatePin pin8 = new GatePin(10, 10, 2);
    
    private ArrayList<Gate> gates = new ArrayList<Gate>();
    
    public HalfAdder() {
        Dimension size = new Dimension(800, 800);
        setPreferredSize(size);
        setMaximumSize(size);

        gates.add(ga);
        gates.add(pin1);
        gates.add(pin2);
        gates.add(pin3);
        gates.add(pin4);
        gates.add(pin5);
        gates.add(pin6);
        gates.add(pin7);
        gates.add(pin8);
    }
    
    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        
        ga.setPosition(w(0.5), h(0.45));
        ga.setScaleX(pd(0.2));
        ga.setScaleY(pd(0.2));
        
        pin1.setPosition(w(0.05), h(0.1));
        pin2.setPosition(w(0.35), h(0.1));
        pin3.setPosition(w(0.35), h(0.4));
        
        pin4.setPosition(w(0.05), h(0.2));
        pin5.setPosition(w(0.25), h(0.2));
        pin6.setPosition(w(0.25), h(0.5));
        
        pin7.setPosition(w(0.95), h(0.15));
        pin8.setPosition(w(0.95), h(0.45));
        
        
        //make connections
        Gate.connect(pin1, 0, pin2, 0);
        Gate.connect(pin2, 0, pin3, 0);
        Gate.connect(pin4, 0, pin5, 0);
        Gate.connect(pin5, 0, pin6, 0);
        Gate.connect(pin3, 0, ga, 0);
        Gate.connect(pin6, 0, ga, 1);
        Gate.connect(ga, 0, pin7, 0);
        Gate.connect(ga, 0, pin8, 0);
        
        
        //draw stuff.
        int thickness = p(0.01);
        for (Gate gate : gates) {
            gate.setThickness(thickness);
            gate.drawFill(g);
            gate.drawStroke(g);
        }
    }
    
}
