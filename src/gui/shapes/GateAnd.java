package gui.shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * This class is an AND or NAND gate for building circuits.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GateAnd extends Gate {
    /** AND gate. On when all inputs are 1. */
    public static final int AND = 0;
    /** NAND gate. Off when all inputs are 1. */
    public static final int NAND = 1;
    /** The radius of the curve at the front of the AND gate. */
    private static final int RADIUS = 200;
    /** The polygon representation of the AND gate shape. */
    private Polygon poly = new Polygon();
    /** The curved arc at the front of the AND gate. */
    private Arc arc;
    /** The inverter circle at the front of a NAND gate. */
    private Circle inverter;
    /** The type of the gate. One of GateAnd.AND, GateAnd.NAND. */
    private int type;

    /**
     * Constructor to make an AND gate at the given position.
     * 
     * @param x The x position of the and gate
     * @param y The y position of the and gate
     * @param thickness The thickness of the stroke 
     *                  used to draw the gate.
     */
    public GateAnd(int x, int y, int thickness) {
        super(x, y, thickness * 10, 2, -1);
        arc = new Arc((int)(RADIUS * 1.6), 0, RADIUS, 0, Math.PI, thickness);
        arc.setSubdivisions(16);
        arc.setPie(false);
        arc.updatePoints();

        inverter = new Circle((int) (
                2.8 * RADIUS), 
                0, 
                (int)(0.2 * RADIUS), 
                thickness * 10);
        inverter.setSubdivisions(32);
        inverter.updatePoints();
        
        setScaleY(0.1);
        setScaleX(0.1);
    }
    
    /**
     * Set the variation (AND, NAND) for this gate.
     * 
     * @param gateType One of GateAnd.AND, or GateAnd.NAND
     */
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
        poly.addPoint(0, -RADIUS);
        
        //bottom left
        poly.addPoint(0, RADIUS);

        clearShapes();
        addShape(poly);
        
        if ((type & NAND) > 0) {
            addShape(inverter.getTransformedPolygon());
        }
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        int width = (int) (2.6 * RADIUS);
        if ((type & NAND) > 0) {
            width += (int) (0.4 * RADIUS); 
        }
        return new Point(width, 0);
    }

    @Override
    public Point getInput(int i) {
        int rY = RADIUS >> 1;
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
        //TODO: add nand functionality.
        boolean out = true;
        for (Link l : inputPorts) {
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
        return "gateAnd (" + ((!getName().equals("")) ? getName() : super.toString()) + ")";
    }

    @Override
    public Rectangle getBounds() {
        if (type == NAND) {
            return new Rectangle(
                    0 - (thickness >> 1), 
                    -RADIUS - (thickness >> 1), 
                    (int) (3 * RADIUS) + thickness, 
                    (RADIUS << 1) + thickness);
        } else {
            return new Rectangle(
                    0 - (thickness >> 1), 
                    -RADIUS - (thickness >> 1), 
                    (int) (2.6 * RADIUS) + thickness, 
                    (RADIUS << 1) + thickness);
        }
    }
}
