package util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Format class, to parse a string 
 * and check if it's valid as binary or not. 
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class BinaryFormat extends Format{
    /** Unique id for serialization. */
    private static final long serialVersionUID = -3159208893249662145L;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        String src = obj.toString();
        
        pos.setBeginIndex(pos.getEndIndex());
        int index = 0;
        
        for (char c: src.toCharArray()) {
            if (c == '0' || c == '1') {
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
        
        StringBuffer result = new StringBuffer();
        int index = 0;
        for (char c: source.toCharArray()) {
            if (c == '0' || c == '1') {
                result.append(c);
                pos.setIndex(index);
            } else {
                pos.setErrorIndex(index);
                break;
            }
            index++;
        }
        pos.setIndex(index);
        if (index > 0)
            pos.setErrorIndex(-1);
        if (result.length() == 0)
            return null;
        else
            return result.toString();
    }

}
