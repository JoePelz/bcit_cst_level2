/**
 * 
 */
package gui.shapes;



/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateAnd extends Gate{
    Arc arc;
    
    public GateAnd(int x, int y, int thickness) {
        super(x, y, thickness);
        arc = new Arc(x, y, 1, 0, Math.PI, thickness);
        arc.setSubdivisions(12);
        addShape(arc);
    }

    @Override
    protected void updatePoints() {
        int rY = (int) (34 * scaleY);
        int rX = (int) (34 * scaleX);
        int width = (int) (56 * scaleX);
        
        xPoints = new int[8];
        yPoints = new int[8];
        
        //inside
        //top middle
        xPoints[0] = x + thickness + width;
        yPoints[0] = y - rY + thickness;
        
        //top left
        xPoints[1] = x + thickness;
        yPoints[1] = y - rY + thickness;
        
        //bottom left
        xPoints[2] = x + thickness;
        yPoints[2] = y + rY - thickness;

        //bottom middle
        xPoints[3] = x + thickness + width;
        yPoints[3] = y + rY - thickness;
        
        //outside
        //bottom middle
        xPoints[4] = x + width + thickness;
        yPoints[4] = y + rY + thickness;
        
        //bottom left
        xPoints[5] = x - thickness;
        yPoints[5] = y + rY + thickness;
        
        //top left
        xPoints[6] = x - thickness;
        yPoints[6] = y - rY - thickness;
        
        //top middle
        xPoints[7] = x + width + thickness;
        yPoints[7] = y - rY - thickness;
        
        arc.setPosition(x + width + thickness, y);
        arc.setScaleX(rX);
        arc.setScaleY(rY);
        arc.setThickness(thickness);
        
        valid = true;
    }
}
