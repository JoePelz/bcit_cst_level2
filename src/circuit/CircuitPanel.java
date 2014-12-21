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
 * This class is a panel to hold many circuits, 
 * chosen from drop-down list.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class CircuitPanel extends JPanel implements ItemListener {
    /** unique id for serialization. */
    private static final long serialVersionUID = -1712387296868793108L;
    /** the panel that holds the circuits, switched via CardLayout. */
    private JPanel circuits = new JPanel(new CardLayout());

    /**
     * Constructor, to build the UI, 
     * including combo box 
     * and circuit panel full of circuits. 
     */
    public CircuitPanel() {
        setLayout(new BorderLayout());
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Interactive Circuits");
        circuits.setBorder(myBorder);
        
        circuitPresets[] circuitList = circuitPresets.values();
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String[] comboBoxItems = new String[circuitList.length];
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
    
    /**
     * Focus all of the circuits in the frame.  
     */
    public void focusAll() {
        for (int i = 0; i < circuits.getComponentCount(); i++) {
            Circuit c = (Circuit) circuits.getComponent(i);
            c.focusView();
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (circuits.getLayout());
        cl.show(circuits, (String) evt.getItem());
        
    }

    /**
     * This class enumerates available circuits.
     * 
     * @author Joe Pelz
     * @version 1.0
     */
    private enum circuitPresets {
//        C_CT("Cam Test", new CamTest()),
        /** Half adder, performs 1-bit addition without carry-in. */
        C_HA("Half Adder", new HalfAdder()),
        /** Full Adder, performs 1-bit addition with carry-in. */
        C_FA("Full Adder", new FullAdder()),
        /** Decodes a 2-bit signal into 4 outputs. */
        C_DEC("Decoder", new Decoder()),
        /** ALU does 1-bit AND, OR, NOT, ADD operations. */
        C_ALU("Arithmetic Logic Unit", new SimpleALU()),
        /** SR Latch tries to be memory. */
        C_SRL("SR Latch", new SRLatch()),
        /** Unclocked D Latch tries to be memory but fails miserably. */
        C_UDL("Unclocked D Latch", new UnclockedDLatch()),
        /** Clocked D Latch tries to be memory and almost succeeds. */
        C_CDL("Clocked D Latch", new ClockedDLatch()),
        /** Flip-flop is a usable memory circuit. */
        C_FF("D Flip-Flop", new FlipFlop());
        
        /** The name of the circuit. */
        private String name;
        /** The circuit object itself. */
        private Circuit circuit;
        
        /**
         * Constructor, stores a name and circuit.
         * 
         * @param name The name of the circuit.
         * @param circuit The circuit object itself.
         */
        circuitPresets(String name, Circuit circuit) {
            this.name = name;
            this.circuit = circuit;
        }

        /**
         * Get the name for this enumeration value.
         * 
         * @return The value's circuit name.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Get the circuit for this enumeration value.
         * 
         * @return This value's circuit object.
         */
        public Circuit getCircuit() {
            return circuit;
        }
    }


    /**
     * Test the circuits panel independently in a simple JFrame.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        final int defaultWidth = 1200;
        final int defaultHeight = 800;
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
        frame.setSize(defaultWidth, defaultHeight);
        frame.validate();
        etp.focusAll();
        frame.setVisible(true);
    }
}
