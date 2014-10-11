package settheory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Venn2 extends Venn {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    private BufferedImage iOutline;
    private BufferedImage iA;
    private BufferedImage iAB;
    private BufferedImage iB;
    private BufferedImage iU;
    @SuppressWarnings("unused")
    private ImageIcon image;
    
    SetsEquation map;

    public Venn2() {
        Toolkit defToolkit = Toolkit.getDefaultToolkit();
        String base = "/images/";
        String path = base + "Venn2_frame.png";
        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        image = new ImageIcon(getClass().getResource(path));
        iOutline = toBufferedImage(defToolkit.getImage(getClass().getResource(path)));

        path = base + "Venn2_A.png";
        image = new ImageIcon(getClass().getResource(path));
        iA = toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_AB.png";
        image = new ImageIcon(getClass().getResource(path));
        iAB = toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_B.png";
        image = new ImageIcon(getClass().getResource(path));
        iB = toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
        path = base + "Venn2_U.png";
        image = new ImageIcon(getClass().getResource(path));
        iU = toBufferedImage(defToolkit.getImage(getClass().getResource(path)));
        
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

    public void setMap(SetsEquation calc) {
        map = calc;
    }
}