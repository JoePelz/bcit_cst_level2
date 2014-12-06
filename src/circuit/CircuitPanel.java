/**
 * 
 */
package circuit;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
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
public class CircuitPanel extends JPanel implements ItemListener {
    private static final long serialVersionUID = -1712387296868793108L;
    private JPanel circuits = new JPanel(new CardLayout());

    public CircuitPanel() {
        setLayout(new BorderLayout());
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Interactive Circuits");
        circuits.setBorder(myBorder);
        
        circuitPresets circuitList[] = circuitPresets.values();
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = new String[circuitList.length];
        for (int i = 0; i < circuitList.length; i++) {
            circuits.add(circuitList[i].getCircuit(), circuitList[i].getName());
            comboBoxItems[i] = circuitList[i].getName();
        }
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        add(comboBoxPane, BorderLayout.PAGE_START);
        add(circuits, BorderLayout.CENTER);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                focusAll();
            }
        });
    }
    
    public void focusAll() {
        for (int i = 0; i < circuits.getComponentCount(); i++) {
            Circuit c = (Circuit) circuits.getComponent(i);
            c.focusView();
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(circuits.getLayout());
        cl.show(circuits, (String)evt.getItem());
        
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
        frame.setSize(1200, 800);
        frame.validate();
        etp.focusAll();
        frame.setVisible(true);
    }

    private enum circuitPresets {
//        C_CT("Cam Test", new CamTest()),
        C_HA("Half Adder", new HalfAdder()),
        C_FA("Full Adder", new FullAdder()),
        C_DEC("Decoder", new Decoder()),
        C_ALU("Arithmetic Logic Unit", new SimpleALU()),
        C_SRL("SR Latch", new SRLatch()),
        C_CDL("Clocked D Latch", new ClockedDLatch()),
        C_UDL("Unclocked D Latch", new UnclockedDLatch()),
        C_FF("D Flip-Flop", new FlipFlop());
        
        private String name;
        private Circuit circuit;
        
        circuitPresets(String name, Circuit circuit) {
            this.name = name;
            this.circuit = circuit;
        }

        public String getName() {
            return name;
        }
        
        public Circuit getCircuit() {
            return circuit;
        }
    }
}
