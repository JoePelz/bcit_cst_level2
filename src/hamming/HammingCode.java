package hamming;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class displays hamming code, 
 * based on a given code or data word and parity.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class HammingCode extends JPanel {
    /**  Unique id for serialization. */ 
    private static final long serialVersionUID = -1052654251262164181L;

    /** A panel to hold the Hamming code digits. */
    private JPanel pHamming;
    /** A panel to hold the decoded result word. */
    private JPanel pResults;

    /** Array of HCodeDigits holding the actual hamming code. */
    private HCodeDigit[] bits;
    /** if each bit is valid. i.e. no parity error on this bit. */
    private boolean[] vBits;
    /** Track whether parity is even or odd. */
    private boolean parityEven = true;
    
    /**
     * Constructor to initialize variables and layout.
     */
    public HammingCode() {
        BoxLayout page = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        
        pHamming = new JPanel(); 
        pResults = new JPanel(); 
        
        BoxLayout layout = new BoxLayout(pHamming, BoxLayout.LINE_AXIS);
        BoxLayout layout3 = new BoxLayout(pResults, BoxLayout.LINE_AXIS);
        
        pHamming.setLayout(layout);
        pResults.setLayout(layout3);
        pHamming.setAlignmentX(LEFT_ALIGNMENT);
        pResults.setAlignmentX(LEFT_ALIGNMENT);
        
        setLayout(page);
        add(pHamming);
        add(pResults);
    }

    /**
     * Calculate the Hamming codeword for a given dataword. 
     * 
     * @param dword The dataword to encode.
     * @return The encoded Hamming codeword.
     */
    private StringBuffer encode(String dword) {
        StringBuffer result = new StringBuffer();
        int power = 1;
        int digit = 1;
        
        //concatenate bits into a string, and insert parity bits as needed.
        //For now, just set all parity bits to 0.
        for (char c : dword.toCharArray()) {
            while (digit == power) {
                power *= 2;
                result.append("0");
                digit++;
            }
            result.append(c);
            digit++;
        }
        
        
        //Calculate each parity bit's value
        int bitSum;
        
        //For each parity bit...
        for (int i = 1; i <= result.length(); i <<= 1) {
            bitSum = 0;
            
            //For all bits in the string AFTER the current parity bit.
            for (int j = i + 1; j <= result.length(); j++) {
                // if the current bit is important for the parity bit
                if ((j & i) == i) {
                    bitSum += (result.charAt(j - 1) == '1' ? 1 : 0);
                }
            }
            
            //check if bitSum is odd or even and match parity.
            if ((bitSum % 2 == 0) == parityEven) {
                result.setCharAt(i - 1, '0');
            } else {
                result.setCharAt(i - 1, '1');
            }
        }
        return result;
    }
    
    /**
     * Set the data word to be displayed. 
     * 
     * @param dword he dataword to be encoded.
     */
    public void setDataWord(String dword) {
        StringBuffer cword = encode(dword);

        //Empty the panels of everything
        pHamming.removeAll();
        pResults.removeAll();

        bits = new HCodeDigit[cword.length()];
        vBits = new boolean[cword.length()];
        
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new HCodeDigit(i);
            bits[i].setBitValue(cword.charAt(i));
            vBits[i] = true;
            pHamming.add(bits[i]);
        }
    }
    
    /**
     * The original data word, decoded with parity errors fixed.
     * 
     * @return The original data word.
     */
    public String getDataWord() {
        StringBuffer dword = new StringBuffer();

        for (int i = 0; i < bits.length; i++) {
            if (!bits[i].isParity()) {
                if (vBits[i]) {
                    dword.append(bits[i].getBitValue());
                } else {
                    dword.append(1 - bits[i].getBitValue());
                }
            }
        }
        return dword.toString();
    }
    
    /**
     * Set the code word, to be encoded.
     * 
     * @param cword The new (binary) codeword to set
     */
    public void setCodeWord(String cword) {

        //Empty the panel of all digits
        pHamming.removeAll();
        pResults.removeAll();

        bits = new HCodeDigit[cword.length()];
        vBits = new boolean[cword.length()];
        
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new HCodeDigit(i);
            bits[i].setBitValue(cword.charAt(i));
            vBits[i] = true;
            pHamming.add(bits[i]);
        }


        int bitSum = 0;

        //=============================
        //validation of the code word
        //=============================
        // please make me more pretty.
        for (int i = 1; i <= bits.length; i++) {
            bitSum = 0;
            
            //if parity bit:
            if (bits[i - 1].isParity()) {
                //find other bits that use it and mark them true/false
                for (int j = i; j <= bits.length; j++) {
                    if ((j & i) == i) {
                        bitSum += bits[j - 1].getBitValue();
                    }
                    
                    if ((bitSum % 2 == 0) == parityEven) {
                        //good
                        bits[i - 1].setBackground(Color.GREEN);
                        vBits[i - 1] = true;
                    } else {
                        //bad
                        bits[i - 1].setBackground(Color.PINK);
                        vBits[i - 1] = false;
                    }
                }
                
            } else { //if not parity bit:                
                bits[i - 1].setBackground(Color.GREEN);
            }
        }
        
        
        /* Get the bad parity bits and mark the 
         *   regular bit they point to.
         *   e.g. if bits 2 and 4 are bad, mark bit 6 too.
         */ 
        int target = 0; 
        for (int i = 1; i <= bits.length; i *= 2) {
            if (!vBits[i - 1]) {
                target += i;
            }
        }
        if (target <= bits.length && target != 0) {
            bits[target - 1].setBackground(Color.PINK);
            vBits[target - 1] = false;
        }
        
        /*
         * print the final word at the bottom.
         */
        JLabel results = new JLabel("Original Data Word: " + getDataWord());
        pResults.add(results);
    }

    /**
     * Set the parity used to be even or odd. 
     * 
     * @param parityEven True to use even parity, false to use odd.
     */
    public void setParityEven(boolean parityEven) {
        this.parityEven = parityEven;
    }
}
