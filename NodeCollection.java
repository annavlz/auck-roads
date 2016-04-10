import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


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
		return a.distance(b) * 1000;
	}
	
	public PriorityQueue<Tuple> getCloseNodes (Location locClick) {
		PriorityQueue<Tuple> closeNodes = new PriorityQueue<Tuple>();
		for(Node node : nodes.values()){ 
			if(locClick.isClose(node.getLoc(), ROUTE_RANGE)){
				closeNodes.add(new Tuple((locClick.distance(node.getLoc())*1000), node));
			}
		}
		return closeNodes;
	}

	public void findShortestPath(Node start, Node finish) {
		for(Node node : nodes.values()){
			node.setVisited(false);
			node.setPathFrom(null);
			node.setPathSegment(null);
		}
		PriorityQueue<ShortPathQueueItem> pQueue = new PriorityQueue<ShortPathQueueItem>();
		pQueue.add(new ShortPathQueueItem(start, null, 0, getDistance(start, finish), null));
		while (!pQueue.isEmpty()) {
			ShortPathQueueItem qItem = pQueue.poll();		
			Node curNode = qItem.curNode;
			if (!curNode.getVisited()) {
				curNode.setVisited(true);
				curNode.setPathFrom(qItem.prevNode);
				curNode.setPathSegment(qItem.segment);
				if (curNode == finish){
					return;
				}
				for (Segment roadOut : curNode.getOutNeighbours()){
					Node nodeNeighbour;
					if(curNode.getId() == roadOut.getNode1Id()){
						nodeNeighbour = roadOut.getEndNode();
					}
					else {
						nodeNeighbour = roadOut.getStartNode();
					}
					
					if (!nodeNeighbour.getVisited()){
						double costToNeigh = qItem.costToHere + (roadOut.getLength() * 1000);
						double estTotalCost = costToNeigh + getDistance(nodeNeighbour, finish);
						pQueue.add(new ShortPathQueueItem(nodeNeighbour, curNode, costToNeigh, estTotalCost, roadOut));
					}				
				}
			}	
		}
	}
	
	
	public Stack<Segment> getThePath (Node goal){
		Stack<Segment> route = new Stack<Segment>();
		goToNode(goal, route);
		route.pop();
		return route;
	}
	

	public void goToNode (Node node, Stack<Segment> route){
		route.push(node.getPathSegment());
		if (node.getPathFrom() != null){
			goToNode(node.getPathFrom(), route);
		}
	}

	public Set<Node> findCriticalPoints() {
		for(Node node : nodes.values()){
			node.setDepth(Integer.MAX_VALUE);
		}
		Set<Node> artPoints = new HashSet<Node>();
		for(Entry<Integer, Node> entry : nodes.entrySet()){
			Integer subTree = 0;
			Node node = entry.getValue();
			if(node.getDepth() == Integer.MAX_VALUE){
				node.setDepth(0);
				for(Node neighbour : node.getNeighbours()){
					if(neighbour.getDepth() == Integer.MAX_VALUE){
						iterateArtPoints(neighbour, node, artPoints);
					}
					subTree++;
				}
			}
			if(subTree > 1){
				artPoints.add(node);
			}			
		}
		return artPoints;
	}
	private void iterateArtPoints(Node firstNode, Node root, Set<Node> artPoints){
		Stack<ArtPointsItem> stackAtWork = new Stack<ArtPointsItem>();
		ArtPointsItem rootItem = new ArtPointsItem(root, 0, null, 0, null);
		stackAtWork.add(new ArtPointsItem(firstNode, 0, rootItem, 1, null));

		while(!stackAtWork.empty()){
			ArtPointsItem stackItem = stackAtWork.peek();
			Node node = stackItem.curNode;
			if(stackItem.children == null){
				node.setDepth(stackItem.depth);
				stackItem.reachBack =stackItem.depth;
				stackItem.children = new LinkedList<Node>();
				for(Node neighbour : node.getNeighbours()){
					if(neighbour != stackItem.parentItem.curNode){
						stackItem.children.add(neighbour);		
					}
				}
			}
			else if(stackItem.children.size() > 0){
				Node child = stackItem.children.poll();
				if(child.getDepth() < Integer.MAX_VALUE){
					stackItem.reachBack = Math.min(stackItem.reachBack, child.getDepth());
				}
				else {
					stackAtWork.add(new ArtPointsItem(child, 0, stackItem, node.getDepth()+1, null));
				}
			}
			else {
				if(firstNode != node){
					if(stackItem.reachBack >= stackItem.parentItem.depth){
						artPoints.add(stackItem.parentItem.curNode);
					}
					stackItem.parentItem.reachBack = Math.min(stackItem.parentItem.reachBack, stackItem.reachBack);
				}
				stackAtWork.pop();
			}
		}
	}
}