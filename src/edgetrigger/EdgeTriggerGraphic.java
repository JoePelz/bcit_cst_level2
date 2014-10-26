package edgetrigger;

import gui.shapes.GateAnd;
import gui.shapes.GateNot;

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
    GateAnd ga = new GateAnd(50, 50, 2);
    GateAnd gaFILL = new GateAnd(50, 50, 2);
    GateNot gn = new GateNot(100, 100, 2);
    GateNot gnFILL = new GateNot(100, 100, 2);
            
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        r = g.getClipBounds();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        ga.setPosition(w(0.58), h(0.52));
        ga.setThickness(p(0.01));
        ga.setScaleX(wd(0.0029));
        ga.setScaleY(hd(0.011));
        gaFILL.setThickness(p(0.01));

        gn.setPosition(w(0.275), h(0.27));
        gn.setThickness(p(0.01));
        gn.setScaleX(wd(0.0031));
        gn.setScaleY(hd(0.0110));
        gnFILL.setThickness(p(0.01));

        g.setColor(Color.LIGHT_GRAY);
        gn.drawFill(g);
        ga.drawFill(g);
        
        g.setColor(Color.YELLOW);
        drawGlow(g);
        
        g.setColor(Color.BLACK);
        drawCircuit(g);

        //input bit
        g.drawString((0.00 < phase && phase < 0.50) ? "1" : "0", w(0), h(0.18));
        //output but
        g.drawString((0.34 < phase && phase < 0.45) ? "1" : "0", w(0.95), h(0.38));
        //bottom AND input
        g.drawString((0.21 < phase && phase < 0.71) ? "1" : "0", w(0.6), h(0.80));
        //top AND input
        g.drawString((0.32 < phase && phase < 0.82) ? "0" : "1", w(0.6), h(0.35));
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
            end   = remap(phase, 0.1, 0.25, 0.0, 0.0031);
            gnFILL.setPosition(w(0.275), h(0.27));
            gnFILL.setScaleX(wd(end));
            gnFILL.setScaleY(hd(0.0110));
        } else {
            //disappearing
            start  = remap(phase, 0.6, 0.75, 0.275, 0.43);
            end    = remap(phase, 0.6, 0.75, 0.0031, 0.0);
            shrink = remap(phase, 0.6, 0.75, 0.011, 0.0);
            gnFILL.setPosition(w(start), h(0.27));
            gnFILL.setScaleX(wd(end));
            gnFILL.setScaleY(hd(shrink));
        }
        gnFILL.drawFill(g);

        //wire NOT to AND
        start = remap(phase, 0.25, 0.32, 0.43, 0.58);
        wire(g, w(start), h(0.27), w(0.58), h(0.27), p(0.05));

        start = remap(phase, 0.75, 0.82, 0.43, 0.58);
        wire(g, w(0.43), h(0.27), w(start), h(0.27), p(0.05));
        
        //AND gate
        if (phase < 0.3) {
            //appearing
            end   = remap(phase, 0.21, 0.28, 0.0, 0.0029);
            gaFILL.setPosition(w(0.58), h(0.52));
            gaFILL.setScaleY(hd(0.011));
            gaFILL.setScaleX(wd(end));
        } else {
            //disappearing
            start  = remap(phase, 0.32, 0.39, 0.58, 0.835);
            end    = remap(phase, 0.32, 0.39, 0.0029, 0.0);
            shrink = remap(phase, 0.32, 0.39, 0.011, 0.006);
            gaFILL.setPosition(w(start), h(0.52));
            gaFILL.setScaleY(hd(shrink));
            gaFILL.setScaleX(wd(end));
        }
        gaFILL.drawFill(g);
        
        //wire output
        start = remap(phase, 0.39, 0.45, 0.84, 0.99);
        end   = remap(phase, 0.28, 0.34, 0.84, 0.99);
        wire(g, w(start), h(0.52), w(end), h(0.52), p(0.05));

//        g.setColor(Color.BLACK);
//        start = remap(phase, 0.2, 0.5, 0.1, 0.15);
//        g.drawString("param: " + phase, 0, 10);
    }

    private void drawCircuit(Graphics g) {
        int thickness = p(0.01);
        //T wire
        wire(g, w(0.005), h(0.27), w(0.275), h(0.27),thickness);
        //T splits
        joint(g, w(0.18), h(0.27), p(0.025));
        //T, down, right to AND
        wire(g, w(0.18), h(0.27), w(0.18), h(0.75), thickness);
        wire(g, w(0.18) - thickness, h(0.75), w(0.58), h(0.75), thickness);
        //T into NOT gate
        gn.drawStroke(g);
        //wire not to AND
        wire(g, w(0.43), h(0.27), w(0.58), h(0.27), thickness);
        //T, T-not into AND gate
        ga.drawStroke(g);
        //output wire
        wire(g, w(0.84), h(0.52), w(0.99), h(0.52), thickness);
        
        //draw clock label
        wire(g, w(0.005),             h(0.5), w(0.025) + thickness, h(0.5), thickness); //left tip
        wire(g, w(0.025),             h(0.4), w(0.025),             h(0.5), thickness); //go up
        wire(g, w(0.025) - thickness, h(0.4), w(0.045) + thickness, h(0.4), thickness); //go right
        wire(g, w(0.045),             h(0.5), w(0.045),             h(0.4), thickness); //go down
        wire(g, w(0.045) - thickness, h(0.5), w(0.065),             h(0.5), thickness); //right tip
        
        setFont(new Font("Tahoma", Font.BOLD, w(0.05)));
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
