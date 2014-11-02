/**
 * 
 */
package gui.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * 
 * @author Joe Pelz - A00893517
 * @version 1.0
 */
public abstract class Gate extends Shape{
    private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    private ArrayList<Polygon> lines = new ArrayList<Polygon>();
    private Color backgroundOn = Color.YELLOW;
    private Color backgroundOff = Color.GRAY;
    private Color strokeOn = Color.BLACK;
    private Color strokeOff = Color.BLACK;
    private GateState enabled;

    protected Link outputPorts[];
    private boolean outputResize;
    protected Link inputPorts[];
    private boolean inputResize;
    
    public Gate(int x, int y, int thickness, int in, int out) throws IllegalArgumentException {
        super(x, y, thickness);
        if (out == -1) {
            outputPorts = new Link[1];
        } else if (out > 0) {
            outputPorts = new Link[out];
        } else {
            throw new IllegalArgumentException("Invalid number of output gates. (must be positive integer or -1)");
        }
        
        if (in == -1) {
            inputPorts = new Link[1];
        } else if (in > 0) {
            inputPorts = new Link[in];
        } else {
            throw new IllegalArgumentException("Invalid number of input gates. (must be positive integer or -1)");
        }
    }
    
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
                Link temp[] = new Link[inputPorts.length + 1];
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
                Link temp[] = new Link[outputPorts.length + 1];
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
    
    public void disconnectOut(int port) {
        outputPorts[port] = null;
    }
    
    public void disconnectIn(int port) {
        inputPorts[port] = null;
    }

    public abstract Point getOutput(int i);
    public abstract Point getInput(int i);
    
    /**
     * @return the gate state.
     */
    public GateState getState() {
        return enabled;
    }
    
    /**
     * @param enabled the gate state. true for enabled.
     */
    public void setState(GateState enabled) {
        this.enabled = enabled;
    }

    protected void addShape(Polygon p) {
        polygons.add(p);
    }
    
    protected void addLine(Polygon p) {
        lines.add(p);
    }
    
    @Override
    public void drawStroke(Graphics2D g) {
        if (!valid) 
            updatePoints();

        //color by state
        if (enabled == GateState.ON) { 
            g.setColor(strokeOn);
        } else {
            g.setColor(strokeOff);
        }
        //Stroke all shapes
        g.setStroke(getStroke());
        for (Polygon p : polygons) {
            g.drawPolygon(p);
        }
        for (Polygon p : lines) {
            g.drawPolyline(p.xpoints, p.ypoints, p.npoints);
        }
        
        //draw input connections.
        for (Link link : inputPorts) {
            if (link != null) {
                Point a = link.getGateOut().getOutput(link.getPortOut());
                Point b = getInput(link.getPortIn());

                if (link.getGateOut().getState() == GateState.ON) {
                    g.setColor(backgroundOn);
                } else {
                    g.setColor(strokeOff);
                }
                g.drawLine(a.x, a.y, b.x, b.y);
            }
        }
    }

    @Override
    public void drawFill(Graphics2D g) {
        if (!valid) 
            updatePoints();

        //color by state
        if (enabled == GateState.ON) { 
            g.setColor(backgroundOn);
        } else {
            g.setColor(backgroundOff);
        }
        //Fill all shapes
        for (Polygon p : polygons) {
            g.fillPolygon(p);
        }
    }
    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }
    public ArrayList<Polygon> getLines() {
        return lines;
    }

    public Color getBackground() {
        if (enabled == GateState.ON)
            return backgroundOn;
        else
            return backgroundOff;
    }

    public Color getStrokeOn() {
        if (enabled == GateState.ON)
            return strokeOn;
        else
            return strokeOff;
    }

    public void setBackgroundOn(Color backgroundOn) {
        this.backgroundOn = backgroundOn;
    }

    public void setBackgroundOff(Color backgroundOff) {
        this.backgroundOff = backgroundOff;
    }

    public void setStrokeOn(Color strokeOn) {
        this.strokeOn = strokeOn;
    }

    public void setStrokeOff(Color strokeOff) {
        this.strokeOff = strokeOff;
    }
    
    public static void connect(Gate gateOut, int portOut, Gate gateIn, int portIn) {
        Link link = new Link(gateOut, portOut, gateIn, portIn);
        gateOut.connectOut(link);
        gateIn.connectIn(link);
    }

    public int calcOut() {
        boolean out = false;
        for(Link l : inputPorts) {
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
     * 
     */
    public void perform(int clickX, int clickY) {
        //this gate was clicked on!
    }
}
