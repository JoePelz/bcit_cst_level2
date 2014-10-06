/**
 * 
 */
package hamming;

import javax.swing.BoxLayout;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class HammingCode extends JPanel {

    private static final long serialVersionUID = -1052654251262164181L;

    private HCodeDigit bits[];
    private boolean parityEven = true;
    
    public HammingCode() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);
    }
    
    private StringBuffer encode(String dword) {
        StringBuffer result = new StringBuffer();
        int power = 1;
        int digit = 1;
        for (char c : dword.toCharArray()) {
            while (digit == power) {
                power *= 2;
                result.append("0");
                digit++;
            }
            result.append(c);
            digit++;
        }
        
        return result;
    }
    
    public void setDataWord(String dword) {
        StringBuffer cword = encode(dword);
        removeAll();
        bits = new HCodeDigit[cword.length()];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new HCodeDigit(i);
            bits[i].setBitValue(cword.charAt(i));
            add(bits[i]);
        }
        
        //iterate over parity bits
        //bits = [0, 0, 1, 1, 0, 1, 0]
        int bitSum;
        for (int i = 0; i < bits.length; i = ((i+1) * 2) - 1) {
            bitSum = 0;
            for (int j = i+1; j < bits.length; j++) {
                if (bits[j].uses(i+1)) {
                    bitSum += bits[j].getBitValue();
                }
            }
            if ((bitSum % 2 == 0) == parityEven) {
                bits[i].setBitValue('0');
            } else {
                bits[i].setBitValue('1');
            }
        }
    }
}
