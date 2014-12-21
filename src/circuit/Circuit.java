package circuit;

import gui.shapes.Gate;
import gui.shapes.GateEdgeTrigger;
import gui.shapes.GateInput;
import gui.shapes.GateState;

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
 * This class is the base class for all circuits. 
 * It calculates the circuit state and handles mouse interaction. 
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public abstract class Circuit extends JPanel {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = 8065048015230286579L;
    /** Nice fixed-width font to use. */
    private static final Font FONT = new Font("Consolas", Font.PLAIN, 15);
    /** factor to use when zooming in and out. */
    private static final double ZOOM_FACTOR = 1.2;
    /** delay of the edge trigger effect in milliseconds. */
    private static final int EDGE_TRIGGER_DELAY = 250;
    
    /** List of all gates in the circuit. */
    protected ArrayList<Gate> gates = new ArrayList<Gate>();

    /** The positioning of the circuit in the window. */
    private Point translate = new Point();
    /** The previous positioning of the circuit in the window. */
    private Point oldTranslate = new Point();
    /** The position where the mouse was pressed down. */
    private Point mouseStart = new Point();
    /** The zoom level of the circuit. */
    private double scale = 1.0;
    /** The transform of circuit to emulate a camera panning and zooming. */
    private AffineTransform transform = new AffineTransform();
    
    /** Indicates if the circuit is done calculating and is updated. */
    private boolean isCalculated;
    
    /** Special timer to show the action of an Edge Trigger. */
    private Timer edgeTriggerDelay;
    
    /**
     * Constructor, to add listeners for the mouse, 
     * and to setup the timer for EdgeTrigger gates.  
     */
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
                translate = new Point(
                        oldTranslate.x + e.getX() - mouseStart.x, 
                        oldTranslate.y + e.getY() - mouseStart.y);
                updateTransform();
                repaint();
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    translate.x = (int) ((translate.x - e.getX()) / ZOOM_FACTOR) + e.getX();
                    translate.y = (int) ((translate.y - e.getY()) * ZOOM_FACTOR) + e.getY();
                    scale /= ZOOM_FACTOR; 
                } else if (e.getWheelRotation() < 0) {
                    translate.x = (int) ((translate.x - e.getX()) * ZOOM_FACTOR) + e.getX();
                    translate.y = (int) ((translate.y - e.getY()) * ZOOM_FACTOR) + e.getY();
                    scale *= ZOOM_FACTOR;
                }
                oldTranslate = new Point(translate);
                updateTransform();
                repaint();
            }
        });
        
        edgeTriggerDelay = new Timer(EDGE_TRIGGER_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setCalculated(false);
                repaint();
            }
        });
        edgeTriggerDelay.setRepeats(false);
    }

    /**
     * update the transform to reflect the current zoom and position.
     */
    private void updateTransform() {
        //transform is xScale, xShear, yShear, yScale, xPos, yPos
        transform.setTransform(scale, 0, 0, scale, translate.x, translate.y);
    }
    
    /**
     * Get the transform of the circuit, 
     * to emulate camera movement and zoom.
     * 
     * @return The circuit's transform.
     */
    public AffineTransform getTransform() {
        return transform;
        
    }
    

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        if (!isCalculated()) {
            calcCircuit();
        }
        
        Graphics2D g = (Graphics2D) g1;
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.transform(getTransform());
        
        g.setFont(FONT);
        //draw stuff.
        for (Gate gate : gates) {
            gate.drawFill(g);
            gate.drawStroke(g);
        }
    }

    /**
     * Calculate the circuit over a default maximum of 20 iterations.
     */
    public void calcCircuit() {
        final int defaultIterations = 20;
        calcCircuit(defaultIterations);
    }
    
    /**
     * Calculate the circuit state.  
     * This method iterates over every node in the circuit and 
     * attempts to calculate what its state should be. 
     * maxIterations is a safety precaution for infinite loops.
     * 
     * @param maxIterations How many times to try and calculate the circuit before giving up.
     */
    public void calcCircuit(int maxIterations) {
//        System.out.println("=========\n  recalc\n=========");
        //Iterate over circuit
        int iteration = 0;
        int changes = 1;
        boolean hasEdgeTriggers = false;
        while ((iteration < maxIterations) && (changes > 0)) {
            changes = 0;
            for (Gate g : gates) {
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
        
        for (Gate g : gates) {
            if (g instanceof GateEdgeTrigger) {
                GateEdgeTrigger get = (GateEdgeTrigger) g;
                get.setOldInput(get.getInput());
            }
        }
        if (hasEdgeTriggers) {
            edgeTriggerDelay.start();
        }
        
        setCalculated(true);
        
    }
    
    /**
     * Handle a click at the given coordinates.  
     * The click is passed forward to the gate 
     * nearest to the click location.
     * 
     * @param x The mouse x position, world space.
     * @param y The mouse y position, world space.
     */
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
        long dist = (gPos.x - click.x) * (gPos.x - click.x) 
                  + (gPos.y - click.y) * (gPos.y - click.y);
        long closestDist = dist; 
        
        for (Gate g : gates) {
            gPos = g.getPosition();
            dist = (gPos.x - click.x) * (gPos.x - click.x)
                 + (gPos.y - click.y) * (gPos.y - click.y);
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
    
    /**
     * Focus the view on the circuit so that it 
     * neatly fits inside the panel.
     */
    public void focusView() {
        final int margin = 10;
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
            
            destTL.setLocation(Math.min(gateTL.x, destTL.x), Math.min(gateTL.y, destTL.y));
            destBR.setLocation(Math.max(gateBR.x, destBR.x), Math.max(gateBR.y, destBR.y));
        }
        
        //Add a margin for visual niceness.
        destTL.x -= margin;
        destTL.y -= margin;
        destBR.x += margin;
        destBR.y += margin;
        
        size = getSize();
        
        double scaleW = ((double) size.width  / (destBR.x - destTL.x));
        double scaleH = ((double) size.height / (destBR.y - destTL.y));
        if (scaleW < scaleH) {
            scale = scaleW;
            translate = new Point(
                    (int) (-destTL.x * scale), 
                    ((int) (-destTL.y * scale) + (int) (-destBR.y * scale) + size.height) / 2);
            oldTranslate = new Point(translate);
        } else {
            scale = scaleH;
            translate = new Point(
                    ((int) (-destTL.x * scale) + (int) (-destBR.x * scale) + size.width) / 2,
                    (int) (-destTL.y * scale)); 
            oldTranslate = new Point(translate);
            
        }
        
        updateTransform();
        repaint();
    }

    /**
     * Check if the circuit is in a fully calculated state.
     * 
     * @return true if the circuit is calculated.
     */
    public boolean isCalculated() {
        return isCalculated;
    }

    /**
     * Tell the circuit that it hasn't or is already calculated.
     * 
     * @param calculated False if the circuit 
     *                     needs to be recalculated
     */
    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }
}
