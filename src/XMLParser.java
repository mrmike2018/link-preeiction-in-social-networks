import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLParser {
	
	//static int maximumNumOfNodes = 10000;
	static int maximumNumOfNodes = LinkPrediction.maximumNumOfNodes;
   //public static void main(String[] args) {
	public static int[][] readXMLfileAndReturnGraphMatrix(String fileDirectory) {
		
		int[][] coAuthorShipMatrix = new int[maximumNumOfNodes][maximumNumOfNodes];// = new Integer[]();
		int[] sumOfPapers = new int[maximumNumOfNodes];
		int counterForID = 0;
		int numberOfPapersInTotal = 0;
      try {
         //File inputFile = new File("input.txt");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\test-xml-dataset.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-10.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-106.xml");
     	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-1006.xml");
     	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-10006.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\lines-1-1006.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\test.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\dblp-two-or-more-authors.xml");
    	 //File inputFile = new File("C:\\MGH\\fau-spring-2018\\social networks\\term project\\dataset\\dblp.xml\\dblp.xml");
    	  
    	  File inputFile = new File(fileDirectory);
    	  
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("article");
         //System.out.println("----------------------------");
         
         HashMap<Integer, String[]> paperIDAuthorsHashMap = new HashMap<>();
         
         HashMap<String, Integer> authorsIDs = new HashMap<String, Integer>();
         //Integer[][] coAuthorShipMatrix = new Integer[maximumNumOfNodes][maximumNumOfNodes];
         for (int i = 0; i < maximumNumOfNodes; i++)
        	 for (int j = 0; j < maximumNumOfNodes; j++)
        		 coAuthorShipMatrix[i][j] = 0;
         
         for (int i = 0; i < maximumNumOfNodes; i++)
        	 sumOfPapers[i] = 0;
        	 
         //int counterForID = 0;
         //saveToFile("paperTitle, paperYear");
         
         
         StringWriter stringWriter = new StringWriter(); // this is for training
         
//         XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
//         XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
         
//         xMLStreamWriter.writeStartDocument();
//         xMLStreamWriter.writeCharacters("\n");
         
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element: " + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               String paperTitle = eElement.getElementsByTagName("title").item(0).getTextContent();
               //System.out.println("paper title: " + paperTitle);
               String paperYear = eElement.getElementsByTagName("year").item(0).getTextContent();
               
               int paperYearInt = Integer.parseInt(paperYear);
               
               
               //if(paperYearInt <= 2010) { // for training dataset
               //if(paperYearInt > 2010) { // for test dataset
               
               //if(paperYearInt >=2011 && paperYearInt <= 2015) { // for training dataset
               //if(paperYearInt >2015) { // for test dataset
               
               if(paperYearInt >=2012 && paperYearInt <= 2016) { // for training dataset
               //if(paperYearInt >2016) { // for test dataset
               
               
               //if(paperYearInt >=2001 && paperYearInt <= 2003) { // for training dataset
               //if(paperYearInt > 2003 && paperYearInt <= 2004) { // for test dataset
            	   numberOfPapersInTotal++;
            	   
//	               xMLStreamWriter.writeStartElement("article");
//		           xMLStreamWriter.writeCharacters("\n");
//		           
//		           xMLStreamWriter.writeStartElement("title");
//		           xMLStreamWriter.writeCharacters(paperTitle);
//		           xMLStreamWriter.writeEndElement();
//		           xMLStreamWriter.writeCharacters("\n");
		           
		           
	               
	               int numOfAuthors = eElement.getElementsByTagName("author").getLength();
	               //System.out.println("numOfAuthors: " + numOfAuthors);
	               String[] authorsArray = new String[numOfAuthors];             
		
		           for (int i = 0; i < numOfAuthors; i++) {
		           	   String tempAuthor = eElement.getElementsByTagName("author").item(i).getTextContent();
		           	   authorsArray[i] = tempAuthor;
		           	   
//			           xMLStreamWriter.writeStartElement("author");
//			           xMLStreamWriter.writeCharacters(tempAuthor);
//			           xMLStreamWriter.writeEndElement();
//			           xMLStreamWriter.writeCharacters("\n");
		           	   
		           	   // if author is new, assign a new id to him:
		               if(!authorsIDs.containsKey(tempAuthor)) {
		              	   authorsIDs.put(tempAuthor, counterForID);
		              	   //System.out.println("author" + counterForID + " created");
		              	   sumOfPapers[counterForID] = 1;
		               	   counterForID++;
		               }
		               // if he already has an id, then increase his number of papers by one:
		               else
		            	   sumOfPapers[counterForID] += 1;
		           }
	
//		           xMLStreamWriter.writeStartElement("year");
//		           xMLStreamWriter.writeCharacters(paperYear);
//		           xMLStreamWriter.writeEndElement();
//		           xMLStreamWriter.writeCharacters("\n");
//		           
//		           xMLStreamWriter.writeEndElement();
//		           xMLStreamWriter.writeCharacters("\n\n");
		           
		           // linking all authors of the current element, here is a paper for example,:
		           for (int i = 0; i < numOfAuthors; i++) {
		        	   for (int j = i + 1; j < numOfAuthors; j++) {        		   
		        		   coAuthorShipMatrix[authorsIDs.get(authorsArray[i])][authorsIDs.get(authorsArray[j])] += 1;
		        		   coAuthorShipMatrix[authorsIDs.get(authorsArray[j])][authorsIDs.get(authorsArray[i])] += 1;
		        	   }
		           }
		           
		           paperIDAuthorsHashMap.put(temp, authorsArray);
            	}
            }
            
         }
         
//         xMLStreamWriter.flush();
//         xMLStreamWriter.close();
//         
//         String xmlString = stringWriter.getBuffer().toString();
//
//         stringWriter.close();
//
//         //System.out.println(xmlString);
//         saveToFile(xmlString, "generatedXML.xml");
         
//         xMLStreamWriter.writeEndDocument();
      } catch (Exception e) {
         e.printStackTrace();
      }
      maximumNumOfNodes = counterForID;
      System.out.println("------------------------numberOfAuthors: " + counterForID);
      System.out.println("------------------------numberOfPapersInTotal: " + numberOfPapersInTotal);
      System.out.println("function readXMLfileAndReturnGraphMatrix Done!");
      return coAuthorShipMatrix;
   }
   
   public static void printMatrix(int[][] A, int row, int column) {
       for (int i = 0; i < row; i++) {
      	 for (int j = 0; j < column; j++)
      		System.out.print(A[i][j] + " ");
      	System.out.println("");
       }
   }
   
   public static void saveToFile(String str) {
       try {
         PrintStream out = new PrintStream(new FileOutputStream("paperTitleYear.txt", true));
         out.println(str);

         out.close();
       } catch (FileNotFoundException e) {
         e.printStackTrace();
       }
     }
   
   public static void saveToFile(String str, String filename) {
       try {
         //PrintStream out = new PrintStream(new FileOutputStream("paperTitleYear.txt", true));
         PrintStream out = new PrintStream(new FileOutputStream(filename, true));
         out.println(str);

         out.close();
       } catch (FileNotFoundException e) {
         e.printStackTrace();
       }
     }
}