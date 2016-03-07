import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class NodeCollection {
	
	private final List<Node> nodes;
	
	public NodeCollection() {
		nodes = new ArrayList<Node>();		    
	}
	
	public List<Node> getNodes(File file) {
		read(file);
		return nodes;
	}

	private void read(File file) {
		String  thisLine = null;
	      try{
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         while ((thisLine = br.readLine()) != null) {
	        	Node node = new Node (thisLine);
	        	nodes.add(node);
	         }       
	         br.close();
	         System.out.println(nodes.size());
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
	
//	public String toString() {
//		return "Nodes: " + nodes.get(0).toString();
//	}
//	
//	public static void main(String[] args) {
//		new NodeCollection();
//	}

}
