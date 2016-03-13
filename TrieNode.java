import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrieNode {
	private char id;
	private Map<Character, TrieNode> children;
	private List<Integer> roadId;
	
	public TrieNode () {
		this.children = null;
	}
	
	public TrieNode (char letter) {
		this.id = letter;
		this.children = null;
		this.roadId = new ArrayList<Integer>();
		
	}
	
	public Map<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, TrieNode> children) {
		this.children = children;
	}

	public List<Integer> getRoadId() {
		return roadId;
	}

	public void setRoadId(List<Integer> roadId) {
		this.roadId = roadId;
	}
}