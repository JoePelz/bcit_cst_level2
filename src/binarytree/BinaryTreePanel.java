package binarytree;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
public class BinaryTreePanel extends JPanel {
    /** unique id for serialization. */
    private static final long serialVersionUID = -1712387296868793108L;
    /** the panel that holds the circuits, switched via CardLayout. */
    private JPanel circuits = new JPanel(new GridLayout(1, 1));
    /** The diagrammatic representation of the binary tree. */
    private BinaryTreeGraphic binaryTree = new BinaryTreeGraphic();
    
    /**
     * Constructor, to build the UI, 
     * including combo box 
     * and circuit panel full of circuits. 
     */
    public BinaryTreePanel() {
        setLayout(new BorderLayout());
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Binary Tree");
        circuits.setBorder(myBorder);
        circuits.add(binaryTree);
        
        final int numberOfForms = 3;
        JPanel entryPane = new JPanel(new GridLayout(2, numberOfForms));
        JLabel lPreOrder  = new JLabel("  Pre-Order");
        JLabel lInOrder   = new JLabel("  In-Order");
        JLabel lPostOrder = new JLabel("  Post-Order");
        final JTextField tePreOrder  = new JTextField("3, 2, 7, 6, 8");
        final JTextField teInOrder   = new JTextField("2, 3, 6, 7, 8");
        final JTextField tePostOrder = new JTextField("2, 6, 8, 7, 3");
        teInOrder.setEditable(false);
        tePostOrder.setEditable(false);
        entryPane.add(lPreOrder);
        entryPane.add(lInOrder);
        entryPane.add(lPostOrder);
        entryPane.add(tePreOrder);
        entryPane.add(teInOrder);
        entryPane.add(tePostOrder);
        add(entryPane, BorderLayout.PAGE_START);
        add(circuits, BorderLayout.CENTER);
        
        JButton btnBalance = new JButton("Rebalance Tree");
        btnBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                binaryTree.rebalanceTree();
                SimpleTree<Integer> tree = binaryTree.getTree();
                teInOrder.setText(tree.getInOrder());
                tePreOrder.setText(tree.getPreOrder());
                tePostOrder.setText(tree.getPostOrder());
            }
        });
        add(btnBalance, BorderLayout.PAGE_END);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                binaryTree.focusView();
            }
        });
        tePreOrder.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                try {
                    String[] vals = (tePreOrder.getText().split("[, ]+"));
                    ArrayList<Integer> values = new ArrayList<Integer>();
                    for (String val : vals) {
                        values.add(Integer.parseInt(val));
                    }
                    binaryTree.setPreOrder(values);
                    SimpleTree<Integer> tree = binaryTree.getTree();
                    tePostOrder.setText(tree.getPostOrder());
                    teInOrder.setText(tree.getInOrder());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid equation.");
                }
            }
        });
    }
    
    /**
     * Test the circuits panel independently in a simple JFrame.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        final int defaultWidth = 1200;
        final int defaultHeight = 800;
        JFrame frame = new JFrame("Binary Tree Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException 
                | InstantiationException
                | IllegalAccessException 
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        BinaryTreePanel etp = new BinaryTreePanel();
        
        
        frame.add(etp);
        frame.pack();
        frame.setSize(defaultWidth, defaultHeight);
        frame.validate();
        frame.setVisible(true);
    }
}
