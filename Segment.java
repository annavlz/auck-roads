import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	private final int roadId;
	private final double length;
	private final int node1Id;
	private final int node2Id;
	private List<Location> locations;
	private int scale; 
	private Location origin;
	private Road road;
	private Node startNode;
	private Node endNode;
	private Color color;
	private Color blue = new Color(0,0,255);

	
	public Segment (String input){
		String [] parts = input.split("\\s+");

		roadId = Integer.parseInt(parts[0]);
		length = Double.parseDouble(parts[1]);
		node1Id = Integer.parseInt(parts[2]);
		node2Id = Integer.parseInt(parts[3]);
		locations = new ArrayList<Location>();
		for (int i = 4; i < parts.length; i+=2){
			double lat = Double.parseDouble(parts[i]);
			double lon = Double.parseDouble(parts[i+1]);
			Location tempLoc = Location.newFromLatLon(lat, lon);
			locations.add(tempLoc);
		}
		setColor(blue);
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
//		Point nodeStart = locations.get(0).asPoint(origin, scale);
//		g.fillRect(nodeStart.x, nodeStart.y, 2, 2);
//		Point nodeEnd = locations.get(-1).asPoint(origin, scale);
//		g.fillRect(nodeEnd.x, nodeEnd.y, 2, 2);
		
//		g.setColor(new Color(100,100,100));
		for(int i = 0; i < locations.size()-1; i++){
			Point p1 = locations.get(i).asPoint(origin, scale);
			Point p2 = locations.get(i+1).asPoint(origin, scale);
//			System.out.println(p1.x);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
//	public String toString() {
//		return "Locations: " + this.locations;
//		return "SegmentId: " + this.id + ", " + this.length + ", " + this.node1Id + ", " + this.node2Id;
//	}

	public int getRoadId() {
		return roadId;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public int getNode1Id() {
		return node1Id;
	}

	public int getNode2Id() {
		return node2Id;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	
	public String toString() {
		return this.road.getLabel() + "  " + this.road.getCity() + this.startNode.getId() + this.endNode.getId();
	}
//	public static void main(String[] args) {
//		Node testNode = new Node("10526	-36.871900	174.693080");
//		System.out.print(testNode);
//	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

