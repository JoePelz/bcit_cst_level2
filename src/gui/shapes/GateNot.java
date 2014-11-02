/**
 * 
 */
package gui.shapes;

import java.awt.Point;
import java.awt.Polygon;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateNot extends Gate {
    private static final double radius = 0.1;
    Polygon triangle = new Polygon();
    Circle circle;

    public GateNot(int x, int y, int thickness) {
        super(x, y, thickness, 1, -1);
        
        circle = new Circle(x, y, radius, thickness);
        circle.setSubdivisions(32);
        addShape(circle.getPolygon());
        addShape(triangle);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Shape2#updatePoints()
     */
    @Override
    protected void updatePoints() {
        int height = (int) (0.3 * scaleY);
        int width = (int) (0.6 * scaleX);
        int px;
        int py;
        
        triangle.reset();
        
        //bottom left
        px = x;
        py = y + height;
        triangle.addPoint(px, py);

        //top left
        px = x;
        py = y - height;
        triangle.addPoint(px, py);
        
        //right center
        px = x + width;
        py = y;
        triangle.addPoint(px, py);

        circle.setPosition(x + width + (int)(thickness / 2.0 + radius * scaleX), y);
        circle.setScaleX(scaleX);
        circle.setScaleY(scaleY);
        circle.setThickness(thickness);
        circle.updatePoints();
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        int width = (int) (0.6 * scaleX);
        
        return new Point(x + width + (int)(thickness / 2.0 + radius * scaleX * 2), y);
    }

    @Override
    public Point getInput(int i) {
        return new Point(x, y);
    }

    @Override
    public int calcOut() {
        GateState input = GateState.NULL;
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
}
