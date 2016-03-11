import java.util.ArrayList;
import java.util.List;

public class Road {
	private int id;
	private int type;
	private String label;
	private String city;
	private int oneway;
	private int speed;
	private int roadclass;
	private int notforcar;
	private int notforpede;
	private int notforbicy;
	private List<Segment> segments = new ArrayList<Segment>();
	
	public Road (String input) {
		String [] parts = input.split("\\t+");
			this.setId(Integer.parseInt(parts[0]));
			type = (Integer.parseInt(parts[1]));
			setLabel(parts[2]);
			setCity(parts[3]);
			setOneway((Integer.parseInt(parts[4])));
			speed = (Integer.parseInt(parts[5]));
			roadclass = (Integer.parseInt(parts[6]));
			notforcar = (Integer.parseInt(parts[7]));
			notforpede = (Integer.parseInt(parts[8]));
			notforbicy = (Integer.parseInt(parts[9]));	
	}
	
	public int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;		
	}
//	
//	private static void read(File file) {
//		String  thisLine = null;
//	      try{
//	         BufferedReader br = new BufferedReader(new FileReader(file));
//	         br.readLine(); //skip first line
//	         while ((thisLine = br.readLine()) != null) {
//	        	Road road = new Road (thisLine);
//	        	System.out.println(road);
//	         }       
//	         br.close();
//	      } catch(Exception e){
//	         e.printStackTrace();
//	      }
//	}
//	
//	
	public String toString() {
		return this.getLabel() + "      " + this.getCity() + "segments" + this.getSegments();
	}
//	
//	public static void main(String[] args) {
//		String path = "C:/Users/Anna/Desktop/COMP261/Assign1/261a1_data/data/small/roadID-roadInfo.tab";
//		File file = new File(path);
//		read(file);
//	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

	public int getOneway() {
		return oneway;
	}

	public void setOneway(int oneway) {
		this.oneway = oneway;
	}
}
