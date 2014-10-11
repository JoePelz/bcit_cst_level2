/**
 * 
 */
package settheory;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SetsTest {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
//        String eq = "B - (A + B)";
        String eq = "(B - (A + B))";
        SetsEquation sq = new SetsEquation(eq);
        
        System.out.println("\n====\nStart: " + eq);
        System.out.println("A: " + sq.getVennSection("A"));
        System.out.println("AB: " + sq.getVennSection("AB"));
        System.out.println("B: " + sq.getVennSection("B"));
//        System.out.println("\nAC: " + sq.getVennSection("AC"));
//        System.out.println("ABC: " + sq.getVennSection("ABC"));
//        System.out.println("BC: " + sq.getVennSection("BC"));
//        System.out.println("\nC: " + sq.getVennSection("C"));
        System.out.println("\nU: " + sq.getVennSection("U"));
        
    }

}
