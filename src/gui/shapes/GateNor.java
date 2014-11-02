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
public class GateNor extends Gate {
    private Arc arcBottom;
    private Arc arcTop;
    private Arc arcLeft;
    Circle circle;
    private Polygon polygon;

    /**
     * @param x
     * @param y
     * @param thickness
     * @throws IllegalArgumentException
     */
    public GateNor(int x, int y, int thickness) {
        super(x, y, thickness, 2, -1);
        polygon = new Polygon();
        arcBottom = new Arc(x, y, 1, 0, Math.PI / 3, thickness);
        arcBottom.setSubdivisions(12);
        arcBottom.setPie(false);
        
        arcTop = new Arc(x, y, 1, 2 * Math.PI / 3, Math.PI, thickness);
        arcTop.setSubdivisions(12);
        arcTop.setPie(false);
        
        arcLeft = new Arc(x, y, 1, 2 * Math.PI / 3, Math.PI / 3, thickness);
        arcLeft.setSubdivisions(8);
        arcLeft.setPie(false);
        
        circle = new Circle(x, y, 0.1, thickness);
        circle.setSubdivisions(32);
        addShape(circle.getPolygon());
        addShape(polygon);
    }
    
    @Override
    protected void updatePoints() {
        int rY = (int) (1 * scaleY);
        int rX = (int) (1.5 * scaleX);
        
        polygon.reset();
        
        //draw a top arc \, draw a bottom arc /, draw a left arc. )

        arcBottom.setPosition(x, y - rY / 2);
        arcBottom.setScaleX(rX);
        arcBottom.setScaleY(rY);
        arcBottom.updatePoints();

        arcTop.setPosition(x, y + rY / 2);
        arcTop.setScaleX(rX);
        arcTop.setScaleY(rY);
        arcTop.updatePoints();
        
        arcLeft.setPosition(x - (int)(0.8660254 * rX), y );
        arcLeft.setScaleX(rX);
        arcLeft.setScaleY(rY);
        arcLeft.updatePoints();

        Polygon temp = arcBottom.getPolygon();
        for (int i = 0; i < temp.npoints; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }

        //i starts at one so that we don't double the output tip point. 
        temp = arcTop.getPolygon();
        for (int i = 1; i < temp.npoints; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }

        //start at 1 and end before the end, to avoid overlaps.
        temp = arcLeft.getPolygon();
        for (int i = 1; i < temp.npoints - 1; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }
        
//        circle.setPosition(x + gateWidth + half circle radius + thickness offset, y);
        circle.setPosition(x + (int)(0.8660254 * rX) + (int)(0.05 * rX) + thickness / 2, y);
        circle.setScaleX(scaleX);
        circle.setScaleY(scaleY);
        circle.setThickness(thickness);
        circle.updatePoints();
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        int rX = (int) (0.5 * scaleX);
        int width = (int) (0.8 * scaleX + 0.15 * scaleX) + thickness;
        return new Point(x + width + rX, y);
    }

    @Override
    public Point getInput(int i) {
        int rY = (int) (0.25 * scaleY);
        int rX = (int) (0.15 * scaleX);
        if (i == 0) {
            return new Point(x + rX, y - rY);
        } else if (i == 1) {
            return new Point(x + rX, y + rY);
        } else {
            return null;
        }
    }
    
    @Override
    public int calcOut() {
        boolean out = true;
        for(Link l : inputPorts) {
            if (l == null || l.getGateOut().getState() != GateState.OFF) {
                out = false;
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
        return "Gate Nor: " + super.toString();
    }
}
