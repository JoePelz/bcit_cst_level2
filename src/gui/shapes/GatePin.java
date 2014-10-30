/**
 * 
 */
package gui.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GatePin extends Gate {

    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GatePin(int x, int y, int thickness) {
        super(x, y, thickness, 1, -1);
        polygons.add(p);
    }

    @Override
    protected void updatePoints() {
        valid = true;
    }

    @Override
    public void drawFill(Graphics2D g) {
        drawStroke(g);
    }
    @Override
    public void drawStroke(Graphics2D g) {
        super.drawStroke(g);
        g.fillOval(x - (int)(1.5 * thickness), y - (int)(1.5 * thickness), thickness * 3, thickness * 3);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getOutput(int)
     */
    @Override
    public Point getOutput(int i) {
        return new Point(x, y);
    }

    /* (non-Javadoc)
     * @see gui.shapes.Gate2#getInput(int)
     */
    @Override
    public Point getInput(int i) {
        return new Point(x, y);
    }

}
