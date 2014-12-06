/**
 * 
 */
package gui.shapes;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public class Link {
    public static final int HVH = 0;
    public static final int VHV = 1;
    public static final int STRAIGHT = 2;
    public static final int HV = 3;
    public static final int VH = 4;

    private Gate gateOut;
    private Gate gateIn;
    private int portOut;
    private int portIn;
    private int style = 0;
    
    public Link(Gate out, int portOut, Gate in, int portIn) {
        gateOut = out;
        this.portOut = portOut;
        gateIn = in;
        this.portIn = portIn;
    }

    public Gate getGateOut() {
        return gateOut;
    }

    public Gate getGateIn() {
        return gateIn;
    }

    public int getPortOut() {
        return portOut;
    }

    public void setPortOut(int portOut) {
        this.portOut = portOut;
    }

    public int getPortIn() {
        return portIn;
    }

    public void setPortIn(int portIn) {
        this.portIn = portIn;
    }

    /** Not implemented yet.
     * 
     */
    public void disconnect() {
        //TODO:implement
        //disconnect input
        //disconnect output
        
    }

    public int getStyle() {
        return style;
    }
    
    public void setStyle(int style) {
        this.style = style;
    }
}
