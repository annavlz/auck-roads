import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class MapDrawer extends GUI{
	private NodeCollection nodeCollection;
	private RoadCollection roadCollection;
	private List<Segment> segmentCollection;
	private Trie trie;
	private int scale;
	private Location origin;
	private List<Segment> prevSegments;
	private String mode = "search";
	private List<Node> routePoints; 
	private Set<Node> artPoints;
	
	private final Color RED = new Color(255,0,0);
	private final Color BLUE = new Color(0,0,255);

	
	
	public  MapDrawer () {
		// Set to show central Auckland
		scale = 54;
		origin = new Location(-5,5);
		prevSegments = new ArrayList<Segment>(); 
		//Create a place to store previous trie search. 
		//This allows to set only red segments back to blue, and to redraw the whole map.
	}

	@Override
	protected void findPath() {
		mode = "route";
		routePoints = new ArrayList<Node>(); 
		getTextOutputArea().setText("Choose your starting point first and then you destination point.\n");
		
	}

	@Override
	protected void findCriticalPoints() {
		artPoints = nodeCollection.findCriticalPoints();
		getTextOutputArea().setText("Critical points.");	
	}
	
	@Override
	protected void redraw(Graphics g) {
		//Draw lines from segments list.
		if (segmentCollection != null){//Catches the bug from GUI: redraw is triggered on run, before any load.
			for (Segment segment : segmentCollection) {
				segment.draw(g, scale, origin);
			}
		}
		if (artPoints != null){
			for (Node node : artPoints){
				node.draw(g, scale, origin);
			}
		}
	}
	
	@Override
	protected void onClick(MouseEvent e) {
		//Redraw previous search result with blue.
		if(!prevSegments.isEmpty()){
			for(Segment seg : prevSegments){
				seg.setColor(BLUE);
			}			
		}
		prevSegments = new ArrayList<Segment>(); //Empty previous search.
		artPoints = null;
		
		getTextOutputArea().setText(""); //Clear the outbox.
		Point pClick = new Point(e.getX(), e.getY()); //Get mouse coordinates.
		Location locClick = Location.newFromPoint(pClick, origin, scale); //Transform pixels to km.
		
		switch(mode){
			case "search":
				search(locClick);
				break;
			case "route":
				route(locClick);
				break;
		}
	}
	
	private void search (Location locClick) {
		Set<Integer> roadIDs = nodeCollection.getNodeRoadsIDs(locClick);
		Set<String> roadNames = roadCollection.getRoadNames(roadIDs);		
		for(String name : roadNames){
			getTextOutputArea().append(name + "\n"); //Print to outbox.
		}
	}
	
	private void route (Location locClick) {
		PriorityQueue<Tuple> closeNodes = nodeCollection.getCloseNodes(locClick);
		if(routePoints.size() == 0){
			Node start = closeNodes.poll().node;
			routePoints.add(start);
			getTextOutputArea().append("Your route from " + start.getId());
		}
		else if (routePoints.size() == 1){
			Node finish = closeNodes.poll().node;
			routePoints.add(finish);
			getTextOutputArea().append("Your route from " + routePoints.get(0).getId() + " to " + routePoints.get(1).getId());
			getTextOutputArea().append("\nClick to confirm.");
		}
		else {
			getTextOutputArea().append("Searching");
			findThePath();
		}
	}
	
	@Override
	protected void onSearch() {
		//Redraw previous search result with blue.
		if(!prevSegments.isEmpty()){
			for(Segment seg : prevSegments){
				seg.setColor(BLUE);
			}			
		}
		prevSegments = new ArrayList<Segment>(); //Empty previous search.
		getTextOutputArea().setText(""); //Clear the outbox.
		String searchWord = getSearchBox().getText();
		List<Integer> results = trie.getWord(searchWord); //Find matching road IDs.
		
		if(!results.isEmpty()){
			//If something found
			Set<String> roadNames = new HashSet<String>(); //Names to display.
			for(int roadId : results){
				Road road = roadCollection.get(roadId);
				List<Segment> segments = road.getSegments(); //Find segments of the road.
				for(Segment seg : segments){
					seg.setColor(RED); //Set them red.
					prevSegments.add(seg); //Memorize them.
				}
				roadNames.add(road.getLabel() + " " + road.getCity()); //Add the name to the set for display.
				
			}
			redraw();
			for(String name : roadNames){
				getTextOutputArea().append(name + "\n"); //Print names
			}
		}
		else {
			//If not found
			getTextOutputArea().setText("Not found");
		}
	}

	@Override
	protected void onMove(Move m) {
		
		switch(m){
			case ZOOM_IN: zoom(2);
				break;
			case ZOOM_OUT: zoom(-2);
				break;
			case NORTH: move(0,2);
				break;
			case SOUTH: move(0,-2);
				break;
			case EAST: move(2,0);
				break;
			case WEST: move(-2,0);
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
		 nodeCollection = new NodeCollection(nodesFile); //Read nodes from file.
		 roadCollection = new RoadCollection(roadsFile); //Read roads from file.
		 segmentCollection = new SegmentCollection().getSegments(segmentsFile); //Read segments from file.
		 redraw();
		 setupCollections();
		 trie = roadCollection.createTrie();
	}

	private void findThePath() {
		mode = "search";
		Node start = routePoints.get(0);
		Node finish = routePoints.get(1);
		nodeCollection.findShortestPath(start, finish);
		Stack<Segment> route = nodeCollection.getThePath(finish);	
		String roads = "";
		Double totalLength = 0.0;
		while(!route.isEmpty()){
			Segment seg = route.pop();
			seg.setColor(RED); //Set them red.
			prevSegments.add(seg); //Memorize them.
			totalLength += seg.getLength();
			Road road = roadCollection.get(seg.getRoadId());
			roads += road.getLabel() + ", " + road.getCity() + ", " + Integer.toString((int) Math.round(seg.getLength() * 1000)) + "m.\n";
		}
		redraw();
		getTextOutputArea().setText(roads);
		getTextOutputArea().append("Total distance: " + Math.round(totalLength) +"km.");
	}
	
	private void setupCollections() {
		//Iterate through the segments and add all data to the data structures.
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
			if (road.getOneway() == 0){ //Checks if road is 2way and doubles segments.
				endNode.getOutNeighbours().add(segment);
				startNode.getInNeighbours().add(segment);
			}
		}
	}
	
	public static void main(String[] args) {
		new MapDrawer ();
	}

}
