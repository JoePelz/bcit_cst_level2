/**
 * 
 */
package gui.shapes;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public abstract class Gate extends Shape{
    protected ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    protected Gate(int x, int y, int thickness) {
        super(x, y, thickness);
    }
    
    protected void addShape(Shape s) {
        shapes.add(s);
    }
    
    @Override
    public void drawStroke(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xPoints, yPoints, xPoints.length);
        //drawStroke all sub-shapes
        for (Shape s : shapes) {
            s.drawStroke(g);
        }
    }

    @Override
    public void drawFill(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xPoints, yPoints, xPoints.length / 2);
        //drawFill all sub-shapes
        for (Shape s : shapes) {
            s.drawFill(g);
        }
    }
}
