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
	private String mode = "search";
	private List<Node> routePoints = new ArrayList<Node>(); 
	
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
		getTextOutputArea().setText("Choose your starting point first and then you destination point.\n");
		
	}

	@Override
	protected void findCriticalPoints() {
		getTextOutputArea().setText("Critical points.");
		
	}
	
	@Override
	protected void redraw(Graphics g) {
		//Draw lines from segments list.
		if (segmentCollection != null){//Catches the bug from GUI: redraw is triggered on run, before any load.
			for (Segment segment : segmentCollection) {
				segment.setScale(scale);
				segment.setOrigin(origin);
				segment.draw(g);
			}
		}
	}
	
	@Override
	protected void onClick(MouseEvent e) {
		getTextOutputArea().setText(""); //Clear the outbox.
		Point pClick = new Point(e.getX(), e.getY()); //Get mouse coordinates.
		Location locClick = Location.newFromPoint(pClick, origin, scale); //Transform pixels to km.
		
		switch(mode){
			case "search":
				Set<Integer> roadIDs = new HashSet<Integer>();
				Set<String> roadNames = new HashSet<String>();
				//Iterate through nodes and show names on closest in range 100 m.
				for(Node node : nodeCollection.values()){ 
					if(locClick.isClose(node.getLoc(), 0.1)){
						List<Segment> inSegments = node.getInNeighbours(); //Get incoming segments.
						List<Segment> outSegments = node.getOutNeighbours(); //Get outgoing segments.
						//Get road IDs from segments, removing duplicates.
						for(Segment seg : inSegments){
							roadIDs.add(seg.getRoadId());
						}
						for(Segment seg : outSegments){
							roadIDs.add(seg.getRoadId());
						}
					}
				}
				//Iterate through all unique road id we've got from closets nodes.
				for(int id : roadIDs){
					Road road = roadCollection.get(id); //Find the road.
					String name = road.getLabel() + " " + road.getCity(); //Get it's name.
					roadNames.add(name); //Remove duplicates.
					
				}
				for(String name : roadNames){
					getTextOutputArea().append(name + "\n"); //Print to outbox.
				}
				break;
			case "route":
				PriorityQueue<Tuple> closeNodes = new PriorityQueue<Tuple>();
				for(Node node : nodeCollection.values()){ 
					if(locClick.isClose(node.getLoc(), 0.1)){
						closeNodes.add(new Tuple(locClick.distance(node.getLoc()), node));
					}
				}
				
				if(routePoints.size() == 0){
					Node start = closeNodes.poll().node;
					routePoints.add(start);
					getTextOutputArea().append("Your route from " + start.getId());
				}
				if(routePoints.size() == 1){
					Node finish = closeNodes.poll().node;
					routePoints.add(finish);
					getTextOutputArea().append(" to " + finish.getId() + " is:");
					findThePath();
					routePoints = new ArrayList<Node>(); 
				}
		}
	}
	
	@Override
	protected void onSearch() {
		//Redraw previous search result with blue.
		if(!prevSegments.isEmpty()){
			for(Segment seg : prevSegments){
				seg.setColor(blue);
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
					seg.setColor(red); //Set them red.
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
		 nodeCollection = new NodeCollection().getNodes(nodesFile); //Read nodes from file.
		 roadCollection = new RoadCollection().getRoads(roadsFile); //Read roads from file.
		 segmentCollection = new SegmentCollection().getSegments(segmentsFile); //Read segments from file.
		 redraw();
		 setupCollections();
		 setupTrie();
	}

	private void findThePath() {
		mode = "search";
//		getTextOutputArea().setText("Your root from ";
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
			if(road.getOneway() == 0){ //Checks if road is 2way and doubles segments.
				endNode.getOutNeighbours().add(segment);
				startNode.getInNeighbours().add(segment);
			}
		}
	}
	
	private void setupTrie() {
		trie = new Trie();
		for (Road road : roadCollection.values()) { //Iterate through all roads
			String name = road.getLabel() + " " + road.getCity(); //Get their names
			int roadId = road.getId(); //take values
			trie.addWord(name, roadId); //and add to the trie.
		}
	}
	
	public static void main(String[] args) {
		new MapDrawer ();
	}

}
