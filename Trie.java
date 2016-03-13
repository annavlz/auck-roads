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
		node.getRoadId().add(roadId);

	}
	
	public List<Integer> getWord (String word){
		node = root;
		List<Integer> results = new ArrayList<Integer>();
		boolean found = false;
		String iWord = word.toLowerCase();
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			TrieNode tNode = node.getChildren().get(c);
			if(tNode != null){	
				node = tNode;
				found = true;
			}
			else {
				found = false;
				break;
			}
		}
		if(found){
			getAll(node, node.getChildren(), results);
		}
		return results;
	}
	
	private void getAll ( TrieNode node, Map<Character, TrieNode> children, List<Integer> results) {
		if (!node.getRoadId().isEmpty()){
			for(int id : node.getRoadId()){
				results.add(id);
			}
		}
		if(node.getChildren() != null){
			for (TrieNode child : children.values()){
				getAll(child, child.getChildren(), results);
			}
		}
	}
}