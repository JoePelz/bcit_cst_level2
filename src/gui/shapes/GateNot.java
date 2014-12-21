/**
 * 
 */
package gui.shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * This class is a NOT gate to invert a signal.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GateNot extends Gate {
    /** The radius of the circle at the top of the not gate. */
    private static final int RADIUS = 200;
    /** The triangle shape of the not gate. */
    private Polygon triangle = new Polygon();
    /** The circle at the tip of the not gate. */
    private Circle circle;

    /**
     * Constructor, to make a NOT at the given position.  
     * 
     * @param x The x position
     * @param y The y position
     * @param thickness The stroke thickness
     */
    public GateNot(int x, int y, int thickness) {
        super(x, y, thickness * 10, 1, -1);
        
        circle = new Circle((int) (RADIUS * 1.7), 0, RADIUS * 0.2, 0);
        circle.setSubdivisions(32);
        circle.updatePoints();
        addShape(circle.getTransformedPolygon());
        addShape(triangle);
        setScaleX(0.1);
        setScaleY(0.1);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Shape2#updatePoints()
     */
    @Override
    protected void updatePoints() {
        int height = (int) (RADIUS);
        int width = (int) (RADIUS * 1.5);
        
        triangle.reset();
        
        //bottom left
        triangle.addPoint(0, height);

        //top left
        triangle.addPoint(0, -height);
        
        //right center
        triangle.addPoint(width, 0);
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        return new Point((int) (1.9 * RADIUS) + (thickness >> 1), 0);
    }

    @Override
    public Point getInput(int i) {
        return new Point();
    }

    @Override
    public int calcOut() {
        GateState input = GateState.UNKNOWN;
        Link l = inputPorts[0];
        if (l != null) {
            input = l.getGateOut().getState();
        }
        
        if (input == GateState.ON) {
            if (getState() != GateState.OFF) {
                setState(GateState.OFF);
                return 1;
            }
        } else {
            if (getState() != GateState.ON) {
                setState(GateState.ON);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Gate Not: " + super.toString();
    }

    @Override
    public Rectangle getBounds() {
            return new Rectangle(
                    0 - (thickness >> 1), 
                    -RADIUS - (thickness >> 1),
                    (int) (1.9 * RADIUS) + thickness, 
                    (RADIUS << 1) + thickness);
    }
}
