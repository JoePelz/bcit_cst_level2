/**
 * 
 */
package gui.shapes;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Arc extends Shape {

    private double radius;
    private double start;
    private double stop;
    private int subdivisions;
    private boolean pie = true;

    /**
     * 
     * @param x The x position of the arc's center
     * @param y The y position of the arc's center
     * @param radius The arc radius in pixels (at 1.0 scale)
     * @param start The starting point of the circle (0.0 is 6 o'clock, pi/2 is 3 o'clock)
     * @param stop The stop point of the circle (0.0 is 6 o'clock, pi/2 is 3 o'clock)
     * @param thickness The edge thickness for the circle
     */
    public Arc(int x, int y, double radius, double start, double stop, int thickness) {
        super(x, y, thickness);
        this.radius = radius;
        this.start = start;
        this.stop = stop;
        
        subdivisions = 8;
    }
    
    public void setSubdivisions(int num) {
        if (num < 3 || num > 100) {
            throw new IllegalArgumentException("Subdivisions must be between 3 and 100.\nNumber given was " + num + ".");
        }
        subdivisions = num;
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
     * @param pie the pie to set
     */
    public void setPie(boolean pie) {
        this.pie = pie;
    }
}
