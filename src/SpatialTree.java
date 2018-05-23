/*Sedak Puri
 * Spatial Tree Assignment
 * Professor Albow
 * Intro to CS 3
 */
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdDraw;

public class SpatialTree {
	private int size;
	private SpatialTreeNode root;

	public SpatialTree() {
		size = 0;
		root = null;
	}

	/**
	 * Adds a point to a tree
	 * @param point is a point to be added to the tree
	 */
	public void add(Point2D point) {
		SpatialTreeNode n = find(root, point);

		//Case 1: Tree is empty
		if(n == null) 
		{
			root = new SpatialTreeNode(point, null, true);
			n = root;
		}

		//Case 2: value exists already
		else if(n.point.equals(point)) {
			return;

		} else if(n.isXNode) {
			//Point is greater than node
			if (n.point.getX() < point.getX()) {
				n.right = new SpatialTreeNode(point, n, false);
			} 
			//Point is less than node
			else if (n.point.getX() >= point.getX()) {
				n.left = new SpatialTreeNode(point, n, false);
			}
		} else if (!n.isXNode) {
			//Point is greater than node
			if (n.point.getY() < point.getY()) {
				n.right = new SpatialTreeNode(point, n, true);
			} 
			//Point is less than node
			else if (n.point.getY() >= point.getY()) {
				n.left = new SpatialTreeNode(point, n, true);
			}
		}
		size++;
		return;
	}


	/**
	 * Find the node that contains v if it exits. If it doesn't exist
	 * then return the node that would have been the parent of a node
	 * containing v.
	 * @param r root of the subtree
	 * @param v value we are searching for
	 * @return Node containing v or node that would have a parent of v
	 */
	private SpatialTreeNode find(SpatialTreeNode r, Point2D point){
		//Case 1: Tree is empty
		if(r == null) {
			return null;
		}

		//Case 2: If the node is equal to the point
		if(r.point.equals(point)) {																					
			return r;
		}

		//If the node is an x
		if(r.isXNode) {
			//Case 3: If the point is greater than the node
			if(r.point.getX() < point.getX() && r.right != null)
			{
				return find(r.right, point);
			}
			//Case 4: If the point is less than the node
			if(r.point.getX() >= point.getX() && r.left != null)
			{
				return find(r.left, point);
			}
			//If the node is a y
		} else if (!r.isXNode) {

			//Case 5: If the point is greater than the node
			if(r.point.getY() < point.getY() && r.right != null)
			{
				return find(r.right, point);
			}

			//Case 6: If the point is greater than the node
			if(r.point.getY() >= point.getY() && r.left != null)
			{
				return find(r.left, point);
			}
		}

		//All other cases: I didn't find the value
		return r;
	}

	/**
	 * Main method to draw the spatial tree
	 */
	public void draw() {
		StdDraw.setXscale(0, 100);
		StdDraw.setYscale(0, 100);
		Rectangle k = new Rectangle(0, 0, 100, 100);
		draw(root, k);
	}

