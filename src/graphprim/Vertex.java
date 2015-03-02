
package graphprim;

import java.util.Hashtable;

/**
 *
 * @author Laurens van den Bercken
 */
public class Vertex {
    
    private String name;
    private Hashtable<Vertex, Integer> outnodes; // Adjacency list, with weighted edges
    
    public Vertex(String str) {
        name = str;
        outnodes = new Hashtable<>();
    }
    
    public void addOutnode(Vertex node, int weight) {
        outnodes.put(node, weight);
    }
 }
