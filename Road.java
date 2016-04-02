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
		//Creates a road object from a file line
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
