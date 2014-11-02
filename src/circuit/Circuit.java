/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateInput;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Circuit extends JPanel {
    private static final long serialVersionUID = 8065048015230286579L;
    private Rectangle r;
    
    protected ArrayList<Gate> gates = new ArrayList<Gate>();
    
    public Circuit() {
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Circuit.this.click(e.getX(), e.getY());
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        r = g.getClipBounds();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void calcCircuit() {
        calcCircuit(10);
    }
    
    public void calcCircuit(int maxIterations) {
        //reset circuit.
//        for(Gate g : gates) {
//            if (g instanceof GateInput) {
//                continue;
//            }
//            g.setState(GateState.NULL);
//        }
        //Iterate over circuit
        int iteration = 0;
        int changes = 1;
        while ((iteration < maxIterations) && (changes > 0)) {
            changes = 0;
            for(Gate g : gates) {
                if (g instanceof GateInput) {
                    continue;
                }
                changes += g.calcOut();
            }
            iteration++;
        }
//        System.out.println("Settling took " + iteration + " iterations.");
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
    
    public void click(int x, int y) {
        Gate closest = gates.get(0);
        Point gPos = closest.getPosition();
        long dist = (gPos.x - x) * (gPos.x - x) + (gPos.y - y) * (gPos.y - y);
        long closestDist = dist; 
        
        for (Gate g : gates) {
            gPos = g.getPosition();
            dist = (gPos.x - x) * (gPos.x - x) + (gPos.y - y) * (gPos.y - y);
            if (dist < closestDist) {
                closestDist = dist;
                closest = g;
            }
        }
//        System.out.printf("Clicked at {%d, %d} near a %s\n", x, y, closest);
        closest.perform(x, y);
        calcCircuit();
        repaint();
    }
}
