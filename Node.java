import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Node {
	private final int id;
	private final Location loc;
	private int scale;
	private Location origin;
	
	public Node (String input){
		String [] parts = input.split("\\s+");

		id = Integer.parseInt(parts[0]);
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		loc = Location.newFromLatLon(lat, lon);
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public void draw(Graphics g) {
		Point nodeLoc = loc.asPoint(origin, scale);
		g.setColor(new Color(  0,   0, 255));
		g.fillRect(nodeLoc.x, nodeLoc.y, 2, 2);
	}

//	public String toString() {
//		return "NodeId: " + this.id + ", Lat: " + this.loc.x + ", Lon: " + this.loc.y;
//	}
//	
//	public static void main(String[] args) {
//		Node testNode = new Node("10526	-36.871900	174.693080");
//		System.out.print(testNode);
//	}
}
