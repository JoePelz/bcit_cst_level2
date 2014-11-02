/**
 * 
 */
package gui.shapes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

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
        g.setColor(this.getBackground());
        g.fillOval(x - (int)(1.5 * thickness), y - (int)(1.5 * thickness), thickness * 3, thickness * 3);
        g.setColor(Color.BLACK);
        
        FontMetrics fm = g.getFontMetrics();
        switch(labelSide) {
        case NE:
        default:
            g.drawString(label, x + thickness, y - thickness);
            break;
        case NW:
            g.drawString(label, x - thickness - fm.stringWidth(label), y - thickness);
            break;
        case SE:
            g.drawString(label, x + thickness, y + thickness + fm.getHeight());
            break;
        case SW:
            g.drawString(label, x - thickness - fm.stringWidth(label), y + thickness + fm.getAscent());
            break;
        }
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getOutput(int)
     */
    @Override
    public Point getOutput(int i) {
        return new Point(x, y);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getInput(int)
     */
    @Override
    public Point getInput(int i) {
        return new Point(x, y);
    }
    
    @Override
    public String toString() {
        return "Gate Pin: " + super.toString();
    }
}
