
package graphprim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Laurens van den Bercken
 */
public class Vertex implements Comparable {
   
    private double key;
    private final int index;
    private Vertex parent;
    private Hashtable<Vertex, Double> outnodes; // Adjacency list, with weighted edges
    
    public Vertex(int index) {
        key = Double.MAX_VALUE;
        this.index = index;
        parent = null;
        outnodes = new Hashtable<>();
    }
    
    public void addOutnode(Vertex node, double weight) {
        outnodes.put(node, weight);
    }
    
    public void deleteOutnode(Vertex node) {
        // Precondition
        if (!outnodes.containsKey(node)) {
            System.out.println("Error: edge does not exist."
                    + "No edge has been deleted.");
            return;
        }
        outnodes.remove(node);
    }
    
    public ArrayList<Vertex> getSuccessors() {
        ArrayList<Vertex> ret = new ArrayList<>();
        Enumeration en = outnodes.keys();
        while(en.hasMoreElements()) {
            ret.add((Vertex) en.nextElement());
        }
        return ret;
    }
    
    public void setKey(double key) {
        this.key = key;
    }
    
    public Hashtable getHashtable() {
        return outnodes;
    }
    
    public double getKey() {
        return key;
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public String toString() {
        return index + "";
    }
    
    public void setParent(Vertex parent) {
        this.parent = parent;
    }
    
    public Vertex getParent() {
        return parent;
    }

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
