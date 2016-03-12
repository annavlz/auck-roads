import java.util.HashMap;

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
	
	public TrieNode getWord (String word){
		node = root;
		String iWord = word.toLowerCase();
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			node = node.getChildren().get(c);
			
			for()
		}
		System.out.println(node.toString());
		return node;
	}
	
//	public void main () {
//		Trie test = new Trie();
//		test.addWord("help", 12345);	
//	}
}