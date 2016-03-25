import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
	TrieNode root = new TrieNode();
	TrieNode node;
	
	public Trie() {
		node = root; //start with a root
	}
	
	public void addWord (String word, int roadId){
		node = root; //every time, adding a word, start from the root
		String iWord = word.toLowerCase(); //case insensitive search
		char[] chars = iWord.toCharArray(); //make possible iteration
		for (char c : chars){
			if (node.getChildren() == null) {
				//create the first child
				node.setChildren(new HashMap<Character, TrieNode>());
				TrieNode newNode = new TrieNode(c);
				node.getChildren().put(c, newNode);
				node = newNode;
			}
			else {
				if (node.getChildren().get(c) == null) {
					//create a new child
					TrieNode newNode = new TrieNode(c);
					node.getChildren().put(c, newNode);
					node = newNode;
				}
				else {
					//move to the next child
					node = node.getChildren().get(c);
				}
			}			
		}
		//at the end of the word add value
		node.getRoadId().add(roadId);

	}
	
	public List<Integer> getWord (String word){
		//start from the root
		node = root;
		List<Integer> results = new ArrayList<Integer>();
		boolean found = false;
		String iWord = word.toLowerCase(); //case insensitive
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			TrieNode tNode = node.getChildren().get(c);
			if(tNode != null){
				//go to the next if match
				node = tNode;
				found = true;
			}
			else {
				//stop if no match
				found = false;
				break;
			}
		}
		if(found){
			//get values from all nodes lower then the last one matched
			getAll(node, node.getChildren(), results);
		}
		return results;
	}
	
	private void getAll ( TrieNode node, Map<Character, TrieNode> children, List<Integer> results) {
		if (!node.getRoadId().isEmpty()){
			for(int id : node.getRoadId()){
				// if there is a value in this node, take all from the array
				results.add(id);
			}
		}
		if(node.getChildren() != null){
			//check if there is deeper level
			for (TrieNode child : children.values()){
				//recurse
				getAll(child, child.getChildren(), results);
			}
		}
	}
}