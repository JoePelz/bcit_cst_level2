/**
 * 
 */
package circuit;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Circuit extends JPanel {
    private static final long serialVersionUID = 8065048015230286579L;
    private Rectangle r;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        r = g.getClipBounds();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        
    }
    
    protected void wire(Graphics2D g, int x1, int y1, int x2, int y2, int thickness) {
        g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g.drawLine(x1, y1, x2, y2);
    }
    
    protected double pd(double d) {
        return (Math.sqrt(r.getWidth() * r.getHeight()) * d);
    }
    protected int p(double d) {
        return (int) pd(d);
    }
    protected double wd(double p) {
        return (r.getWidth() * p);
    }
    protected double hd(double p) {
        return (r.getHeight() * p);
    }
    protected int w(double p) {
        return (int) wd(p);
    }
    protected int h(double p) {
        return (int) hd(p);
    }
    public static double clamp(double d, double min, double max) {
        return (d < min ? min : d > max ? max : d);
    }
    
    public static double remap(double d, double oldMin, double oldMax, double newMin, double newMax) {
        double result = (d - oldMin) / (oldMax - oldMin);
        result *= (newMax - newMin);
        result += newMin;
        return clamp(result, Math.min(newMin, newMax), Math.max(newMin, newMax));
    }
}
