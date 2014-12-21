package hamming;

import gui.ImprovedFormattedTextField;
import gui.Spacer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.BinaryFormat;

/**
 * This panel provides functionality to input a code word, 
 * encoded in Hamming Code, 
 * and show the decoded dataword.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class HammingDecodePanel extends JPanel {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = 7579549903973444096L;

    /** Input field label. */
    private JLabel dWord = new JLabel("Code Word");
    /** The codeword input field. String of binary digits. */
    private ImprovedFormattedTextField dwField;
    /** The result panel to display the decoding process 
     * and the resulting dataword. */
    private HammingCode dwResult = new HammingCode();

    /**
     *  Constructor to build the decoding UI.
     */
    public HammingDecodePanel() {
        final int hSpacing = 10;
        
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        
        createInputField();
        
        //Alignment
        dWord.setHorizontalAlignment(JLabel.RIGHT);
        dWord.setAlignmentY(CENTER_ALIGNMENT);
        dwField.setAlignmentY(CENTER_ALIGNMENT);
        
        dwResult.setCodeWord("0111000");

        add(Spacer.horizontal(hSpacing));
        add(dWord);
        add(Spacer.horizontal(hSpacing));
        add(dwField);
        add(Spacer.horizontal(hSpacing));
        add(dwResult);
        //add(Box.createHorizontalGlue());
        add(Spacer.horizontalStretch(1));
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        //On construction, this function is called 
        //prior to initializing some fields.
        if (font != null && dWord != null && dwField != null && dwResult != null) {
            dWord.setFont(font);
            dwField.setFont(font);
            dwResult.setFont(font);
        }       
    }

    /**
     * Helper UI function to build the input text field.  
     */
    private void createInputField() {
        final int fieldWidth  = 200;
        final int fieldMinWidth = 100;
        final int fieldHeight = 30;
        final int fieldColumns = 17;
        
        BinaryFormat bf = new BinaryFormat();
        dwField = new ImprovedFormattedTextField(bf, "0111000");
        
        dwField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        dwField.setMinimumSize(new Dimension(fieldMinWidth, 0));
        dwField.setColumns(fieldColumns);
        
        dwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                if (dwField.isEditValid()) {
                    dwResult.setCodeWord(dwField.getText());
                    revalidate();
                }
            }
        });
    }
    
    /**
     * Set the parity to assume when decoding the codeword.
     * 
     * @param parityEven
     *            True if parity should be even. 
     *            False if parity should be odd.
     */
    public void setParityEven(boolean parityEven) {
        dwResult.setParityEven(parityEven);
        if (dwField.isEditValid()) {
            dwResult.setCodeWord(dwField.getText());
            revalidate();
        }
    }
}
