package settheory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import util.Utils;

/**
 * A Venn diagram with four overlapping circles, 
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Venn4 extends Venn {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    /** The image of the outlines of the shapes. 
     * (a quartet of black overlapping circle) */
    private BufferedImage iOutline;
    /** An image of the A region, shaded. */
    /** An image of the A region, shaded. */
    private BufferedImage iA;
    /** An image of the B region, shaded. */
    private BufferedImage iB;
    /** An image of the C region, shaded. */
    private BufferedImage iC;
    /** An image of the C region, shaded. */
    private BufferedImage iD;
    /** An image of the AB region, shaded. */
    private BufferedImage iAB;
    /** An image of the AC region, shaded. */
    private BufferedImage iAC;
    /** An image of the BC region, shaded. */
    private BufferedImage iBC;
    /** An image of the AD region, shaded. */
    private BufferedImage iAD;
    /** An image of the BD region, shaded. */
    private BufferedImage iBD;
    /** An image of the CD region, shaded. */
    private BufferedImage iCD;
    /** An image of the ABC region, shaded. */
    private BufferedImage iABC;
    /** An image of the ABD region, shaded. */
    private BufferedImage iABD;
    /** An image of the ACD region, shaded. */
    private BufferedImage iACD;
    /** An image of the BCD region, shaded. */
    private BufferedImage iBCD;
    /** An image of the ABCD region, shaded. */
    private BufferedImage iABCD;
    /** An image of the universe, shaded. */
    private BufferedImage iU;
    /** A construction object for loading images. */
    @SuppressWarnings("unused")
    private ImageIcon image;
    
    /** The current state of the diagram. */
    SetsEquation map;

    /**
     * Construct a Venn diagram with three circles.
     */
    public Venn4() {
        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        String base = "/images/";
        String path = base + "Venn4_frame.png";
        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        image = new ImageIcon(getClass().getResource(path));
        iOutline = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));

        path = base + "Venn4_A.png";
        image = new ImageIcon(getClass().getResource(path));
        iA = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_AB.png";
        image = new ImageIcon(getClass().getResource(path));
        iAB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_B.png";
        image = new ImageIcon(getClass().getResource(path));
        iB = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_AC.png";
        image = new ImageIcon(getClass().getResource(path));
        iAC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_ABC.png";
        image = new ImageIcon(getClass().getResource(path));
        iABC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_BC.png";
        image = new ImageIcon(getClass().getResource(path));
        iBC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_C.png";
        image = new ImageIcon(getClass().getResource(path));
        iC = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_D.png";
        image = new ImageIcon(getClass().getResource(path));
        iD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_AD.png";
        image = new ImageIcon(getClass().getResource(path));
        iAD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_BD.png";
        image = new ImageIcon(getClass().getResource(path));
        iBD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_CD.png";
        image = new ImageIcon(getClass().getResource(path));
        iCD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_ABD.png";
        image = new ImageIcon(getClass().getResource(path));
        iABD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_ACD.png";
        image = new ImageIcon(getClass().getResource(path));
        iACD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_BCD.png";
        image = new ImageIcon(getClass().getResource(path));
        iBCD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_ABCD.png";
        image = new ImageIcon(getClass().getResource(path));
        iABCD = Utils.toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn4_U.png";
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
            if (map.getVennSection("U")) 
                g.drawImage(iU, 0, 0, null); 

            if (map.getVennSection("A"))
                g.drawImage(iA, 0, 0, null); 
            if (map.getVennSection("B")) 
                g.drawImage(iB, 0, 0, null);
            if (map.getVennSection("C")) 
                g.drawImage(iC, 0, 0, null);
            if (map.getVennSection("D")) 
                g.drawImage(iD, 0, 0, null);

            if (map.getVennSection("AB"))
                g.drawImage(iAB, 0, 0, null);
            if (map.getVennSection("AC")) 
                g.drawImage(iAC, 0, 0, null);
            if (map.getVennSection("AD")) 
                g.drawImage(iAD, 0, 0, null);
            if (map.getVennSection("BC")) 
                g.drawImage(iBC, 0, 0, null);
            if (map.getVennSection("BD")) 
                g.drawImage(iBD, 0, 0, null);
            if (map.getVennSection("CD")) 
                g.drawImage(iCD, 0, 0, null);

            if (map.getVennSection("ABC")) 
                g.drawImage(iABC, 0, 0, null);
            if (map.getVennSection("ABD")) 
                g.drawImage(iABD, 0, 0, null);
            if (map.getVennSection("ACD")) 
                g.drawImage(iACD, 0, 0, null);
            if (map.getVennSection("BCD")) 
                g.drawImage(iBCD, 0, 0, null);

            if (map.getVennSection("ABCD")) 
                g.drawImage(iABCD, 0, 0, null);
        }
    }
    
    @Override
    public void setMap(SetsEquation calc) {
        map = calc;
    }
}