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
	private Trie trie;
	private int scale;
	private Location origin;
	
	public  MapDrawer () {
		scale = 10;
		origin = new Location(50,50);
	}

	@Override
	protected void redraw(Graphics g) {
//		for (Node node : nodeCollection.values()) {
//			node.setScale(scale);
//			node.setOrigin(origin);
//			node.draw(g);		
//		}
		if (segmentCollection != null){
			for (Segment segment : segmentCollection) {
				segment.setScale(scale);
				segment.setOrigin(origin);
				segment.draw(g);
			}
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
		 setupCollections();
		 setupTrie();
	}


	private void setupCollections() {
		for (Segment segment : segmentCollection) {
			int roadId = segment.getRoadId();
			int startNodeId = segment.getNode1Id();
			int endNodeId = segment.getNode2Id();
			Road road = roadCollection.get(roadId);
			Node startNode = nodeCollection.get(startNodeId);
			Node endNode = nodeCollection.get(endNodeId);
			segment.setRoad(road);
			segment.setStartNode(startNode);
			segment.setEndNode(endNode);
			road.getSegments().add(segment);
			startNode.getOutNeighbours().add(segment);
			endNode.getInNeighbours().add(segment);
			if(road.getOneway() == 0){
				endNode.getOutNeighbours().add(segment);
				startNode.getInNeighbours().add(segment);
			}
		}
	}
	
	private void setupTrie() {
		trie = new Trie();
//		for (Road road : roadCollection.values()) {
//			String name = road.getLabel() + " " + road.getCity();
//			int roadId = road.getId();
//			trie.addWord(name, roadId);
//		}
		trie.addWord("Queen st", 555);
		trie.getWord("Queen st");
	}
	
	public static void main(String[] args) {
		new MapDrawer ();
	}

}
