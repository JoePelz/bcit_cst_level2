/**
 * 
 */
package gui.shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateAnd extends Gate {
    public static final int AND = 0;
    public static final int NAND = 1;
    private static final int radius = 200;
    private Polygon poly = new Polygon();
    private Arc arc;
    private Circle inverter;
    private int type;

    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GateAnd(int x, int y, int thickness) {
        super(x, y, thickness * 10, 2, -1);
        arc = new Arc((int)(radius * 1.6), 0, radius, 0, Math.PI, thickness);
        arc.setSubdivisions(16);
        arc.setPie(false);
        arc.updatePoints();

        inverter = new Circle((int)(
                2.8 * radius), 
                0, 
                (int)(radius * 0.2), 
                thickness * 10);
        inverter.setSubdivisions(32);
        inverter.updatePoints();
        
        setScaleY(0.1);
        setScaleX(0.1);
    }
    
    public void setVariation(int gateType) {
        if (gateType > 2) {
            throw new IllegalArgumentException("Gate must be of type GateAnd.AND / NAND");
        }
        type = gateType;
        updatePoints();
    }

    @Override
    public void updatePoints() {

        poly = arc.getTransformedPolygon();
        
        //top left
        poly.addPoint(0, -radius);
        
        //bottom left
        poly.addPoint(0, radius);

        clearShapes();
        addShape(poly);
        
        if ((type & NAND) > 0) {
            addShape(inverter.getTransformedPolygon());
        }
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        int width = (int) (2.6 * radius);
        if ((type & NAND) > 0) {
            width += (int)(radius * 0.4); 
        }
        return new Point(width, 0);
    }

    @Override
    public Point getInput(int i) {
        int rY = radius >> 1;
        if (i == 0) {
            return new Point(0, -rY);
        } else if (i == 1) {
            return new Point(0, rY);
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
//            System.out.print("(" + getName() + ") input " + l.getPortIn() + ": " + l.getGateOut().getState() + ",  ");
            if (l.getGateOut().getState() != GateState.ON) {
                out = false;
                break;
            }
        }
        if (out) {
            if (getState() != GateState.ON) {
//                System.out.println(this.toString() + " flipped on!");
                setState(GateState.ON);
                return 1;
            }
        } else {
            if (getState() != GateState.OFF) {
//                System.out.println(this.toString() + " flipped off!");
                setState(GateState.OFF);
                return 1;
            }
        }
//        System.out.println(this.toString() + " didn't change.");
        return 0;
    }

    @Override
    public String toString() {
        return "gateAnd (" + ((!getName().equals("")) ? getName() : super.toString()) + ")";
    }

    public Rectangle getBounds() {
        if (type == NAND)
            return new Rectangle(
                    0 - (thickness >> 1), 
                    -radius - (thickness >> 1), 
                    (int)(3 * radius) + thickness, 
                    (radius << 1) + thickness);
        else
            return new Rectangle(
                    0 - (thickness >> 1), 
                    -radius - (thickness >> 1), 
                    (int)(2.6 * radius) + thickness, 
                    (radius << 1) + thickness);
    }
}
