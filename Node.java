import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private Location loc;
	private int scale;
	private Location origin;
	private List<Segment> outNeighbours = new ArrayList<Segment>(2);
	private List<Segment> inNeighbours = new ArrayList<Segment>(2);
	
	public Node (String input){
		String [] parts = input.split("\\s+");

		this.setId(Integer.parseInt(parts[0]));
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		this.setLoc(Location.newFromLatLon(lat, lon));
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public void draw(Graphics g) {
		Point nodeLoc = getLoc().asPoint(origin, scale);
		g.setColor(new Color(  0,   0, 255));
		g.fillRect(nodeLoc.x, nodeLoc.y, 2, 2);
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

	public String toString() {
		return "NodeId: " + this.id + ", out " + this.outNeighbours.size() + ", in " + this.inNeighbours.size();
	}
//	
//	public static void main(String[] args) {
//		Node testNode = new Node("10526	-36.871900	174.693080");
//		System.out.print(testNode);
//	}
}
