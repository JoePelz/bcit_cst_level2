/**
 * 
 */
package settheory;

import java.util.HashMap;


/**
 * This class holds a String equation 
 * and the calculated result of computing that equation.
 * 
 * 
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetsEquation {
    /** The equation itself, stored as a string */
    private final String givenEquation;
    /** The computed result of <code>givenEquation</code>. */
    private SetsState results;
    /** Internal reference associating letters with SetsStates. */
    private HashMap<Character, SetsState> refs = new HashMap<Character, SetsState>();
    /** The next available index in the above internal reference. */
    private char refs_i = 'A';
    
    /**
     * Constructor, gets rid of spaces and 
     * replaces all weird symbols with a more consistent 
     * set of symbols: + / * / ^  
     * @param eq The string holding the equation.
     */
    public SetsEquation(final String eq) {
        String transform = eq.replaceAll(" ", "");
        transform = transform.replaceAll("∪", "+");
        transform = transform.replaceAll("∩", "*");
        transform = transform.replaceAll("∆", "^");
        givenEquation = transform;
        
//        System.out.println("\n==================\nBegin parse: " + givenEquation);
        results = parseEquation(substituteSets(givenEquation));
    }

    /**
     * Get the original String equation for this object.
     * 
     * @return The source String for the object.
     */
    public String getEquation() {
        return givenEquation;
    }
    
    /**
     * Check if a given character is one of the reserved 
     * operation characters or not. 
     * <p>Chars include: <code>(, ), +, -, \, *, ^</code></p>
     * 
     * @param c The character to check.
     * @return true if the character is part of the reserved set.
     */
    private boolean reserved(char c) {
        if (c == '(' 
                || c == ')'
                || c == '+'
                || c == '-'
                || c == '\''
                || c == '*'
                || c == '^') {
            return true;
        }
        return false;
    }
    
    /**
     * Replaces constants (A, B, ...) in the equation
     * with SetsState instances holding the state of the diagram.
     * 
     * @param eq The equation string to operate on.
     * @return The equation string, with constants replaced by references to SetsStates (using <code>refs</code>)
     */
    private String substituteSets(final String eq) {
        String output;
        StringBuffer newString = new StringBuffer(eq.length());
        for (char c : eq.toCharArray()) {
            if (c >= 'A' && c <= 'D') {
                refs.put(refs_i, new SetsState(c));
                newString.append(refs_i);
                refs_i++;
            } else if (reserved(c)){
                newString.append(c);
            } else {
                throw new IllegalArgumentException("Invalid Equation");
            }
        }
        output = newString.toString();
        return output;
    }
    
    /**
     * <p>Calculate the final result of the givenEquation.</p>
     * <p>Use order of operations and parse the inner-most 
     * operation until only the final value</p>
     * 
     * @param eq The equation to parse, that has 
     *      already had each constant substituted via substituteSets
     * @return The final SetsState, parsed from the equation.
     */
    private SetsState parseEquation(String eq) {
        if (eq.length() == 0) {
            return new SetsState();
        }
        int opPos;
        int opPos2;
        int opParen;
        SetsState op1;
        SetsState op2;
        
//        System.out.println("Starting " + eq);
        //=============================
        //while there is a )
        //  find the ( before it
        //  save before(...)after
        //  evaluate (...) (recursively) 
        //  and substitute.
        opPos = eq.indexOf(')');
        while (opPos != -1) {
            //find the matching parenthesis.
            opParen = eq.lastIndexOf('(', opPos);
            if (opParen == -1) {
                throw new IllegalArgumentException("Invalid Equation");
            }
            
            //break into start/middle/end strings
            String start = eq.substring(0, opParen);
            String middle = eq.substring(opParen + 1, opPos); 
            String end = eq.substring(opPos + 1);
            
//            System.out.println(eq + ": Parens: " + middle);
            //recourse the middle
            op1 = parseEquation(middle); 
            
            //add middle to refs
            refs.put(refs_i,  op1);
            
            //concatenate middle with ends
            eq = start + refs_i + end;
            refs_i++;
            
            opPos = eq.indexOf(')');
        }
        
        //=============================
        //while there is a NOT operator
        //  invert the preceding set
        opPos = eq.indexOf('\'');
        while (opPos != -1) {
//            System.out.println(eq + ": Not.");
            op1 = refs.get(eq.charAt(opPos - 1));
            op1.invert();
            
            //remove the ' from the equation.
            eq = eq.substring(0, opPos) + eq.substring(opPos + 1);
            opPos = eq.indexOf('\'');
        }

        //=============================
        //while there is a AND operator
        //  connect the sets
        //  save the results as op1
        //  sub the results back in.
        opPos = eq.indexOf('*');
        while (opPos != -1) {
//            System.out.println(eq + ": And.");
            try {
                op1 = refs.get(eq.charAt(opPos - 1));
                op2 = refs.get(eq.charAt(opPos + 1));
            } catch (StringIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid Equation");
            }
            op1.and(op2);
            
            //Turn A*B into A.  (remove the * and the B)
            eq = eq.substring(0, opPos) + eq.substring(opPos + 2);
            opPos = eq.indexOf('*');
        }

        //=============================
        //while there is a XOR operator
        //  connect the sets
        //  save the results as op1
        //  sub the results back in.
        opPos = eq.indexOf('^');
        while (opPos != -1) {
//            System.out.println(eq + ": Xor.");
            try {
                op1 = refs.get(eq.charAt(opPos - 1));
                op2 = refs.get(eq.charAt(opPos + 1));
            } catch (StringIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid Equation");
            }
            op1.xor(op2);
            
            //Turn A^B into A.  (remove the ^ and the B)
            eq = eq.substring(0, opPos) + eq.substring(opPos + 2);
            opPos = eq.indexOf('^');
        }
        
        //=============================
        //while there is an OR or MINUS operator
        //  connect the sets
        //  save the results as op1
        //  sub the results back in.
        opPos = eq.indexOf('+');
        opPos2 = eq.indexOf('-');
        if (opPos == -1)
            opPos = opPos2;
        if (opPos2 == -1)
            opPos2 = opPos;
        opPos = Math.min(opPos, opPos2);

        while (opPos != -1) {
            try {
                op1 = refs.get(eq.charAt(opPos - 1));
                op2 = refs.get(eq.charAt(opPos + 1));
            } catch (StringIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid Equation");
            }
            if (eq.charAt(opPos) == '+') {
//                System.out.println(eq + ": Or.");
                op1.or(op2);
            } else if (eq.charAt(opPos) == '-') {
//                System.out.println(eq + ": Minus.");
                op1.minus(op2);
            }
            
            //Turn A*B into A.  (remove the * and the B)
            eq = eq.substring(0, opPos) + eq.substring(opPos + 2);

            //find the next one
            opPos = eq.indexOf('+');
            opPos2 = eq.indexOf('-');
            if (opPos == -1)
                opPos = opPos2;
            else if (opPos2 == -1)
                opPos2 = opPos;
        }
        
        //=============================
        // Should be done now.
        if (eq.length() != 1) {
            throw new IllegalArgumentException("Invalid Equation");
        }
        
        return refs.get(eq.charAt(0));
    }
    
    /**
     * Get the state of a particular region in the resulting Venn diagram. 
     * 
     * @param target the Venn region you want, boolean eg: A, CD, CDB, U 
     * @return True if the venn region is 'on'.
     */
    public boolean getVennSection(String target) {
        return results.get(target);
    }
}
