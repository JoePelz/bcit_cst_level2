package circuit;

import gui.shapes.Gate;
import gui.shapes.GateEdgeTrigger;
import gui.shapes.GateInput;
import gui.shapes.GateState;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Circuit extends JPanel {
    private static final long serialVersionUID = 8065048015230286579L;

    private static Font font = new Font("Consolas", Font.PLAIN, 15);
    
    protected ArrayList<Gate> gates = new ArrayList<Gate>();

    private Point translate = new Point();
    private Point oldTranslate = new Point();
    private Point mouseStart = new Point();
    private double scale = 1.0;
    private AffineTransform transform = new AffineTransform();
    
    private boolean isCalculated;
    
    private Timer EdgeTriggerDelay;
    
    public Circuit() {
        //add keyboard listener that will let you press F and 
        //center the view on the shapes 
        // (b-boxes touching the screen edges)
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    focusView();
                    return;
                }
                Circuit.this.click(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseStart = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                oldTranslate = new Point(translate);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                translate = new Point(oldTranslate.x + e.getX() - mouseStart.x, oldTranslate.y + e.getY() - mouseStart.y);
                updateTransform();
                repaint();
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    translate.x = (int)((translate.x - e.getX()) * 0.8) + e.getX();
                    translate.y = (int)((translate.y - e.getY()) * 0.8) + e.getY();
                    scale *= 0.8; 
                } else if (e.getWheelRotation() < 0) {
                    translate.x = (int)((translate.x - e.getX()) * 1.2) + e.getX();
                    translate.y = (int)((translate.y - e.getY()) * 1.2) + e.getY();
                    scale *= 1.2;
                }
                oldTranslate = new Point(translate);
                updateTransform();
                repaint();
            }
        });
        
        EdgeTriggerDelay = new Timer(250, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                setCalculated(false);
                repaint();
            }
        });
        EdgeTriggerDelay.setRepeats(false);
    }

    private void updateTransform() {
        //transform is xScale, xShear, yShear, yScale, xPos, yPos
        transform.setTransform(scale, 0, 0, scale, translate.x, translate.y);
    }
    
    public AffineTransform getTransform() {
        return transform;
        
    }
    

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        if (!isCalculated()) {
            calcCircuit(20);
        }
        
        Graphics2D g = (Graphics2D) g1;
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.transform(getTransform());
        
        g.setFont(font);
        //draw stuff.
        for (Gate gate : gates) {
            gate.drawFill(g);
            gate.drawStroke(g);
        }
    }

    public void calcCircuit() {
        calcCircuit(20);
    }
    
    public void calcCircuit(int maxIterations) {
//        System.out.println("=========\n  recalc\n=========");
        //Iterate over circuit
        int iteration = 0;
        int changes = 1;
        boolean hasEdgeTriggers = false;
        while ((iteration < maxIterations) && (changes > 0)) {
            changes = 0;
            for(Gate g : gates) {
                if (g instanceof GateInput) {
                    continue;
                }
//                System.out.println("Working on " + g.toString());
                changes += g.calcOut();
                
                if (g instanceof GateEdgeTrigger) {
                    GateEdgeTrigger get = (GateEdgeTrigger) g;
                    if (get.getInput() == GateState.ON  
                            && get.getInput() != get.getOldInput()) {
                        get.setState(GateState.ON);
                        hasEdgeTriggers = true;
                    } else if (get.getInput() == get.getOldInput()) {
                        get.setState(GateState.OFF);
                        hasEdgeTriggers = false;
                    }
                }
            }
            iteration++;
        }
//        if (iteration == maxIterations) {
//            System.out.println("Settling timed out at " + iteration + " iterations.");
//        } else {
//            System.out.println("Settling took " + iteration + " iterations.");
//        }
        
        for(Gate g : gates) {
            if (g instanceof GateEdgeTrigger) {
                GateEdgeTrigger get = (GateEdgeTrigger) g;
                get.setOldInput(get.getInput());
            }
        }
        if (hasEdgeTriggers) {
            EdgeTriggerDelay.start();
        }
        
        setCalculated(true);
        
    }
    
    protected void wire(Graphics2D g, int x1, int y1, int x2, int y2, int thickness) {
        g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g.drawLine(x1, y1, x2, y2);
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
        Point click = new Point(x, y);
        if (gates.size() == 0) {
            return;
        }
        Gate closest = gates.get(0);
        Point gPos = closest.getPosition();
        AffineTransform at;
        try {
            at = getTransform().createInverse();
        } catch (NoninvertibleTransformException e) {
            System.err.println("Couldn't invert the world transform?!");
            return;
        }
        at.transform(click, click);
        long dist = (gPos.x - click.x) * (gPos.x - click.x) + (gPos.y - click.y) * (gPos.y - click.y);
        long closestDist = dist; 
        
        for (Gate g : gates) {
            gPos = g.getPosition();
            dist = (gPos.x - click.x) * (gPos.x - click.x) + (gPos.y - click.y) * (gPos.y - click.y);
            if (dist < closestDist) {
                closestDist = dist;
                closest = g;
            }
        }
//        System.out.printf("Clicked at {%d, %d} near a %s\n", click.x, click.y, closest);
        closest.perform(x, y);
        setCalculated(false);
        repaint();
    }
    
    public void focusView() {
        Dimension size;
        Rectangle bounds;
        AffineTransform at;
        Point gateTL = new Point();
        Point gateBR = new Point();
        Point destTL = new Point();
        Point destBR = new Point();
        if (gates.size() == 0) {
            translate.setLocation(0, 0);
            scale = 1.0;
            return;
        }
        
        bounds = gates.get(0).getBounds();
        destTL.setLocation(bounds.x, bounds.y);
        destBR.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
        at = gates.get(0).getTransform();
        at.transform(destTL, destTL);
        at.transform(destBR, destBR);
        
        for (Gate g : gates) {
            bounds = g.getBounds();
            gateTL.setLocation(bounds.x, bounds.y);
            gateBR.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
            at = g.getTransform();
            at.transform(gateTL, gateTL);
            at.transform(gateBR, gateBR);
            
//            System.out.printf("The gate bounds are LEFT %3d,\tTOP %3d,\tRIGHT %3d,\tBOTTOM %3d\n", gateTL.x, gateTL.y, gateBR.x, gateBR.y);
            destTL.setLocation(Math.min(gateTL.x, destTL.x), Math.min(gateTL.y, destTL.y));
            destBR.setLocation(Math.max(gateBR.x, destBR.x), Math.max(gateBR.y, destBR.y));
        }
//        System.out.printf("The Final Bounds\n are LEFT %3d,\tTOP %3d,\tRIGHT %3d,\tBOTTOM %3d\n", destTL.x, destTL.y, destBR.x, destBR.y);
        
        //Add a margin for visual niceness.
        destTL.x -= 10;
        destTL.y -= 10;
        destBR.x += 10;
        destBR.y += 10;
        
        size = getSize();
        
        double scaleW = ((double)size.width  / (destBR.x - destTL.x));
        double scaleH = ((double)size.height / (destBR.y - destTL.y));
        if (scaleW < scaleH) {
            scale = scaleW;
            translate = new Point(
                    (int)(-destTL.x * scale), 
                    ((int)(-destTL.y * scale) + (int)(-destBR.y * scale) + size.height) / 2);
            oldTranslate = new Point(translate);
        } else {
            scale = scaleH;
            translate = new Point(
                    ((int)(-destTL.x * scale) + (int)(-destBR.x * scale) + size.width) / 2,
                    (int)(-destTL.y * scale)); 
            oldTranslate = new Point(translate);
            
        }
        
        updateTransform();
        repaint();
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean isCalculated) {
        this.isCalculated = isCalculated;
    }
}
