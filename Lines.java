import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is a small example class to demonstrate extending the GUI class and
 * implementing the abstract methods. Instead of doing anything maps-related, it
 * draws some squares to the drawing area which are removed when clicked. Some
 * information is given in the text area, and pressing any of the navigation
 * buttons makes a new set of squares.
 * 
 * @author tony
 */
public class Lines extends GUI {


	public Lines() {
		redraw();
	}

	@Override
	protected void redraw(Graphics g) {
		g.setColor(new Color(  0,   0, 255));
		g.drawLine(50, 50,100,100);
	}

	@Override
	protected void onClick(MouseEvent e) {

	}

	@Override
	protected void onSearch() {
		getTextOutputArea().setText(getSearchBox().getText());
	}

	@Override
	protected void onMove(Move m) {

	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		getTextOutputArea().setText("example doesn't load any files.");
	}

	/**
	 * A simple inner class that stores the data for the squares and has some
	 * helper methods.
	 */

	public static void main(String[] args) {
		new Lines();
	}
}

// code for COMP261 assignments
