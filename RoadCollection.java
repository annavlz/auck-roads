import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class RoadCollection {
	
	private Map<Integer,Road> roads;
	
	public RoadCollection() {
		roads = new HashMap<Integer, Road>();		    
	}
	
	public Map<Integer, Road> getRoads(File file) {
		read(file);
		return roads;
	}

	private void read(File file) {
		String  thisLine = null;
	      try{
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         br.readLine(); //skip first line
	         while ((thisLine = br.readLine()) != null) {
	        	Road road = new Road (thisLine);
	        	roads.put(road.getId(), road);
	         }       
	         br.close();
	      } catch(Exception e){
	         e.printStackTrace();
	      }
	}
}