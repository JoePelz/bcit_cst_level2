package settheory;

import java.util.HashMap;

/**
 * This class holds the current state of a Venn diagram.  
 * It maps on or off to each of the regions.
 * The current state can be modified by operations 
 * such as and, or, minus, xor, and invert.
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetsState {
    /** A list of all possible regions in the set map.
     * (assuming a max of four circles) */
    private static final String[] CBNS = {"U", "A", "B", "C", "D", 
        "AB", "AC", "AD", "BC", "BD", "CD", 
        "ABC", "ABD", "ACD", "BCD", "ABCD"};
    /** A map associating each region with on or off */
    private HashMap<String, Boolean> map;
    
    /** The region this state represented when constructed. */
    private String original;

    /** Construct a state with all regions set to off. */
    public SetsState() {
        map = new HashMap<String, Boolean>();
        for (String s : CBNS) {
            map.put(s, false);
        }
        original = "\0";
    }

    /**
     * Construct a state as a duplicate of another state.
     * @param h The state to replicate.
     */
    public SetsState(HashMap<String, Boolean> h) {
        map = new HashMap<String, Boolean>();
        map.putAll(h);
        original = "\0";
    }
    
    /**
     * Construct a state with the given region (A, B, C, D, or U)
     * being set to on.
     * 
     * @param s The region to turn on.
     */
    public SetsState(final String s) {
        map = getSimpleMap(s);
        original = s;
    }
    
    /**
     * Construct a state with the given region (A, B, C, D, or U)
     * being set to on.
     * 
     * @param s The region to turn on.
     */
    public SetsState(final char s) {
        map = getSimpleMap(s);
        original = "" + s;
    }

    /**
     * Construct a state with the given region (A, B, C, D, or U)
     * being set to on.
     * 
     * @param SetLetter The region to turn on.
     * @return The newly constructed state.
     */
    public static HashMap<String, Boolean> getSimpleMap(final String SetLetter) {
        return getSimpleMap(SetLetter.charAt(0));
    }

    /**
     * Construct a state with the given region (A, B, C, D, or U)
     * being set to on.
     * 
     * @param SetLetter The region to turn on.
     * @return The newly constructed state.
     */
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
    
    /**
     * Get the original region this SetsState was constructed with.
     * 
     * @return The creation region
     */
    public String getSource() {
        return original;
    }
    
    /**
     * Get the current state of a given region of the SetsState.
     * e.g. get("AB") returns true or false,
     * if that region is turned on or not.
     * 
     * @param s The region to query
     * @return the on/off state of that region
     */
    public boolean get(final String s) {
        Boolean b = map.get(s);
        if (b != null) 
            return b;
        return false;
    }

    /**
     * Get the inner map used to store the state.
     * 
     * @return The map object used to store the state.
     */
    public HashMap<String, Boolean> getMap() {
        return map;
    }
    
    /** 
     * flip the state of all regions between on and off.
     */
    public void invert() {
        for (String s : map.keySet()) {
            map.put(s, !map.get(s));
        }
        
    }

    /**
     * Perform a logical AND between this SetsState 
     * and the given SetsState. 
     * <p>The result is stored in this SetsState.</p>
     * 
     * @param op2 The second operand of the AND operation.
     */
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
    
    /**
     * Perform a logical OR between this SetsState
     * and the given SetsState.
     * <p>The result is stored in this SetsState.</p>
     * 
     * @param op2 The second operand of the OR operation.
     */
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
    
    /**
     * Perform a logical subtraction between this SetsState
     * and the given SetsState.
     * <table border=1><tr><td>A</td><td>B</td><td>Result</td></tr>
     * <tr><td>0</td><td>0</td><td>0</td></tr>
     * <tr><td>0</td><td>1</td><td>0</td></tr>
     * <tr><td>1</td><td>0</td><td>1</td></tr>
     * <tr><td>1</td><td>1</td><td>0</td></tr></table>
     * <p>The result is stored in this SetsState.</p>
     * 
     * @param op2 The second operand of the MINUS operation.
     */
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
    
    /**
     * Perform a logical XOR between this SetsState
     * and the given SetsState.
     * <p>The result is stored in this SetsState.</p>
     * 
     * @param op2 The second operand of the XOR operation.
     */
    public void xor(SetsState op2) {
        HashMap<String, Boolean> other = op2.getMap();
        for (String s : map.keySet()) {
            try {
                map.put(s, (map.get(s) && !other.get(s)) || (!map.get(s) && other.get(s)));
            } catch (NullPointerException e) {
                System.err.println("oops.");
            }
        }
    }
}
