import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class SegmentCollection {
	
	private final List<Segment> segments;
	
	public SegmentCollection() {
		segments = new ArrayList<Segment>();		    
	}
	
	public List<Segment> getSegments(File file) {
		read(file);
		return segments;
	}

	private void read(File file) {
		String  thisLine = null;
	      try{
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         br.readLine(); //skip first line
	         while ((thisLine = br.readLine()) != null) {
	        	Segment segment = new Segment (thisLine);
	        	segments.add(segment);
	         }       
	         br.close();
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}

//	public String toString() {
//		return "Segments: " + segments.toString();
//	}
	
//	public static void main(String[] args) {
//		String path = "C:/Users/Anna/Desktop/COMP261/Assign1/261a1_data/data/small/roadSeg-roadID-length-nodeID-nodeID-coords.tab";
//		File file = new File(path);
//		List<Segment> segmentsEx = new SegmentCollection().getSegments(file);
//		System.out.println(segmentsEx.toString());
//	}

}
