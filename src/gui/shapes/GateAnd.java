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
public class GateAnd extends Gate {

    private Arc arc;

    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GateAnd(int x, int y, int thickness) {
        super(x, y, thickness, 2, -1);
        arc = new Arc(x, y, 1, 0, Math.PI, thickness);
        arc.setSubdivisions(12);
        arc.setPie(false);
        addShape(arc.getPolygon());
    }

    /* (non-Javadoc)
     * @see gui.shapes.Shape2#updatePoints()
     */
    @Override
    public void updatePoints() {
        int rY = (int) (0.5 * scaleY);
        int rX = (int) (0.5 * scaleX);
        int px;
        int py;
        int width = (int) (0.8 * scaleX);

        arc.setPosition(x + width, y);
        arc.setScaleX(rX);
        arc.setScaleY(rY);
        arc.updatePoints();
        
        Polygon temp = arc.getPolygon();
        
        //top left
        px = x;
        py = y - rY;
        temp.addPoint(px, py);
        
        //bottom left
        px = x;
        py = y + rY;
        temp.addPoint(px, py);

        valid = true;
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getOutput(int)
     */
    @Override
    public Point getOutput(int i) {
        int rX = (int) (0.5 * scaleX);
        int width = (int) (0.8 * scaleX);
        return new Point(x + width + rX, y);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getInput(int)
     */
    @Override
    public Point getInput(int i) {
        int rY = (int) (0.25 * scaleY);
        if (i == 0) {
            return new Point(x, y - rY);
        } else if (i == 1) {
            return new Point(x, y + rY);
        } else {
            return null;
        }
    }
    
    @Override
    public int calcOut() {
        boolean out = true;
        for(Link l : inputPorts) {
            if (l == null) {
                out = false;
                break;
            }
            if (l.getGateOut().getState() != GateState.ON) {
                out = false;
                break;
            }
        }
        if (out) {
            if (getState() != GateState.ON) {
                setState(GateState.ON);
                return 1;
            }
        } else {
            if (getState() != GateState.OFF) {
                setState(GateState.OFF);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Gate And: " + super.toString();
    }
}
