/**
 * 
 */
package circuit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class CircuitPanel extends JPanel {
    private static final long serialVersionUID = -1712387296868793108L;

//    private EdgeTriggerGraphic graphic;
    private Circuit circuit;
    
    public CircuitPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //Add an image panel
        addGraphicsPanel();
    }
    
    private void addGraphicsPanel() {
//        circuit = new DLatch();
        circuit = new SRLatch();
//        circuit = new HalfAdder();
//        circuit = new CamTest();
        add(circuit);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circuits, by Joe Pelz, Fall 2014");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException 
                | InstantiationException
                | IllegalAccessException 
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        CircuitPanel etp = new CircuitPanel();

        frame.add(etp);
        frame.pack();
        frame.setVisible(true);
        etp.circuit.focusView();
    }

}
