//	int [] tall = new int [100];
//
//	String s =in.readLine();
//
//	while(s!=null)
//	{
//	    int i = 0;
//	    tall[i] = Integer.parseInt(s); //this is line 19
//	    System.out.println(tall[i]);
//	    s = in.readLine();
//	}	
public class Node {
	private final int id;
	private final Location loc;
	
	public Node (String input){
		String [] parts = input.split("\\s+");

		id = Integer.parseInt(parts[0]);
		double lat = Double.parseDouble(parts[1]);
		double lon = Double.parseDouble(parts[2]);
		loc = new Location (lat, lon);
	}
	
	public String toString() {
		return "NodeId: " + this.id + ", Lat: " + this.loc.x + ", Lon: " + this.loc.y;
	}
	
	public static void main(String[] args) {
		Node testNode = new Node("10526	-36.871900	174.693080");
		System.out.print(testNode);
	}
}
