package settheory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.Utils;

/**
 * One-region venn diagram. 
 * Not very useful, but included for completeness.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class Venn1 extends Venn {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    /** The image of the outline of the shapes. (a black circle) */
    private BufferedImage iOutline;
    /** The image of A region, filled in. */
    private BufferedImage iA;
    /** The image of the universe outside of any shapes. */
    private BufferedImage iU;
    /** A construction object for loading images. */
    @SuppressWarnings("unused")
    private ImageIcon image;
    
    /** The current state of the diagram. */
    SetsEquation map;

    /**
     * Construct a venn diagram with 1 circle.
     * Not particularly useful, but included for completeness.
     */
    public Venn1() {
        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        String base = "/images/";
        String path = base + "Venn1_frame.png";
        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. (gives -1) 
         */
        image = new ImageIcon(getClass().getResource(path));
        iOutline = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));

        path = base + "Venn1_A.png";
        image = new ImageIcon(getClass().getResource(path));
        iA = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn1_U.png";
        image = new ImageIcon(getClass().getResource(path));
        iU = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        Dimension size = new Dimension(iOutline.getWidth(), iOutline.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(iOutline, 0, 0, null);
        
        if (map != null) {
            if (map.getVennSection("A"))
                g.drawImage(iA, 0, 0, null);
            if (map.getVennSection("U")) 
                g.drawImage(iU, 0, 0, null); 
        }
    }

    @Override
    public void setMap(SetsEquation calc) {
        map = calc;
    }
}