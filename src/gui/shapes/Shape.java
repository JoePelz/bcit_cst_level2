/**
 * 
 */
package gui.shapes;

import java.awt.Graphics;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public abstract class Shape {
    protected int x;
    protected int y;
    protected int thickness;
    protected boolean valid;
    
    protected double scaleX = 1.0;
    protected double scaleY = 1.0;
    
    protected int[] xPoints;
    protected int[] yPoints;
    
    protected Shape(int x, int y, int thickness) {
        this.x = x;
        this.y = y;
        this.thickness = thickness;
        valid = false;
    }
    
    protected abstract void updatePoints();
    
    /**
     * Draw the given gate onto the graphics context.
     * @param g Graphics context to draw into
     */
    public void drawStroke(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
    /**
     * Fill the given gate onto the graphics context.
     * @param g Graphics context to draw into
     */
    public void drawFill(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xPoints, yPoints, xPoints.length / 2);
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        valid = false;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
        valid = false;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
        valid = false;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        valid = false;
    }
}
