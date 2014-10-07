package core;

import hamming.HammingPanel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        JFrame frame = new JFrame("Direction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        panel.add(new HammingPanel());
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}