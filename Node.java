import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
	private int id;
	private Location loc;
	private Boolean visited;
	private Node pathFrom;
	private List<Segment> outNeighbours = new ArrayList<Segment>(2);
	private List<Segment> inNeighbours = new ArrayList<Segment>(2);
	private Segment pathSegment;
	private int depth;
	
	public Node (String input){
		//Create node from a file line
		String [] parts = input.split("\\s+");

		this.setId(Integer.parseInt(parts[0]));
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		this.setLoc(Location.newFromLatLon(lat, lon)); //Transform degrees to kms.
	}
	
	public Set<Node> getNeighbours() {
		Set<Node> neighbours = new HashSet<Node>();
		for(Segment seg : outNeighbours){
			neighbours.add(seg.getEndNode());
		}
		for(Segment seg : inNeighbours){
			neighbours.add(seg.getStartNode());
		}
		return neighbours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public List<Segment> getOutNeighbours() {
		return outNeighbours;
	}

	public void setOutNeighbours(List<Segment> outNeighbours) {
		this.outNeighbours = outNeighbours;
	}

	public List<Segment> getInNeighbours() {
		return inNeighbours;
	}

	public void setInNeighbours(List<Segment> inNeighbours) {
		this.inNeighbours = inNeighbours;
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public Node getPathFrom() {
		return pathFrom;
	}

	public void setPathFrom(Node pathFrom) {
		this.pathFrom = pathFrom;
	}

	public Segment getPathSegment() {
		return pathSegment;
	}

	public void setPathSegment(Segment pathSegment) {
		this.pathSegment = pathSegment;
	}

	public void setDepth(int depth) {
		this.depth = depth;	
	}
	
	public int getDepth() {
		return this.depth;
	}
}
