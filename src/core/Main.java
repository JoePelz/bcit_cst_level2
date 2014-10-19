package core;

import hamming.HammingPanel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import settheory.SetPanel;
import edgetrigger.EdgeTriggerPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Main {
    /**
    * Creates and displays the application frame.
    * @param args Unused
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CST Level 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel archPanel = new JPanel();
        archPanel.setLayout(new BoxLayout(archPanel, BoxLayout.PAGE_AXIS));
        archPanel.add(new HammingPanel());
        archPanel.add(new EdgeTriggerPanel());
        
        tabbedPane.addTab("Architecture", null, archPanel, "COMP 2721");
        tabbedPane.addTab("Math", null, new SetPanel(), "COMP 2121");
        
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        frame.add(tabbedPane);
        frame.pack();
        frame.setVisible(true);
    }
}