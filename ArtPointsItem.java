import java.util.Queue;

public class ArtPointsItem {
    public Node curNode;
    public ArtPointsItem parentItem;
    public int reachBack;
    public int depth;
    public Queue<Node> children;

    public ArtPointsItem(Node curNode, int reachBack, ArtPointsItem parentItem, int depth, Queue<Node> children) {
        this.curNode = curNode;
        this.parentItem = parentItem;
        this.reachBack = reachBack;
        this.depth = depth;
        this.children = children;
    }
}
