import java.util.Comparator;

public class Tuple implements Comparable<Tuple> {
    public final int dist;
    public final Node node;

    public Tuple(double dist, Node node) {
        this.dist = (int) dist;
        this.node = node;
    }

	public int compareTo(Tuple second) {
		return  this.dist - second.dist;
	}
}
