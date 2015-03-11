
package graphprim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Main class, contains core algorithm and user interaction
 * @author Laurens van den Bercken, s4057384
 * @author Jeftha Spunda, s4174615
 */
public class Main {
    
    public static int GRAPHSIZE; //nr of nodes
    public static final int ROOT = 0;//starting node for building MST
    private static String input = ""; //user input
    
    /**
     * Main function that runs input loop. From there
     * the necessary functions are called to run the program.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        //Welcome message
        System.out.println("Prim's algorithm for building MST by Laurens van den Bercken and Jeftha Spunda\n"
        		+ "We have 3 variants of the algorithm, each with a different complexity. However, they all use the same graph representation.\n"
        		+ "1. Using a normal list and constantly selecting the minimum value by hand\n"
        		+ "2. Using a priorityqueue that is updated every iteration to select the minimum value\n"
        		+ "3. Using a Fibonacci heap class to keep track of the ordering of vertices and their keys\n");
        
        //Keep going through input loop until user types -1
        while (!input.equals("-1")) {
        	System.out.println("Type the corresponding number to choose a variant or -1 to end the program.");
        	input = scanner.nextLine();
        	
            //Run normal list version
            switch (input) {
                case "1":
                    printfilelist();
                    input = scanner.nextLine();
                    switch (input) {
                        case "1":
                        {
                            Graph graph = reader("slides.txt");
                            runmst(graph);
                            break;
                        }
                        case "2":
                        {
                            Graph graph = reader("normal.txt");
                            runmst(graph);
                            break;
                        }
                        case "3":
                        {
                            Graph graph = reader("largeEWG.txt");
                            runmst(graph);
                            break;
                        }
                    }
                    break;
                case "2":
                    printfilelist();
                    input = scanner.nextLine();
                    switch (input) {
                        case "1":
                        {
                            Graph graph = reader("slides.txt");
                            runPrio(graph);
                            break;
                        }
                        case "2":
                        {
                            Graph graph = reader("normal.txt");
                            runPrio(graph);
                            break;
                        }
                        case "3":
                        {
                            Graph graph = reader("largeEWG.txt");
                            runPrio(graph);
                            break;
                        }
                    }
                    break;
                case "3":
                    printfilelist();
                    input = scanner.nextLine();
                    switch (input) {
                        case "1":
                        {
                            Graph graph = reader("slides.txt");
                            runFibo(graph);
                            break;
                        }
                        case "2":
                        {
                            Graph graph = reader("normal.txt");
                            runFibo(graph);
                            break;
                        }
                        case "3":
                        {
                            Graph graph = reader("largeEWG.txt");
                            runFibo(graph);
                            break;
                        }
                    }
                    break;
            }
        }
        scanner.close();
    }
    
    
    /**
     * This function prints the MST, if constructed already
     */
    public static void printMST(Graph g) {
    	System.out.println("Final MST");
    	ArrayList<Vertex> nodes = g.getAllNodes();
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
     * Runs the normal list version 
     * @param g the graph of which the MST is going to be computed.
     */
    static void runmst(Graph g) {
        double starttime = System.currentTimeMillis();
        mst_prim(g, ROOT);
        double stoptime = System.currentTimeMillis();
        printMST(g);
        System.out.printf("Runtime in millisec: %f (printing MST not included)\n", stoptime-starttime);
    }
    
    /**
     * Runs the priorityqueue version 
     * @param g the graph of which the MST is going to be computed.
     */
    static void runPrio(Graph g) {
        double starttime = System.currentTimeMillis();
        mst_primPrio(g, ROOT);
        double stoptime = System.currentTimeMillis();
        printMST(g);
        System.out.printf("Runtime in millisec: %f (printing MST not included)\n", stoptime-starttime);
    }
    
    /**
     * Runs the fibonacci heap version 
     * @param g the graph of which the MST is going to be computed.
     */
    static void runFibo(Graph g) {
        double starttime = System.currentTimeMillis();
        mst_primFibo(g, ROOT);
        double stoptime = System.currentTimeMillis();
        printMST(g);
        System.out.printf("Runtime in millisec: %f (printing MST not included)\n", stoptime-starttime);
    }
    
    /**
     * Prints the files the user can choose to compute an MST of.
     */
    static void printfilelist() {
    	System.out.println("Type corresponding number to choose a file or -1 to end the program:\n"
    			+ "1. slides.txt (for the graph from the lecture slides)\n"
    			+ "2. normal.txt (for a mediumsized graph of 10000 nodes)\n"
    			+ "3. largeEWG.txt (for a large graph of 1000000 nodes (only Fibonacci variant finishes this within feasible time))\n"
    			+ "PLEASE NOTE: Because largeEWG.txt is 180MB, we did not include it when handing in via blackboard.\n"
    			+ "You can download it from http://algs4.cs.princeton.edu/43mst/largeEWG.txt");
    }
    
    /**
     * Small class that's used as a comparator so that Collections class
     * knows how to compare two Vertices.
     */
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
    
    /**
     * Actual implementation of the normal list version of Prim's algorithm
     * As you can see the q is a normal (unsorted) arraylist.
     * Vertex u is extracted and then removed by calling native java functions.
     * These are expensive operations all running in linear time
     * @param graph
     * @param root
     */
    public static void mst_prim(Graph graph, int root) {
        graph.getVertex(root).setKey(0); // O(1)
        ArrayList<Vertex> q = new ArrayList<>(); // O(1)
        ArrayList<Vertex> vertices = graph.getAllNodes(); // O(1)
        for(Vertex v : vertices) { // O(|V|)
            q.add(v); // O(1)
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
    
    /**
     * Actual implementation of the priorityqueue version of Prim's algorithm
     * The q is now a priorityqueue that is updated everytime something is added.
     * So if we change the key of one of the vertices, the queue is not updated
     * until the element is removed and inserted again. This is obviously 
     * making this algorithm more complex
     * 
     * Extracting the minimal value of the queue happens in log n time, but 
     * removing a specific object happens in linear time. Inserting that element
     * into the queue again happens in log n time again
     * 
     * @param graph
     * @param root
     */
    public static void mst_primPrio(Graph graph, int root) {
        graph.getVertex(root).setKey(0); // O(1)
        PriorityQueue<Vertex> q = new PriorityQueue<>(); // O(1)
        ArrayList<Vertex> vertices = graph.getAllNodes(); // O(1)
        for(Vertex v : vertices) { // O(|V|)
            q.add(v); // O(1)
        }
        while(!q.isEmpty()) { // O(|V|)
            Vertex u = q.remove(); // O(log (len q))
            ArrayList<Vertex> successors = u.getSuccessors(); // O(1)
            for (Vertex v : successors) { // O(len(suc))
                double weight = (double) u.getHashtable().get(v); // Get weight of edge (u,v), O(len(suc))
                if(q.contains(v) && weight < v.getKey()) { // O(len(q))
                    v.setParent(u); // O(1)
                    v.setKey(weight); // O(1)
                    q.remove(v); //O(len(q)) note that complexity is not log but linear here due to removal of specific object v
                    q.add(v);  //O(log (len q))
                }
            }
        }
    }
    
    /**
     * Actual implementation of Prim's algorithm using a fibonacci heap.
     * We are using an external class found on the web (see FibonacciHeap.java for author)
     * with a few additions of our own to make it usable with our implementation of a graph.
     * 
     * This is the most efficient version, because the most expensive operation is extracting
     * the minimum of the queue, which happens in log n time.  
     * @param graph
     * @param root
     */
    public static void mst_primFibo(Graph graph, int root) {
        graph.getVertex(root).setKey(0); // O(1)
        FibonacciHeap<Vertex> q = new FibonacciHeap<>(); // O(1)
        ArrayList<Vertex> vertices = graph.getAllNodes(); // O(1)
        for(Vertex v : vertices) { // O(|V|)
            FibonacciHeap.Entry<Vertex> a = q.enqueue(v, v.getKey());
            a.setValue(v);          
        }
        while(!q.isEmpty()) { // O(|V|)
            FibonacciHeap.Entry<Vertex> min = q.dequeueMin();
            Vertex u = min.getValue();
            ArrayList<Vertex> successors = u.getSuccessors(); // O(1)\
            for (Vertex v : successors) { // O(len(suc))
            	FibonacciHeap.Entry<Vertex> vf = q.getEntryAt(v.getIndex());
                double weight = (double) u.getHashtable().get(v); // Get weight of edge (u,v), O(len(suc))
                if(q.contains(vf) && weight < vf.getPriority()) { // O(1)
                    v.setParent(u); // O(1)
                    q.decreaseKey(vf, weight); // O(1)
                }
            }
        }
    }
    
    /**
     * Reader function that returns a graph after reading it from a file
     * The file is expected to have the following format
     * line 1: 		|V|
     * line 2: 		|E|
     * line 3-|E|:	node1 node2 weight
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static Graph reader(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        System.out.println("Reading " + filename + "...");
        GRAPHSIZE = Integer.parseInt(br.readLine());
        int lines = Integer.parseInt(br.readLine());
        Graph graph = new Graph(GRAPHSIZE);
        for(int i = 0; i < lines; i++) {
            String line = br.readLine();
            String split[] = line.split(" ");
            // Add edge in both directions, since every edge is present in only one way in the file
            graph.addEdge(Integer.parseInt(split[0]), Integer.parseInt(split[1]),  Double.parseDouble(split[2]));
            graph.addEdge(Integer.parseInt(split[1]), Integer.parseInt(split[0]),  Double.parseDouble(split[2]));
        }
        br.close();
        System.out.println("Done reading.\n");
        return graph;
    }
}