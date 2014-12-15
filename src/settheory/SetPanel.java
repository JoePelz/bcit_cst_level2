package settheory;

import gui.EquationField;
import gui.Spacer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import util.SetEqFormat;

/**
 * This class represents the UI panel that displays the set theory calculator.
 * @author Joe Pelz
 * @version 1.0
 */
public class SetPanel extends JPanel {
    /**  Unique generated identifier. */
    private static final long serialVersionUID = -6895003958115345068L;
    // Text identifiers to indicate the number of sets to display.
    /** The text for only 1 set. */
    private static final String varsOne = "Sets: A only";
    /** The text for 2 sets. */
    private static final String varsTwo = "Sets: A, B";
    /** The text for 3 sets. */
    private static final String varsThree = "Sets: A, B, C";
    /** The text for 4 sets. */
    private static final String varsFour = "Sets: A, B, C, D";
    
    //Buttons on the calculator pad.
    private JButton addA = new JButton("A");
    private JButton addB = new JButton("B");
    private JButton addC = new JButton("C");
    private JButton addD = new JButton("D");
    private JButton addUNION = new JButton("∪");
    private JButton addINTERSECTION = new JButton("∩");
    private JButton addMINUS = new JButton("-");
    private JButton addNOT = new JButton("'");
    private JButton addXOR = new JButton("∆");
    private JButton addOPEN = new JButton("(");
    private JButton addCLOSE = new JButton(")");
    private JButton clear = new JButton("Clear");
    
    /** Equation entry box. */
    private EquationField eqBox;
    
    /** The image of the current Venn diagram displayed. */ 
    private Venn image;
    
    /** The calculator to store the equation and compute the result. */
    private SetsEquation calc;
    
    /**
     * Construct a new SetPanel, including:
     * <ul>
     * <li>variables selector, </li>
     * <li>formula entry, </li>
     * <li>calculator buttons, </li>
     * <li>and dynamic illustration.</li>
     * </ul> 
     */
    public SetPanel() {
        //needs drop-down on top to choose number of variables (1, 2, 3 or 4)
        //left side has text field
        //left side has buttons for UNION / INTERSECTION / NOT
        //right side is dynamic illustration

        setLayout(new GridBagLayout());
        
        //Add a selector box
        addComboBox();

        //Add a spacer
        addSpacer();
        
        //add a text input box
        addEquationField();
        
        //add formula buttons.
        addButtons();
        
        //Add an image panel
        changeVenn(3);
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Set Theory");
        setBorder(myBorder);
    }

    /** Adds a spacer to the UI; 30 pixels tall, 10 pixels wide. */
    private void addSpacer() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;        
        add(Spacer.Vertical(30), c);
        
        c.gridx = 0;
        c.gridy = 0;
        add(Spacer.Horizontal(10), c);
    }

    /** Add the equation / formula text entry box. */
    private void addEquationField() {
        eqBox = new EquationField(new SetEqFormat());
        eqBox.setColumns(20);
        eqBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                if (eqBox.validContent()) {
                    try {
                        calc = new SetsEquation(eqBox.getText());
                        updateVenn();
                        revalidate();
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid equation.");
                    }
                }
            }
        });
        
        Font font = eqBox.getFont();
        font = font.deriveFont(19);
        font = new Font("Deja Vu Sans", Font.PLAIN, 16);
        eqBox.setFont(font);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        
        add(eqBox, c);
    }

    /**
     * Action Listener for button clicks. This class will 
     * add a character to the formula box when triggered.
     * 
     * @author Joe Pelz
     * @version 1.0
     */
    private class AddToEquation implements ActionListener {
        /** The character(s) to add to the formula when triggered. */
        private final String token;
        /**
         * Constructor, to define the token added by this Listener.
         * @param textToAdd The character(s) to add.
         */
        private AddToEquation(String textToAdd) {
            token = textToAdd;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            eqBox.pushText(token);
            if (eqBox.validContent()) {
                try {
                    calc = new SetsEquation(eqBox.getText());
                    updateVenn();
                    revalidate();
                } catch (IllegalArgumentException ex) {
                    System.err.println("Invalid equation.");
                }
            }
        }
    }
    
    /**
     * Add all the calculator buttons to the panel.
     */
    private void addButtons() {
        JPanel jpButtons = new JPanel(new GridLayout(3, 4));

        addA.addActionListener(new AddToEquation("A"));
        addB.addActionListener(new AddToEquation("B"));
        addC.addActionListener(new AddToEquation("C"));
        addD.addActionListener(new AddToEquation("D"));
        addUNION.addActionListener(new AddToEquation("∪"));
        addINTERSECTION.addActionListener(new AddToEquation("∩"));
        addMINUS.addActionListener(new AddToEquation("-"));
        addXOR.addActionListener(new AddToEquation("∆"));
        addNOT.addActionListener(new AddToEquation("'"));
        addOPEN.addActionListener(new AddToEquation("("));
        addCLOSE.addActionListener(new AddToEquation(")"));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eqBox.setText("");
                if (eqBox.validContent()) {
                    try {
                        calc = new SetsEquation(eqBox.getText());
                        updateVenn();
                        revalidate();
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Invalid equation.");
                    }
                }
            }
        });
        
        jpButtons.add(addA);
        jpButtons.add(addB);
        jpButtons.add(addC);
        jpButtons.add(addD);
        jpButtons.add(addUNION);
        jpButtons.add(addINTERSECTION);
        jpButtons.add(addMINUS);
        jpButtons.add(addXOR);
        jpButtons.add(addNOT);
        jpButtons.add(addOPEN);
        jpButtons.add(addCLOSE);
        jpButtons.add(clear);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        
        add(jpButtons, c);
    }

    /**
     * Replace the dynamic image with a new image.
     * Use the given size to determine which image to display.
     * 
     * @param size Which image to display.
     */
    private void changeVenn(int size) {
        if (image != null) {
            remove(image);
        }
        switch (size) {
        case 1:
            image = new Venn1();
            break;
        case 2:
            image = new Venn2();
            break;
        case 3:
            image = new Venn3();
            break;
        case 4:
            image = new Venn4();
            break;
        default:
            image = new Venn1();
            System.err.println("Invalid Venn Size chosen.");
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 5;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        
        updateVenn();
        add(image, c);
    }
    
    /** Update the Venn diagram to display the current equation. */
    private void updateVenn() {
        image.setMap(calc);
        image.repaint();
    }

    /** Add the selector box, to choose what Venn diagram to display,
     * or how many variables to work with.
     */
    private void addComboBox() {
        String comboBoxItems[] = { varsOne, varsTwo, varsThree, varsFour };
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
        cb.setEditable(false);
        cb.setSelectedItem(varsThree);
        cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    Object trigger = evt.getItem();
                    if (trigger.equals(varsOne)) {
                        changeVenn(1);
                    } else if (trigger.equals(varsTwo)){
                        changeVenn(2);
                    } else if (trigger.equals(varsThree)){
                        changeVenn(3);
                    } else if (trigger.equals(varsFour)){
                        changeVenn(4);
                    }
                    revalidate();
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        
        add(cb, c);
    }
    
    /**
     * Testing function, to run this module independently.
     * Creates a frame that displays the venn application.
     * 
     * @param args unused.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Set Theory Venn Diagrams, by Joe Pelz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
       
        
        
        SetPanel sp = new SetPanel();
        
        frame.add(sp);
        frame.pack();
        frame.setVisible(true);
    }
}
