import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	private final int id;
	private final double length;
	private final int node1Id;
	private final int node2Id;
	private List<Location> locations;
	private int scale; 
	private Location origin;
	
	public Segment (String input){
		String [] parts = input.split("\\s+");

		id = Integer.parseInt(parts[0]);
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
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(  0,   0, 255));
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
	
//	public static void main(String[] args) {
//		Node testNode = new Node("10526	-36.871900	174.693080");
//		System.out.print(testNode);
//	}
}

