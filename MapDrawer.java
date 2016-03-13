import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MapDrawer extends GUI{
	private Map<Integer, Node> nodeCollection;
	private Map<Integer, Road> roadCollection;
	private List<Segment> segmentCollection;
	private Trie trie;
	private int scale;
	private Location origin;
	private Color red = new Color(255,0,0);
	private Color blue = new Color(0,0,255);
	private List<Segment> prevSegments;
	
	public  MapDrawer () {
		// Set to show central Auckland
		scale = 54;
		origin = new Location(-5,5);
		prevSegments = new ArrayList<Segment>();
	}

	@Override
	protected void redraw(Graphics g) {
		if (segmentCollection != null){
			for (Segment segment : segmentCollection) {
				segment.setScale(scale);
				segment.setOrigin(origin);
				segment.draw(g);
			}
		}
//		System.out.println(scale);
//		System.out.println(origin.toString());
	}
	

	@Override
	protected void onClick(MouseEvent e) {
		getTextOutputArea().setText("");
		Point pClick = new Point(e.getX(), e.getY());
		Location locClick = Location.newFromPoint(pClick, origin, scale);
		Set<Integer> roadIDs = new HashSet<Integer>();
		for(Node node : nodeCollection.values()){
			if(locClick.isClose(node.getLoc(), 0.1)){
				List<Segment> inSegments = node.getInNeighbours();
				List<Segment> outSegments = node.getOutNeighbours();
				for(Segment seg : inSegments){
					roadIDs.add(seg.getRoadId());
				}
				for(Segment seg : outSegments){
					roadIDs.add(seg.getRoadId());
				}
			}
		}
		for(int id : roadIDs){
			Road road = roadCollection.get(id);
			getTextOutputArea().append(road.getLabel() + " " + road.getCity() + "\n");
		}
	}
	
	@Override
	protected void onSearch() {
		if(!prevSegments.isEmpty()){
			for(Segment seg : prevSegments){
				seg.setColor(blue);
			}			
		}
		prevSegments = new ArrayList<Segment>();
		getTextOutputArea().setText("");
		String searchWord = getSearchBox().getText();
		List<Integer> results = trie.getWord(searchWord);
		
		if(!results.isEmpty()){
			for(int roadId : results){
				Road road = roadCollection.get(roadId);
				List<Segment> segments = road.getSegments();
				for(Segment seg : segments){
					seg.setColor(red);
					prevSegments.add(seg);
				}
				redraw();
				getTextOutputArea().append(road.getLabel() + " " + road.getCity() + "\n");
			}
		}
		else {
			getTextOutputArea().setText("Not found");
		}
	}

	@Override
	protected void onMove(Move m) {
		
		switch(m){
			case ZOOM_IN: zoom(1);
				break;
			case ZOOM_OUT: zoom(-1);
				break;
			case NORTH: move(0,5);
				break;
			case SOUTH: move(0,-5);
				break;
			case EAST: move(5,0);
				break;
			case WEST: move(-5,0);
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
		for (Road road : roadCollection.values()) {
			String name = road.getLabel() + " " + road.getCity();
			int roadId = road.getId();
			trie.addWord(name, roadId);
		}
	}
	
	public static void main(String[] args) {
		new MapDrawer ();
	}

}
