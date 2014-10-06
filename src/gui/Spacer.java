/**
 * 
 */
package gui;

import java.awt.Dimension;

import javax.swing.Box;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Spacer {

    public static Box.Filler HorizontalStretch(int space) {
        Dimension minSize = new Dimension(space, 0);
        Dimension prefSize = new Dimension(space, 0);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 0);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

    public static Box.Filler Horizontal(int space) {
        Dimension minSize = new Dimension(space, 0);
        Dimension prefSize = new Dimension(space, 0);
        Dimension maxSize = new Dimension(space, 0);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

    public static Box.Filler VerticalStretch(int space) {
        Dimension minSize = new Dimension(0, space);
        Dimension prefSize = new Dimension(0, space);
        Dimension maxSize = new Dimension(0, Short.MAX_VALUE);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }
    
    public static Box.Filler Vertical(int space) {
        Dimension minSize = new Dimension(0, space);
        Dimension prefSize = new Dimension(0, space);
        Dimension maxSize = new Dimension(0, space);
        
        return new Box.Filler(minSize, prefSize, maxSize);
    }

}
