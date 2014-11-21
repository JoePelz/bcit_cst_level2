/**
 * 
 */
package gui.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class GateEdgeTrigger extends Gate {
    
    private ArrayList<Gate> innerGates = new ArrayList<Gate>();
    
    private GateNot gn;
    private GateAnd ga;
    private GateInput p2;
    private GatePin p3;

    private GateState oldState = GateState.UNKNOWN;
    private GateState input = GateState.UNKNOWN;
    private GateState oldInput = GateState.UNKNOWN;

    /**
     * @param x
     * @param y
     * @param thickness
     */
    public GateEdgeTrigger(int x, int y, int thickness) {
        super(x, y, thickness, 1, 1);

        p2 = new GateInput(x+0, y+0, thickness, GateState.UNKNOWN);
        p3 = new GatePin(x+0, y+20, thickness);
        gn = new GateNot(x+20, y+20, thickness);
        ga = new GateAnd(x+80, y+10, thickness);
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

    /* (non-Javadoc)
     * @see gui.shapes.Gate#getOutput(int)
     */
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
        for (Gate gate : innerGates ) {
            gate.drawStroke(g);
        }
    }
    
    @Override
    public void drawFill(Graphics2D g) {
        super.drawFill(g);

        // draw stuff.
        for (Gate gate : innerGates ) {
            gate.drawFill(g);
        }
    }

    @Override
    public int calcOut() {
        Link linkIn = this.inputPorts[0];
        if (linkIn == null) {
            return 0;
        }
        GateState input = linkIn.getGateOut().getState();
        p2.setState(input);
        //Iterate over circuit
        int iteration = 0;
        int changes = 1;
        while ((iteration < 5) && (changes > 0)) {
            changes = 0;
            for(Gate g : innerGates) {
                if (g instanceof GateInput) {
                    continue;
                }
                changes += g.calcOut();
            }
            iteration++;
        }
        if (iteration == 5)
            System.out.println("Max iterations reached in GateEdgeTrigger");
        
        setInput(input);
        return 1;
    }

    public GateState getOldState() {
        return oldState;
    }

    public void setOldState(GateState oldState) {
        this.oldState = oldState;
    }

    public GateState getInput() {
        return input;
    }

    public void setInput(GateState input) {
        this.input = input;
    }

    public GateState getOldInput() {
        return oldInput;
    }

    public void setOldInput(GateState oldInput) {
        this.oldInput = oldInput;
    }
}
