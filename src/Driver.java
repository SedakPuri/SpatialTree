import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.Random;

public class Driver {

	public static void main(String[] args) {
		StdDraw.enableDoubleBuffering();
		ArrayList<Point2D> query = null;
		Random rng = new Random();

		//Adding in the 100 random points
		SpatialTree st = new SpatialTree();
		for (int i = 0; i < 100; i++) {
			st.add(new Point2D.Double(rng.nextInt(100) + 1, rng.nextInt(100) + 1));
		}
//		System.out.println(st);	
		st.draw();
		StdDraw.show();

		//Animation Loop for query method
		System.out.println("Circle a zone on the canvas!");
		boolean previousClickState = false;
		double startClickX = 0.0;
		double startClickY = 0.0;
		int guide = 0;
		while(true) {
			StdDraw.clear();
			//Checking if user is currently clicking
			boolean currentClickState = StdDraw.mousePressed();
			double releaseClickX, releaseClickY;
			if(currentClickState != previousClickState) {
				if(currentClickState) {
					// started clicking, set initial click location
					startClickX = StdDraw.mouseX();
					startClickY = StdDraw.mouseY();
				} else {
					releaseClickX = StdDraw.mouseX();
					releaseClickY = StdDraw.mouseY();
				}
			}

			//update previous mouse state
			previousClickState = currentClickState;
			st.draw();
			
			//Draw a circle if and while the user is clicking
			if(currentClickState) {
				double currentClickX = StdDraw.mouseX();
				double currentClickY = StdDraw.mouseY();
				StdDraw.setPenRadius(.005);
				StdDraw.circle(startClickX, startClickY, Point2D.distance(startClickX, startClickY, currentClickX, currentClickY));
				query = st.query(new Point2D.Double(startClickX, startClickY), Point2D.distance(startClickX, startClickY, currentClickX, currentClickY));
				guide = 1;
			//If the user stops clicking
			} else if (!currentClickState && guide == 1) {
				StdDraw.clear();
				st.draw();
				String a = "";
				for (int i = 0; i < query.size(); i++) {
					a += "(" + query.get(i).getX() + ", " + query.get(i).getY() + ")\n";
				}
				System.out.println((query.size() == 1) ? ("\nThe point located within this circle query is: \n" + a) : ("\nThe points located within this circle query are: \n" + a));
				guide = 0;
			}
			StdDraw.show();
		}
	}
}
