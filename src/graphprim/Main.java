
package graphprim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Laurens van den Bercken
 */
public class Main {
    
    public static final char[] ALPHABET = {'A','B','C','D','E','F','G','H','I','J'};
    public static final int GRAPHSIZE = 9;
    
    public static void main(String[] args) {
        // Build graph (from slides)
        Graph graph = new Graph(GRAPHSIZE);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 8, 2);
        graph.addEdge(2, 5, 4);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);
        graph.addEdge(1, 0, 4);
        graph.addEdge(7, 0, 8);
        graph.addEdge(2, 1, 8);
        graph.addEdge(7, 1, 11);
        graph.addEdge(3, 2, 7);
        graph.addEdge(8, 2, 2);
        graph.addEdge(5, 2, 4);
        graph.addEdge(4, 3, 9);
        graph.addEdge(5, 3, 14);
        graph.addEdge(5, 4, 10);
        graph.addEdge(6, 5, 2);
        graph.addEdge(7, 6, 1);
        graph.addEdge(8, 6, 6);
        graph.addEdge(8, 7, 7);
        
        System.out.print(graph);
        
        // Prim's Algorithm
        Graph MST = mst_prim(graph, 0);
        System.out.println(MST);
        
    }
    
    static class Vsort implements Comparator<Vertex> {
           // Overriding the compare method to sort the weights
           @Override
           public int compare(Vertex v1, Vertex v2) {
               return v1.getKey() - v2.getKey();
           }
    }
    
    public static Graph mst_prim(Graph graph, int root) {
        Graph MST = new Graph(graph.getSize());
        graph.getVertex(root).setKey(0);
        PriorityQueue<Vertex> q = new PriorityQueue<>(MST.getSize(), new Vsort());
        for(int i = 0; i < graph.getSize(); i++) {
            q.add(graph.getVertex(i));
        }
        while(!q.isEmpty()) {
            Vertex u = q.remove();
            ArrayList<Vertex> successors = u.getSuccessors();
            for (Vertex v : successors) {
                int weight = (int) u.getHashtable().get(v);
                if(q.contains(v) && weight < v.getKey()) {
                    MST.addEdge(v.getIndex(), u.getIndex(), weight);
                    MST.addEdge(u.getIndex(), v.getIndex(), weight);
                    v.setKey(weight);
                }
            }
        }
        return MST;
    }
}