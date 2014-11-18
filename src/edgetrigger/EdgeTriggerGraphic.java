package edgetrigger;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateNot;
import gui.shapes.GatePin;
import gui.shapes.Shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class EdgeTriggerGraphic extends JPanel {
    private static final long serialVersionUID = -7578879378352051486L;
    private double phase = 0;
    
    private Rectangle r;
    private GateAnd ga = new GateAnd(50, 50, 2);
    private GateAnd gaFILL = new GateAnd(50, 50, 2);
    private GateNot gn = new GateNot(100, 100, 2);
    private GateNot gnFILL = new GateNot(100, 100, 2);
    private GatePin n1 = new GatePin(0, 0, 2);
    private GatePin n2 = new GatePin(0, 0, 2);
    private GatePin nStart = new GatePin(0, 0, 2);
    private GatePin nEnd = new GatePin(0, 0, 2);
    
    public EdgeTriggerGraphic() {
        gnFILL.setBackgroundOff(Color.YELLOW);
        gaFILL.setBackgroundOff(Color.YELLOW);
    }
            
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        r = g.getClipBounds();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        

        nStart.setPosition(w(0.005), h(0.333));
        n1.setPosition(w(0.18), h(0.333));
        n2.setPosition(w(0.18), h(0.708));
        nEnd.setPosition(w(0.99), h(0.52));
        

        //Create the two gates
        int thickness = p(0.02);
        ga.setPosition(w(0.58), h(0.52));
        ga.setThickness(thickness);
        ga.setScaleX(wd(0.0005));
        ga.setScaleY(hd(0.00185));
        gaFILL.setThickness(thickness);

        gn.setPosition(w(0.275), h(0.333));
        gn.setThickness(thickness);
        gn.setScaleX(wd(0.00044));
        gn.setScaleY(hd(0.0014));
        gnFILL.setThickness(thickness);
        
        //Link them all
        Gate.connect(nStart, -1, n1, -1);
        Gate.connect(n1, -1, n2, -1);
        Gate.connect(n1, 0, gn, 0);
        Gate.connect(gn, 0, ga, -1);
        Gate.connect(n2, 0, ga, -1);
        Gate.connect(ga, 0, nEnd, 0);

        gn.drawFill(g2);
        ga.drawFill(g2);
        
        drawGlow(g2);
        drawCircuit(g2);

        drawLabels(g2);
    }
    
    private void drawLabels(Graphics2D g) {
        setFont(new Font("Tahoma", Font.BOLD, w(0.05)));
        //input bit
        g.drawString((0.00 < phase && phase < 0.50) ? "1" : "0", w(0), h(0.18));
        //output but
        g.drawString((0.34 < phase && phase < 0.45) ? "1" : "0", w(0.95), h(0.38));
        //bottom AND input
        g.drawString((0.21 < phase && phase < 0.71) ? "1" : "0", w(0.6), h(0.80));
        //top AND input
        g.drawString((0.32 < phase && phase < 0.82) ? "0" : "1", w(0.6), h(0.35));   

        //draw clock label
        int thickness = p(0.01);
        int xPoints[] = new int[6];
        int yPoints[] = new int[6];
        xPoints[0] = w(0.005);
        yPoints[0] = h(0.55);

        xPoints[1] = w(0.025);
        yPoints[1] = h(0.55);

        xPoints[2] = w(0.025);
        yPoints[2] = h(0.45);
        
        xPoints[3] = w(0.045);
        yPoints[3] = h(0.45);
        
        xPoints[4] = w(0.045);
        yPoints[4] = h(0.55);
        
        xPoints[5] = w(0.065);
        yPoints[5] = h(0.55);
        g.setStroke(new BasicStroke(thickness));
        g.drawPolyline(xPoints, yPoints, 6);
    }

    private void drawGlow(Graphics2D g) {
        double start;
        double end;
        double shrink;

        //NOT gate
        if (phase < 0.5) {
            //appearing
            end   = remap(phase, 0.1, 0.25, 0.0, 0.00044);
            gnFILL.setPosition(w(0.275), h(0.333));
            gnFILL.setScaleX(wd(end));
            gnFILL.setScaleY(hd(0.0014));
        } else {
            //disappearing
            start  = remap(phase, 0.6, 0.75, 0.275, 0.44);
            end    = remap(phase, 0.6, 0.75, 0.00044, 0.0);
            shrink = remap(phase, 0.6, 0.75, 0.0014, 0.0);
            gnFILL.setPosition(w(start), h(0.333));
            gnFILL.setScaleX(wd(end));
            gnFILL.setScaleY(hd(shrink));
        }
        gnFILL.drawFill(g);

        //AND gate
        if (phase < 0.3) {
            //appearing
            end   = remap(phase, 0.21, 0.28, 0.0, 0.0005);
            gaFILL.setPosition(w(0.58), h(0.52));
            gaFILL.setScaleY(hd(0.00185));
            gaFILL.setScaleX(wd(end));
        } else {
            //disappearing
            start  = remap(phase, 0.32, 0.39, 0.58, 0.835);
            end    = remap(phase, 0.32, 0.39, 0.0005, 0.0);
            shrink = remap(phase, 0.32, 0.39, 0.00185, 0.0001);
            gaFILL.setPosition(w(start), h(0.52));
            gaFILL.setScaleY(hd(shrink));
            gaFILL.setScaleX(wd(end));
        }
        gaFILL.drawFill(g);

        g.setColor(Color.YELLOW);
        //T wire
        start = remap(phase, 0.5, 0.6, 0.005, 0.275);
        end   = remap(phase, 0.0, 0.1, 0.005, 0.275);
        wire(g, w(start), h(0.333), w(end), h(0.333), p(0.05));

        //T, down, right to AND
        start = remap(phase, 0.565, 0.61, 0.333, 0.708);
        end   = remap(phase, 0.065, 0.11, 0.333, 0.708);
        wire(g, w(0.18), h(start), w(0.18), h(end), p(0.05));
        start = remap(phase, 0.61, 0.71, 0.18, 0.58);
        end   = remap(phase, 0.11, 0.21, 0.18, 0.58);
        wire(g, w(start), h(0.708), w(end), h(0.708), p(0.05));
        

        //wire NOT to AND
        //disappearing
        start = remap(phase, 0.25, 0.32, 0.44, 0.58);
        wire(g, w(start), h(0.333), w(0.58), h(0.333), p(0.05));
        //appearing
        start = remap(phase, 0.75, 0.82, 0.44, 0.58);
        wire(g, w(0.44), h(0.333), w(start), h(0.333), p(0.05));
        
        //wire output
        start = remap(phase, 0.39, 0.45, 0.84, 0.99);
        end   = remap(phase, 0.28, 0.34, 0.84, 0.99);
        wire(g, w(start), h(0.52), w(end), h(0.52), p(0.05));        
    }

    private void drawCircuit(Graphics2D g) {
        //T wire
        //T splits
        //T, down, right to AND
        //T into NOT gate
        //wire not to AND
        //T, T-not into AND gate
        //output wire
        

        int thickness = p(0.015);
        
        nStart.setThickness(thickness);
        n1.setThickness(thickness);
        n2.setThickness(thickness);
        nEnd.setThickness(thickness);

//        l1.setThickness(wireThickness);
//        l2.setThickness(wireThickness);
//        l3.setThickness(wireThickness);
//        l4.setThickness(wireThickness);
//        l5.setThickness(wireThickness);
//        l6.setThickness(wireThickness);

        gn.drawStroke(g);
        ga.drawStroke(g);
        nStart.drawStroke(g);
        n1.drawStroke(g);
        n2.drawStroke(g);
        nEnd.drawStroke(g);
//        l1.drawStroke(g);
//        l2.drawStroke(g);
//        l3.drawStroke(g);
//        l4.drawStroke(g);
//        l5.drawStroke(g);
//        l6.drawStroke(g);
    }

    private void wire(Graphics2D g, int x1, int y1, int x2, int y2, int thickness) {
        g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g.drawLine(x1, y1, x2, y2);
    }

    public void sketchShape(Shape shape, Graphics2D g, int size) {
        Polygon p = shape.getPolygon();
        g.setColor(Color.BLACK);
        for (int i = 0; i < p.npoints; i++) {
            System.out.printf("point [ %d\t%d ]\n", p.xpoints[i], p.ypoints[i]);
            g.fillOval(p.xpoints[i] - size, p.ypoints[i] - size, size * 2, size * 2);
        }
    }
    
    public void sketchShape(Gate shape, Graphics2D g, int size) {
        g.setColor(Color.BLACK);
        for (Polygon p : shape.getPolygons()) {
            for (int i = 0; i < p.npoints; i++) {
                System.out.printf("point [ %d\t%d ]\n", p.xpoints[i], p.ypoints[i]);
                g.fillOval(p.xpoints[i] - size, p.ypoints[i] - size, size * 2, size * 2);
            }
        }
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
