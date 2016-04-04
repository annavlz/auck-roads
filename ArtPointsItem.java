import java.util.Queue;

public class ArtPointsItem {
    public Node curNode;
    public Node parent;
    public int reachBack;
    public int depth;
    public Queue<Node> children;

    public ArtPointsItem(Node curNode, int reachBack, Node parent, int depth, Queue<Node> children) {
        this.curNode = curNode;
        this.parent = parent;
        this.reachBack = reachBack;
        this.depth = depth;
        this.children = children;
    }
}
