import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MapDrawer extends GUI{
	private  List<Node> nodeCollection;
	private int scale;
	
	public  MapDrawer () {
		nodeCollection = new ArrayList<Node>();
		scale = 800;
	}


	@Override
	protected void redraw(Graphics g) {
		for (Node node : nodeCollection) {
			node.setScale(scale);
			node.draw(g);		
		}
	}

	@Override
	protected void onClick(MouseEvent e) {
		System.out.print(e);
		
	}

	@Override
	protected void onSearch() {

		
	}

	@Override
	protected void onMove(Move m) {
		if (m == Move.ZOOM_IN) {
			zoom(10);
		} else if (m == Move.ZOOM_OUT) {
			zoom(-10);
		}
		
	}

	private void zoom(int step) {
		scale = scale + step;
		System.out.println(scale);
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
