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
	
//	public String toString() {
//		return "Nodes: " + nodes.get(0).toString();
//	}
	
//	public static void main(String[] args) {
//		String path = "C:/Users/Anna/Desktop/COMP261/Assign1/261a1_data/data/small/nodeID-lat-lon.tab";
//		File file = new File(path);
//		List<Node> nodesEx = new NodeCollection().getNodes(file);
//		System.out.println(nodesEx.toString());
//	}

}
