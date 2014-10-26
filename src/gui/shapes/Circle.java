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
public class Circle extends Shape {
    private double radius;
    private int subdivisions;
    private int[] xFillPoints;
    private int[] yFillPoints;

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
        int i;
        //For the triangle.
        int points = subdivisions;
        xPoints = new int[(points + 1) << 1];
        yPoints = new int[(points + 1) << 1];
        xFillPoints = new int[(points)];
        yFillPoints = new int[(points)];

        double factor = Math.PI * 2 / points;
        
        
        //inside
        rX = (int)(radius * scaleX) - thickness;
        rY = (int)(radius * scaleY) - thickness;
        for(i = 0; i < points; i++) {
            xPoints[i] = x + (int) (Math.sin(i * factor) * rX);
            yPoints[i] = y + (int) (Math.cos(i * factor) * rY);
        }
        xPoints[i] = xPoints[0];
        yPoints[i] = yPoints[0];

        //outside
        rX = (int)(radius * scaleX) + thickness;
        rY = (int)(radius * scaleY) + thickness;
        for(i = points + 1; i < xPoints.length - 1; i++) {
            xPoints[i] = x + (int) (Math.sin(i * factor) * rX);
            yPoints[i] = y + (int) (Math.cos(i * factor) * rY);
        }
        xPoints[i] = xPoints[points + 1];
        yPoints[i] = yPoints[points + 1];

        
        //fill
        rX = (int)(radius * scaleX);
        rY = (int)(radius * scaleY);
        for(i = 0; i < points; i++) {
            xFillPoints[i] = x + (int) (Math.sin(i * factor) * rX);
            yFillPoints[i] = y + (int) (Math.cos(i * factor) * rY);
        }
        
        valid = true;
    }
    
    @Override
    public void drawFill(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xFillPoints, yFillPoints, xFillPoints.length);
    }
}
