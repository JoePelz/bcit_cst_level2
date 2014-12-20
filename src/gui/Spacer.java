package gui;

import java.awt.Dimension;

import javax.swing.Box;

/**
 * This class houses static methods to create spacer 
 * panels for laying out components.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class Spacer {

    /**
     * A spacing component that will stretch horizontally, 
     * from a minimum of the given width. 
     * <p>height is 0.</p>
     *  
     * @param space The minimum width for the spacer.
     * @return A horizontally stretching spacing component.
     */
    public static Box.Filler HorizontalStretch(int space) {
        Dimension minSize = new Dimension(space, 0);
        Dimension prefSize = new Dimension(space, 0);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 0);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

    /**
     * A spacing component with a fixed width. 
     * <p>height is 0.</p>
     * 
     * @param space The width of the component.
     * @return A horizontal spacing component.
     */
    public static Box.Filler Horizontal(int space) {
        Dimension minSize = new Dimension(space, 0);
        Dimension prefSize = new Dimension(space, 0);
        Dimension maxSize = new Dimension(space, 0);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

    /**
     * A vertically stretching spacer component
     * with the given height as a minimum.
     * <p>Width is 0.</p>
     * 
     * @param space The minimum height to use.
     * @return A vertically stretching spacing component.
     */
    public static Box.Filler VerticalStretch(int space) {
        Dimension minSize = new Dimension(0, space);
        Dimension prefSize = new Dimension(0, space);
        Dimension maxSize = new Dimension(0, Short.MAX_VALUE);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }
    
    /**
     * A vertical spacing component of the given height.
     * <p>Width is 0.</p>
     * 
     * @param space The height of this component.
     * @return A vertical spacing component.
     */
    public static Box.Filler Vertical(int space) {
        Dimension minSize = new Dimension(0, space);
        Dimension prefSize = new Dimension(0, space);
        Dimension maxSize = new Dimension(0, space);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

}
