package gui.shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * This class is a OR, XOR, NOR, or XNOR gate. 
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GateOr extends Gate {
    /** OR type option. ON if any inputs are ON. */
    public static final int OR = 0;
    /** XOR type option. ON if a single input is ON. */
    public static final int XOR = 1;
    /** NOR type option. OFF if any inputs are ON. */
    public static final int NOR = 2;
    /** XNOR type option. OFF if a single input is ON. */
    public static final int XNOR = XOR | NOR;
    
    /** size of the gate. */
    private static final int RADIUS = 200;

    /** The bottom arc of the gate shape. */
    private Arc arcBottom;
    /** The top arc of the gate shape. */
    private Arc arcTop;
    /** The left arc of the gate shape. */
    private Arc arcLeft;

    /** The second left arc for a XOR gate. */
    private Arc arcLeft2;
    /** The inverter for a NOR gate. */
    private Circle inverter;
    /** The polygon representing the main OR shape. */ 
    private Polygon polygon;
    /** The type of this gate, one of OR, XOR, NOR, XNOR. */
    private int type;
    
    /**
     * Constructor, builds and places 
     * all polygons and polylines in the right places.  
     * 
     * @param x The x position of the gate
     * @param y The y position of the gate
     * @param thickness The thickness of the stroke to draw this gate
     */
    public GateOr(int x, int y, int thickness) {
        super(x, y, thickness * 10, 2, -1);
        polygon = new Polygon();
        arcBottom = new Arc(0, -RADIUS, RADIUS << 1, 0, Math.PI / 3, thickness);
        arcBottom.setScaleX(1.5);
        arcBottom.setSubdivisions(12);
        arcBottom.setPie(false);
        arcBottom.updatePoints();
        
        arcTop = new Arc(0, RADIUS, RADIUS << 1, 2 * Math.PI / 3, Math.PI, thickness);
        arcTop.setScaleX(1.5);
        arcTop.setSubdivisions(12);
        arcTop.setPie(false);
        arcTop.updatePoints();
        
        arcLeft = new Arc(
                (int) (-2.6 * RADIUS), 
                0, 
                RADIUS << 1, 
                2 * Math.PI / 3, 
                Math.PI / 3, 
                thickness);
        arcLeft.setScaleX(1.5);
        arcLeft.setSubdivisions(8);
        arcLeft.setPie(false);
        arcLeft.updatePoints();

        inverter = new Circle(
                (int)(2.8 * RADIUS), 
                0, 
                RADIUS * 0.2, 
                thickness * 10);
        inverter.setSubdivisions(32);
        inverter.updatePoints();
        
        arcLeft2 = new Arc(
                (int)(-3 * RADIUS), 
                0, 
                RADIUS << 1, 
                2 * Math.PI / 3, 
                Math.PI / 3, 
                thickness);
        arcLeft2.setScaleX(1.5);
        arcLeft2.setSubdivisions(8);
        arcLeft2.setPie(false);
        arcLeft2.updatePoints();
        
        updatePoints();
        
        setScaleX(0.1);
        setScaleY(0.1);
    }
    
    /**
     * Set what type of gate this is. One of OR, XOR, NOR, XNOR.
     * 
     * @param gateType The gate type. GateOr.OR, XOR, NOR, XNOR.
     */
    public void setVariation(int gateType) {
        final int numberOfTypes = 4;
        if (gateType > numberOfTypes) {
            throw new IllegalArgumentException("Gate must be of type GateOr.OR / XOR / NOR / XNOR");
        }
        type = gateType;
        updatePoints();
    }
    
    @Override
    protected void updatePoints() {
        clearShapes();
        
        polygon.reset();

        Polygon temp = arcBottom.getTransformedPolygon();
        for (int i = 0; i < temp.npoints; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }

        //i starts at one so that we don't double the output tip point. 
        temp = arcTop.getTransformedPolygon();
        for (int i = 1; i < temp.npoints; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }

        //start at 1 and end before the end, to avoid overlaps.
        temp = arcLeft.getTransformedPolygon();
        for (int i = 1; i < temp.npoints - 1; i++) {
            polygon.addPoint(temp.xpoints[i], temp.ypoints[i]);
        }
        addShape(polygon);
        
        if ((type & NOR) > 0) {
          addShape(inverter.getTransformedPolygon());  
        }
        
        if ((type & XOR) > 0) {
            addLine(arcLeft2.getTransformedPolygon());
        }
        
        valid = true;
    }

    @Override
    public Point getOutput(int i) {
        int width = (int) (2.6 * RADIUS);
        if ((type & NOR) > 0) {
            width += (int) (0.4 * RADIUS); 
        }
        return new Point(width, 0);
    }

    @Override
    public Point getInput(int i) {
        int rY = RADIUS >> 1;
        int rX = (int) (0.3 * RADIUS);
        if (i == 0) {
            return new Point(rX, -rY);
        } else if (i == 1) {
            return new Point(rX,  rY);
        } else {
            return null;
        }
    }

    @Override
    public int calcOut() {
        GateState destState = GateState.UNKNOWN;
        switch(type) {
        case OR: 
        case NOR:
            destState = GateState.OFF;
            boolean nullExists = false;
            for (Link l : inputPorts) {
                if (l == null || l.getGateOut().getState() == GateState.UNKNOWN) {
                    nullExists = true;
                    continue;
                } else if (l.getGateOut().getState() == GateState.ON) {
                    destState = GateState.ON;
                    break;
                }
            }
            
            //0 OR unknown == unknown
            //1 OR unknown == 1
            if (destState == GateState.OFF && nullExists) {
                destState = GateState.UNKNOWN;
            }
            
            break;
        case XOR: 
        case XNOR:
            destState = GateState.OFF;
            for (Link l : inputPorts) {
                // If the input is disconnected (null), or is unknown (GateState.UNKNOWN)
                if (l == null || l.getGateOut().getState() == GateState.UNKNOWN) {
                    destState = GateState.UNKNOWN;
                    break;
                } else if (l.getGateOut().getState() == GateState.ON) { // Else if the input is 'ON'
                    if (destState == GateState.ON) {
                        destState = GateState.OFF;
                        break;
                    } else {
                        destState = GateState.ON;
                    }
                }
            }
            break;
        default:
            return super.calcOut();
        }

        //This filters type NOR and XNOR
        if ((type & NOR) > 1) {
            if (destState == GateState.ON) {
                destState = GateState.OFF;
            } else if (destState == GateState.OFF) {
                destState = GateState.ON;
            }
        }
        
        if (getState() != destState) {
            setState(destState);
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Gate Or: " + super.toString();
    }
    
    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(
                0 - thickness >> 1, 
                -RADIUS - (thickness >> 1), 
                (int) (2.6 * RADIUS) + thickness, 
                (RADIUS << 1) + thickness);
        
        //extend the nose if NOR gate
        if ((type & NOR) != 0) {
            r.width += (int) (0.4 * RADIUS);
        }
        
        //extend the head if XOR gate
        if ((type & XOR) != 0) {
            r.x -= RADIUS * 0.42;
            r.width += RADIUS * 0.42;
        }
            
        return r;
    }

}
