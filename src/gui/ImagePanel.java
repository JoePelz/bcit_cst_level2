package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.Utils;

public class ImagePanel extends JPanel {

    /** Random generated serial id. */
    private static final long serialVersionUID = 1773275490065175902L;
    private BufferedImage image;
    @SuppressWarnings("unused")
    private ImageIcon images;
    private Image img;

    public ImagePanel(String path) {
        URL res;
        Toolkit defToolkit = Toolkit.getDefaultToolkit();

        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        images = new ImageIcon(getClass().getResource(path));
        res = getClass().getResource(path);
        img = defToolkit.getImage(res);
        image = Utils.toBufferedImage(img);
        
        Dimension size = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the
                                        // parameters
    }
}