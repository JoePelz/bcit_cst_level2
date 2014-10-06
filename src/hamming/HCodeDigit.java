/**
 * 
 */
package hamming;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HCodeDigit extends JPanel {
    private static final long serialVersionUID = 3427769338112327838L;
    private int CH;
    private int CW;
    private int MW;
    private int[] CA;
    
    public HCodeDigit(int index) {
        super();

        /*  CA should equal the number, 
         *  and the powers of 2 composing its index.
         *  e.g.
         *  index = 2, CA =  [0, 2]
         *  index = 14, CA = [0, 8, 4, 2]
         *  index = 31, CA = [0, 16, 8, 4, 2, 1]
         *  index = 32, CA = [0, 32] 
         */
        CA = getCodeArray(index + 1);
        
        FontMetrics metrics = getFontMetrics(getFont());
        Dimension size = new Dimension();
        
        CH = metrics.getHeight();
        CW = metrics.getWidths()[48];
        if (CA[1] >= 100) {
            MW = CW * 3 + 10;
        } else if (CA[1] >= 10) {
            MW = CW * 2 + 10;
        } else {
            MW = CW * 1 + 10;
        }
        size.setSize(MW, CH * CA.length + 2);

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentY(Component.TOP_ALIGNMENT);
    }
    
    public void setBitValue(char c) {
        if (c == '0' || c == 0) {
            CA[0] = 0;
        }  else {
            CA[0] = 1;
        }
    }
    
    public int getBitValue() {
        return CA[0];
    }
    
    public boolean uses(int i) {
        for (int n = 1; n < CA.length; n++) {
            if (CA[n]==i) {
                return true;
            }
        }
        return false;
    }
    
    public int[] getCodeArray(int index) {
        char binaryString[] = Integer.toBinaryString(index).toCharArray();
        
        int rLen = 1;
        for (char c : binaryString) {
            if (c == '1')
                rLen++;
        }
        
        int result[] = new int[rLen];
        result[0] = 0;
        int otherCounter = 1;
        for (int i = 0; i < binaryString.length; i++) {
            if (binaryString[i] == '1') {
                result[otherCounter] = (int) Math.pow(2,  binaryString.length - i - 1);
                otherCounter++;
            }
        }
        return result;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int line = 1;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i : CA) {
            g2.drawString("" + i, 
                    MW / 2 - (("" + i).length() * CW) / 2, 
                    CH * line);
            line++;
        }

        //underscore the bit
        g.drawLine(5,
                CH + 2,
                MW - 5,
                CH + 2);
        
        //If the bit is a parity bit, outline it.
        if (CA.length == 2) {
            Rectangle r = g.getClipBounds();
            g2.drawRect(0, 0, r.width - 1, r.height - 1);
        }
    }
}
