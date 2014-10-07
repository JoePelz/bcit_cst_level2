/**
 * 
 */
package hamming;

import gui.Spacer;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

enum Parity {
    EVEN,
    ODD
}

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HammingPanel extends JPanel {
    /**  Generated serial ID. */
    private static final long serialVersionUID = 1089393901520809383L;
    
    private Font fixed = new Font("Consolas", Font.PLAIN, 14);
    private Font fixedBold = new Font("Consolas", Font.BOLD, 14);
    
    public HammingPanel() {
        super();

        BoxLayout layoutMain = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layoutMain);
        JPanel rowTwo = new JPanel();
        BoxLayout layout = new BoxLayout(rowTwo, BoxLayout.LINE_AXIS);
        rowTwo.setLayout(layout);

        HammingEncodePanel hep = new HammingEncodePanel();
        hep.setFont(fixed);
        add(hep);
        add(Spacer.VerticalStretch(10));

        HammingDecodePanel hdp = new HammingDecodePanel();
        hep.setFont(fixed);
        add(hdp);

        
        setFont(fixedBold);
        setMinimumSize(new Dimension(900, 0));
        TitledBorder myBorder = BorderFactory.createTitledBorder("Hamming Code");
        myBorder.setTitleFont(fixedBold);
        setBorder(myBorder);
    }
}
