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
 * This class draws a picture of a single Hamming Code digit,
 * with the value on top and the constituent powers of two below.
 *  
 * @author Joe Pelz
 * @version 1.0
 */
public class HCodeDigit extends JPanel {
    /** Unique id for serialization. */
    private static final long serialVersionUID = 3427769338112327838L;
    /** The margin to the left and right of each hamming digit. */
    private static final int MARGIN = 5;

    /** The height of a character. */
    private int ch;
    /** The width of a 0 in this font. */
    private int cw;
    /** The width of the char string, with a margin. */
    private int mw;
    /** Code-Array. The first array element is the actual binary value. 
     * subsequent array elements are the constituent powers of two that
     * make up the value. */
    private int[] ca;
    
    /**
     * Constructor to take the index and initialize the code array for drawing.
     * 
     * @param index
     *            the position of this number in the final bit string. (e.g.
     *            first bit or ninth bit)
     */
    public HCodeDigit(final int index) {
        
        
        /*  CA should equal the number, 
         *  and the powers of 2 composing its index.
         *  e.g.
         *  index = 2, CA =  [0, 2]
         *  index = 14, CA = [0, 8, 4, 2]
         *  index = 31, CA = [0, 16, 8, 4, 2, 1]
         *  index = 32, CA = [0, 32] 
         */
        ca = getCodeArray(index + 1);
        
        FontMetrics metrics = getFontMetrics(getFont());
        Dimension size = new Dimension();
        
        ch = metrics.getHeight();
        cw = metrics.getWidths()['0'];
        mw = Integer.toString(ca[1]).length() * cw + MARGIN * 2;
        size.setSize(mw, ch * ca.length + 2);

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentY(Component.TOP_ALIGNMENT);
    }
    
    /**
     * Set the value of this particular bit.
     * 
     * @param c the state to set the bit to. 1 or 0.
     */
    public void setBitValue(final char c) {
        if (c == '0' || c == 0) {
            ca[0] = 0;
        }  else {
            ca[0] = 1;
        }
    }
    
    /**
     * Access the value of the bit.
     * 
     * @return numeric 1 if the bit is on, 0 if the bit is off.
     */
    public int getBitValue() {
        return ca[0];
    }
    
    /**
     * Test if this digit uses the given power of two. 
     * (e.g. test if this bit (bit 13) is checked by parity bit 4)
     * 
     * @param i The parity bit to test this digit against. 
     * @return True if this digit is checked by the given parity bit.
     */
    public boolean uses(final int i) {
        for (int n = 1; n < ca.length; n++) {
            if (ca[n] == i) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Take a given index, and convert it to an array of 1s and 0s 
     * representing the number in binary. 
     * The first digit in the resulting array will be 0 
     * and the rest will the powers of two that the index
     * comprises.
     * 
     * @param index The number to convert to binary
     * @return The array of bits
     */
    public int[] getCodeArray(final int index) {
        char[] binaryString = Integer.toBinaryString(index).toCharArray();
        
        int rLen = 1;
        for (char c : binaryString) {
            if (c == '1') {
                rLen++;
            }
        }
        
        int[] result = new int[rLen];
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
        for (int i : ca) {
            g2.drawString("" + i, 
                    mw / 2 - (("" + i).length() * cw) / 2, 
                    ch * line);
            line++;
        }

        //underscore the bit
        g.drawLine(MARGIN,
                ch + 2,
                mw - MARGIN,
                ch + 2);
        
        //If the bit is a parity bit, outline it.
        if (isParity()) {
            Rectangle r = g.getClipBounds();
            g2.drawRect(0, 0, r.width - 1, r.height - 1);
        }
    }

    /**
     * Test if this bit itself is a parity bit.
     * 
     * @return True if this is a parity bit.
     */
    public final boolean isParity() {
        return ca.length == 2;
    }
}
