package edgetrigger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class EdgeTriggerGraphic extends JPanel {
    private static final long serialVersionUID = -7578879878352051486L;
    private double phase = 0;

    Rectangle r;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        r = g.getClipBounds();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(Color.LIGHT_GRAY);
        fillNot(g, w(0.275), h(0.27), wd(0.0029), hd(0.011), p(0.01));
        fillAnd(g, w(0.58), h(0.52), wd(0.0029), hd(0.011), p(0.01));
        
        g.setColor(Color.YELLOW);
        drawGlow(g);
        
        g.setColor(Color.BLACK);
        drawCircuit(g);
    }
    
    private void drawGlow(Graphics g) {
        double start;
        double end;
        double shrink;
        //T wire
        start = remap(phase, 0.5, 0.6, 0.005, 0.275);
        end   = remap(phase, 0.0, 0.1, 0.005, 0.275);
        wire(g, w(start), h(0.27), w(end), h(0.27), p(0.05));

        //T, down, right to AND
        start = remap(phase, 0.565, 0.61, 0.27, 0.825);
        end   = remap(phase, 0.065, 0.11, 0.27, 0.825);
        wire(g, w(0.18), h(start), w(0.18), h(end), p(0.05));
        start = remap(phase, 0.61, 0.71, 0.155, 0.58);
        end   = remap(phase, 0.11, 0.21, 0.155, 0.58);
        wire(g, w(start), h(0.75), w(end), h(0.75), p(0.05));
        
        //NOT gate
        if (phase < 0.5) {
            //appearing
            end   = remap(phase, 0.1, 0.3, 0.0, 0.0029);
            fillNot(g, w(0.275), h(0.27), wd(end), hd(0.011), p(0.01));
        } else {
            //disappearing
            start = remap(phase, 0.6, 0.8, 0.275, 0.43);
            end   = remap(phase, 0.6, 0.8, 0.0029, 0.0);
            shrink = remap(phase, 0.6, 0.8, 0.011, 0.0);
            fillNot(g, w(start), h(0.27), wd(end), hd(shrink), p(0.01));
        }

        //wire NOT to AND
        start = remap(phase, 0.3, 0.37, 0.43, 0.58);
        wire(g, w(start), h(0.27), w(0.58), h(0.27), p(0.05));

        start = remap(phase, 0.80, 0.87, 0.43, 0.58);
        wire(g, w(0.43), h(0.27), w(start), h(0.27), p(0.05));
        
        //AND gate
        if (phase < 0.3) {
            //appearing
            end   = remap(phase, 0.21, 0.28, 0.0, 0.0029);
            fillAnd(g, w(0.58), h(0.52), wd(end), hd(0.011), p(0.01));
        } else {
            //disappearing
            start = remap(phase, 0.37, 0.44, 0.58, 0.835);
            end   = remap(phase, 0.37, 0.44, 0.0029, 0.0);
            shrink = remap(phase, 0.37, 0.44, 0.011, 0.006);
            fillAnd(g, w(start), h(0.52), wd(end), hd(shrink), p(0.01));
        }
        
        //wire output
        start = remap(phase, 0.44, 0.5, 0.84, 0.99);
        end   = remap(phase, 0.28, 0.34, 0.84, 0.99);
        wire(g, w(start), h(0.52), w(end), h(0.52), p(0.05));

//        g.setColor(Color.BLACK);
//        start = remap(phase, 0.2, 0.5, 0.1, 0.15);
//        g.drawString("param: " + phase, 0, 10);
    }

    private void drawCircuit(Graphics g) {
        //T wire
        wire(g, w(0.005), h(0.27), w(0.275), h(0.27), p(0.01));
        //T splits
        joint(g, w(0.18), h(0.27), p(0.025));
        //T, down, right to AND
        wire(g, w(0.18), h(0.27), w(0.18), h(0.75), p(0.01));
        wire(g, w(0.18), h(0.75), w(0.58), h(0.75), p(0.01));
        //T into NOT gate
        not(g, w(0.275), h(0.27), wd(0.0029), hd(0.011), p(0.01));
        //wire not to AND
        wire(g, w(0.43), h(0.27), w(0.58), h(0.27), p(0.01));
        //T, T-not into AND gate
        and(g, w(0.58), h(0.52), wd(0.0029), hd(0.011), p(0.01));
        //output wire
        wire(g, w(0.84), h(0.52), w(0.99), h(0.52), p(0.01));
        
        //draw labels
        wire(g, w(0.005), h(0.5), w(0.025), h(0.5), p(0.01));
        wire(g, w(0.025), h(0.4), w(0.025), h(0.5), p(0.01));
        wire(g, w(0.025), h(0.4), w(0.045), h(0.4), p(0.01));
        wire(g, w(0.045), h(0.5), w(0.045), h(0.4), p(0.01));
        wire(g, w(0.045), h(0.5), w(0.065), h(0.5), p(0.01));
        setFont(new Font("Tahoma", Font.BOLD, w(0.05)));
        g.drawString("NOT", w(0.37), h(0.15));
        g.drawString("AND", w(0.62), h(0.57));
    }

    private void fillAnd(Graphics g, int x, int y, double scaleX, double scaleY, int thickness) {
        int rY = (int) (34 * scaleY);
        int rX = (int) (34 * scaleX);
        int width = (int) (56 * scaleX);
        //For the triangle.
        int[] xPoints = new int[8];
        int[] yPoints = new int[8];
        //outside
        xPoints[0] = x;
        yPoints[0] = y - rY;
        
        xPoints[1] = x;
        yPoints[1] = y + rY;
        
        xPoints[2] = x + width;
        yPoints[2] = y + rY;
        
        xPoints[3] = x + width + (int)(rX * 0.707);
        yPoints[3] = y + (int)(rY * 0.707);
        
        xPoints[4] = x + width + rX;
        yPoints[4] = y;
        
        xPoints[5] = x + width + (int)(rX * 0.707);
        yPoints[5] = y - (int)(rY * 0.707);
        
        xPoints[6] = x + width;
        yPoints[6] = y - rY;
        
        xPoints[7] = x;
        yPoints[7] = y - rY;
        
        g.fillPolygon(xPoints, yPoints, 8);
    }
    
    private void and(Graphics g, int x, int y, double scaleX, double scaleY, int thickness) {
        int rY = (int) (34 * scaleY);
        int rX = (int) (34 * scaleX);
        int width = (int) (56 * scaleX);
        
        //For the triangle.
        int[] xPoints = new int[16];
        int[] yPoints = new int[16];
        //outside
        xPoints[0] = x;
        yPoints[0] = y - rY - (thickness << 1);
        
        xPoints[1] = x;
        yPoints[1] = y + rY + (thickness << 1);
        
        xPoints[2] = x + width + (thickness << 1);
        yPoints[2] = y + rY + (thickness << 1);
        
        xPoints[3] = x + width + (int)((rX * 0.707) + (thickness * 1.414));
        yPoints[3] = y + (int)((rY * 0.707) + (thickness * 1.414));
        
        xPoints[4] = x + width + rX + (thickness << 1);
        yPoints[4] = y;
        
        xPoints[5] = x + width + (int)((rX * 0.707) + (thickness * 1.414));
        yPoints[5] = y - (int)((rY * 0.707) + (thickness * 1.414));
        
        xPoints[6] = x + width + (thickness << 1);
        yPoints[6] = y - rY - (thickness << 1);
        
        xPoints[7] = x;
        yPoints[7] = y - rY - (thickness << 1);
        
        //inside
        xPoints[8] = x + (thickness << 1);
        yPoints[8] = y - rY;
        
        xPoints[9] = x + (thickness << 1) + width;
        yPoints[9] = y - rY;
        
        xPoints[10] = x + width + (int)(rX * 0.707);
        yPoints[10] = y - (int)(rY * 0.707);
        
        xPoints[11] = x + width + rX;
        yPoints[11] = y;
        
        xPoints[12] = x + width + (int)(rX * 0.707);
        yPoints[12] = y + (int)(rY * 0.707);
        
        xPoints[13] = x + (thickness << 1) + width;
        yPoints[13] = y + rY;
        
        xPoints[14] = x + (thickness << 1);
        yPoints[14] = y + rY;
        
        xPoints[15] = x + (thickness << 1);
        yPoints[15] = y - rY;
        
        g.fillPolygon(xPoints, yPoints, 16);
    }

    private void fillNot(Graphics g, int x, int y, double scaleX, double scaleY, int thickness) {
        int height = (int) (22 * scaleY);
        int width = (int) (40 * scaleX);
        int rX = (int) (8 * scaleX);
        int rY = (int) (8 * scaleY);
        int jointOffset = width + rX + (thickness << 1);
        
        //For the triangle.
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        //outside
        xPoints[0] = x;
        yPoints[0] = y;
        xPoints[1] = x;
        yPoints[1] = y + height;
        xPoints[2] = x + width;
        yPoints[2] = y;
        xPoints[3] = x;
        yPoints[3] = y - height;
        g.fillPolygon(xPoints, yPoints, 4);

        xPoints[0] = x + width + (thickness << 1);
        yPoints[0] = y;
        xPoints[1] = x + jointOffset;
        yPoints[1] = y + rY;
        xPoints[2] = x + jointOffset + rX;
        yPoints[2] = y;
        xPoints[3] = x + jointOffset;
        yPoints[3] = y - rY;
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    private void not(Graphics g, int x, int y, double scaleX, double scaleY, int thickness) {
        int height = (int) (22 * scaleY);
        int width = (int) (40 * scaleX);
        int rX = (int) (8 * scaleX);
        int rY = (int) (8 * scaleY);
        int jointOffset = width + rX;
        
        //For the triangle.
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        //outside
        xPoints[0] = x;
        yPoints[0] = y;
        xPoints[1] = x;
        yPoints[1] = y + height + (thickness << 1);
        xPoints[2] = x + width + (thickness << 1);
        yPoints[2] = y;
        xPoints[3] = x;
        yPoints[3] = y - height - (thickness << 1);
        xPoints[4] = x;
        yPoints[4] = y;
        //inside
        xPoints[5] = x + (thickness << 1);
        yPoints[5] = y;
        xPoints[6] = x + (thickness << 1);
        yPoints[6] = y + height - (thickness << 1);
        xPoints[7] = x + width - (thickness << 1);
        yPoints[7] = y;
        xPoints[8] = x + (thickness << 1);
        yPoints[8] = y - height + (thickness << 1);
        xPoints[9] = x + (thickness << 1);
        yPoints[9] = y;
        g.fillPolygon(xPoints, yPoints, 10);

        //Sigh.  Square circles.
        wire(g, x + width, y, x + jointOffset, y + rY, thickness);
        wire(g, x + width, y, x + jointOffset, y - rY, thickness);
        wire(g, x + jointOffset + rX, y, x + jointOffset, y + rY, thickness);
        wire(g, x + jointOffset + rX, y, x + jointOffset, y - rY, thickness);
    }
    
    private void wire(Graphics g, int x1, int y1, int x2, int y2, int thickness) {
        /*
         *        .
         *      ,' `, Width
         *    ,'     >
         *   <     ,'
         *    `, ,' Length
         *      `
         */
        Point length = new Point (x2 - x1, y2 - y1);
        double mag = Point.distance(x1, y1, x2, y2);
        
        double wx = ((-length.y / mag) * thickness);
        if (wx > 0.5 && wx < 1)
            wx = 1.01;
        else if (wx < -0.5 && wx > -1)
            wx = -1.01;
        
        double wy = ((length.x / mag) * thickness);
        if (wy > 0.5 && wy < 1)
            wy = 1.01;
        else if (wy < -0.5 && wy > -1)
            wy = -1.01;
        
        Point width = new Point ((int)wx, (int)wy);
        
        //Overlap at the ends by the thickness.
//        x2 += width.y;
//        y2 -= width.x;
//        x1 -= width.y;
//        y1 += width.x;
        
        int[] xPoints = {x1 - width.x, x2 - width.x, x2 + width.x, x1 + width.x};
        int[] yPoints = {y1 - width.y, y2 - width.y, y2 + width.y, y1 + width.y};
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    private void joint(Graphics g, int x, int y, int r) {
        g.fillOval(x - r, y - r, r << 1, r << 1);
    }
    
    private double pd(double d) {
        return (Math.sqrt(r.getWidth() * r.getHeight()) * d);
    }
    private int p(double d) {
        return (int) pd(d);
    }
    private double wd(double p) {
        return (r.getWidth() * p);
    }
    private double hd(double p) {
        return (r.getHeight() * p);
    }
    private int w(double p) {
        return (int) wd(p);
    }
    private int h(double p) {
        return (int) hd(p);
    }
    private double clamp(double d, double min, double max) {
        return (d < min ? min : d > max ? max : d);
    }
    private double remap(double d, double oldMin, double oldMax, double newMin, double newMax) {
        double result = (d - oldMin) / (oldMax - oldMin);
        result *= (newMax - newMin);
        result += newMin;
        return clamp(result, Math.min(newMin, newMax), Math.max(newMin, newMax));
    }
    /**
     * @return the phase
     */
    public double getPhase() {
        return phase;
    }
    /**
     * @param phase the phase to set
     */
    public void setPhase(double phase) {
        if (phase >= 0.0 && phase <= 1.0) {
            this.phase = phase;
        } else {
            throw new IllegalArgumentException("Phase must be between 0 and 1.");
        }
    }
    
}
