/**
 * 
 */
package util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import settheory.SetsEquation;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetEqFormat extends Format{
    private static final long serialVersionUID = -3159208893249662145L;

    public boolean isValidChar(char c) {
        if (c == ' ' 
                || c == '(' 
                || c == ')' 
                || (c >= 'A' && c <= 'D') 
                || c == '+' 
                || c == '∪' 
                || c == '-' 
                || c == '*' 
                || c == '∩' 
                || c == '∆' 
                || c == '^'
                || c == '\'')
            return true;
        return false;
    }
    
    /* (non-Javadoc)
     * @see java.text.Format#format(java.lang.Object, java.lang.StringBuffer, java.text.FieldPosition)
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
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

    /* (non-Javadoc)
     * @see java.text.Format#parseObject(java.lang.String, java.text.ParsePosition)
     */
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
        } catch (Exception e) {
            pos.setErrorIndex(source.length());
        }
        
        /*
        if (source.length() == 0)
            return null;
        else
            return source;
        */
        return source;
    }

}
