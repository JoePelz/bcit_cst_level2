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
        /*for (int i = 0; i < 9; i++) {
            JTextField textField = new JTextField(Integer.toString(i));
            //when i==4, put long text in the text field...
            if (i==4) {
                textField.setText("This is longer than you think.");
            }
            panel.add(textField);
        }*/
        
        panel.add(new HammingPanel());
        
        /*SpringUtilities.makeGrid(panel,
                3, 3, // rows, cols
                20, 5, // initial x, y
                20, 5);// xPad, yPad */
        
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}