
package graphprim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Laurens van den Bercken
 */
public class Main {
    
    // public static final char[] ALPHABET = {'A','B','C','D','E','F','G','H','I'};
    public static int GRAPHSIZE;
    public static final int ROOT = 0;
    
    public static void main(String[] args) throws IOException {
        // Build graph (from slides)
        Graph graph = reader("file.txt");
// Graph from slides
//        graph.addEdge(0, 1, 4);
//        graph.addEdge(0, 7, 8);
//        graph.addEdge(1, 2, 8);
//        graph.addEdge(1, 7, 11);
//        graph.addEdge(2, 3, 7);
//        graph.addEdge(2, 8, 2);
//        graph.addEdge(2, 5, 4);
//        graph.addEdge(3, 4, 9);
//        graph.addEdge(3, 5, 14);
//        graph.addEdge(4, 5, 10);
//        graph.addEdge(5, 6, 2);
//        graph.addEdge(6, 7, 1);
//        graph.addEdge(6, 8, 6);
//        graph.addEdge(7, 8, 7);
//        graph.addEdge(1, 0, 4);
//        graph.addEdge(7, 0, 8);
//        graph.addEdge(2, 1, 8);
//        graph.addEdge(7, 1, 11);
//        graph.addEdge(3, 2, 7);
//        graph.addEdge(8, 2, 2);
//        graph.addEdge(5, 2, 4);
//        graph.addEdge(4, 3, 9);
//        graph.addEdge(5, 3, 14);
//        graph.addEdge(5, 4, 10);
//        graph.addEdge(6, 5, 2);
//        graph.addEdge(7, 6, 1);
//        graph.addEdge(8, 6, 6);
//        graph.addEdge(8, 7, 7);
        
        System.out.print(graph);
        
        // Prim's Algorithm
        mst_prim(graph, ROOT);
        double weight = 0;
        for(int i = 0; i < GRAPHSIZE; i++) {
            Vertex v = graph.getVertex(i);
            // Note that root does not have a parent
            if(i != ROOT) {
                weight += (double) v.getHashtable().get(v.getParent());
            }
            System.out.println(v + " with parent: " + v.getParent());
        }
        System.out.println("Total weight of MST = " + weight);
        
        
    }
    
    static class Vsort implements Comparator<Vertex> {
           // Overriding the compare method to sort the weights
           @Override
           public int compare(Vertex v1, Vertex v2) {
               if(v1.getKey() == v2.getIndex()) {
                   return 0;
               }
               else if(v1.getKey() < v2.getKey()) {
                   return -1;
               }
               else {
                   return 1;
               }
           }
    }
    
    public static void mst_prim(Graph graph, int root) {
        graph.getVertex(root).setKey(0); // O(1)
        ArrayList<Vertex> q = new ArrayList<>(); 
        ArrayList<Vertex> vertices = graph.getAllNodes(); // O(1)
        for(Vertex v : vertices) { // O(|V|)
            q.add(v);
        }
        while(!q.isEmpty()) { // O(|V|)
            Vertex u = Collections.min(q, new Vsort()); // O(len(q))
            q.remove(u); // O(len(q))
            ArrayList<Vertex> successors = u.getSuccessors(); // O(1)
            for (Vertex v : successors) { // O(len(suc))
                double weight = (double) u.getHashtable().get(v); // Get weight of edge (u,v), O(len(suc))
                if(q.contains(v) && weight < v.getKey()) { // O(len(q)
                    v.setParent(u); // O(1)
                    v.setKey(weight); // O(1)
                }
            }
        }
    }
    
    private static Graph reader(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        GRAPHSIZE = Integer.parseInt(br.readLine());
        int lines = Integer.parseInt(br.readLine());
        Graph graph = new Graph(GRAPHSIZE);
        for(int i = 0; i < lines; i++) {
            String line = br.readLine();
            String split[] = line.split(" ");
            graph.addEdge(Integer.parseInt(split[0]), Integer.parseInt(split[1]),  Double.parseDouble(split[2]));
            graph.addEdge(Integer.parseInt(split[1]), Integer.parseInt(split[0]),  Double.parseDouble(split[2]));
        }
        br.close();
        System.out.println("Done reading");
        return graph;
    }
}