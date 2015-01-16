/**
 * 
 */
package binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple binary tree class that allows
 * insertion, clearing, and three types of traversal.
 * 
 * @param <T> The object type to store in this collection.
 * @author Joe Pelz
 * @version 1.0
 */
public class SimpleTree<T extends Comparable<T>> {
    /** The data stored in this node. */
    public T data;
    /** A subtree of comparably lesser values. */
    public SimpleTree<T> left;
    /** A subtree of comparably greater values. */
    public SimpleTree<T> right;

    /**
     * Constructor, to initialize an empty simple tree. 
     *
     */
    public SimpleTree() { }
    /**
     * Constructor, to initialize a simple tree with one node. 
     * 
     * @param element The element to be stored in this node.
     */
    public SimpleTree(T element) {
        data = element;
    }

    /**
     * Add a node to the tree. It will be auto-placed 
     * or an exception will be thrown.
     * 
     * @param element The element to add.
     */
    public void add(T element) {
        if (data == null) {
            data = element;
        } else if (element.compareTo(data) < 0) {
            if (left == null) {
                left = new SimpleTree<T>(element);
            } else {
                left.add(element);
            }
        } else if (element.compareTo(data) > 0) {
            if (right == null) {
                right = new SimpleTree<T>(element);
            } else {
                right.add(element);
            }
        } else {
            throw new IllegalArgumentException("Number already exists.");
        }
    }
    
    /**
     * Add all the elements in the given collection to the tree.
     * 
     * @param values The collection of elements to add.
     */
    public void addAll(Collection<T> values) {
        for (T value : values) {
            try {
                add(value);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
    }
    
    /**
     * rebalance the tree to have evenly distributed nodes.
     */
    public void rebalance() {
        ArrayList<T> vals = getInOrderValues(this);
        List<T> result = rebalance(vals);
        
        clear();
        addAll(result);
    }
    
    /**
     * internal helper recursive function to rebalance the tree. 
     * 
     * @param vals The ordered array to rebalance.
     * @return A proper rearranged list
     */
    private List<T> rebalance(List<T> vals) {
        List<T> result = new ArrayList<T>();
        if (vals.size() == 0) {
            return result;
        }
        int center = vals.size() / 2;
        result.add(vals.get(center));
        
        if (center > 0) {
            result.addAll(rebalance(vals.subList(0, center)));
        }
        
        if (center + 1 <= vals.size()) {
            result.addAll(rebalance(vals.subList(center + 1, vals.size())));
        }
        
        return result;
    }

    /**
     * return a string describing an In-Order traversal of the tree.
     * 
     * @return The in-order traversal string.
     */
    public String getInOrder() { return getInOrder(this); }
    /**
     * return a string describing an Pre-Order traversal of the tree.
     * 
     * @return The pre-order traversal string.
     */
    public String getPreOrder() { return getPreOrder(this); }
    /**
     * return a string describing an Post-Order traversal of the tree.
     * 
     * @return The post-order traversal string.
     */
    public String getPostOrder() { return getPostOrder(this); }

    /**
     * return a string describing an In-Order traversal of the tree.
     * 
     * @param tree The tree to traverse.
     * @return The in-order traversal string.
     */
    public static String getInOrder(SimpleTree<?> tree) {
        if (tree == null || tree.data == null) {
            return "";
        }
        return getInOrder(tree.left) 
                + ((tree.left != null) ? ", " : "") 
                + tree.data + ((tree.right != null) ? ", " : "") 
                + getInOrder(tree.right);
    }

    /**
     * return a string describing an Pre-Order traversal of the tree.
     * 
     * @param tree The tree to traverse.
     * @return The pre-order traversal string.
     */
    public static String getPreOrder(SimpleTree<?> tree) {
        if (tree == null || tree.data == null) {
            return "";
        }
        return tree.data 
                + ((tree.left != null) ? ", " : "") 
                + getPreOrder(tree.left) 
                + ((tree.right != null) ? ", " : "") 
                + getPreOrder(tree.right);
    }

    /**
     * return a string describing an Post-Order traversal of the tree.
     * 
     * @param tree The tree to traverse.
     * @return The Post-order traversal string.
     */
    public static String getPostOrder(SimpleTree<?> tree) {
        if (tree == null || tree.data == null) {
            return "";
        }
        return getPostOrder(tree.left) 
                + ((tree.left != null) ? ", " : "") 
                + getPostOrder(tree.right) 
                + ((tree.right != null) ? ", " : "") 
                + tree.data;
    }
    
    /**
     * Get an array of all in-order values from the tree or subtree.
     * 
     * @param <T> The type of data stored in the tree.
     * @param tree The tree to examine
     * @return An array of in-order values from the tree.
     */
    public static <T> ArrayList<T> getInOrderValues(SimpleTree<? extends T> tree) {
        ArrayList<T> result = new ArrayList<T>();
        if (tree == null || tree.data == null) {
            return result;
        }
        result.addAll(getInOrderValues(tree.left));
        result.add(tree.data);
        result.addAll(getInOrderValues(tree.right));
        return result;
    }
    
    /**
     * Remove all nodes from the tree.
     */
    public void clear() {
        data = null;
        left = null;
        right = null;
    }
}
