package gui.shapes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * This class is a joint in the wires. 
 * It can fork a wire, or just be a corner.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GatePin extends Gate {
    /** Indicates a label should be placed above and to the right. */
    public static final int NE = 0;
    /** Indicates a label should be placed above and to the left. */
    public static final int NW = 1;
    /** Indicates a label should be placed below and to the right. */
    public static final int SE = 2;
    /** Indicates a label should be placed below and to the left. */
    public static final int SW = 3;

    /** A label to draw next to the pin. */
    private String label = new String();
    /** Which side of the pin to draw the label. */
    private int labelSide;
    
    /**
     * Constructor, to place a pin at a particular position. 
     * 
     * @param x The x position.
     * @param y The y position.
     * @param thickness The thickness of the stroke to draw this pin.
     */
    public GatePin(int x, int y, int thickness) {
        super(x, y, thickness, 1, -1);
        setBackgroundOff(Color.BLACK);
        addShape(p);
    }

    /**
     * Set the label to draw next to this pin.
     * 
     * @param label The label text for this pin.
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Set which side of the pin to draw the label on. 
     * Use one of GatePin.NE, NW, SE, SW
     * 
     * @param side The side to draw the label on.
     */
    public void setLabelSide(int side) {
        labelSide = side;
    }

    @Override
    protected void updatePoints() {
        valid = true;
    }

    @Override
    public void drawFill(Graphics2D g) {
        drawStroke(g);
    }
    @Override
    public void drawStroke(Graphics2D g) {
        super.drawStroke(g);

        AffineTransform at_old = g.getTransform();
        g.transform(getTransform());
        g.setColor(this.getBackground());
        g.fillOval((int)(-1.5 * thickness), (int)(-1.5 * thickness), 3 * thickness, 3 * thickness);
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        switch(labelSide) {
        case NE:
        default:
            g.drawString(label, thickness, -thickness);
            break;
        case NW:
            g.drawString(label, -thickness - fm.stringWidth(label), -thickness);
            break;
        case SE:
            g.drawString(label, thickness, thickness + fm.getHeight());
            break;
        case SW:
            g.drawString(label, -thickness - fm.stringWidth(label), thickness + fm.getAscent());
            break;
        }
        g.setTransform(at_old);
    }

    @Override
    public Point getOutput(int i) {
        return new Point();
    }

    @Override
    public Point getInput(int i) {
        return new Point();
    }
    
    @Override
    public String toString() {
        return "Gate Pin: " + super.toString();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(-1.5 * thickness), (int)(-1.5 * thickness), 3 * thickness, 3 * thickness);
    }
}
