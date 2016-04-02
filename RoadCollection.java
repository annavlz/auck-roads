import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class RoadCollection {
	
	private Map<Integer, Road> roads = new HashMap<Integer, Road>();
		
	public RoadCollection(File file) { 
		roads = getRoads(file);
	}
	
	private Map<Integer, Road> getRoads(File file) {
		read(file);
		return roads;
	}
	
	public Set<String> getRoadNames(Set<Integer> roadIDs) {
		Set<String> roadNames = new HashSet<String>();
		//Iterate through all unique road id we've got from closets nodes.
		for(int id : roadIDs){
			Road road = roads.get(id); //Find the road.
			String name = road.getLabel() + " " + road.getCity(); //Get it's name.
			roadNames.add(name); //Remove duplicates.			
		}
		return roadNames;
	}
	
	private void read(File file) {
		//Reads file and creates a hashmap of roads
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

	public Road get(int roadId) {
		return roads.get(roadId);
	}


	public Trie createTrie() {	
		Trie trie = new Trie();
		for (Road road : roads.values()) { //Iterate through all roads
			String name = road.getLabel() + " " + road.getCity(); //Get their names
			int roadId = road.getId(); //take values
			trie.addWord(name, roadId); //and add to the trie.
		}
		return trie;
	}
}