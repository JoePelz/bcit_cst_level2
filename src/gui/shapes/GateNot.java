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
public class GateNot extends Gate{
    private static final int radius = 6;
    private Circle circle;
    private int[] xFillPoints;
    private int[] yFillPoints;

    public GateNot(int x, int y, int thickness) {
        super(x, y, thickness);
        circle = new Circle(x, y, radius, thickness);
        circle.setSubdivisions(32);
        addShape(circle);
    }
    
    @Override
    protected void updatePoints() {
        int height = (int) (22 * scaleY);
        int width = (int) (40 * scaleX);
        xPoints = new int[10];
        yPoints = new int[10];
        xFillPoints = new int[3];
        yFillPoints = new int[3];
        
        //inside
        //bottom left
        xPoints[0] = x + thickness;
        yPoints[0] = y + (int)(height - thickness * 1.5);

        //top left
        xPoints[1] = x + thickness;
        yPoints[1] = y - (int)(height - thickness * 1.5);
        
        //right center
        xPoints[2] = x + width - thickness * 2;
        yPoints[2] = y;
        xPoints[3] = x + width - thickness * 2;
        yPoints[3] = y;
        
        //bottom left
        xPoints[4] = x + thickness;
        yPoints[4] = y + (int)(height - thickness * 1.5);
        
        //outside
        //bottom left
        xPoints[5] = x - thickness;
        yPoints[5] = y + (int)(height + thickness * 1.5);

        //top left
        xPoints[6] = x - thickness;
        yPoints[6] = y - (int)(height + thickness * 1.5);
        
        //right center
        xPoints[7] = x + width;// + (int)(thickness * 0.5);
        yPoints[7] = y - thickness;
        xPoints[8] = x + width;// + (int)(thickness * 0.5);
        yPoints[8] = y + thickness;
        
        //bottom left
        xPoints[9] = x - thickness;
        yPoints[9] = y + (int)(height + thickness * 1.5);
        
        //inside points
        //bottom left
        xFillPoints[0] = x;
        yFillPoints[0] = y + (int)(height);
        
        //top left
        xFillPoints[1] = x;
        yFillPoints[1] = y - (int)(height);
        
        //right
        xFillPoints[2] = x + width;
        yFillPoints[2] = y;
        
        circle.setPosition(x + width - thickness + (int)(radius * scaleX), y);
        circle.setScaleX(scaleX);
        circle.setScaleY(scaleY);
        circle.setThickness(thickness);
        
        valid = true;
    }
    
    @Override
    public void drawFill(Graphics g) {
        if (!valid) 
            updatePoints();
        g.fillPolygon(xFillPoints, yFillPoints, xFillPoints.length);
        //drawFill all sub-shapes
        for (Shape s : shapes) {
            s.drawFill(g);
        }
    }
}
