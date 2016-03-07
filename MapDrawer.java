import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class MapDrawer extends GUI{
	private  List<Node> nodeCollection;
	
	public  MapDrawer () {
		nodeCollection = new ArrayList<Node>();
	}


	@Override
	protected void redraw(Graphics g) {
		for (Node node : nodeCollection)
			node.draw(g);		
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

	@Override
	protected void onLoad(File nodesFile, File roads, File segments, File polygons) {
		 nodeCollection = new NodeCollection().getNodes(nodesFile);
		 redraw();
	}
	
	public static void main(String[] args) {
		new MapDrawer ();

	}

}
