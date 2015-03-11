
package graphprim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Vertex class, contains list of nodes with weight (adjacency list)
 * and basic vertex operations
 * @author Laurens van den Bercken, s4057384
 * @author Jeftha Spunda, s4174615
 */
public class Vertex implements Comparable {
   
    private double key; // key used for building MST
    private final int index; // index in the graph
    private Vertex parent; // parent vertex, used for building MST
    private Hashtable<Vertex, Double> outnodes; // Adjacency list, with weighted edges
    
    /**
     * Vertex constructor
     * @param index index in the graph
     */
    public Vertex(int index) {
        key = Double.MAX_VALUE;
        this.index = index;
        parent = null;
        outnodes = new Hashtable<>();
    }
    
    /**
     * Adds an adjacent node to this Vertex
     * @param node the adjacent node
     * @param weight adds node with this weight
     */
    public void addOutnode(Vertex node, double weight) {
        outnodes.put(node, weight);
    }
    
    /**
     * Deletes an adjacent node of this Vertex (if there exist an edge)
     * @param node node to be deleted
     */
    public void deleteOutnode(Vertex node) {
        // Precondition
        if (!outnodes.containsKey(node)) {
            System.out.println("Error: edge does not exist."
                    + "No edge has been deleted.");
            return;
        }
        outnodes.remove(node);
    }
    
    /**
     * This function returns all successors of this Vertex
     * @return Successors
     */
    public ArrayList<Vertex> getSuccessors() {
        ArrayList<Vertex> ret = new ArrayList<>();
        Enumeration en = outnodes.keys();
        while(en.hasMoreElements()) {
            ret.add((Vertex) en.nextElement());
        }
        return ret;
    }
    
    /**
     * Setter for key
     * @param key as a double
     */
    public void setKey(double key) {
        this.key = key;
    }
    
    /**
     * Getter for adjacency list
     * @return adjacency list
     */
    public Hashtable getHashtable() {
        return outnodes;
    }
    
    /**
     * Getter for key
     * @return key
     */
    public double getKey() {
        return key;
    }
    
    /**
     * Getter for index
     * @return index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * To string function of a Vertex
     * @return string representation
     */
    @Override
    public String toString() {
        return index + "";
    }
    
    /**
     * Setter for parent
     * @param parent parent Vertex
     */
    public void setParent(Vertex parent) {
        this.parent = parent;
    }
    
    /**
     * Getter for parent
     * @return parent
     */
    public Vertex getParent() {
        return parent;
    }

    /**
     * Compare function for sorting Vertex
     * @param o object
     * @return -1 if smaller than o, 1 if greater and 0 if equal
     */
    @Override
    public int compareTo(Object o) {
	Vertex that = (Vertex) o;
	if (this.key - that.getKey() < 0) {
		return -1;
	} else if(this.key - that.getKey() > 0) {
		return 1;
	}
	return 0;
	}
 }
