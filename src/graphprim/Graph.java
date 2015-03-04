
package graphprim;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Laurens van den Bercken
 */
public class Graph {
    
    private int size;
    private ArrayList<Vertex> nodes;
    
    public Graph(int size) {
        nodes = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            nodes.add(new Vertex(i));
        }
        this.size = size;
    }
    
    public void addEdge(int from, int to, int weight) {
        // Precondition
        if(from >= size || to >= size) {
            System.out.println("Error: from or to is greater than size."
                    + "No edge has been added");
            return;
        }
        nodes.get(from).addOutnode(nodes.get(to), weight);
    }
    
    public void deleteEdge(int from, int to) {
        // Precondition
        if(from >= size || to >= size) {
            System.out.println("Error: from or to is greater than size."
                    + "No edge has been deleted");
            return;
        }
        nodes.get(from).deleteOutnode(nodes.get(to));
    }
    
    public boolean isEdge(int from, int to) {
        if(from >= size || to >= size) {
            return false;
        }
        return nodes.get(from).getSuccessors().contains(nodes.get(to));
    }
    
    public ArrayList<Vertex> getSuccessors(int vertex) {
        // Precondition
        if(vertex >= size) {
            System.out.println("Error: vertex does not exist.");
            return null;
        }
        return nodes.get(vertex).getSuccessors();
    }
    
    public Vertex getVertex(int i) {
        return nodes.get(i);
    }
    
    public int getSize() {
        return size;
    }
    
    public ArrayList getAllNodes() {
        return nodes;
    }
    
    @Override
    public String toString() {
        int weight = 0;
        String ret = "";
        for(Vertex node : nodes) {
            ret += node.toString();
            Hashtable table = node.getHashtable();
            for(Vertex successor : node.getSuccessors()) {
                ret += successor.toString();
                weight +=(int) table.get(successor);
            }
            ret += "\n";
        }
        return ret + "\nWeight = " + weight/2 + "\n\n";
    }
}