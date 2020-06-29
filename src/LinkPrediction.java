import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkPrediction {
	
	//static final int numberOfNodes = 317080; //Number of nodes in the graph
	static int maximumNumOfNodes = 10000; //Number of nodes in the graph
	
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // this function reads the graph from a text file where each line shows an edge between two nodes separated by a space
    // example: 1 2 means there is an edge between nodes 1 and 2
    public static int[][] readGraphFromTextFile(){
    	int[][] graph = new int[maximumNumOfNodes][maximumNumOfNodes];
    	Set<Integer> nodeIDs = new HashSet<Integer>();
    	String fileName = "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\com-dblp.ungraph.txt\\com-dblp.ungraph.txt";
    	//String fileName = "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\facebook_combined.txt\\facebook_combined.txt";
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				
				String[] edge = sCurrentLine.split(" ");
				String firstNodeOfEdge = edge[0];
				String secondNodeOfEdge = edge[1];
				
				int firstNodeOfEdgeAsInteger = Integer.parseInt(firstNodeOfEdge);
				int secondNodeOfEdgeAsInteger = Integer.parseInt(secondNodeOfEdge);
				
//				graph[firstNodeOfEdgeAsInteger-1][secondNodeOfEdgeAsInteger-1] = 1;
//				graph[secondNodeOfEdgeAsInteger-1][firstNodeOfEdgeAsInteger-1] = 1;
				if (firstNodeOfEdgeAsInteger <= maximumNumOfNodes && secondNodeOfEdgeAsInteger <= maximumNumOfNodes) {
					nodeIDs.add(firstNodeOfEdgeAsInteger);
					nodeIDs.add(secondNodeOfEdgeAsInteger);
					graph[firstNodeOfEdgeAsInteger][secondNodeOfEdgeAsInteger] = 1;
					graph[secondNodeOfEdgeAsInteger][firstNodeOfEdgeAsInteger] = 1;
				}
			}
			
			System.out.print("nodeIDs: ");
	        System.out.println(nodeIDs);

		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return graph;
    }
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static HashMap<String,Integer> calculateSumOfPapers() throws IOException{
    	int[] graph = new int[maximumNumOfNodes];
    	HashMap<String,Integer> hm = new HashMap< String,Integer>();
    	String fileName = "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\com-dblp.ungraph.txt\\com-dblp.ungraph.txt";
    	//String fileName = "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\facebook_combined.txt\\facebook_combined.txt";
    	
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	BufferedReader br2 = new BufferedReader(new FileReader(fileName));
			
	    String sCurrentLine;
	    String sCurrentLine2;
	
		while ((sCurrentLine = br.readLine()) != null) {
			hm.put(sCurrentLine, 0);
		}
		br.close();
		br = new BufferedReader(new FileReader(fileName));
		int counter1 = 0;
		int counter2 = 0;
		while (((sCurrentLine = br.readLine()) != null) && counter1 < maximumNumOfNodes) {
			if (hm.get(sCurrentLine)==0) {
				while (((sCurrentLine2 = br2.readLine()) != null)  && counter2 < maximumNumOfNodes) {
					System.out.println("sCurrentLine: " + sCurrentLine + "          sCurrentLine2: " + sCurrentLine2);
					if (sCurrentLine.equals(sCurrentLine2)) {
						
						hm.put(sCurrentLine, hm.get(sCurrentLine) + 1);
						System.out.println("similar string found");
					}
					counter2++;
				}
			}
			br2 = new BufferedReader(new FileReader(fileName));
			counter1++;
			System.out.println("counter1: " + counter1);
		}
    	br.close();
    	br2.close();
    	System.out.println("SumOfPapers: ");
        // Returns Set view     
        Set<Map.Entry<String,Integer>> st = hm.entrySet();   
  
        for (Map.Entry< String,Integer> me:st) {
        	if (me.getValue() > 1) {
        		System.out.print(me.getKey()+":");
            	System.out.println(me.getValue());
            }
        }
    	System.out.println("");
    	
    	return hm;
    }
    
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String args[]) throws IOException {
		//String fileDirctory= "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\test.xml";
		//String fileDirctory= "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-10006.xml";
		//String fileDirctory= "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-63990.xml";
		String fileDirctory= "C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-50000.xml";
		
		
	    // read graph of question 7 - homework 3
		//int graph[][] = readGraphFromTextFile();
		//XMLParser xmlParser = new XMLParser();
		int graph[][] = XMLParser.readXMLfileAndReturnGraphMatrix(fileDirctory);
		
		maximumNumOfNodes = XMLParser.maximumNumOfNodes;
		System.out.println("maximumNumOfNodes: " + maximumNumOfNodes);
		
        ShortestPath t = new ShortestPath();
        //t.dijkstra(graph, 0);
        
        
        //ShortestPath.saveToFile("#fromNodeId, toNodeId, shortestPathDistance, coAuthorOrNot");
        //ShortestPath.saveToFile("(i,  j), shortestPathDistance, sumOfNeighbors, sumOfPapers, coAuthorOrNot");
        //ShortestPath.saveToFile("sumOfNeighbors, sumOfPapers, coAuthorOrNot");
        ShortestPath.saveToFile("(fromNodeId, toNodeId), shortestPathDistance, sumOfNeighbors, sumOfPapers, coAuthorOrNot");
        for (int i = 0; i < maximumNumOfNodes; i++) {
        	t.dijkstra(graph, i);
        	
        	//System.out.println("-----------------------------");
        }
        
        
        
        //calculateSumOfPapers();
        
        System.out.println("Done!");
        
        
        
        
        
//		System.out.println("Graph (adjacency matrix): ");
//		for(int i = 0; i < graph.length; i++) {
//			for(int j = 0; j < graph.length; j++) {
//				System.out.print(graph[i][j] + " ");
//			}
//			System.out.println("");
//		}
	}
}