/**
 * 
 */
package gui.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public abstract class Shape {
    private AffineTransform transform = new AffineTransform();
    protected int thickness;
    protected boolean valid;
    
    protected double scaleX = 1.0;
    protected double scaleY = 1.0;
    protected int posX;
    protected int posY;
    
    protected Polygon p = new Polygon();
    
    private BasicStroke stroke;
    
    public Shape(int x, int y, int thickness) {
        this.thickness = thickness;
        stroke = new BasicStroke(thickness);
        posX = x;
        posY = y;
        updateTransform();
        valid = false;
    }
    
    private void updateTransform() {
        transform.setTransform(scaleX, 0, 0, scaleY, posX, posY);
    }

    public AffineTransform getTransform() {
        return transform;
    }
    
    public void setTransform(double sclX, double sclY, double centerX, double centerY) {
        transform.setTransform(sclX, 0, 0, sclY, centerX, centerY);
        posX = (int)centerX;
        posY = (int)centerY;
        scaleX = sclX;
        scaleY = sclY;
    }
    
    protected abstract void updatePoints();
    
    /**
     * Draw the given gate onto the graphics context.
     * @param g Graphics context to draw into
     */
    public void drawStroke(Graphics2D g) {
        if (!valid) 
            updatePoints();
        
        AffineTransform at_old = g.getTransform();
        g.transform(getTransform());
        g.setStroke(stroke);
        g.drawPolygon(p);
        g.setTransform(at_old);
    }
    /**
     * Fill the given gate onto the graphics context.
     * @param g Graphics context to draw into
     */
    public void drawFill(Graphics2D g) {
        if (!valid) 
            updatePoints();
        AffineTransform at_old = g.getTransform();
        g.transform(getTransform());
        g.fillPolygon(p);
        g.setTransform(at_old);
    }
    
    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
        updateTransform();
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
        updateTransform();
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
        updateTransform();
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        stroke = new BasicStroke(thickness);
    }
    public Polygon getPolygon() {
        return p;
    }
    
    public Polygon getTransformedPolygon() {
        Point point = new Point();
        Polygon result = new Polygon();
        AffineTransform at = getTransform();
        for (int i = 0; i < p.npoints; i++) {
            point.setLocation(p.xpoints[i], p.ypoints[i]);
            at.transform(point, point);
            result.addPoint(point.x, point.y);
        }
        return result;
    }
    
    public BasicStroke getStroke() {
        return stroke;
    };

    public Point getPosition() {
        return new Point((int)transform.getTranslateX(), (int)transform.getTranslateY());
    }
}
