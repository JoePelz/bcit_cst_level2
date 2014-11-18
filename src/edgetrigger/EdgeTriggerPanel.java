/**
 * 
 */
package edgetrigger;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class EdgeTriggerPanel extends JPanel {
    private static final long serialVersionUID = -1712387296868793108L;

    private EdgeTriggerGraphic graphic;
    
    public EdgeTriggerPanel() {
        //needs drop-down on top to choose number of variables (1, 2, 3 or 4)
        //left side has text field
        //left side has buttons for UNION / INTERSECTION / NOT
        //right side is dynamic illustration
        //will need to be able to parse the content of the text field.

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //Add an image panel
        addGraphicsPanel();

        //Add a selector box
        addSliderbar();
        
        TitledBorder myBorder = BorderFactory.createTitledBorder("Edge Trigger circuit");
        setBorder(myBorder);
//        Dimension size = new Dimension(300, 150);
        Dimension size = new Dimension(1200, 400);
        setPreferredSize(size);
        setMaximumSize(size);
        setAlignmentX(LEFT_ALIGNMENT);
    }
    
    private void addGraphicsPanel() {
//        graphic = new EdgeTriggerGraphic();
        graphic = new EdgeTriggerGraphic();
        add(graphic);
    }

    private void addSliderbar() {
        JSlider bar = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
        bar.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                graphic.setPhase(((JSlider)(e.getSource())).getValue() / 1000.0);
                graphic.repaint();
            }
        });
        add(bar);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flip-flop Edge Trigger, by Joe Pelz, Fall 2014");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException 
                | InstantiationException
                | IllegalAccessException 
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        
        EdgeTriggerPanel etp = new EdgeTriggerPanel();

        frame.add(etp);
        frame.pack();
        frame.setVisible(true);
    }

}
