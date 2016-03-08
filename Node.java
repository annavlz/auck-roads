import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Node {
	private final int id;
	private final Location loc;
	private int scale;
	
	public Node (String input){
		String [] parts = input.split("\\s+");

		id = Integer.parseInt(parts[0]);
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		loc = Location.newFromLatLon(lat, lon);
		scale = 800;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	public void draw(Graphics g) {
////		g.setColor(color);
//	public Point asPoint(Location origin, double scale) {
//		int u = (int) ((x - origin.x) * scale);
//		int v = (int) ((origin.y - y) * scale);
//		return new Point(u, v);
//	}	
		Location origin = new Location(0,0);
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
