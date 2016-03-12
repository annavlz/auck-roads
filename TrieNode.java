import java.util.Map;

public class TrieNode {
	private char id;
	private Map<Character, TrieNode> children;
	private boolean marked;
	private int roadId;
	
	public TrieNode () {
		this.setChildren(null);
	}
	
	public TrieNode (char letter) {
		this.id = letter;
		this.setChildren(null);
		this.marked = false;
		this.roadId = 0;
		
	}
	
	public void addChild (TrieNode child){
		getChildren().put(child.id, child);
	}

	public Map<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, TrieNode> children) {
		this.children = children;
	}
	
	public String toString() {
		return this.id + " " + this.marked + " " + this.roadId;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public int getRoadId() {
		return roadId;
	}

	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}
}