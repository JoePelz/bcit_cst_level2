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
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HammingDecodePanel extends JPanel {
    private static final long serialVersionUID = 7579549903973444096L;

    private JLabel dWord = new JLabel("Code Word");
    private ImprovedFormattedTextField dwField;
    private HammingCode dwResult = new HammingCode();
    private Font fixed = new Font("Consolas", Font.PLAIN, 14);

    public HammingDecodePanel() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        
        createInputField();
        
        //Alignment
        dWord.setHorizontalAlignment(JLabel.RIGHT);
        dWord.setAlignmentY(CENTER_ALIGNMENT);
        dwField.setAlignmentY(CENTER_ALIGNMENT);
        
        dWord.setFont(fixed);
        dwField.setFont(fixed);
        dwResult.setFont(fixed);        
        dwResult.setCodeWord("0111000");

        add(Spacer.Horizontal(10));
        add(dWord);
        add(Spacer.Horizontal(10));
        add(dwField);
        add(Spacer.Horizontal(10));
        add(dwResult);
        //add(Box.createHorizontalGlue());
        add(Spacer.HorizontalStretch(1));
    }

    /**
     * 
     * @return
     */
    private void createInputField() {
        BinaryFormat bf = new BinaryFormat();
        dwField = new ImprovedFormattedTextField(bf, "0111000");
        
        dwField.setMaximumSize(new Dimension(200, 30));
        dwField.setMinimumSize(new Dimension(100, 0));
        dwField.setColumns(17);
        
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
    
    public void setParityEven(boolean parityEven) {
        dwResult.setParityEven(parityEven);
        if (dwField.isEditValid()) {
            dwResult.setCodeWord(dwField.getText());
            revalidate();
        }
    }
}
