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
public class Arc extends Shape {
    private double radius;
    private double start;
    private double stop;
    private int subdivisions;
    private int[] xFillPoints;
    private int[] yFillPoints;

    public Arc(int x, int y, double radius, double start, double stop, int thickness) {
        super(x, y, thickness);
        this.radius = radius;
        this.start = start;
        this.stop = stop;
        
        subdivisions = 4;
    }
    
    public void setSubdivisions(int num) throws IllegalArgumentException {
        if (num < 3 || num > 50) {
            throw new IllegalArgumentException("Subdivisions must be between 3 and 50.\nNumber given was " + num + ".");
        }
        subdivisions = num;
    }

    @Override
    protected void updatePoints() {
        int rX;
        int rY;
        int i;
        //For the triangle.
        int points = subdivisions + 1;
        xPoints = new int[(points) << 1];
        yPoints = new int[(points) << 1];
        xFillPoints = new int[(points) + 1];
        yFillPoints = new int[(points) + 1];

        double factor = (stop - start) / subdivisions;
//        double factor = Math.PI * 2 / points;
        double offset = start;
//        double offset = 0;
        
        
        //inside
        rX = (int)(radius * scaleX) - thickness;
        rY = (int)(radius * scaleY) - thickness;
        for(i = 0; i < points; i++) {
            xPoints[i] = x + (int) (Math.sin(i * factor + offset) * rX);
            yPoints[i] = y + (int) (Math.cos(i * factor + offset) * rY);
        }

        //outside
        rX = (int)(radius * scaleX) + thickness;
        rY = (int)(radius * scaleY) + thickness;
        for(i = 0; i < points; i++) {
            xPoints[xPoints.length - 1 - i] = x + (int) (Math.sin(i * factor + offset) * rX);
            yPoints[xPoints.length - 1 - i] = y + (int) (Math.cos(i * factor + offset) * rY);
        }
        
        //fill
        rX = (int)(radius * scaleX);
        rY = (int)(radius * scaleY);
        for(i = 0; i < points; i++) {
            xFillPoints[i] = x + (int) (Math.sin(i * factor + offset) * rX);
            yFillPoints[i] = y + (int) (Math.cos(i * factor + offset) * rY);
        }
        xFillPoints[points] = x;
        yFillPoints[points] = y;
        
        valid = true;
    }
    
    @Override
    public void drawFill(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xFillPoints, yFillPoints, xFillPoints.length);
    }
}
