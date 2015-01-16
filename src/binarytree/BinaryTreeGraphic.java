package binarytree;

import gui.shapes.BinaryTreeNumber;
import gui.shapes.Gate;
import gui.shapes.Link;

import java.awt.Point;
import java.util.Collection;

import circuit.Circuit;

/**
 * This graphic displays a binary tree with links.
 * It can be updated via setPreOrder, 
 * and rebalanced via rebalanceTree().
 * 
 * @author Joe Pelz
 * @version 1.0
 */
public class BinaryTreeGraphic extends Circuit {
    /** The horizontal spacing between nodes. */ 
    private static final int X_SPACING = 100;
    /** The vertical spacing between nodes. */
    private static final int Y_SPACING = 150;

    /** The binary tree of values itself. */
    private SimpleTree<Integer> data = new SimpleTree<Integer>();
    
    /**
     * Constructor, initialized a Binary Tree graphic.
     *
     */
    public BinaryTreeGraphic() {
        super();
        SimpleTree<Integer> bob = new SimpleTree<Integer>();
        for (String num : "3 2 7 6 8".split(" ")) {
            bob.add(Integer.parseInt(num));
        }
        buildGraph(bob, 0, 0);
    }
    
    /**
     * rearrange the tree to be evenly distributed 
     * and then redraw it.  
     */
    public void rebalanceTree() {
        data.rebalance();
        buildGraph(data);
        repaint();
    }
    
    /**
     * Update the graphic to display the given set of 
     * values in the given order.
     * 
     * @param values The values to insert into the Binary Tree.
     */
    public void setPreOrder(Collection<Integer> values) {
        data.clear();
        data.addAll(values);
        buildGraph(data);
    }
    
    /**
     * Accessor for the SimpleTree tree itself.
     * 
     * @return The internal binary tree
     */
    public SimpleTree<Integer> getTree() {
        return data;
    }
    
    /**
     * Build the display graphic from the given tree root.
     * 
     * @param tree the binary tree root to start drawing at.
     * Upon completion, arrange data more nicely,
     * and focus the viewport.
     */
    private void buildGraph(SimpleTree<Integer> tree) {
        gates.clear();
        buildGraph(tree, 0, 0);
        rearrangeGraph();
        focusView();
    }

    /**
     * Recursive helper function to build the graphic.
     * Recursively adds each subtree to the collection.
     * 
     * @param tree The subtree to graph.
     * @param x The x position to draw it at.
     * @param y The y position to draw it at.
     * @return The node that is built.
     */
    private BinaryTreeNumber buildGraph(SimpleTree<Integer> tree, int x, int y) {
        if (tree == null || tree.data == null) {
            return null;
        }
        BinaryTreeNumber result = new BinaryTreeNumber(x, y, tree.data.intValue()); 
        gates.add(result);
        Gate cnx1 = buildGraph(tree.left, x - X_SPACING, y + Y_SPACING);
        if (cnx1 != null) {
            Gate.connect(result, -1, cnx1, -1, Link.STRAIGHT);
        }
        Gate cnx2 = buildGraph(tree.right, x + X_SPACING, y + Y_SPACING);
        if (cnx2 != null) {
            Gate.connect(result, -1, cnx2, -1, Link.STRAIGHT);
        }
        return result;
    }
    
    /**
     * <p>Take each node and rearrange it 
     * so that each node is in it's own column.</p>
     * 
     * <p>This is done by evenly spacing the values of the 
     * inOrder traversal on the x axis 
     * without modifying the existing y axis values.</p>
     */
    private void rearrangeGraph() {
        String inOrder = data.getInOrder();
        String[] nodes = inOrder.split(", ");
        int x;
        for (Gate g : gates) {
            Point oldPos = g.getPosition();
            x = 0;
            for (int i = 0; ((BinaryTreeNumber) g).getValue() != Integer.parseInt(nodes[i]); i++) {
                x += X_SPACING;
            }
            g.setPosition(x, oldPos.y);
        }
    }
}
