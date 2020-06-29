import java.util.*;

public class SecondShortestPath {

    private static final Graph.Edge[] GRAPH = { 
        new Graph.Edge("A", "G", 8), 
        new Graph.Edge("A", "B", 1), 
        new Graph.Edge("A", "E", 1), 
        new Graph.Edge("B", "C", 1), 
        new Graph.Edge("B", "E", 1),
        new Graph.Edge("B", "F", 2),
        new Graph.Edge("C", "G", 1),
        new Graph.Edge("C", "D", 1),
        new Graph.Edge("D", "F", 1),
        new Graph.Edge("D", "Z", 1),
        new Graph.Edge("E", "F", 4),
        new Graph.Edge("F", "Z", 4),
        new Graph.Edge("G", "Z", 2),
    };

    private static final String START = "A";
    private static final String END = "Z";
    
    public static void shortestPath() {
        Graph g = new Graph(GRAPH);
        g.dijkstra(START);
        g.restorePath(END);
        g.revertEdges(END);
        g.assignPotentials();
        g.dijkstra(START);
        g.restorePath(END);

        g.printPaths(START, END);
    }
    
    public static void secondShortestPath() {
        Graph g = new Graph(GRAPH);
        g.dijkstra(START);
        g.restorePath(END);
        g.revertEdges(END);
        g.assignPotentials();
        g.dijkstra(START);
        g.restorePath(END);

        g.printPaths(START, END);
    }
    

    public static void main(String[] args) {
        Graph g = new Graph(GRAPH);
        g.dijkstra(START);
        g.restorePath(END);
        g.revertEdges(END);
        g.assignPotentials();
        g.dijkstra(START);
        g.restorePath(END);

        g.printPaths(START, END);
    }
}


