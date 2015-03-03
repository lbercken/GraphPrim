
package graphprim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Laurens van den Bercken
 */
public class Vertex {
   
    private int key;
    private final int index;
    private Hashtable<Vertex, Integer> outnodes; // Adjacency list, with weighted edges
    
    public Vertex(int index) {
        key = Integer.MAX_VALUE;
        this.index = index;
        outnodes = new Hashtable<>();
    }
    
    public void addOutnode(Vertex node, int weight) {
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
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public Hashtable getHashtable() {
        return outnodes;
    }
    
    public int getKey() {
        return key;
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public String toString() {
        return Main.ALPHABET[this.index] + " ";
    }
 }
