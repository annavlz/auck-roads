public class ShortPathQueueItem implements Comparable<ShortPathQueueItem> {
    public Node curNode;
    public Node prevNode;
    public double costToHere;
    public int estTotalCost;
    public Segment segment;

    public ShortPathQueueItem(Node curNode, Node prevNode, double costToHere, double estTotalCost, Segment segment) {
        this.curNode = curNode;
        this.prevNode = prevNode;
        this.costToHere = costToHere;
        this.estTotalCost = (int) estTotalCost;
        this.segment = segment;
    }

	public int compareTo(ShortPathQueueItem another) {
		return  this.estTotalCost - another.estTotalCost;
	}
}

