import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
	TrieNode root = new TrieNode();
	TrieNode node;
	
	public Trie() {
		node = root; 
	}
	
	public void addWord (String word, int roadId){
		node = root;
		String iWord = word.toLowerCase();
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			if (node.getChildren() == null) {
				node.setChildren(new HashMap<Character, TrieNode>());
				TrieNode newNode = new TrieNode(c);
				node.getChildren().put(c, newNode);
				node = newNode;
			}
			else {
				if (node.getChildren().get(c) == null) {
					TrieNode newNode = new TrieNode(c);
					node.getChildren().put(c, newNode);
					node = newNode;
				}
				else {
					node = node.getChildren().get(c);
				}
			}			
		}
		node.setRoadId(roadId);

	}
	
	public List<Integer> getWord (String word){
		node = root;
		List<Integer> results = new ArrayList<Integer>();
		String iWord = word.toLowerCase();
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			node = node.getChildren().get(c);
		}
		getAll(node, node.getChildren(), results);
		return results;

	}
	
	private void getAll ( TrieNode node, Map<Character, TrieNode> children, List<Integer> results) {
		if (node.getRoadId() > 0){
			results.add(node.getRoadId());
		}
		if(node.getChildren() != null){
			for (TrieNode child : children.values()){
				getAll(child, child.getChildren(), results);
			}
		}
	}
}