	/**
	 * Helper Method to draw the spatial tree
	 * @param n is the node being recursively passed in
	 * @param r is the rectangle bounds
	 */
	private void draw(SpatialTreeNode n, Rectangle bounds) {
		//Drawing the root
		if (n == root) {
			StdDraw.setPenRadius(.005);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(n.point.getX(), 0, n.point.getX(), 100);
			StdDraw.setPenRadius(.015);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.point(n.point.getX(), n.point.getY());
			if (n.left != null) {					
				Rectangle rct = new Rectangle(0, 0,(int) n.point.getX(), 100);
				draw(n.left, rct);
			}
			if (n.right != null) {
				Rectangle rct = new Rectangle((int) n.point.getX(), 0, (int) (100 - n.point.getX()), 100);
				draw(n.right, rct);
			}
			//Drawing a vertical x node (excluding the root)
		} else if (n != null && n.isXNode) {
			StdDraw.setPenRadius(.005);
			StdDraw.setPenColor(StdDraw.BLUE);	
			StdDraw.line(n.point.getX(), bounds.getMinY(), n.point.getX(), bounds.getMaxY());		
			StdDraw.setPenRadius(.015);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.point(n.point.getX(), n.point.getY());
			//Left horizontal
			if (n.left != null) {
				Rectangle rct = new Rectangle((int) bounds.getMinX(),(int) bounds.getMinY(),(int) (n.point.getX() - bounds.getMinX()), (int) (bounds.getMaxY() - bounds.getMinY()));		
				draw(n.left, rct);
			}
			//Right horizontal
			if (n.right != null) {
				Rectangle rct = new Rectangle((int) n.point.getX(), (int) bounds.getMinY(),(int) (bounds.getMaxX() - n.point.getX()), (int) (bounds.getMaxY() - bounds.getMinY()));		 
				draw(n.right, rct);
			}
			//Drawing a horizontal Y Node 
		} else if (n != null && !n.isXNode) {
			StdDraw.setPenRadius(.005);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(bounds.getMinX(), n.point.getY(), bounds.getMaxX(), n.point.getY());		
			StdDraw.setPenRadius(.015);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.point(n.point.getX(), n.point.getY());
			//Below
			if (n.left != null) {
				Rectangle rct = new Rectangle((int) bounds.getMinX(),(int) bounds.getMinY(),(int) (bounds.getMaxX() - bounds.getMinX()), (int) (n.point.getY() - bounds.getMinY()));		
				draw(n.left, rct);
			}
			//Above
			if (n.right != null) {
				Rectangle rct = new Rectangle((int) bounds.getMinX(),(int) n.point.getY(),(int) (bounds.getMaxX() - bounds.getMinX()), (int) (bounds.getMaxY() - n.point.getY()));		
				draw(n.right, rct);
			}		
		} 
	}


	/**
	 * Method to list all points located inside query circle
	 * @param center is the point object of center
	 * @param radius is the radius of the circle the user input
	 * @return
	 */
	public ArrayList<Point2D> query(Point2D center, double radius){
		ArrayList<Point2D> points = new ArrayList<>();
		addToArrayList(root, points, center,radius);
		return points;
	}

	/**
	 * Helper method to recursively add to arrayList for the query method
	 * @param n is the node being recursively passed in
	 * @param p is the arrayList
	 * @param center is the point object of center
	 * @param radius is the radius of the circle the user input
	 */
	private void addToArrayList(SpatialTreeNode n, ArrayList<Point2D> p, Point2D center, double radius) {
		if (n == null) {
			return;
		} else {
			//Only add to the arraylist if the point is within the radius distance
			if (n.point.distance(center.getX(), center.getY(), n.point.getX(), n.point.getY()) <= radius) {
				p.add(n.point);
			}
		}
		if(n.left == null && n.right == null) {
			return;
		} else { 
			//Only searching what is absolutely necessary
			if(n.left != null) {
				addToArrayList(n.left, p, center, radius);
			}
			if(n.right != null) {
				addToArrayList(n.right, p, center, radius);
			}
		}
	}
	public class SpatialTreeNode{
		private Point2D point;
		private SpatialTreeNode left;
		private SpatialTreeNode right;
		private SpatialTreeNode parent;
		boolean isXNode;

		public SpatialTreeNode(Point2D point, SpatialTreeNode parent, boolean isXNode) {
			this.isXNode = isXNode;
			this.point = point;
			left = null;
			right = null;
			this.parent = parent;
		}

		public String toString() {
			return point.getX() + " " + point.getY();
		}
	}

	/**
	 * Method recursively print out the tree
	 * @param r is the node being passed in recursively
	 * @param sb is the stringbuilder object that is being appended to
	 * @param level is the level of spacing at that level 
	 */
	private void toString(SpatialTreeNode r, StringBuilder sb, int level)
	{
		if(r != null)
		{
			//Print the root
			for(int i=0; i < 2 * level; i++)
			{
				sb.append(" ");
			}

			//Recursively print the left and right children
			sb.append("[" + (int) r.point.getX() + ", " + (int) r.point.getY() + ", " + ((r.isXNode == true)? "x": "y") + "]" + "\n");

			toString(r.left, sb, level+1);
			toString(r.right, sb, level+1);
		}
	}

	/**
	 * Method to pretty print the tree
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		toString(root, sb, 0);
		return sb.toString();
	}
}

