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
 * This panel builds and lays out the Hamming panels 
 * for encoding and decoding.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class HammingPanel extends JPanel {
    /**  Generated serial ID. */
    private static final long serialVersionUID = 1089393901520809383L;
    /** A nice fixed-width font to use. */
    public static Font font = new Font("Consolas", Font.PLAIN, 14);

    /** Parity choice button group. */
    private ButtonGroup group = new ButtonGroup();
    /** Radiobutton to choose even parity. */
    private JRadioButton rbEven = new JRadioButton("Even parity");
    /** Radiobutton to choose odd parity. */
    private JRadioButton rbOdd = new JRadioButton("Odd parity");

    /** Hamming encoding panel. */
    HammingEncodePanel hep;
    /** Hamming decoding panel. */
    HammingDecodePanel hdp; 
    
    /**
     * Constructor to build the panel and components.
     */
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
        hep.setFont(font);
        hep.setAlignmentX(LEFT_ALIGNMENT);
        add(hep);
        add(Spacer.Vertical(10));

        hdp = new HammingDecodePanel();
        hdp.setFont(font);
        hdp.setAlignmentX(LEFT_ALIGNMENT);
        add(hdp);
        add(Spacer.VerticalStretch(10));

        
        setMinimumSize(new Dimension(900, 0));
        TitledBorder myBorder = BorderFactory.createTitledBorder("Hamming Code");
        setBorder(myBorder);
        setAlignmentX(LEFT_ALIGNMENT);
    }
}
