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
     * @param x
     * @param y
     * @param thickness
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
    protected void updatePoints() {
        int rX;
        int rY;
        int i;
        int px;
        int py;
        
        int points = subdivisions;

        double factor = (stop - start) / subdivisions;
//        double factor = Math.PI * 2 / points;
        double offset = start;
        
        //clear the polygon first.
        p.reset();
        //add points
        rX = (int)(radius * scaleX);
        rY = (int)(radius * scaleY);
        for(i = 0; i <= points; i++) {
            px = x + (int) (Math.sin(i * factor + offset) * rX);
            py = y + (int) (Math.cos(i * factor + offset) * rY);
            p.addPoint(px, py);
        }
        
        if (pie) {
            p.addPoint(x, y);
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
