// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph
import java.util.*;
import java.lang.*;
import java.io.*;
 
class ShortestPath {
	
	int sourceNode;
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
    //static final int V=9;
    static final int V = LinkPrediction.maximumNumOfNodes;
    int minDistance(int dist[], Boolean sptSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }
 
    // A utility function to print the constructed distance array
    //void printSolution(int dist[], int n) {
    void printSolution(int graph[][], int dist[], int n) {
        //System.out.println("Vertex   Distance from " + sourceNode + ":");
    	int sumOfNeighbors = 0;
        for (int i = 0; i < V; i++) {
        	int sumOfNeighborsOfsource = 0;
        	int sumOfNeighborsOfi = 0;
        	
	        for (int j = 0; j < V; j++) {
	        	if (graph[sourceNode][j] != 0)
	        		sumOfNeighborsOfsource++;
	        }
	        for (int j = 0; j < V; j++) {
	        	if (graph[i][j] != 0)
	        		sumOfNeighborsOfi++;
	        }
	        sumOfNeighbors = sumOfNeighborsOfsource + sumOfNeighborsOfi;
	        if (graph[sourceNode][i] != 0)
	        	sumOfNeighbors -= 2;
	        for (int j = 0; j < V; j++) {
	        	if (graph[sourceNode][j] != 0 && graph[i][j] != 0)
	        		sumOfNeighbors--;
	        }
        	
        	if(i != sourceNode && dist[i] < Integer.MAX_VALUE) {
        		//System.out.println(i + " --- " + dist[i]);
        		//saveToFile("(" + sourceNode + ", " + i + "), " + dist[i]);
        		//saveToFile(sourceNode + ", " + i + ", " + dist[i] + ", " + graph[sourceNode][i]);
        		//saveToFile(dist[i] + "\t" + graph[sourceNode][i]);
        		//saveToFile("(" + sourceNode + ", " + i + "), " + dist[i] + ", " + sumOfPapers);
        		//saveToFile(dist[i] + "\t" + sumOfNeighbors + "\t" + graph[sourceNode][i]);
        		int coAuthorsOrNot;
        		if (graph[sourceNode][i] > 0)
        			coAuthorsOrNot = 1;
        		else
        			coAuthorsOrNot = 0;
        		//saveToFile("(" + sourceNode + ", " + i + "), " + dist[i] + ", " + sumOfNeighbors + "\t" + graph[sourceNode][i] + "\t" + coAuthors);
        		//saveToFile(sumOfNeighbors + "\t" + graph[sourceNode][i] + "\t" + coAuthorsOrNot); // graph[sourceNode][i]: shows the number of paper by sourceNode and i pair of authors
        		//saveToFile(sumOfNeighbors + ", " + graph[sourceNode][i] + ", " + coAuthorsOrNot); // graph[sourceNode][i]: shows the number of paper by sourceNode and i pair of authors
        		//saveToFile("(" + sourceNode + ", " + i + "), " + dist[i] + ", " + sumOfNeighbors + ", " + graph[sourceNode][i] + ", " + coAuthorsOrNot); // graph[sourceNode][i]: shows the number of paper by sourceNode and i pair of authors, i.e. sumOfPapers

        		//saveToFile(dist[i] + ", " + sumOfNeighbors + ", " + graph[sourceNode][i] + ", " + coAuthorsOrNot); // graph[sourceNode][i]: shows the number of paper by sourceNode and i, (sourceNode, i) pair of authors
        		
        		
        		// these features are very good:
        		saveToFile(dist[i] + ", " + sumOfNeighbors + ", " + coAuthorsOrNot); // dist[i]: shows the shortest distance
        		
        		// the following is used to check the effect of shortest distance on the models performance, in Weka...
        		// ignore or delete it:
//                // create instance of Random class
//                Random rand = new Random();
//                // Generate random integers in range 1 to 5
//                int randomInt = rand.nextInt(5) + 1;
//                int secondShortestDistanceRanomForTest = randomInt + dist[i];
//                System.out.println("secondShortestDistanceRanomForTest: " + secondShortestDistanceRanomForTest);
//                saveToFile(dist[i] + ", " + secondShortestDistanceRanomForTest + ", " + sumOfNeighbors + ", " + coAuthorsOrNot); // dist[i]: shows the shortest distance
        	}
        }
    }
 
    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void dijkstra(int graph[][], int src) {
    	
    	int[][] graphZeroOne = new int[V][V];;
    	for (int i = 0; i < V; i++) {
    		for (int j = 0; j < V; j++) {
    			if (graph[i][j] != 0)
    				graphZeroOne[i][j] = 1;
    			else
    				graphZeroOne[i][j] = 0;
    		}
    	}
    	
    	sourceNode = src;
    	
        int dist[] = new int[V]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
 
        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];
 
        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
 
        // Distance of source vertex from itself is always 0
        dist[src] = 0;
 
        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);
 
            // Mark the picked vertex as processed
            sptSet[u] = true;
 
            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)
 
                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v]!=0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u]+graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }
 
        // print the constructed distance array
        //printSolution(dist, V);
        printSolution(graph, dist, V);
    }
    
    public static void saveToFile(String str) {
        try {
          PrintStream out = new PrintStream(new FileOutputStream("OutFile.txt", true));
          out.println(str);

          out.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    
    
    // Driver method
    public static void main (String[] args) {
        /* Let us create the example graph discussed above */
       int graph[][] = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0},
                                  {4, 0, 8, 0, 0, 0, 0, 11, 0},
                                  {0, 8, 0, 7, 0, 4, 0, 0, 2},
                                  {0, 0, 7, 0, 9, 14, 0, 0, 0},
                                  {0, 0, 0, 9, 0, 10, 0, 0, 0},
                                  {0, 0, 4, 14, 10, 0, 2, 0, 0},
                                  {0, 0, 0, 0, 0, 2, 0, 1, 6},
                                  {8, 11, 0, 0, 0, 0, 1, 0, 7},
                                  {0, 0, 2, 0, 0, 0, 6, 7, 0}
                                 };
        ShortestPath t = new ShortestPath();
        t.dijkstra(graph, 0);
    }
}