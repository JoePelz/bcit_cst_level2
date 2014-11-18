package circuit;

import gui.shapes.Arc;
import gui.shapes.Circle;
import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateOr;
import gui.shapes.GateNot;
import gui.shapes.GateInput;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Shape;

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
    private Shape circle1;
    private Shape circle2;
    private Shape circle3;
    private Shape circle4;
    
    public CamTest() {
        Dimension size = new Dimension(1400, 500);
        setPreferredSize(size);
        setMaximumSize(size);
        
        
        circle1 = new Circle(0, 0, 20, 3);
        circle2 = new Arc(100, 0, 20, Math.PI / 2, Math.PI * 1.5, 3);
        circle3 = new GateNot(100, 100, 3);
        circle4 = new GateInput(-100, 100, 3, GateState.ON);

//        ((GateAnd)circle3).setVariation(GateAnd.NAND);
        
        gates.add((Gate)circle3);
        gates.add((Gate)circle4);
    }
    
    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        
        circle1.drawStroke(g);
        circle2.drawStroke(g);
        circle3.drawStroke(g);
        circle4.drawStroke(g);
    }
}
