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

/**
 * This class will display an image from the given path in a panel.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    /** Unique id for serialization. */
    private static final long serialVersionUID = 1773275490065175902L;
    /** The stored image to display. */
    private BufferedImage image;
    /** A temp object to load the image. */
    @SuppressWarnings("unused")
    private ImageIcon images;

    /**
     * Constructor, build a new panel showing the image 
     * found at the given path. 
     * 
     * @param path The path to the image to display.
     */
    public ImagePanel(String path) {
        URL res;
        Toolkit defToolkit = Toolkit.getDefaultToolkit();

        /* Don't ask me why this next line is important. Just trust me.
         * It has something to do with image observers, and without it
         * a call to image.getWidth(null) fails. 
         */
        images = new ImageIcon(getClass().getResource(path));
        res = getClass().getResource(path);
        Image img = defToolkit.getImage(res);
        image = Utils.toBufferedImage(img);
        
        Dimension size = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
