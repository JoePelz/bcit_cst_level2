/**
 * 
 */
package gui.shapes;

/**
 * This class draws an arc shape.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class Arc extends Shape {
    /** The radius of the arc. */
    private double radius;
    /** The starting point of the arc shape. 
     * (0.0 is 6 o'clock, pi/2 is 3 o'clock) 
     */
    private double start;
    /** The stopping point of the arc shape. 
     * (0.0 is 6 o'clock, pi/2 is 3 o'clock) 
     */
    private double stop;
    /** The number of subdivisions to use in constructing the shape. */
    private int subdivisions;
    /** Whether or not to link to the arc center. */
    private boolean pie = true;

    /**
     * Constructor, to build an arc at the given position and radius.  
     * Start and stop control which direction to start and stop 
     * drawing at. 0.0 is 6 o'clock and pi/2 is 3 o'clock. 
     * 
     * @param x The x position of the arc's center
     * @param y The y position of the arc's center
     * @param radius The arc radius in pixels (at 1.0 scale)
     * @param start The starting point of the circle 
     *              (0.0 is 6 o'clock, pi/2 is 3 o'clock)
     * @param stop The stop point of the circle 
     *              (0.0 is 6 o'clock, pi/2 is 3 o'clock)
     * @param thickness The edge thickness for the circle
     */
    public Arc(int x, int y, double radius, double start, double stop, int thickness) {
        super(x, y, thickness);
        this.radius = radius;
        this.start = start;
        this.stop = stop;
        
        subdivisions = 8;
    }
    
    /**
     * Set the number of subdivisions to use for drawing this arc. 
     * Note, points are limited by integer precision. 
     * 
     * @param num The number of subdivisions in the arc path.
     */
    public void setSubdivisions(int num) {
        if (num < 3 || num > 100) {
            throw new IllegalArgumentException("Subdivisions must be between 3 and 100.\nNumber given was " + num + ".");
        }
        subdivisions = num;
        valid = false;
    }
    
    @Override
    public void updatePoints() {
        int i;
        int px;
        int py;
        
        int points = subdivisions;

        double factor = (stop - start) / subdivisions;
        double offset = start;
        
        //clear the polygon first.
        p.reset();
        //add points
        for(i = 0; i <= points; i++) {
            px = (int) (Math.sin(i * factor + offset) * radius);
            py = (int) (Math.cos(i * factor + offset) * radius);
            p.addPoint(px, py);
        }
        
        if (pie) {
            p.addPoint(0, 0);
        }
        
        valid = true;
    }

    /**
     * Set whether or not to connect the arc to the arc center 
     * like a pie or just be a bare arc. 
     * 
     * @param pie True if the arc should connect to its center.
     */
    public void setPie(boolean pie) {
        this.pie = pie;
    }
}
