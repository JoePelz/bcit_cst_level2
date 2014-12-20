package gui;

import java.awt.Color;
import java.text.Format;
import java.text.ParsePosition;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Caret;

/**
 * This class validates user input to a format, and indicates to the user
 * (through colors) whether the input is valid or not.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class EquationField extends JTextField {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = -4679363073411866988L;
    /** Color of the background if an error is detected. */
    private static final Color ERROR_BACKGROUND_COLOR = new Color(255, 215, 215);
    /** The color of the forground if an error is detected. */
    private static final Color ERROR_FOREGROUND_COLOR = null;

    /** The normal colors of the background and foreground. */
    private Color fBackground, fForeground;

    /** The formatter to use for validation. */
    private Format formatter;

    /** The saved selection and caret location for insertions. */
    private int selStart;
    /** The saved selection and caret location for insertions. */
    private int selEnd;

    /**
     * Create a new {@code ImprovedFormattedTextField} instance which will use
     * {@code aFormat} for the validation of the user input.
     *
     * @param aFormat
     *            The format. May not be {@code null}
     */
    public EquationField(Format aFormat) {
        updateBackgroundOnEachUpdate();
        formatter = aFormat;
        preserveSelection();
    }

    /**
     * Add a listener to the caret to keep track of where the caret last was.
     */
    private void preserveSelection() {
        this.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent c) {
                selStart = Math.min(c.getDot(), c.getMark());
                selEnd = Math.max(c.getDot(), c.getMark());
            }
        });
    }

    /**
     * Insert text into the field, at the current selection point.
     * 
     * @param push
     *            The text to insert.
     */
    public void pushText(String push) {
        // endpoint must be saved because setText will change selEnd
        int savedEnd = selEnd;

        String left = getText().substring(0, selStart);
        String right = getText().substring(selEnd);
        setText(left + push + right);

        // Restore caret position
        Caret c = getCaret();
        c.setDot(savedEnd + push.length());
    }

    /**
     * Create a new {@code ImprovedFormattedTextField} instance which will use
     * {@code aFormat} for the validation of the user input. The field will be
     * initialized with {@code aValue}.
     *
     * @param aFormat
     *            The format. May not be {@code null}
     * @param aValue
     *            The initial value
     */
    public EquationField(Format aFormat, String aValue) {
        this(aFormat);
        setText(aValue);
    }

    /**
     * Add a document listener to track changes to the content of the field and
     * update the background to show if there's an error.
     */
    private void updateBackgroundOnEachUpdate() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBackground();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBackground();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateBackground();
            }
        });
    }

    /**
     * Update the background color depending on the valid state of the current
     * input. This provides visual feedback to the user
     */
    private void updateBackground() {
        boolean valid = validContent();
        if (ERROR_BACKGROUND_COLOR != null) {
            setBackground(valid ? fBackground : ERROR_BACKGROUND_COLOR);
        }
        if (ERROR_FOREGROUND_COLOR != null) {
            setForeground(valid ? fForeground : ERROR_FOREGROUND_COLOR);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        fBackground = getBackground();
        fForeground = getForeground();
    }

    /**
     * Check if the current content is valid.
     * 
     * @return True if the current content is valid.
     */
    public boolean validContent() {
        ParsePosition pp = new ParsePosition(0);
        if (formatter != null) {
            formatter.parseObject(getText(), pp);
            if (pp.getErrorIndex() != -1)
                return false;
            return true;
        }
        System.out
                .println("Something went wrong in validContent of EquationField.");
        return true;
    }
}
