/**
 * 
 */
package hamming;

import gui.ImprovedFormattedTextField;
import gui.Spacer;
import gui.UnderlinedChars;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import util.BinaryFormat;

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
    
    private JLabel cWord = new JLabel("Code Word");
    private ImprovedFormattedTextField cwField;
    private Font fixed = new Font("Consolas", Font.PLAIN, 14);
    private Font fixedBold = new Font("Consolas", Font.BOLD, 14);
    private Results cwResult = new Results(true);
    
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
        add(rowTwo);

        //Right-align the labels
        cWord.setHorizontalAlignment(JLabel.RIGHT);
        
        BinaryFormat bf = new BinaryFormat();
        
        cwField = new ImprovedFormattedTextField(bf, "1100101");
        cwField.setColumns(15);
        cwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                if (cwField.isEditValid()) {
                    cwResult.setCode(cwField.getText());
                }
            }
        });
        
        //Set fonts
        cWord.setFont(fixed);
        cwField.setFont(fixed);
        
        //Attach Listeners
        
        //Populate the panel
        rowTwo.add(cWord);
        rowTwo.add(Spacer.Horizontal(10));
        rowTwo.add(cwField);
        rowTwo.add(Spacer.Horizontal(10));
        rowTwo.add(cwResult);
        
        setFont(fixedBold);
        TitledBorder myBorder = BorderFactory.createTitledBorder("Hamming Code");
        myBorder.setTitleFont(fixedBold);
        setBorder(myBorder);
    }
    
    private class Results extends JPanel{
        /** random serial id. */
        private static final long serialVersionUID = 2265551522959616294L;
        
        private String inCode = "0";
        private String outCode = "0000";
        private Parity parity = Parity.EVEN;
        private UnderlinedChars lResult;
        private boolean reverse;
        
        
        private Results(boolean reverse) {
            this.reverse = reverse;
            lResult = new UnderlinedChars(outCode);
            lResult.setFont(fixed);
            GridLayout form = new GridLayout(1, 1);
            setLayout(form);
            add(lResult);
        }
        
        private void setCode(Object input) {
            inCode = input.toString();
            if (reverse)
                deCode();
            else
                enCode();
            update();
        }

        private void update() {
            lResult.setText(outCode);
        }
        
        private void enCode() {
            String result = "";
            int power = 1;
            int digit = 1;
            for (char c : inCode.toCharArray()) {
                while (digit == power) {
                    power *= 2;
                    result = result + "P";
                    digit++;
                }
                result = result + c;
                digit++;
            }
            outCode = result;
        }
        private void deCode() {
            String result = "";
            int power = 1;
            int digit = 1;
            for (char c : inCode.toCharArray()) {
                if (digit == power) {
                    power *= 2;
                } else {
                    result = result + c;
                }
                digit++;
            }
            outCode = result;
        }
    }
}
