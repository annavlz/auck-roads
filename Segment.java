import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	private final int roadId;
	private final double length;
	private final int startNodeId;
	private final int endNodeId;
	private List<Location> locations;
	private Road road;
	private Node startNode;
	private Node endNode;
	private Color color;

	
	public Segment (String input){
		//Creates a segment object from a file line
		String [] parts = input.split("\\s+");

		roadId = Integer.parseInt(parts[0]);
		length = Double.parseDouble(parts[1]);
		startNodeId = Integer.parseInt(parts[2]); //start node
		endNodeId = Integer.parseInt(parts[3]); //end node
		locations = new ArrayList<Location>(); //coordinates for curves
		for (int i = 4; i < parts.length; i+=2){
			double lat = Double.parseDouble(parts[i]);
			double lon = Double.parseDouble(parts[i+1]);
			Location tempLoc = Location.newFromLatLon(lat, lon);
			locations.add(tempLoc);
		}
		setColor(new Color(0,0,255)); //make the map blue
	}
	
	public void draw(Graphics g, int scale, Location origin) {
		g.setColor(getColor());
		for(int i = 0; i < locations.size()-1; i++){
			Point p1 = locations.get(i).asPoint(origin, scale);
			Point p2 = locations.get(i+1).asPoint(origin, scale);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	
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
		return startNodeId;
	}

	public int getNode2Id() {
		return endNodeId;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public double getLength(){
		return this.length;
	}
}

