import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class NodeCollection extends GUI{
	
	private final List<Node> nodes;
	
	public NodeCollection () {
		nodes = new ArrayList<Node>();		     
	}

	@Override
	protected void onLoad(File nodesFile, File roads, File segments, File polygons) {
		 String  thisLine = null;
		      try{
		         BufferedReader br = new BufferedReader(new FileReader(nodesFile));
		         while ((thisLine = br.readLine()) != null) {
		        	Node node = new Node (thisLine);
		        	nodes.add(node);
		         }       
		         br.close();
		         System.out.println(toString());
		         System.out.println(nodes.size());
		      } catch(Exception e){
		         e.printStackTrace();
		      }
		
	}
	
	public String toString() {
		return "Nodes: " + nodes.get(0).toString();
	}
	
	public static void main(String[] args) {
		new NodeCollection();
	}

	@Override
	protected void redraw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub
		
	}
}
