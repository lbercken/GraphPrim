
package graphprim;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Graph class, contains list of nodes and basic graph operations
 * @author Laurens van den Bercken, s4057384
 * @author Jeftha Spunda, s4174615
 */
public class Graph {
    
    private int size; // size of graph
    private ArrayList<Vertex> nodes; // list of nodes
    
    /**
     * Graph constructor
     * @param size size of graph
     */
    public Graph(int size) {
        nodes = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            nodes.add(new Vertex(i));
        }
        this.size = size;
    }
    
    /**
     * Add an edge
     * @param from from as an integer
     * @param to to as an integer
     * @param weight with weight as a double
     * from and to are integers and not Vertex, because adding an edge is O(1) like this
     */
    public void addEdge(int from, int to, double weight) {
        // Precondition
        if(from >= size || to >= size) {
            System.out.println("Error: from or to is greater than size."
                    + "No edge has been added");
            return;
        }
        nodes.get(from).addOutnode(nodes.get(to), weight);
    }
    
    /**
     * Delete an edge
     * @param from from as an integer
     * @param to to as an integer
     * from and to are integers and not Vertex, because deleting an edge is O(1) like this
     */
    public void deleteEdge(int from, int to) {
        // Precondition
        if(from >= size || to >= size) {
            System.out.println("Error: from or to is greater than size."
                    + "No edge has been deleted");
            return;
        }
        nodes.get(from).deleteOutnode(nodes.get(to));
    }
    
    /**
     * This functions checks whether an edge (from, to) exists
     * @param from as an integer
     * @param to as an integer
     * @return returns true when an edge (from, to) exists, else returns false
     * from and to are integers and not Vertex, because checking an edge is O(1) like this
     */
    public boolean isEdge(int from, int to) {
        if(from >= size || to >= size) {
            return false;
        }
        return nodes.get(from).getSuccessors().contains(nodes.get(to));
    }
    
    /**
     * This function gets all successors of a Vertex
     * @param vertex as an integer
     * @return a list with successors of vertex
     * vertex is an integer and not Vertex, because getSuccessors is O(|V| - 1) like this
     */
    public ArrayList<Vertex> getSuccessors(int vertex) {
        // Precondition
        if(vertex >= size) {
            System.out.println("Error: vertex does not exist.");
            return null;
        }
        return nodes.get(vertex).getSuccessors();
    }
    
    /**
     * The functions gets Vertex i
     * @param i as an integer
     * @return Vertex i
     */
    public Vertex getVertex(int i) {
        return nodes.get(i);
    }
    
    /**
     * This functions return the size of the graph
     * @return The size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * This functions gets all nodes of the graph
     * @return all nodes
     */
    public ArrayList<Vertex> getAllNodes() {
        return nodes;
    }
    
    /**
     * This function prints the MST, if constructed already
     */
    public void printMST() {
        String s = "";
        double total = 0;
        for (int i = 0; i < nodes.size(); i++) {
            s += nodes.get(i) +  " with parent ";
            Vertex parent = nodes.get(i).getParent();
            if (parent != null) {
                double weight = (double) nodes.get(i).getHashtable().get(parent);
                s += parent + " with weight " + weight;
                total += weight;
            } else {
                s += "null";
            }
            System.out.println(s);
            s = new String();
        }
       System.out.println("Total weight = " + total);
    }
    
    /**
     * To string function of a graph
     * @return the graph as a string representation
     */
    @Override
    public String toString() {
        double weight = 0;
        String ret = "";
        for(Vertex node : nodes) {
            ret += node.toString() + " ";
            Hashtable table = node.getHashtable();
            for(Vertex successor : node.getSuccessors()) {
                ret += successor.toString() + " ";
                weight += (double) table.get(successor);
            }
            ret += "\n";
        }
        return ret + "\nWeight = " + weight/2 + "\n\n";
    }
}