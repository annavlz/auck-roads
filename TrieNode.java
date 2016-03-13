import java.util.Map;

public class TrieNode {
	private char id;
	private Map<Character, TrieNode> children;
	private int roadId;
	
	public TrieNode () {
		this.children = null;
	}
	
	public TrieNode (char letter) {
		this.id = letter;
		this.children = null;
		this.roadId = 0;
		
	}
	
	public Map<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, TrieNode> children) {
		this.children = children;
	}

	public int getRoadId() {
		return roadId;
	}

	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}
}