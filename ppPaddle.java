package ppPackage;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;

import java.awt.Color;

/**
 * The ppPaddle class extends thread to allow it to run at the same time as other parts of the program. It creates the paddle, determines how to update the position and determine the velocity of the paddle without actually having the parameters since the mouse acts as the parameter setter and defines a few methods to help with the collision process as well as the paddle location.
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 3 instructions and from professor Katrina Poulin's tutorial.
 */
public class ppPaddle extends Thread {
	double X;					// X-coordinate of the paddle
	double Y;					// Y-coordinate of the paddle
	double Vx;					// X-velocity of the paddle
	double Vy;					// Y-velocity of the paddle
	GRect myPaddle;				// GRect
	GraphicsProgram GProgram;	// GraphicsProgram methods
	ppTable myTable;			// ppTable methods
	Color myColor;				// Color
	
	/**
	 * Parameters for ppPaddle and paddle creation.
	 * @param X paddle X position.
	 * @param Y paddle Y position.
	 * @param myColor Color of paddle
	 * @param myTable access to ppTable methods.
	 * @param GProgram access to GraphicsProgram methods.
	 */
	public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram)	{
		this.X=X;
		this.Y=Y;
		this.myTable=myTable;
		this.GProgram=GProgram;
		this.myColor=myColor;
		
		// world coordinates of the paddle
		double upperLeftX=X-ppPaddleW/2;	// The position of the paddle is defined by its top left corner so we have to take that into account.
		double upperLeftY=Y+ppPaddleH/2;
		
		// p is in screen coordinates
		GPoint p= myTable.W2S(new GPoint(upperLeftX,upperLeftY));
		
		// screen
		double ScrX = p.getX();
		double ScrY = p.getY();
		this.myPaddle= new GRect(ScrX,ScrY, ppPaddleW*Xs, ppPaddleH*Ys);
		myPaddle.setFilled(true);
		myPaddle.setColor(myColor);
		GProgram.add(myPaddle);
		
		
	}
	public void run() {
		double lastX=X;
		double lastY=Y;
		
		while (true) {
			Vx=(X-lastX)/TICK;			// Determines the velocity of the paddle using the position travelled by the paddle over a tick.
			Vy=(Y-lastY)/TICK;
			lastX=X;					// Updates the position coordinates
			lastY=Y;
			GProgram.pause(TICK*TSCALE); // Tick
			
		}
	}
	/**
	 * Return Vx and Vy inside GPoint object.
	 * @return the new velocities in X and in Y.
	 */
	public GPoint getV() {
		return new GPoint(Vx,Vy);
			
	}
	/**
	 * Sets the location of the paddle on the screen using point P given by mouse location in ppSim.
	 * @param P in world coordinates, it is the position that the paddle should be set at.
	 */
	public void setP(GPoint P) {
		this.X= P.getX();
		this.Y= P.getY();
		
		double upperLeftX=X-ppPaddleW/2;
		double upperLeftY=Y+ppPaddleH/2;
		
		GPoint p = myTable.W2S(new GPoint(upperLeftX,upperLeftY));
		
		double ScrX=p.getX();
		double ScrY=p.getY();
		
		this.myPaddle.setLocation(ScrX,ScrY);
	}
	/**
	 * The method getP() returns the coordinates of the point P.
	 * @return (X,Y)
	 */
	public GPoint getP()	{
		return new GPoint (X,Y);
	}
	/**
	 * The method getSgnVy() is used for coherence during the ball collision with paddle calculations so that the signs stay consistent.
	 * @return +1 if the Y-velocity is positive and -1 if the Y-velocity is negative.
	 */
	public double getSgnVy()	{
		if (Vy>0) return 1;
		else return -1;
	}
	/**
	 * The method contact is called when the ball would hit the right wall (which became the right paddle) and returns a boolean depending on if the Y coordinates of the ball are in the range of the paddle's top and bottom Y coordinates.
	 * @param Sx X-position of the ball.
	 * @param Sy Y-position of the ball.
	 * @return true if the ball Y-position is in the range of the paddle's Y-coordinates or false if the ball Y-position is not in the range.
	 */
	public boolean contact (double Sx, double Sy)	{
		// called whenever X+Xo >= myPaddle.getP().getX()
		// true if ballY is in the paddleY range
		// false if not
		return ((Sy>= Y-ppPaddleH/2) && (Sy <= Y + ppPaddleH/2));
		
	}
}
