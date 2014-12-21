package util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import settheory.SetsEquation;

/**
 * This class represents a formatter for strings 
 * that represent valid set theory equations 
 * using sets A, B, C, D, and U for the universe. 
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetEqFormat extends Format {
    /** Unique ID for serialization. */
    private static final long serialVersionUID = -3159208893249662145L;

    /**
     * Check if a given character is a valid charcter 
     * in a Set equation string.
     * 
     * @param c the character to check
     * @return true if the character is valid. false otherwise.
     */
    public static boolean isValidChar(char c) {
        if (c == ' ' 
                || c == '(' 
                || c == ')' 
                || (c >= 'A' && c <= 'D') 
                || c == '+' 
                || c == '\u222A'
                || c == '-' 
                || c == '*' 
                || c == '\u2229' 
                || c == '\u2206' 
                || c == '^'
                || c == '\'') {
            return true;
        }
        return false;
    }
    
    @Override
    public StringBuffer format(Object obj, 
            StringBuffer toAppendTo, 
            FieldPosition pos) {
        String src = obj.toString();
        
        pos.setBeginIndex(pos.getEndIndex());
        int index = 0;
        
        for (char c: src.toCharArray()) {
            if (isValidChar(c)) {
                toAppendTo.append(c);
                index++;
            }
        }

        pos.setEndIndex(pos.getBeginIndex() + index);
        
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        if (pos == null || source == null) {
            throw new NullPointerException();
        }
        

        //How to tell if it's a valid string?
        // Run it through the parser and see if it fails! :D
        // TODO:  I should probably just have SetsEquation 
        //        implement Format or something.
        try {
            @SuppressWarnings("unused")
            SetsEquation test = new SetsEquation(source);
            pos.setIndex(source.length());
        } catch (IllegalArgumentException e) {
            pos.setErrorIndex(source.length());
        }
        
        return source;
    }

}
