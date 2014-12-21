/**
 * 
 */
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
 * This panel provides functionality to input a binary data word 
 * and have it be encoded using Hamming Code.   
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class HammingEncodePanel extends JPanel {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = 7579549903973444096L;

    /** The label for this panel's input box. */
    private JLabel dWord = new JLabel("Data Word");
    /** The input text box for the data word. */
    private ImprovedFormattedTextField dwField;
    /** The resulting Hamming-code. */
    private HammingCode dwResult = new HammingCode();

    /**
     * Constructor, to build the UI for the encode panel.
     */
    public HammingEncodePanel() {
        final int hSpacing = 10;
        
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        
        createInputField();
        
        //Alignment
        dWord.setHorizontalAlignment(JLabel.RIGHT);
        dWord.setAlignmentY(CENTER_ALIGNMENT);
        dwField.setAlignmentY(CENTER_ALIGNMENT);
        
        dwResult.setDataWord("110010010011");

        add(Spacer.Horizontal(hSpacing));
        add(dWord);
        add(Spacer.Horizontal(hSpacing));
        add(dwField);
        add(Spacer.Horizontal(hSpacing));
        add(dwResult);
        //add(Box.createHorizontalGlue());
        add(Spacer.HorizontalStretch(1));
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
     * UI helper function, that builds the input 
     * field for the encode panel.
     */
    private void createInputField() {
        final int fieldWidth  = 200;
        final int fieldMinWidth = 100;
        final int fieldHeight = 30;
        final int fieldColumns = 17;
        
        BinaryFormat bf = new BinaryFormat();
        dwField = new ImprovedFormattedTextField(bf, "110010010011");
        
        dwField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        dwField.setMinimumSize(new Dimension(fieldMinWidth, 0));
        dwField.setColumns(fieldColumns);
        
        dwField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                if (dwField.isEditValid()) {
                    dwResult.setDataWord(dwField.getText());
                    revalidate();
                }
            }
        });
    }

    /**
     * Set the parity used to be even parity or odd parity.
     * 
     * @param parityEven True if parity should be even. False if odd.
     */
    public void setParityEven(boolean parityEven) {
        dwResult.setParityEven(parityEven);
        if (dwField.isEditValid()) {
            dwResult.setDataWord(dwField.getText());
            revalidate();
        }
    }
}
