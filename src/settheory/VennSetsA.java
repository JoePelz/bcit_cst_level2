/**
 * 
 */
package settheory;

import javax.swing.JPanel;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class VennSetsA extends JPanel {
    private String equation;
    boolean SetA = false;
    boolean SetU = false;
    
    public void setEquation(final String eq) {
        //remove spaces from eq.
        equation = eq;
        parseEquation(eq);
    }
    
    public String getEquation() {
        //add spaces?
        return equation;
    }
    
    private void parseEquation(String eq) {
        //recurse through the equation subsets
        SetA = true;
        SetU = false;
    }
    
    
}
