/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.GateXor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HalfAdder extends Circuit {
    private static final long serialVersionUID = 442544548172570492L;
    
    private Font font = new Font("Consolas", Font.PLAIN, 15);

    private GateAnd ga = new GateAnd(10, 10, 2);
    private GateXor go = new GateXor(10, 10, 2);
//    private GateOr go = new GateOr(10, 10, 2);
    
    private GateInput input1 = new GateInput(10, 10, 2, GateState.ON);
    private GatePin pin2 = new GatePin(10, 10, 2);
    private GatePin pin3 = new GatePin(10, 10, 2);
    private GateInput input2 = new GateInput(10, 10, 2, GateState.ON);
    private GatePin pin5 = new GatePin(10, 10, 2);
    private GatePin pin6 = new GatePin(10, 10, 2);
    private GatePin pin7 = new GatePin(10, 10, 2);
    private GatePin pin8 = new GatePin(10, 10, 2);
    
    public HalfAdder() {
        Dimension size = new Dimension(800, 500);
        setPreferredSize(size);
        setMaximumSize(size);

        gates.add(ga);
        gates.add(go);
        gates.add(input1);
        gates.add(pin2);
        gates.add(pin3);
        gates.add(input2);
        gates.add(pin5);
        gates.add(pin6);
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
        Gate.connect(pin2, 0, pin3, 0);
        Gate.connect(input2, 0, pin5, 0);
        Gate.connect(pin5, 0, pin6, 0);
        Gate.connect(pin3, 0, ga, 0);
        Gate.connect(pin6, 0, ga, 1);
        Gate.connect(pin2, -1, go, -1);
        Gate.connect(pin5, -1, go, -1);
        Gate.connect(go, 0, pin7, 0);
        Gate.connect(ga, 0, pin8, 0);

        calcCircuit(20);
    }
    
    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setFont(font.deriveFont((float)pd(0.03)));
        
        ga.setPosition(w(0.5), h(0.45));
        ga.setScaleX(w(0.2));
        ga.setScaleY(h(0.3));
        go.setPosition(w(0.5), h(0.15));
        go.setScaleX(w(0.2));
        go.setScaleY(h(0.3));
        
        input1.setPosition(w(0.05), h(0.1));
        pin2.setPosition(w(0.35), h(0.1));
        pin3.setPosition(w(0.35), h(0.4));
        
        input2.setPosition(w(0.05), h(0.2));
        pin5.setPosition(w(0.25), h(0.2));
        pin6.setPosition(w(0.25), h(0.5));
        
        pin7.setPosition(w(0.95), h(0.15));
        pin8.setPosition(w(0.95), h(0.45));
        
        
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
        */
    }
    
}
