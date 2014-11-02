/**
 * 
 */
package circuit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

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
        //needs drop-down on top to choose number of variables (1, 2, 3 or 4)
        //left side has text field
        //left side has buttons for UNION / INTERSECTION / NOT
        //right side is dynamic illustration
        //will need to be able to parse the content of the text field.

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //Add an image panel
        addGraphicsPanel();
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Circuitry");
        setBorder(myBorder);
        setAlignmentX(LEFT_ALIGNMENT);
    }
    
    private void addGraphicsPanel() {
        circuit = new DLatch();
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
    }

}
