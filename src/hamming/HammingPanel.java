/**
 * 
 */
package hamming;

import gui.Spacer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

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
    
    private ButtonGroup group = new ButtonGroup();
    private JRadioButton rbEven = new JRadioButton("Even parity");
    private JRadioButton rbOdd = new JRadioButton("Odd parity");
    
    HammingEncodePanel hep;
    HammingDecodePanel hdp; 
    
    public HammingPanel() {
        super();
        
        group = new ButtonGroup();
        rbEven = new JRadioButton("Even parity");
        rbEven.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //set encode & decode to parity even
                hep.setParityEven(true);
                hdp.setParityEven(true);
            }
        });
        rbEven.setSelected(true);
        rbOdd = new JRadioButton("Odd parity");
        rbOdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //set encode & decode to parity even
                hep.setParityEven(false);
                hdp.setParityEven(false);
            }
        });
        group.add(rbEven);
        group.add(rbOdd);
        

        BoxLayout layoutMain = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(layoutMain);
        JPanel radioRow = new JPanel();
        BoxLayout layout = new BoxLayout(radioRow, BoxLayout.LINE_AXIS);
        radioRow.setLayout(layout);
        radioRow.add(rbEven);
        radioRow.add(rbOdd);
        radioRow.setAlignmentX(LEFT_ALIGNMENT);
        radioRow.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(radioRow);
        add(Spacer.Vertical(5));

        hep = new HammingEncodePanel();
        hep.setFont(fixed);
        hep.setAlignmentX(LEFT_ALIGNMENT);
        add(hep);
        add(Spacer.Vertical(10));

        hdp = new HammingDecodePanel();
        hdp.setFont(fixed);
        hdp.setAlignmentX(LEFT_ALIGNMENT);
        add(hdp);
        add(Spacer.VerticalStretch(10));

        
        setFont(fixedBold);
        setMinimumSize(new Dimension(900, 0));
        TitledBorder myBorder = BorderFactory.createTitledBorder("Hamming Code");
        myBorder.setTitleFont(fixedBold);
        setBorder(myBorder);
    }
}
