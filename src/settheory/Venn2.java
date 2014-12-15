package settheory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.Utils;

/**
 * A Venn diagram with two overlapping circles, 
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Venn2 extends Venn {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    /** The image of the outline of the shapes. 
     * (a pair of black overlapping circle) */
    private BufferedImage iOutline;
    /** An image of the A region of the Venn diagram, filled in. */
    private BufferedImage iA;
    /** An image of the AB region of the Venn diagram, filled in. */
    private BufferedImage iAB;
    /** An image of the B region of the Venn diagram, filled in. */
    private BufferedImage iB;
    /** An image of the Outside region of the Venn diagram,
     *  filled in. */
    private BufferedImage iU;
    /** A construction object for loading images. */
    @SuppressWarnings("unused")
    private ImageIcon image;
    
    /** The current state of the diagram. */
    SetsEquation map;

    /**
     * Construct a Venn diagram with two circles.
     */
    public Venn2() {
        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        String base = "/images/";
        String path = base + "Venn2_frame.png";
        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        image = new ImageIcon(getClass().getResource(path));
        iOutline = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));

        path = base + "Venn2_A.png";
        image = new ImageIcon(getClass().getResource(path));
        iA = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_AB.png";
        image = new ImageIcon(getClass().getResource(path));
        iAB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_B.png";
        image = new ImageIcon(getClass().getResource(path));
        iB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_U.png";
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
            if (map.getVennSection("AB"))
                g.drawImage(iAB, 0, 0, null);
            if (map.getVennSection("B")) 
                g.drawImage(iB, 0, 0, null);
            if (map.getVennSection("U")) 
                g.drawImage(iU, 0, 0, null); 
        }
    }

    @Override
    public void setMap(SetsEquation calc) {
        map = calc;
    }
}