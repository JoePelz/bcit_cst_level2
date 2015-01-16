/**
 * 
 */
package gui.shapes;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * This class draws a number in a circle.
 * Extending gate allows circuit-like connections 
 * to other circled numbers. 
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class BinaryTreeNumber extends Gate {
    /** The scale of the outer diameter for the circle. */
    private static final int OUTER_SCALE = 10;
    /** The scale of the inner diameter for the circle. */
    private static final int INNER_SCALE = 8;
    /** Size used for links, and overall radius multiplier. */
    private static final int THICKNESS = 10;
    /** The radius of the circle to draw. */
    private static final int OUTER_RADIUS = (int) ((double) OUTER_SCALE / 2.0 * THICKNESS);
    /** The diameter of the circle to draw. */
    private static final int OUTER_DIAMETER = OUTER_RADIUS * 2;
    /** The radius of the inner circle to draw. */
    private static final int INNER_RADIUS = (int) ((double) INNER_SCALE / 2.0 * THICKNESS);
    /** The diameter of the inner circle to draw. */
    private static final int INNER_DIAMETER = INNER_RADIUS * 2;
    /** The font scaling. 1.5f fits nicely inside the circle. */
    private static final float FONT_SCALE = 1.5f;
    /** The value to display in the circle. */
    private int value;
    
    /** 
     * Constructor, builds a Binary Tree Number 
     * with the given coordinates and value.
     * 
     * @param x The x position of the Binary Tree Number.
     * @param y The y position of the Binary Tree Number.
     * @param value The number to display.
     */
    public BinaryTreeNumber(int x, int y, int value) {
        super(x, y, THICKNESS, -1, -1);
        this.value = value;
    }
    
    /**
     * The value displayed by this object.
     * 
     * @return The objects display number.
     */
    public int getValue() {
        return value;
    }
    
    @Override
    public void drawStroke(Graphics2D g) {
        g.setColor(getStrokeOn());
        g.setStroke(new BasicStroke((float) (thickness * getTransform().getScaleY())));
        for (Link link : inputPorts) {
            if (link != null) {
                Point a = link.getGateOut().getOutput(link.getPortOut());
                AffineTransform aAT = link.getGateOut().getTransform();
                Point b = getInput(link.getPortIn());
                AffineTransform bAT = getTransform();
                
                aAT.transform(a, a);
                bAT.transform(b, b);
                double theta = Math.atan2(b.y - a.y, b.x - a.x);
                
                //A is the problem side.
                a.y += Math.sin(theta) * OUTER_RADIUS;
                a.x += Math.cos(theta) * OUTER_RADIUS;
                
                g.drawLine(a.x, a.y, b.x, b.y);
            }
        }
        
        
        AffineTransform atOld = g.getTransform();
        g.transform(getTransform());
        g.fillOval(
                -OUTER_RADIUS, 
                -OUTER_RADIUS, 
                OUTER_DIAMETER, 
                OUTER_DIAMETER);
        g.setColor(getBackground());
        g.fillOval(
                -INNER_RADIUS, 
                -INNER_RADIUS, 
                INNER_DIAMETER, 
                INNER_DIAMETER);
        g.setColor(getStrokeOn());
        Font font = g.getFont().deriveFont(
                        INNER_SCALE * thickness * FONT_SCALE 
                        / Math.max(2, Integer.toString(value).length()));
        FontMetrics metrics = g.getFontMetrics(font);
        
        g.setFont(font);
        
        final int vOffsetDenom = 3;
        g.drawString(Integer.toString(value), 
                -metrics.stringWidth(Integer.toString(value)) / 2, 
                metrics.getAscent() / vOffsetDenom);
        g.setTransform(atOld);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate#getOutput(int)
     */
    @Override
    public Point getOutput(int i) {
        return new Point();
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate#getInput(int)
     */
    @Override
    public Point getInput(int i) {
        return new Point();
    }

    /* (non-Javadoc)
     * @see gui.shapes.Shape#updatePoints()
     */
    @Override
    protected void updatePoints() {
        valid = true;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(-OUTER_RADIUS, -OUTER_RADIUS, OUTER_DIAMETER, OUTER_DIAMETER);
    }
}
