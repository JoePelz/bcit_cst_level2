package circuit;


import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateEdgeTrigger;
import gui.shapes.GateInput;
import gui.shapes.GateState;

/**
 * This class is for testing gate and circuit constructions.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class CamTest extends Circuit {
    /** unique id for serialization. */
    private static final long serialVersionUID = 442544548172569392L;
    
    private GateInput circle1;
    private GateInput circle2;
    private GateEdgeTrigger circle3;
    private GateAnd circle4;

    /**
     * Constructor, set default values for gates  
     * and make connections.
     */
    public CamTest() {
        
        circle1 = new GateInput(0,   0, 3, GateState.OFF);
        circle2 = new GateInput(0, 50, 3, GateState.OFF);
        circle3 = new GateEdgeTrigger(20, 50, 3);
        circle4 = new GateAnd(200, 30, 3);
        circle4.setName("CamTest");

        Gate.connect(circle1, -1, circle4, -1);
        Gate.connect(circle2, -1, circle3, -1);
        Gate.connect(circle3, -1, circle4, -1);
        
        gates.add((Gate) circle1);
        gates.add((Gate) circle2);
        gates.add((Gate) circle3);
        gates.add((Gate) circle4);
    }
}
