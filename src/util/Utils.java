/**
 * 
 */
package util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Utils {
    /**
     * Convert an Image into a BufferedImage, so that it's editable.
     * @param img the image to convert
     * @return the image as a BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), 
                                                 img.getHeight(null), 
                                                 BufferedImage.TYPE_4BYTE_ABGR);

        // Draw the image on to the buffered image
        Graphics bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
