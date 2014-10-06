package gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class UnderlinedChars extends JLabel {

    private static final long serialVersionUID = -3089351308056853205L;
    private int spacing = 2;

    public UnderlinedChars() {
        this("");
    }

    public UnderlinedChars(String text) {
        super(text);
    }

    public void paint(Graphics g) {
        Rectangle r;
        super.paint(g);
        r = g.getClipBounds();
        
        int prelength = 0;
        int length = 0;
        for (int i = 0; i < getText().length(); i++) {
            if (getText().charAt(i) == ' ')
                continue;
            
            if (getHorizontalAlignment() == RIGHT) {
                prelength = getFontMetrics(getFont()).stringWidth(getText().substring(i+1));
                length = getFontMetrics(getFont()).stringWidth(getText().substring(i, i+1));
                g.drawLine(r.width - prelength - length + 1,
                        r.height - getFontMetrics(getFont()).getDescent(),
                        r.width - prelength - 2,
                        r.height - getFontMetrics(getFont()).getDescent());
            } else {
                prelength = getFontMetrics(getFont()).stringWidth(getText().substring(0, i));
                length = getFontMetrics(getFont()).stringWidth(getText().substring(i, i+1));
                g.drawLine(prelength + spacing,
                        r.height - getFontMetrics(getFont()).getDescent(),
                        prelength + length - spacing,
                        r.height - getFontMetrics(getFont()).getDescent());
            }
        }
    }
}