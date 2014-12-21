/**
 * 
 */
package gui.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This is an Edge Trigger, as used in a flip-flop memory circuit.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class GateEdgeTrigger extends Gate {
    /** List of gates used in the circuit. */
    private ArrayList<Gate> innerGates = new ArrayList<Gate>();

    /** NOT Gate used in Edge Trigger circuit. */
    private GateNot gn;
    /** AND Gate used in Edge Trigger circuit. */
    private GateAnd ga;
    /** Input wire used in Edge Trigger circuit. */
    private GateInput p2;
    /** Wire junction used in Edge Trigger circuit. */
    private GatePin p3;

    /** The current state of the input to this gate. 
     * One of ON, OFF, UNKNOWN. */
    private GateState input = GateState.UNKNOWN;
    /** The previous state of the input from this gate. 
     * One of ON, OFF, UNKNOWN. */
    private GateState oldInput = GateState.UNKNOWN;

    /**
     * Constructor, to create an Edge Trigger at the given location. 
     * 
     * @param x The x position for this gate.
     * @param y The y position for this gate.
     * @param thickness The thickness of the stroke 
     * used to draw this shape.
     */
    public GateEdgeTrigger(int x, int y, int thickness) {
        super(x, y, thickness, 1, 1);

        p2 = new GateInput(x + 0,  y + 0,  thickness, GateState.UNKNOWN);
        p3 = new   GatePin(x + 0,  y + 20, thickness);
        gn = new   GateNot(x + 20, y + 20, thickness);
        ga = new   GateAnd(x + 80, y + 10, thickness);
        ga.setName("EdgeTrigger");

        innerGates.add(p2);
        innerGates.add(p3);
        innerGates.add(ga);
        innerGates.add(gn);

        Gate.connect(p2, -1, p3, -1);
        Gate.connect(p2, -1, ga, -1);
        Gate.connect(p3, -1, gn, -1);
        Gate.connect(gn, -1, ga, -1);
    }

    @Override
    public Point getOutput(int i) {
        return new Point(130, 10);
    }

    @Override
    public Point getInput(int i) {
        return new Point(0, 0);
    }

    @Override
    protected void updatePoints() {
    }
    
    @Override
    public void drawStroke(Graphics2D g) {
        super.drawStroke(g);

        // draw stuff.
        for (Gate gate : innerGates) {
            gate.drawStroke(g);
        }
    }
    
    @Override
    public void drawFill(Graphics2D g) {
        super.drawFill(g);

        // draw stuff.
        for (Gate gate : innerGates) {
            gate.drawFill(g);
        }
    }

    @Override
    public int calcOut() {
        Link linkIn = this.inputPorts[0];
        if (linkIn == null) {
            return 0;
        }
        GateState tempIn = linkIn.getGateOut().getState();
        p2.setState(tempIn);
        //Iterate over circuit
        int iteration = 0;
        int changes = 1;
        while ((iteration < 5) && (changes > 0)) {
            changes = 0;
            for (Gate g : innerGates) {
                if (g instanceof GateInput) {
                    continue;
                }
                changes += g.calcOut();
            }
            iteration++;
        }
        if (iteration == 5) {
            System.out.println("Max iterations reached in GateEdgeTrigger");
        }
        setInput(tempIn);
        return 1;
    }

    /**
     * Get the current input state. One of ON, OFF, UNKNOWN.
     * 
     * @return The state of the input wire for this gate.
     */
    public GateState getInput() {
        return input;
    }

    /**
     * Set the current input state of this gate.
     * 
     * @param input The input state to set.
     */
    private void setInput(GateState input) {
        this.input = input;
    }

    /**
     * Get the previous input state.
     * 
     * @return The previous input state.
     */
    public GateState getOldInput() {
        return oldInput;
    }

    /**
     * Set the previous input state.
     * 
     * @param oldInput the previous input state
     */
    public void setOldInput(GateState oldInput) {
        this.oldInput = oldInput;
    }
}
