/**
 * 
 */
package gui.shapes;

/**
 * This class draws a circle shape.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class Circle extends Shape {
    /** The radius of the circle. */
    private double radius;
    /** The number of subdivisions 
     * in the polygon representation of the circle. */
    private int subdivisions;
    
    /**
     * Constructor to make a circle at the given position, 
     * with the given radius.
     * 
     * @param x the x position of the circle center.
     * @param y The y position of the circle center. 
     * @param radius The radius of the circle in pixels.
     * @param thickness How thick the circle's stroke is.
     */
    public Circle(int x, int y, double radius, int thickness) {
        super(x, y, thickness);
        final int defaultSubdivisions = 8;
        
        this.radius = radius;
        
        subdivisions = defaultSubdivisions;
    }
    
    /**
     * Set the number of subdivisions to use 
     * when making the polygon representation.
     * 
     * @param num The number of edges in the polygon circle.
     */
    public void setSubdivisions(int num) {
        // maximum reasonable subdivisions
        final int max = 100;
        // minimum reasonable subdivisions
        final int min = 3;
        
        if (num < min || num > max) {
            throw new IllegalArgumentException("Subdivisions must be between "
                    + "3 and 100.\nNumber given was " + num + ".");
        }
        subdivisions = num;
        valid = false;
    }

    @Override
    public void updatePoints() {
        int rX;
        int rY;
        int px;
        int py;
        
        int points = subdivisions;
        double factor = Math.PI * 2 / points;
        
        
        //clear the polygon first.
        p.reset();
        //add points
        rX = (int) (radius);
        rY = (int) (radius);
        for (int i = 0; i < points; i++) {
            px = (int) (Math.sin(i * factor) * rX);
            py = (int) (Math.cos(i * factor) * rY);
            p.addPoint(px, py);
        }
        
        valid = true;
    }
}
