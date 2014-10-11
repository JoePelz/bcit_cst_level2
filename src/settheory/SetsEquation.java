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
public class SetsEquation {
    private static final String[] CBNS = {"U", "A", "B", "C", "D", 
        "AB", "AC", "AD", "BC", "BD", "CD", 
        "ABC", "ABD", "ACD", "BCD", "ABCD"};
    private final String givenEquation;
    private SetsState results;
    private HashMap<Character, SetsState> refs = new HashMap<Character, SetsState>();
    private char refs_i = 'A';
    
    public SetsEquation(final String eq) {
        String transform = eq.replaceAll(" ", "");
        transform = transform.replaceAll("∪", "+");
        transform = transform.replaceAll("∩", "*");
        givenEquation = transform;
        
//        System.out.println("\n==================\nBegin parse: " + givenEquation);
        results = parseEquation(substituteSets(givenEquation));
    }

    public String getEquation() {
        return givenEquation;
    }
    
    private boolean reserved(char c) {
        if (c == '(' 
                || c == ')'
                || c == '+'
                || c == '-'
                || c == '\''
                || c == '*') {
            return true;
        }
        return false;
    }
    
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
    
    private SetsState parseEquation(String eq) {
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
    
    /**
     * 
     * @param target the venn section you want, boolean eg: A, CD, CDB, U 
     */
    public boolean getVennSection(String target) {
        return results.get(target);
    }
}
