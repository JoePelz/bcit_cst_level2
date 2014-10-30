/**
 * 
 */
package gui.shapes;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Circle extends Shape {

    private double radius;
    private int subdivisions;
    
    /**
     * @param x
     * @param y
     * @param thickness
     */
    public Circle(int x, int y, double radius, int thickness) {
        super(x, y, thickness);
        this.radius = radius;
        
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
        int px;
        int py;
        
        int points = subdivisions;
        double factor = Math.PI * 2 / points;
        
        
        //clear the polygon first.
        p.reset();
        //add points
        rX = (int)(radius * scaleX);
        rY = (int)(radius * scaleY);
        for(int i = 0; i < points; i++) {
            px = x + (int) (Math.sin(i * factor) * rX);
            py = y + (int) (Math.cos(i * factor) * rY);
            p.addPoint(px, py);
        }
        
        valid = true;
    }
}
