package core;

import hamming.HammingPanel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import settheory.SetPanel;

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

        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Architecture", null, new HammingPanel(), "Does nothing");
        tabbedPane.addTab("Math", null, new SetPanel(), "Does nothing");
        
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        frame.add(tabbedPane);
        frame.pack();
        frame.setVisible(true);
    }
}