import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class NodeCollection {
	private final double SEARCH_RANGE = 0.1;
	private final double ROUTE_RANGE = 1;
	
	private Map<Integer,Node> nodes = new HashMap<Integer, Node>();;
	
	public NodeCollection(File file) {
		nodes = getNodes(file);		    
	}
	
	private Map<Integer, Node> getNodes(File file) {
		read(file);
		return nodes;
	}

	private void read(File file) {
		//Reads file and creates a hashmap of nodes
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

	public Node get(int startNodeId) {
		return nodes.get(startNodeId);
	}
	
	public Set<Integer> getNodeRoadsIDs(Location locClick) {
		Set<Integer> roadIDs = new HashSet<Integer>();
		//Iterate through nodes and show names on closest in range 100 m.
		for(Node node : nodes.values()){ 
			if(locClick.isClose(node.getLoc(), SEARCH_RANGE)){
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
		return roadIDs;
	}
	
	private double getDistance(Node start, Node finish) {
		Location a = start.getLoc();
		Location b = finish.getLoc();
		return a.distance(b);
	}
	public PriorityQueue<Tuple> getCloseNodes (Location locClick) {
		PriorityQueue<Tuple> closeNodes = new PriorityQueue<Tuple>();
		for(Node node : nodes.values()){ 
			if(locClick.isClose(node.getLoc(), ROUTE_RANGE)){
				closeNodes.add(new Tuple(locClick.distance(node.getLoc()), node));
			}
		}
		return closeNodes;
	}

	public List<Node> findShortestPath(Node start, Node finish) {
		List<Node> routeNodes = new ArrayList<Node>();
		for(Node node : nodes.values()){
			node.setVisited(false);
			node.setPathFrom(null);
		}
		PriorityQueue<ShortPathQueueItem> pQueue = new PriorityQueue<ShortPathQueueItem>();
		pQueue.add(new ShortPathQueueItem(start, null, 0, getDistance(start, finish), null));
		while (!pQueue.isEmpty()) {
			ShortPathQueueItem qItem = pQueue.poll();
			Node curNode = qItem.curNode;
			if (!curNode.getVisited()) {
				curNode.setVisited(true);
				curNode.setPathFrom(qItem.prevNode);
				curNode.setCost(qItem.costToHere);
				curNode.setPathSegment(qItem.segment);
				
			}
			
		}
		return routeNodes;
		
	}

}
