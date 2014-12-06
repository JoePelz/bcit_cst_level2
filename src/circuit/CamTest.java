package circuit;


import gui.shapes.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CamTest extends Circuit {
    private GateInput circle1;
    private GateInput circle2;
    private GateEdgeTrigger circle3;
    private GateAnd circle4;
    
    public CamTest() {
//        Dimension size = new Dimension(800, 500);
//        setPreferredSize(size);
//        setMaximumSize(size);
        
        
        circle1 = new GateInput(0,   0, 3, GateState.OFF);
        circle2 = new GateInput(0, 50, 3, GateState.OFF);
        circle3 = new GateEdgeTrigger(20, 50, 3);
        circle4 = new GateAnd(200, 30, 3);
        circle4.setName("CamTest");

        Gate.connect(circle1, -1, circle4, -1);
        Gate.connect(circle2, -1, circle3, -1);
        Gate.connect(circle3, -1, circle4, -1);
        
        gates.add((Gate)circle1);
        gates.add((Gate)circle2);
        gates.add((Gate)circle3);
        gates.add((Gate)circle4);
    }
}
