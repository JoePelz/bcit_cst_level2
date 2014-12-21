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
 * The base class for all shapes, provides useful drawing controls for 
 * transformations, scale, strokes, painting and polygons.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public abstract class Shape {
    /** The transform of the shape. */
    private AffineTransform transform = new AffineTransform();
    /** How thick the shape's outline should be. */
    protected int thickness;
    /** Whether the internal polygon is correct or not. */
    protected boolean valid;

    /** The scale value of the shape. */
    protected double scaleX = 1.0;
    /** The scale value of the shape. */
    protected double scaleY = 1.0;
    /** The position value of the shape. */
    protected int posX;
    /** The position value of the shape. */
    protected int posY;
    
    /** The internal polygon representing this shape. */
    protected Polygon p = new Polygon();
    
    /** The stroke to outline this shape with. */
    private BasicStroke stroke;
    
    /**
     * Constructor, to create the shape with 
     * a given position and stroke thickness. 
     * 
     * @param x The x position.
     * @param y The y position.
     * @param thickness The thickness of the shape.
     */
    public Shape(int x, int y, int thickness) {
        this.thickness = thickness;
        stroke = new BasicStroke(thickness);
        posX = x;
        posY = y;
        updateTransform();
        valid = false;
    }
    
    /**
     * Update the transform to reflect the position and scale of the shape.
     */
    private void updateTransform() {
        transform.setTransform(scaleX, 0, 0, scaleY, posX, posY);
    }

    /**
     * Get the transform of the shape, generally for drawing.
     * 
     * @return The shape's transform.
     */
    public AffineTransform getTransform() {
        return transform;
    }
    
    /**
     * Set the transform by terms of position and scale. 
     * Rotation is not supported yet.
     * 
     * @param sclX The x-axis scale
     * @param sclY The y-axis scale
     * @param centerX The x position
     * @param centerY The y position.
     */
    public void setTransform(double sclX, double sclY, double centerX, double centerY) {
        transform.setTransform(sclX, 0, 0, sclY, centerX, centerY);
        posX = (int)centerX;
        posY = (int)centerY;
        scaleX = sclX;
        scaleY = sclY;
    }
    
    /**
     * Update the polygon points to reflect 
     * any internal changes made to the construction. 
     */
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
    
    /**
     * Move the shape to a new position.
     * 
     * @param x The x position
     * @param y The y position
     */
    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
        updateTransform();
    }

    /**
     * Set the x-axis scale.
     * 
     * @param scaleX the x-axis scale.
     */
    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
        updateTransform();
    }

    /**
     * Set the y-axis scale.
     * 
     * @param scaleY the y-axis scale.
     */
    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
        updateTransform();
    }

    /**
     * Set the stroke thickness for this shape.
     * 
     * @param thickness The thickness of the stroke.
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
        stroke = new BasicStroke(thickness);
    }
    
    /**
     * Get a reference to the internal polygon.
     * 
     * @return The internal polygon for this shape.
     */
    public Polygon getPolygon() {
        return p;
    }
    
    /**
     * Get a copy of the internal polygon, with all points 
     * transformed by the current transform. 
     * 
     * @return The transformed polygon
     */
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
    
    /**
     * Get the stroke object for this shape (basically how thick the outline is).
     * 
     * @return The stroke object for outlining this shape.
     */
    public BasicStroke getStroke() {
        return stroke;
    };

    /**
     * Get the current position of this shape.
     * 
     * @return The current center position of this shape.
     */
    public Point getPosition() {
        return new Point((int)transform.getTranslateX(), (int)transform.getTranslateY());
    }
}
