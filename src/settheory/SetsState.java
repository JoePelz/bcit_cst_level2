/**
 * 
 */
package settheory;

import java.util.HashMap;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetsState {
    private static final String[] CBNS = {"U", "A", "B", "C", "D", 
        "AB", "AC", "AD", "BC", "BD", "CD", 
        "ABC", "ABD", "ACD", "BCD", "ABCD"};
    private HashMap<String, Boolean> map;
    
    private String original;

    public SetsState() {
        map = new HashMap<String, Boolean>();
        for (String s : CBNS) {
            map.put(s, false);
        }
        original = "\0";
    }

    public SetsState(HashMap<String, Boolean> h) {
        map = new HashMap<String, Boolean>();
        map.putAll(h);
        original = "\0";
    }
    
    public SetsState(String s) {
        map = getSimpleMap(s);
        original = s;
    }
    
    public SetsState(char s) {
        map = getSimpleMap(s);
        original = "" + s;
    }

    public static HashMap<String, Boolean> getSimpleMap(final String SetLetter) {
        return getSimpleMap(SetLetter.charAt(0));
    }

    public static HashMap<String, Boolean> getSimpleMap(final char SetLetter) {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        for (String s : CBNS) {
            if (s.indexOf(SetLetter) != -1) {
                map.put(s, true);
            } else {
                map.put(s, false);
            }
        }
        return map;
    }
    
    public String getSource() {
        return original;
    }
    
    public boolean get(final String s) {
        Boolean b = map.get(s);
        if (b != null) 
            return b;
        return false;
    }

    public HashMap<String, Boolean> getMap() {
        return map;
    }
    
    public void invert() {
        for (String s : map.keySet()) {
            map.put(s, !map.get(s));
        }
        
    }

    public void and(SetsState op2) {
        HashMap<String, Boolean> other = op2.getMap();
        for (String s : map.keySet()) {
            try {
                map.put(s, map.get(s) && other.get(s));
            } catch (NullPointerException e) {
                System.err.println("oops.");
            }
        }
    }
    
    public void or(SetsState op2) {
        HashMap<String, Boolean> other = op2.getMap();
        for (String s : map.keySet()) {
            try {
                map.put(s, map.get(s) || other.get(s));
            } catch (NullPointerException e) {
                System.err.println("oops.");
            }
        }
    }

    public void minus(SetsState op2) {
        HashMap<String, Boolean> other = op2.getMap();
        for (String s : map.keySet()) {
            try {
                map.put(s, other.get(s) ? false : map.get(s));
            } catch (NullPointerException e) {
                System.err.println("oops.");
            }
        }
    }
}
