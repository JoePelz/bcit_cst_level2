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
    private Gate gateOut;
    private Gate gateIn;
    private int portOut;
    private int portIn;
    
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

    /**
     * 
     */
    public void disconnect() {
        
        //disconnect input
        //disconnect output
        
    }
}
