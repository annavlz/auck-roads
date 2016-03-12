public class Trie {
	TrieNode root = new TrieNode();
	TrieNode node;
	
	public Trie() {
		node = root; 
	}
	
	public void addWord (String word, int roadId){
		String iWord = word.toLowerCase();
		char[] chars = iWord.toCharArray();
		for (char c : chars){
			if (node.getChildren().get(c) == null) {
				TrieNode newNode = new TrieNode(c);
				node.getChildren().put(c, newNode);
				node = newNode;
			}
			else {
				node = node.getChildren().get(c);
			}			
		}
		node.setMarked(true);
		node.setRoadId(roadId);
	}
	
	public void main () {
		Trie test = new Trie();
		test.addWord("help", 12345);
	}
}