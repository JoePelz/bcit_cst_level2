/**
 * 
 */
package settheory;

import javax.swing.JPanel;

/**
 * Base class for venn diagrams, 
 * to indicate the methods required 
 * and to allow polymorphism.
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class Venn extends JPanel{
    /**
     * Update the panel to match the given SetsEquation. 
     * i.e. set shading to match the regions 
     * identified in the SetsEquation as being 1/true/on 
     * (contrasted with 0/false/off).  
     * 
     * @param calc The new state for the venn diagram to display.
     */
    public abstract void setMap(SetsEquation calc);
}
