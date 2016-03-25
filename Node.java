import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private Location loc;
	private List<Segment> outNeighbours = new ArrayList<Segment>(2);
	private List<Segment> inNeighbours = new ArrayList<Segment>(2);
	
	public Node (String input){
		//Create node from a file line
		String [] parts = input.split("\\s+");

		this.setId(Integer.parseInt(parts[0]));
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		this.setLoc(Location.newFromLatLon(lat, lon)); //Transform degrees to kms.
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
}
