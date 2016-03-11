import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MapDrawer extends GUI{
	private Map<Integer, Node> nodeCollection;
	private Map<Integer, Road> roadCollection;
	private List<Segment> segmentCollection;
	private int scale;
	private Location origin;
	
	public  MapDrawer () {
		scale = 10;
		origin = new Location(50,50);
	}


	@Override
	protected void redraw(Graphics g) {
		for (Node node : nodeCollection.values()) {
			node.setScale(scale);
			node.setOrigin(origin);
			node.draw(g);		
		}
		for (Segment segment : segmentCollection) {
			segment.setScale(scale);
			segment.setOrigin(origin);
			segment.draw(g);
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
		
		switch(m){
			case ZOOM_IN: zoom(2);
				break;
			case ZOOM_OUT: zoom(-2);
				break;
			case NORTH: move(0,10);
				break;
			case SOUTH: move(0,-10);
				break;
			case EAST: move(10,0);
				break;
			case WEST: move(-10,0);
				break;
		default:
			break;
		}	
	}

	private void zoom(int step) {
		scale += step;
	}
	
	private void move(int stX, int stY) {
		origin.x += stX;
		origin.y += stY;
	}

	@Override
	protected void onLoad(File nodesFile, File roadsFile, File segmentsFile, File polygons) {
		 nodeCollection = new NodeCollection().getNodes(nodesFile);
		 roadCollection = new RoadCollection().getRoads(roadsFile);
		 segmentCollection = new SegmentCollection().getSegments(segmentsFile);
		 redraw();
	}
	
	public static void main(String[] args) {
		new MapDrawer ();

	}

}
