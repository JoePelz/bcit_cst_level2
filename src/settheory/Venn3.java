package settheory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.Utils;

/**
 * A Venn diagram with three overlapping circles.
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Venn3 extends Venn {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    /** The image of the outlines of the shapes. 
     * (a trio of black overlapping circle) */
    private BufferedImage iOutline;
    /** An image of the A region, shaded. */
    private BufferedImage iA;
    /** An image of the B region, shaded. */
    private BufferedImage iB;
    /** An image of the C region, shaded. */
    private BufferedImage iC;
    /** An image of the AB region, shaded. */
    private BufferedImage iAB;
    /** An image of the AC region, shaded. */
    private BufferedImage iAC;
    /** An image of the BC region, shaded. */
    private BufferedImage iBC;
    /** An image of the ABC region, shaded. */
    private BufferedImage iABC;
    /** An image of the Universe region, shaded. */
    private BufferedImage iU;
    /** A construction object for loading images. */
    @SuppressWarnings("unused")
    private ImageIcon image;
    
    /** The current state of the diagram. */
    private SetsEquation map;

    /**
     * Construct a Venn diagram with three circles.
     */
    public Venn3() {
        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        String base = "/images/";
        String path = base + "Venn3_frame.png";
        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        image = new ImageIcon(getClass().getResource(path));
        iOutline = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));

        path = base + "Venn3_A.png";
        image = new ImageIcon(getClass().getResource(path));
        iA = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_AB.png";
        image = new ImageIcon(getClass().getResource(path));
        iAB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_B.png";
        image = new ImageIcon(getClass().getResource(path));
        iB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_AC.png";
        image = new ImageIcon(getClass().getResource(path));
        iAC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_ABC.png";
        image = new ImageIcon(getClass().getResource(path));
        iABC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_BC.png";
        image = new ImageIcon(getClass().getResource(path));
        iBC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_C.png";
        image = new ImageIcon(getClass().getResource(path));
        iC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn3_U.png";
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
            if (map.getVennSection("A")) {
                g.drawImage(iA, 0, 0, null);
            } 
            if (map.getVennSection("AB")) {
                g.drawImage(iAB, 0, 0, null);
            }
            if (map.getVennSection("B")) {
                g.drawImage(iB, 0, 0, null);
            }
            if (map.getVennSection("AC")) {
                g.drawImage(iAC, 0, 0, null);
            }
            if (map.getVennSection("ABC")) {
                g.drawImage(iABC, 0, 0, null);
            }
            if (map.getVennSection("BC")) {
                g.drawImage(iBC, 0, 0, null);
            }
            if (map.getVennSection("C")) {
                g.drawImage(iC, 0, 0, null);
            }
            if (map.getVennSection("U")) {
                g.drawImage(iU, 0, 0, null);
            } 
        }
    }

    @Override
    public void setMap(SetsEquation calc) {
        map = calc;
    }
}
