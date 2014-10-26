/**
 * 
 */
package gui.shapes;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateOr extends Gate {
    Arc arcLeft;
    Arc arcBottom;
    Arc arcTop;
    
    public GateOr(int x, int y, int thickness) {
        super(x, y, thickness);

        arcTop = new Arc(x, y, 1, 0, 1, thickness);
        arcTop.setSubdivisions(12);
        addShape(arcTop);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Shape#updatePoints()
     */
    @Override
    protected void updatePoints() {
        int rY = (int) (34 * scaleY);
        int rX = (int) (34 * scaleX);
        
        arcTop.setPosition(x + thickness, y);
        arcTop.setScaleX(rX);
        arcTop.setScaleY(rY);
        arcTop.setThickness(thickness);

    }

}
