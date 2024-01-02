package ppPackage;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * The ppTable class defines the physical boundaries of the simulation (the ground) and includes methods to convert coordinates and reset the screen.
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 4 instructions.
 *
 */

public class ppTable {
	
	GraphicsProgram GProgram;
	
	public ppTable(GraphicsProgram GProgram) {
		this.GProgram = GProgram;
		// Create the ground plane
		drawGroundLine();
	}
	/**
	 * Method to convert from world to screen coordinates. Taken from professor Ferrie's assignment 1 example.
	 * @param p a point object in world coordinates.
	 * @return the corresponding point object in screen coordinates.
	 */
	public GPoint W2S (GPoint p) {
		return new GPoint((p.getX()-Xmin)*Xs,ymax-(p.getY()-Ymin)*Ys); } 	// Using calculations
	
	
	/**
	 * Method to convert from screen to world coordinates. Derived from W2S method.
	 * @param P a point object in screen coordinates.
	 * @return the corresponding point object in world coordinates.
	 */
	public GPoint S2W (GPoint P) {
		double ScrX=P.getX();
		double ScrY=P.getY();
		return new GPoint((((ScrX/Xs)+Xmin)),(((ymax-ScrY)/Ys)+Ymin));		// Using reverse calculations from W2S to find S2W
	}
	/**
	 * Method to reset the screen display. Removes all the stuff on the screen and draws the ground line.
	 */
	public void newScreen()	{
		GProgram.removeAll();	
		drawGroundLine();
		
	}
	/**
	 * Method to draw the ground.
	 */
	public void drawGroundLine() {
		GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3);	// A thick line HEIGHT pixels down from the top
    	gPlane.setColor(Color.BLACK);
    	gPlane.setFilled(true);
    	GProgram.add(gPlane);
	}
	
}
