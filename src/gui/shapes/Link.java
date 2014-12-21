package gui.shapes;

/**
 * Class to wire up components together. Creates wires between components.
 * TODO: put drawing code in the link class.
 * @author Joe Pelz
 * @version 1.0
 */
public class Link {
    /** Draw the wire as horizontal, 
     * with a vertical connection in the middle. */
    public static final int HVH = 0;
    /** Draw the wire as vertical, 
     * with a horizontal connection in the middle. */
    public static final int VHV = 1;
    /** Draw the wire as straight between the out and in ports. */
    public static final int STRAIGHT = 2;
    /** Draw the wire horizontally then vertically. */
    public static final int HV = 3;
    /** Draw the wire vertically then horizontally. */
    public static final int VH = 4;

    /** The output gate of the link. */
    private Gate gateOut;
    /** The input gate of the link. */
    private Gate gateIn;
    /** The output port of the link. */
    private int portOut;
    /** The input port of the link. */
    private int portIn;

    /** The draw style of the link. */
    private int style;
    
    /**
     * Constructor, create a connection 
     * between the given gates, 
     * through the given ports. 
     * 
     * @param out The source gate
     * @param portOut The source port
     * @param in The destination gate
     * @param portIn The destination port
     */
    public Link(Gate out, int portOut, Gate in, int portIn) {
        gateOut = out;
        this.portOut = portOut;
        gateIn = in;
        this.portIn = portIn;
    }

    /**
     * Get the output gate, at the start of the link.
     * 
     * @return The output gate
     */
    public Gate getGateOut() {
        return gateOut;
    }

    /**
     * Get the input gate, at the end of the link.
     * 
     * @return The input gate
     */
    public Gate getGateIn() {
        return gateIn;
    }

    /**
     * Get the output port, at the start of the link.
     * 
     * @return The output port
     */
    public int getPortOut() {
        return portOut;
    }

    /**
     * Get the input port, at the end of the link.
     * 
     * @return The input port
     */
    public int getPortIn() {
        return portIn;
    }
    
    /**
     * Set the port used by the output / source gate.
     * 
     * @param port The port to use.
     */
    public void setPortOut(int port) {
        portOut = port;
    }

    /**
     * Set the port used by the input / destination gate.
     * 
     * @param port The port to use.
     */
    public void setPortIn(int port) {
        portIn = port;
    }
    
    /** 
     * Disconnect a link from both ends.  
     * Link should end up deleted. This is untested.
     * TODO: test link disconnection.
     */
    public void disconnect() {
        //disconnect input
        gateIn.disconnectIn(portIn);
        //disconnect output
        gateOut.disconnectOut(portOut);
    }

    /**
     * Get the draw style of the connection.
     * 
     * @return The drawing style.
     */
    public int getStyle() {
        return style;
    }
    
    /**
     *  Set the drawing style of the connection.
     * 
     * @param style The drawing style. 
     *              One of Link.VHV, HVH, HV, VH, STRAIGHT
     */
    public void setStyle(int style) {
        this.style = style;
    }
}
