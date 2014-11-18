/**
 * 
 */
package gui.shapes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GatePin extends Gate {
    public static final int NE = 0;
    public static final int NW = 1;
    public static final int SE = 2;
    public static final int SW = 3;

    private String label = new String();
    private int labelSide;
    
    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GatePin(int x, int y, int thickness) {
        super(x, y, thickness, 1, -1);
        setBackgroundOff(Color.BLACK);
        addShape(p);
    }

    public void setLabel(String label) {
        this.label = label;
    }
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

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getOutput(int)
     */
    @Override
    public Point getOutput(int i) {
        return new Point();
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getInput(int)
     */
    @Override
    public Point getInput(int i) {
        return new Point();
    }
    
    @Override
    public String toString() {
        return "Gate Pin: " + super.toString();
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)(-1.5 * thickness), (int)(-1.5 * thickness), 3 * thickness, 3 * thickness);
    }
}
