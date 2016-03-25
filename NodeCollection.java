import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class NodeCollection {
	
	private Map<Integer,Node> nodes;
	
	public NodeCollection() {
		nodes = new HashMap<Integer, Node>();		    
	}
	
	public Map<Integer, Node> getNodes(File file) {
		read(file);
		return nodes;
	}

	private void read(File file) {
		//Reads file and creates a hashmap of nodes
		String  thisLine = null;
	      try{
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         while ((thisLine = br.readLine()) != null) {
	        	Node node = new Node (thisLine);
	        	nodes.put(node.getId(), node);
	         }       
	         br.close();
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
}
