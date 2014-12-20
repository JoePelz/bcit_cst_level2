package edgetrigger;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateNot;
import gui.shapes.GatePin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * This class represents a graphic of an Edge Trigger that
 * can be animated by controlling the phase variable.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class EdgeTriggerGraphic extends JPanel {
    /** unique ID for serialization. */
    private static final long serialVersionUID = -7578879378352051486L;
    /** The current phase of the hard-coded animation. */
    private double phase = 0;
    
    /** Rectangle to hold the clipping bounds of the panel. */
    private Rectangle r;
    /** AND gate. */
    private GateAnd ga = new GateAnd(50, 50, 2);
    /** AND gate. (yellow filling) */
    private GateAnd gaFILL = new GateAnd(50, 50, 2);
    /** NOT gate. */
    private GateNot gn = new GateNot(100, 100, 2);
    /** NOT gate. (yellow filling) */
    private GateNot gnFILL = new GateNot(100, 100, 2);
    /** The higher of the two joints in the wires. */
    private GatePin n1 = new GatePin(0, 0, 2);
    /** The lower of the two joints in the wires. */
    private GatePin n2 = new GatePin(0, 0, 2);
    /** The input pin. */
    private GatePin nStart = new GatePin(0, 0, 2);
    /** The output pin. */
    private GatePin nEnd = new GatePin(0, 0, 2);
    
    /**
     * Constructor, Sets some default colors. 
     *
     */
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
    
    /**
     * Draw labels onto the diagram, 
     * including 1s, 0s, and a clock icon.
     * 
     * @param g The graphics context to draw into.
     */
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

    /**
     * Draw the yellow glow of activated wires and gates. This varies with the value of {@code phase}.
     * 
     * @param g The graphics context to draw into.
     */
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

    /**
     * Draw the entire circuit, all black and grey.
     * 
     * @param g The graphics context to draw into.
     */
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

        gn.drawStroke(g);
        ga.drawStroke(g);
        nStart.drawStroke(g);
        n1.drawStroke(g);
        n2.drawStroke(g);
        nEnd.drawStroke(g);
    }

    /**
     * Draw a wire. (a rectangle line.)
     * 
     * @param g The graphics context to draw into.
     * @param x1 The starting x position
     * @param y1 The starting y position
     * @param x2 The ending x position
     * @param y2 The ending y position
     * @param thickness The thickness of the wire to draw
     */
    private void wire(Graphics2D g, int x1, int y1, int x2, int y2, int thickness) {
        g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g.drawLine(x1, y1, x2, y2);
    }    
    
    /**
     * Get a double, proportional to the diagonal of the canvas.
     * 
     * @param d The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the diagonal length of the canvas.
     */
    private double pd(double d) {
        return (Math.sqrt(r.getWidth() * r.getHeight()) * d);
    }
    /**
     * Get a integer, proportional to the diagonal of the canvas.
     * 
     * @param d The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the diagonal length of the canvas.
     */
    private int p(double d) {
        return (int) pd(d);
    }
    /**
     * Get a double, proportional to the width of the canvas.
     * 
     * @param p The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the width of the canvas.
     */
    private double wd(double p) {
        return (r.getWidth() * p);
    }
    /**
     * Get a double, proportional to the height of the canvas.
     * 
     * @param p The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the height of the canvas.
     */
    private double hd(double p) {
        return (r.getHeight() * p);
    }
    /**
     * Get an integer, proportional to the width of the canvas.
     * 
     * @param p The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the width of the canvas.
     */
    private int w(double p) {
        return (int) wd(p);
    }
    /**
     * Get an integer, proportional to the width of the canvas.
     * 
     * @param p The value to interpret proportionally (generally between 0 and 1)
     * @return The input value, proportional to the height of the canvas.
     */
    private int h(double p) {
        return (int) hd(p);
    }
    /**
     * Clamp a given value within the given ranges.
     * 
     * @param d The value to clamp
     * @param min The minimum value for d to take on.
     * @param max The maximum value for d to take on.
     * @return The value of d within the given min/max range.
     */
    private double clamp(double d, double min, double max) {
        return (d < min ? min : d > max ? max : d);
    }
    
    /**
     * Convert a value proportionally from one min/max 
     * to another min/max.
     * 
     * @param d
     *            The value to reinterpret.
     * @param oldMin
     *            The old minimum for d
     * @param oldMax
     *            The old maximum for d
     * @param newMin
     *            The new minimum for d
     * @param newMax
     *            The new maximum for d
     * @return The reinterpreted value of d, proportionally 
     *         transfered from the old range to the new range.
     */
    private double remap(double d, double oldMin, double oldMax, double newMin, double newMax) {
        double result = (d - oldMin) / (oldMax - oldMin);
        result *= (newMax - newMin);
        result += newMin;
        return clamp(result, Math.min(newMin, newMax), Math.max(newMin, newMax));
    }
    
    /**
     * Get the current value of the phase.
     * @return the phase
     */
    public double getPhase() {
        return phase;
    }
    
    /**
     * Set the phase (between 0 and 1 inclusive)
     * @param phase the phase to set (0 to 1 inclusive)
     */
    public void setPhase(double phase) {
        if (phase >= 0.0 && phase <= 1.0) {
            this.phase = phase;
        } else {
            throw new IllegalArgumentException("Phase must be between 0 and 1.");
        }
    }
    
}
