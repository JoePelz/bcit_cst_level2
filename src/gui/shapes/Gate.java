/**
 * 
 */
package gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Base class for circuit gates. All gates should extend this class.
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public abstract class Gate extends Shape {
    /** Outbound connections to other gates. */
    protected Link[] outputPorts;
    /** Inbound connections to the gate. */
    protected Link[] inputPorts;
    
    /** The collection of closed polygons involved in drawing this gate. */
    private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    /** The collection of poly lines involved in drawing this gate. */
    private ArrayList<Polygon> lines = new ArrayList<Polygon>();
    /** The color to use for the ON state. */
    private Color backgroundOn = Color.YELLOW;
    /** The background color for the OFF state. */
    private Color backgroundOff = Color.GRAY;
    /** The outline color for the ON state. */
    private Color strokeOn = Color.BLACK;
    /** The outline color for the OFF state. */
    private Color strokeOff = Color.BLACK;
    /** Whether this gate is ON or OFF. True means the gate is ON. */
    private GateState enabled;
    /** The name of the gate, for debugging mostly. */
    private String name = "";

    /** True if the input can support infinite connections. */
    private boolean inputResize;
    /** True if the output can support infinite connections. */
    private boolean outputResize;
    
    /**
     * Constructor, to make a gate and place it in a particular place.  
     * 
     * @param x The x position to place the gate at.
     * @param y The y position to place the gate at.
     * @param thickness The thickness of the gate.
     * @param in The number of input connections permitted.
     * @param out The number of output connections permitted.
     * @throws IllegalArgumentException When {@code in} or {@code out} are 0 or <-1.
     */
    public Gate(int x, int y, int thickness, int in, int out) {
        super(x, y, thickness);
        if (out == -1) {
            outputPorts = new Link[1];
        } else if (out > 0) {
            outputPorts = new Link[out];
        } else {
            throw new IllegalArgumentException("Invalid number of "
                    + "output gates. (must be positive integer or -1)");
        }
        
        if (in == -1) {
            inputPorts = new Link[1];
        } else if (in > 0) {
            inputPorts = new Link[in];
        } else {
            throw new IllegalArgumentException("Invalid number of "
                    + "input gates. (must be positive integer or -1)");
        }
    }
    
    /**
     * Set the name of the gate. Mostly for debugging.
     * 
     * @param n the name of the gate.
     */
    public void setName(String n) {
        name = n;
    }
    
    /**
     * Get the name of the gate. Mostly for debugging.
     * 
     * @return The gate name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Plug the given link into the gate's input connections.
     * The link object specifies which port to connect to 
     * on the gate.
     * 
     * @param in The link to connect. 
     */
    public void connectIn(Link in) {
        if (in.getPortIn() == -1) {
            for (int i = 0; i < inputPorts.length; i++) {
                if (inputPorts[i] == null) {
                    inputPorts[i] = in;
                    in.setPortIn(i);
                    return;
                }
            }
            if (inputResize) {
                Link[] temp = new Link[inputPorts.length + 1];
                for (int i = 0; i < inputPorts.length; i++) {
                    temp[i] = inputPorts[i];
                }
                temp[inputPorts.length] = in;
                in.setPortIn(inputPorts.length);
                inputPorts = temp;
            }
        } else {
            try {
                if (inputPorts[in.getPortIn()] != null) {
                    inputPorts[in.getPortIn()].disconnect();
                }
                inputPorts[in.getPortIn()] = in;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.printf(
                        "GateIn port (%d) is outside of range 0:%d\n",
                        in.getPortIn(), inputPorts.length - 1);
                e.fillInStackTrace();
                throw e;
            }
        }
    }
    
    /**
     * Plug the given link into the gate's output connections.
     * The link object specifies which port to connect to 
     * on the gate.
     * 
     * @param out The link to connect.
     */
    public void connectOut(Link out) {
        if (out.getPortOut() == -1) {
            for (int i = 0; i < outputPorts.length; i++) {
                if (outputPorts[i] == null) {
                    outputPorts[i] = out;
                    out.setPortOut(i);
                    return;
                }
            }
            if (outputResize) {
                Link[] temp = new Link[outputPorts.length + 1];
                for (int i = 0; i < outputPorts.length; i++) {
                    temp[i] = outputPorts[i];
                }
                temp[outputPorts.length] = out;
                out.setPortOut(outputPorts.length);
                outputPorts = temp;
            }
        } else {
            try {
                if (outputPorts[out.getPortOut()] != null) {
                    outputPorts[out.getPortOut()].disconnect();
                }
                outputPorts[out.getPortOut()] = out;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.printf(
                        "GateOut port (%d) is outside of range 0:%d\n",
                        out.getPortOut(), outputPorts.length - 1);
                e.fillInStackTrace();
                throw e;
            }
        }
    }
    
    /**
     * Disconnect any link from the given output port.
     * 
     * @param port The out-bound connection to disconnect.
     * @throws IndexOutOfBoundsException if the port doesn't exist.
     */
    public void disconnectOut(int port) {
        outputPorts[port] = null;
    }
    
    /**
     * Disconnect any link from the given input port.
     * 
     * @param port The out-bound connection to disconnect.
     * @throws IndexOutOfBoundsException if the port doesn't exist.
     */
    public void disconnectIn(int port) {
        inputPorts[port] = null;
    }

    /**
     * Get the position of the given output port on this gate.
     * The position should be in gate space. (not world space)
     * 
     * @param i The port to get the position of.
     * @return The position in gate space of the given output port.
     */
    public abstract Point getOutput(int i);

    /**
     * Get the position of the given input port on this gate.
     * The position should be in gate space. (not world space)
     * 
     * @param i The port to get the position of.
     * @return The position in gate space of the given input port.
     */
    public abstract Point getInput(int i);
    
    /**
     * Get the current state of the gate. Can be one of: 
     * {@code GateState.ON}, 
     * {@code GateState.OFF}, 
     * {@code GateState.UNKNOWN}.
     *  
     * @return the gate state.
     */
    public GateState getState() {
        return enabled;
    }
    
    /**
     * Set the current state for the gate. 
     * 
     * @param state the gate state. true for enabled.
     */
    public void setState(GateState state) {
        enabled = state;
    }

    /**
     * Add a polygon shape to the object's closed-polygon list.
     * 
     * @param p The polygon to add.
     */
    protected void addShape(Polygon p) {
        polygons.add(p);
    }
    
    /**
     * Remove all shapes from the drawing lists.
     */
    protected void clearShapes() {
        polygons.clear();
        lines.clear();
    }
    
    /**
     * Add a polygon shape to the object's line-drawing list.
     * 
     * @param p The polygon to add.
     */
    protected void addLine(Polygon p) {
        lines.add(p);
    }
    
    @Override
    public void drawStroke(Graphics2D g) {
        if (!valid) {
            updatePoints();
        }

        
        
        //Draw input connections. (links)
        g.setStroke(new BasicStroke((float) (thickness * getTransform().getScaleY())));
        for (Link link : inputPorts) {
            if (link != null) {
                Point a = link.getGateOut().getOutput(link.getPortOut());
                AffineTransform aAT = link.getGateOut().getTransform();
                Point b = getInput(link.getPortIn());
                AffineTransform bAT = getTransform();
                
                aAT.transform(a, a);
                bAT.transform(b, b);
                
                if (link.getGateOut().getState() == GateState.ON) {
                    g.setColor(backgroundOn);
                } else {
                    g.setColor(strokeOff);
                }
                if (link.getStyle() == Link.HVH && a.y != b.y) {
                    g.drawLine(a.x, a.y, (b.x + a.x) / 2, a.y);
                    g.drawLine((b.x + a.x) / 2, a.y, (b.x + a.x) / 2, b.y);
                    g.drawLine((b.x + a.x) / 2, b.y, b.x, b.y);
                } else if (link.getStyle() == Link.VHV && a.x != b.x) {
                    g.drawLine(a.x, a.y, a.x, (a.y + b.y) / 2);
                    g.drawLine(a.x, (a.y + b.y) / 2, b.x, (a.y + b.y) / 2);
                    g.drawLine(b.x, (a.y + b.y) / 2, b.x, b.y);
                } else if (link.getStyle() == Link.HV) {
                    g.drawLine(a.x, a.y, b.x, a.y);
                    g.drawLine(b.x, a.y, b.x, b.y);
                } else if (link.getStyle() == Link.VH) {
                    g.drawLine(a.x, a.y, a.x, b.y);
                    g.drawLine(a.x, b.y, b.x, b.y);
                } else {
                    g.drawLine(a.x, a.y, b.x, b.y);
                }
            }
        }
        
        //color by state
        if (enabled == GateState.ON) { 
            g.setColor(strokeOn);
        } else {
            g.setColor(strokeOff);
        }
        //Stroke all shapes
        g.setStroke(getStroke());
        AffineTransform atOld = g.getTransform();
        g.transform(getTransform());
        for (Polygon p : polygons) {
            g.drawPolygon(p);
        }
        for (Polygon p : lines) {
            g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
        }
        g.setTransform(atOld);
    }

    @Override
    public void drawFill(Graphics2D g) {
        if (!valid) {
            updatePoints();
        }

        //color by state
        if (enabled == GateState.ON) { 
            g.setColor(backgroundOn);
        } else {
            g.setColor(backgroundOff);
        }
        //Fill all shapes
        AffineTransform atOld = g.getTransform();
        g.transform(getTransform());
        for (Polygon p : polygons) {
            g.fillPolygon(p);
        }
        g.setTransform(atOld);
    }
    
    /**
     * Get the list of polygons drawn by this gate.
     * 
     * @return The ArrayList of polygons that make this gate's shape.
     */
    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }
    
    /**
     * Get the list of polylines drawn by this gate.
     * 
     * @return The ArrayList of polygons that make this gate's lines.
     */
    public ArrayList<Polygon> getLines() {
        return lines;
    }

    /**
     * Get the background color of this gate. 
     * Depends on if the gate is ON or not.
     * 
     * @return The color the gate should be filled with.
     */
    public Color getBackground() {
        if (enabled == GateState.ON) {
            return backgroundOn;
        } else {
            return backgroundOff;
        }
    }

    /**
     * Get the stroke (outline) color of this gate.
     * This depends on if the gate is ON or not. 
     * 
     * @return The stroke color for this gate.
     */
    public Color getStrokeOn() {
        if (enabled == GateState.ON) {
            return strokeOn;
        } else {
            return strokeOff;
        }
    }

    /**
     * Set the background color for when the gate is on.
     * 
     * @param backgroundOn The color to set.
     */
    public void setBackgroundOn(Color backgroundOn) {
        this.backgroundOn = backgroundOn;
    }

    /**
     * Set the background color for when the gate is off.
     * 
     * @param backgroundOff The color to set.
     */
    public void setBackgroundOff(Color backgroundOff) {
        this.backgroundOff = backgroundOff;
    }

    /**
     * Set the stroke color for when the gate is on.
     * 
     * @param strokeOn The color to set.
     */
    public void setStrokeOn(Color strokeOn) {
        this.strokeOn = strokeOn;
    }

    /**
     * Set the stroke color for when the gate is off.
     * 
     * @param strokeOff The color to set.
     */
    public void setStrokeOff(Color strokeOff) {
        this.strokeOff = strokeOff;
    }

    /**
     * Create a connection between two gates 
     * given their out and in ports.
     * 
     * @param gateOut The source gate
     * @param portOut The output port on the source gate
     * @param gateIn The destination gate
     * @param portIn The input port on the destination gate
     */
    public static void connect(Gate gateOut, int portOut, Gate gateIn, int portIn) {
        Link link = new Link(gateOut, portOut, gateIn, portIn);
        gateOut.connectOut(link);
        gateIn.connectIn(link);
    }
    
    /**
     * Create a connection between two gates 
     * given their out and in ports. Also specify a style of connection for how to draw the wire. 
     * One of:
     * <ul>
     * <li> Link.VH (draw vertically, then horizontally)</li>
     * <li> Link.HV (draw horizontally, then vertically) </li>
     * <li> Link.STRAIGHT (draw a straight line) </li>
     * <li> Link.HVH (draw horizontally, but with a vertical line in 
     * the middle to connect the two ends) </li>
     * <li> Link.VHV (draw vertically, but with a horizontal line in 
     * the middle to connect the two ends) </li>
     * </ul>
     * 
     * @param gateOut The source gate
     * @param portOut The output port on the source gate
     * @param gateIn The destination gate
     * @param portIn The input port on the destination gate
     * @param style The style of the wire for the connection.
     */
    public static void connect(Gate gateOut, int portOut, Gate gateIn, int portIn, int style) {
        Link link = new Link(gateOut, portOut, gateIn, portIn);
        gateOut.connectOut(link);
        gateIn.connectIn(link);
        link.setStyle(style);
    }

    /**
     * <p>This method calculates the output of the gate 
     * based on the inputs available.</p>
     * 
     * <p>The inputs can be any of {GateState.ON, GateState.OFF, GateState.NULL, null}
     * and any number of them, depending on the gate.</p>  
     * 
     * <p>Default operation is logical OR.</p>
     * 
     * @return Whether the calcOut changed the value of this gate's output.
     */
    public int calcOut() {
        boolean out = false;
        for (Link l : inputPorts) {
            if (l == null) {
                continue;
            }
            if (l.getGateOut().getState() == GateState.ON) {
                out = true;
                break;
            }
        }
        if (out) {
            if (getState() != GateState.ON) {
                setState(GateState.ON);
                return 1;
            }
        } else {
            if (getState() != GateState.OFF) {
                setState(GateState.OFF);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Perform action if the gate is clicked on. Override to if needed.
     * 
     * @param clickX The mouse X coordinate
     * @param clickY The mouse Y coordinate
     */
    public void perform(int clickX, int clickY) {
        //this gate was clicked on!
    }

    /**
     * Get the bounding rectangle for this gate. 
     * Used to focus the viewport on the circuit.
     * 
     * @return The bounding rectangle of the gate.
     */
    public Rectangle getBounds() {
        final int size = 10;
        return new Rectangle(-size, -size, size, size);
    }
}
