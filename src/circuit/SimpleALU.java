/**
 * 
 */
package circuit;

import gui.shapes.Gate;
import gui.shapes.GateAnd;
import gui.shapes.GateInput;
import gui.shapes.GateNot;
import gui.shapes.GateOr;
import gui.shapes.GatePin;
import gui.shapes.GateState;
import gui.shapes.Link;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class SimpleALU extends Circuit {

    //==================================================
    //Decoder
    //==================================================
    private GateAnd dec_and00    = new GateAnd  (200, 150, 2);
    private GateAnd dec_and01    = new GateAnd  (200, 200, 2);
    private GateAnd dec_and10    = new GateAnd  (200, 250, 2);
    private GateAnd dec_and11    = new GateAnd  (200, 300, 2);
    private GateNot dec_not00a   = new GateNot  (140, 140, 2);
    private GateNot dec_not00b   = new GateNot  ( 90, 160, 2);
    private GateNot dec_not01    = new GateNot  (140, 190, 2);
    private GateNot dec_not10    = new GateNot  (140, 260, 2);
    private GatePin dec_output00 = new GatePin  (330, 150, 2);
    private GatePin dec_output01 = new GatePin  (350, 200, 2);
    private GatePin dec_output10 = new GatePin  (370, 250, 2);
    private GatePin dec_output11 = new GatePin  (390, 300, 2);
    private GatePin dec_nodeA0   = new GatePin  ( 30, 140, 2);
    private GatePin dec_nodeA1   = new GatePin  ( 30, 190, 2);
    private GatePin dec_nodeA2   = new GatePin  ( 30, 240, 2);
    private GatePin dec_nodeA3   = new GatePin  ( 30, 290, 2);
    private GatePin dec_nodeB0   = new GatePin  ( 50, 160, 2);
    private GatePin dec_nodeB1   = new GatePin  ( 50, 210, 2);
    private GatePin dec_nodeB2   = new GatePin  ( 50, 260, 2);
    private GatePin dec_nodeB3   = new GatePin  ( 50, 310, 2);
    private GateInput dec_inputA = new GateInput(-50, 140, 2, GateState.ON);
    private GateInput dec_inputB = new GateInput(-50, 160, 2, GateState.OFF);
    

    //==================================================
    //Full Adder
    //==================================================
    private GateOr    fa_gxor   = new GateOr   (500,  80, 2);
    private GateOr    fa_gxor2  = new GateOr   (630,  90, 2);
    private GateAnd   fa_ga     = new GateAnd  (500, 170, 2);
    private GateAnd   fa_ga2    = new GateAnd  (630, 140, 2);
    private GateOr    fa_gCarry = new GateOr   (720, 160, 2);
    private GatePin   fa_pin2   = new GatePin  (480,  70, 2);
    private GatePin   fa_pin5   = new GatePin  (460,  90, 2);
    private GateInput fa_cIn    = new GateInput(600, -150, 2, GateState.ON);
    private GatePin   fa_pin7   = new GatePin  (600, 100, 2);
    private GatePin   fa_pin9   = new GatePin  (580,  80, 2);
    private GatePin   fa_sum    = new GatePin  (900, 100, 2);
    private GatePin   fa_cOut   = new GatePin  (900, 300, 2);
    private GateAnd   fa_enSum  = new GateAnd  (830, 100, 2);
    private GateAnd   fa_enCO   = new GateAnd  (830, 170, 2);
    private GatePin   fa_EnACO  = new GatePin  (800,  180, 2);
    
    //==================================================
    //Interconnects
    //==================================================
    private GateInput inINVA     = new GateInput(-50, -100, 2, GateState.OFF);
    private GateInput inA        = new GateInput(-50, -70, 2, GateState.ON);
    private GateInput inENA      = new GateInput(-50, -50, 2, GateState.ON);
    private GateInput inB        = new GateInput(-50, -20, 2, GateState.ON);
    private GateInput inENB      = new GateInput(-50, 0, 2, GateState.ON);
    private GateAnd   inAndA     = new GateAnd  ( -5, -60, 2);
    private GateAnd   inAndB     = new GateAnd  ( -5, -10, 2);
    private GateOr    inXorA     = new GateOr   ( 80, -70, 2);
    private GatePin   invJoint1  = new GatePin  ( 55, -100, 2);
    private GateOr    fin_preOr1 = new GateOr   (920, -70, 2);
    private GateOr    fin_preOr2 = new GateOr   (920, -20, 2);
    private GatePin   preOr1a    = new GatePin  (500, -80, 2);
    private GatePin   preOr1b    = new GatePin  (500, -60, 2);
    private GatePin   preOr2a    = new GatePin  (500, -30, 2);
    private GateOr    finOr      = new GateOr   (1000, -40, 2);
    private GatePin   output     = new GatePin  (1100, -40, 2);
    
    //==================================================
    // Logic Unit
    //==================================================
    private GatePin   lu_inA     = new GatePin  (200, -70, 2);
    private GatePin   lu_inB     = new GatePin  (220,  -10, 2);
    private GateAnd   lu_AandB   = new GateAnd  (250, -110, 2);
    private GateOr    lu_AorB    = new GateOr   (250, -60, 2);
    private GateNot   lu_notB    = new GateNot  (250,  -10, 2);
    private GatePin   lu_jA1     = new GatePin  (200, -120, 2);
    private GatePin   lu_jB1     = new GatePin  (220, -50, 2);
    private GatePin   lu_jB2     = new GatePin  (220, -100, 2);
    private GateAnd   lu_enAnd   = new GateAnd  (400, -100, 2);
    private GateAnd   lu_enOr    = new GateAnd  (400, -50, 2);
    private GateAnd   lu_enNot   = new GateAnd  (400, 0, 2);
    
    
    public SimpleALU() {
        
        //Add decoder
        gates.add(dec_and00);
        gates.add(dec_and01);
        gates.add(dec_and10);
        gates.add(dec_and11);
        gates.add(dec_not00a);
        gates.add(dec_not00b);
        gates.add(dec_not01);
        gates.add(dec_not10);
        gates.add(dec_output00);
        gates.add(dec_output01);
        gates.add(dec_output10);
        gates.add(dec_output11);
        gates.add(dec_nodeA0);
        gates.add(dec_nodeA1);
        gates.add(dec_nodeA2);
        gates.add(dec_nodeA3);
        gates.add(dec_nodeB0);
        gates.add(dec_nodeB1);
        gates.add(dec_nodeB2);
        gates.add(dec_nodeB3);
        gates.add(dec_inputA);
        gates.add(dec_inputB);
        dec_output00.setLabel("00");
        dec_output00.setLabelSide(GatePin.NW);
        dec_output01.setLabel("01");
        dec_output01.setLabelSide(GatePin.NW);
        dec_output10.setLabel("10");
        dec_output10.setLabelSide(GatePin.NW);
        dec_output11.setLabel("11");
        dec_output11.setLabelSide(GatePin.NW);
        dec_inputA.setLabel("F0");
        dec_inputB.setLabel("F1");
        
        //add Adder
        gates.add(fa_ga);
        gates.add(fa_ga2);
        gates.add(fa_gxor);
        gates.add(fa_gxor2);
        gates.add(fa_gCarry);
        gates.add(fa_pin2);
        gates.add(fa_pin5);
        gates.add(fa_cIn);
        gates.add(fa_pin7);
        gates.add(fa_pin9);
        gates.add(fa_sum);
        gates.add(fa_cOut);
        gates.add(fa_enCO);
        gates.add(fa_enSum);
        fa_gxor.setVariation(GateOr.XOR);
        fa_gxor2.setVariation(GateOr.XOR);
        fa_cIn.setLabel("C-In");
        fa_sum.setLabel("Sum");
        fa_sum.setLabelSide(GatePin.NE);
        fa_cOut.setLabel("C-Out");
        fa_cOut.setLabelSide(GatePin.SE);
        
        //add Interconnects
        gates.add(fa_EnACO);
        gates.add(inINVA);
        gates.add(inA);
        gates.add(inENA);
        gates.add(inB);
        gates.add(inENB);
        gates.add(inAndA);
        gates.add(inAndB);
        gates.add(inXorA);
        gates.add(invJoint1);
        gates.add(fin_preOr1);
        gates.add(fin_preOr2);
        gates.add(preOr1a);
        gates.add(preOr1b);
        gates.add(preOr2a);
        gates.add(finOr);
        gates.add(output);
        inXorA.setVariation(GateOr.XOR);
        inINVA.setLabel("INVA");
        inA.setLabel("A");
        inENA.setLabel("ENA");
        inB.setLabel("B");
        inENB.setLabel("ENB");
        preOr1a.setLabel("Logical And");
        preOr1b.setLabel("Logical Or");
        preOr2a.setLabel("Logical Not");
        
        output.setLabel("Output");
        output.setLabelSide(GatePin.NW);
        
        //add Logic Unit
        gates.add(lu_inA);
        gates.add(lu_inB);
        gates.add(lu_AandB);
        gates.add(lu_AorB);
        gates.add(lu_notB);
        gates.add(lu_jA1);
        gates.add(lu_jB1);
        gates.add(lu_jB2);
        gates.add(lu_enAnd);
        gates.add(lu_enOr);
        gates.add(lu_enNot);

        //Connect Decoder
        Gate.connect(dec_and00, 0,   dec_output00, 0);
        Gate.connect(dec_and01, 0,   dec_output01, 0);
        Gate.connect(dec_and10, 0,   dec_output10, 0);
        Gate.connect(dec_and11, 0,   dec_output11, 0);
        Gate.connect(dec_not00a, 0,  dec_and00, 0);
        Gate.connect(dec_nodeA0, 0,  dec_not00a, 0);
        Gate.connect(dec_nodeB0, 0,  dec_not00b, 0);
        Gate.connect(dec_not00b, 0,  dec_and00, 1);
        Gate.connect(dec_not01, 0,   dec_and01, 0);
        Gate.connect(dec_nodeA1, 0,  dec_not01, 0);
        Gate.connect(dec_nodeB1, 0,  dec_and01, 1);
        Gate.connect(dec_nodeA2, 0,  dec_and10, 0);
        Gate.connect(dec_nodeB2, 0,  dec_not10, 0);
        Gate.connect(dec_not10, 0,   dec_and10, 1);
        Gate.connect(dec_nodeA3, 0,  dec_and11, 0);
        Gate.connect(dec_nodeB3, 0,  dec_and11, 1);
        Gate.connect(dec_inputA, 0,  dec_nodeA0, 0);
        Gate.connect(dec_inputB, 0,  dec_nodeB0, 0);
        Gate.connect(dec_nodeA0, -1, dec_nodeA1, -1);
        Gate.connect(dec_nodeB0, -1, dec_nodeB1, -1);
        Gate.connect(dec_nodeA1, -1, dec_nodeA2, -1);
        Gate.connect(dec_nodeB1, -1, dec_nodeB2, -1);
        Gate.connect(dec_nodeA2, -1, dec_nodeA3, -1);
        Gate.connect(dec_nodeB2, -1, dec_nodeB3, -1);
        
        //Connect Adder
        Gate.connect(lu_inA, -1, fa_pin2, 0, Link.VH);
        Gate.connect(lu_inB, -1, fa_pin5, 0, Link.VH);
        Gate.connect(fa_pin2, 0,   fa_ga, 0, Link.VH);
        Gate.connect(fa_pin5, 0,   fa_ga, 1, Link.VH);
        Gate.connect(fa_pin2, -1,  fa_gxor, -1);
        Gate.connect(fa_pin5, -1,  fa_gxor, -1);
        Gate.connect(fa_cIn,  -1,  fa_pin7, -1);
        Gate.connect(fa_pin7, -1,  fa_gxor2, 1);
        Gate.connect(fa_pin7, -1,  fa_ga2,  0, Link.VH);
        Gate.connect(fa_gxor,  0,  fa_pin9, 0);
        Gate.connect(fa_pin9, -1,  fa_gxor2, 0);
        Gate.connect(fa_pin9, -1,  fa_ga2, 1, Link.VH);
        Gate.connect(fa_gxor2,  0, fa_enSum, 0);
        Gate.connect(fa_enSum,  0, fa_sum, 0);
        Gate.connect(fa_ga2,   -1, fa_gCarry, -1);
        Gate.connect(fa_ga,    -1, fa_gCarry, -1);
        Gate.connect(fa_gCarry, 0, fa_enCO, 0);
        Gate.connect(fa_enCO, 0,   fa_cOut, 0, Link.HV);
        
        //Connect Components
        Gate.connect(dec_output11, -1, fa_EnACO, -1, Link.HV);
        Gate.connect(fa_EnACO, -1,     fa_enSum, 1, Link.VH);
        Gate.connect(fa_EnACO, -1,     fa_enCO, 1);
        Gate.connect(inA, -1,          inAndA, 0);
        Gate.connect(inENA, -1,        inAndA, 1);
        Gate.connect(inAndA, -1,       inXorA, 1);
        Gate.connect(inB, -1,          inAndB, 0);
        Gate.connect(inENB, -1,        inAndB, 1);
        Gate.connect(inINVA, -1,       invJoint1, -1);
        Gate.connect(invJoint1, -1,    inXorA, 0, Link.VH);
        Gate.connect(inXorA, -1,       lu_inA, -1);
        Gate.connect(inAndB, -1,       lu_inB, -1);
        Gate.connect(dec_output00, -1, lu_enAnd, 1, Link.VH);
        Gate.connect(dec_output01, -1, lu_enOr,  1, Link.VH);
        Gate.connect(dec_output10, -1, lu_enNot, 1, Link.VH);
        Gate.connect(preOr1a, -1, fin_preOr1, 0);
        Gate.connect(preOr1b, -1, fin_preOr1, 1);
        Gate.connect(preOr2a, -1, fin_preOr2, 0);
        Gate.connect(lu_enAnd, -1, preOr1a, -1, Link.HV);
        Gate.connect(lu_enOr,  -1, preOr1b, -1, Link.HV);
        Gate.connect(lu_enNot, -1, preOr2a, -1, Link.HV);
        Gate.connect(fa_sum,   -1, fin_preOr2, 1, Link.VH);
        Gate.connect(fin_preOr1, 0, finOr, 0);
        Gate.connect(fin_preOr2, 0, finOr, 1);
        Gate.connect(finOr, -1, output, -1);
        
        //Connect Logic Unit
        Gate.connect(lu_inB, -1,   lu_notB, 0);
        Gate.connect(lu_inA, -1,   lu_AorB, 0);
        Gate.connect(lu_inA, -1,   lu_jA1, -1);
        Gate.connect(lu_jA1, -1,   lu_AandB, 0);
        Gate.connect(lu_inB, -1,   lu_jB1, -1);
        Gate.connect(lu_jB1, -1,   lu_AorB, 1);
        Gate.connect(lu_jB1, -1,   lu_jB2, -1);
        Gate.connect(lu_jB2, -1,   lu_AandB, 1);
        Gate.connect(lu_AandB, -1, lu_enAnd, 0);
        Gate.connect(lu_AorB,  -1, lu_enOr, 0);
        Gate.connect(lu_notB,  -1, lu_enNot, 0);
    }
}
