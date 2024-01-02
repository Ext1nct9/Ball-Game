package ppPackage;

import static ppPackage.ppSimParams.*;


import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * The ppBall class allows multiple parts of the program to run simultaneously using thread. It creates the contour of the ball, determines the trajectory equations of the ball, determines the collision process with the ground, the left wall and the right paddle, determines the ball display, and codes for tracers for ball trajectory.
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 1,2,3 and assignment 4 instructions.
 */
public class ppBall extends Thread	{
	private double Xinit;				// Initial position of ball - X
	private double Yinit;				// Initial position of ball - Y
	private double Vo;					// Initial velocity (Magnitude)
	private double theta;				// Initial direction
	private double loss;				// Energy loss on collision
	private Color color;				// Color of ball
	private GraphicsProgram GProgram;	// Instance of ppSim class (this)	
	GOval myBall;						// Graphics project representing ball
	ppTable myTable;
	ppPaddle RightPaddle;
	ppPaddle LeftPaddle;
	double X, Xo, Y, Yo;
	double Vx, Vy;
	boolean running;
	
	
	/**
	 *  The ppBall constructor defines the ball variables that are used to create it.
	 * @param Xinit Initial value of X (m).
	 * @param Yinit	Initial value of Y (m).
	 * @param Vo	Initial velocity (m/s).
	 * @param theta	Initial angle in degrees.
	 * @param loss	Loss parameter (loss of energy for each collision).
	 * @param color	Ball color.
	 * @param myTable To be able to call the methods in ppTable.
	 * @param GProgram	GProgram for add() and pause().
	 */
	public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, ppTable myTable, GraphicsProgram GProgram)	{
		this.Xinit=Xinit;
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color=color;
		this.GProgram=GProgram;
		this.myTable=myTable;
		
	//GOval()
		GPoint p = myTable.W2S (new GPoint(Xinit,Yinit+bSize));
		double ScrX = p.getX();
		double ScrY = p.getY();
		this.myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
		this.myBall.setColor(color);
		this.myBall.setFilled(true);
		this.GProgram.add(myBall);
		GProgram.pause(1000);
	}
		public void run() {						// Runs the simulation
			
			System.out.printf("\t\t\t Ball Position and Velocity\n");		// Header
			// Initialize simulation parameters
			Xo = Xinit;					// Set initial X position
			Yo = Yinit;					// Set initial Y position
			double time = 0;							// Time starts at 0 and counts up
			double Vt = bMass*g / (4*Pi*bSize*bSize*k); // Terminal velocity
			double Vox=Vo*Math.cos(theta*Pi/180);		// X component of velocity
			double Voy=Vo*Math.sin(theta*Pi/180);		// Y component of velocity
			
			// Main simulation loop
			running = true;						// Initial state = running.
			if (traceButton.isSelected()) trace(Xo+X, Yo+Y);
			// Important - X and Y are ***relative*** to the initial starting position Xo,Yo.
			// So the absolute position is Xabs = X+Xo and Yabs = Y+Yo.
			// Also - print out a header line for the displayed values.
			
			
			while (running) {
				X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));				// Update relative position
				Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time; 
	    		Vx = Vox*Math.exp(-g*time/Vt);						// Update velocity
	    		Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
	    		double KEx = 0.5*bMass*Vx*Vx;								// define KEx, KEy and PE and calculate the values.
	    		double KEy = 0.5*bMass*Vy*Vy;
	    		double PE = bMass*g*(Y+Yo);
	    		
	    				
	    	// Check to see if we hit the ground yet.  When the ball hits the ground, the height of the center
	    	// is the radius of the ball.
	    			
	    			if (Vy < 0 && (Yo+Y) <= bSize) {						// Happens when the y-velocity is negative and the y-position of the ball is smaller or equal to the radius of the ball.
	    				
	    				KEx=0.5*bMass*Vx*Vx*(1-loss);					// Kinetic energy - loss.
	    				KEy=0.5*bMass*Vy*Vy*(1-loss);					
	    				PE=0;											// Since the ball is on the ground, the potential energy is near 0.
	    				Vox=Math.sqrt(2*KEx/bMass);						// Calculate initial velocity.
	    				Voy=Math.sqrt(2*KEy/bMass);
	    				if (Vx<0) Vox=-Vox;								// If the x-velocity was negative, since the new Vox has to be positive because of the square root, we have to keep it consistent and thus the velocity is changed to negative if it was negative before the collision.
	    				
	    				time = 0;										// Reset time, Update position coordinates and reset X and Y (they will be recalculated later).
	    				Xo+=X;
	    				Yo=bSize;
	    				X=0;
	    				Y=0;
	    				
	    			}
	    			
	    			if ((Yo+Y) > Ymax) {
	    				running=false;
	    				
	    			}
	    	// Check to see if we hit the right wall then the paddle yet. 
	    			
	    			if ((Xo+X) >= (RightPaddle.getP().getX()-bSize-ppPaddleW/2) && Vx > 0) { 			// When the ball hits the right wall, the position of the ball is bigger or equal to the position of the right paddle-the size of the ball-half of the paddle's width and the x-velocity is positive.
	    				// possible collision
	    				if(RightPaddle.contact(X+Xo, Y+Yo))	{						// Using the contact method in ppPaddle, we are able to determine if the ball hit the paddle and then use an if statement to make the ball collide with the paddle if there is contact. If there is no contact, the while loop will skip through the if statement, thus there wont be any collision with the right paddle and the ball will freeze and the while loop will stop.
	    				KEx = 0.5*bMass*Vx*Vx*(1-loss);
	    				KEy = 0.5*bMass*Vy*Vy*(1-loss);
	    				PE = bMass*g*(Y); 
	    				Vox=Math.sqrt(2*KEx/bMass);						// Calculate initial velocity.
	    				Voy=Math.sqrt(2*KEy/bMass);
	    				Vox=-Vox*ppPaddleXgain;	// Scale X component of velocity
	    				Voy=Voy*ppPaddleYgain*RightPaddle.getSgnVy();		// Scale Y + same dir. as paddle
	    				if (-Vox>VoxMAX)	{							// If the calculated x velocity is higher than VoxMAX, then it becomes VoxMAX.
	    					Vox=-VoxMAX;
	    				}			
	    				time = 0;										// Reset time, update the position of the ball and reset X and Y
	    				Xo = RightPaddle.getP().getX()-bSize-ppPaddleW/2;
	    				Yo+=Y;
	    				X=0;
	    				Y=0;
	    				}	else {
	    					running=false;								// If the right paddle does not come into contact with the ball, the simulation ends.
	    				}
	    			}
	    	//Check to see if we hit the left wall yet then the left paddle yet.
	    			
	    			if (Xo+X <= (LeftPaddle.getP().getX()+bSize+ppPaddleW/2) && Vx < 0) {				// When the ball hits the left wall, the position of the ball is smaller or equal to the position of the left wall + the size of the ball and the x-velocity is negative.
	    				
	    				if(LeftPaddle.contact(X+Xo, Y+Yo))	{
	    				KEx = 0.5*bMass*Vx*Vx*(1-loss);
	    				KEy = 0.5*bMass*Vy*Vy*(1-loss);
	    				PE = bMass*g*(Y+Yo); 
	    				Vox=Math.sqrt(2*KEx/bMass);
	    				Voy=Math.sqrt(2*KEy/bMass);
	    				Vox=Vox*LeftPaddleXgain;							// Scale X component of velocity
	    				Voy=Voy*LeftPaddleYgain*LeftPaddle.getSgnVy();		// Scale Y + same dir. as paddle
	    				if (Vox>VoxMAX) {									// If the calculated x velocity becomes higher than VoxMAX, then Vox becomes VoxMAX.
	    					Vox=VoxMAX; }
	    				   				
	    				
	    											// Since Voy is a result of a square root, it is always positive but if the y-velocity of the ball before the collision is negative, to keep it consistent we have to keep it negative after the collision too.
	    				
	    				time = 0;										// Reset time, update the position of the ball and reset X and Y
	    				Xo = XwallL+bSize;
	    				Yo+=Y;
	    				X=0;
	    				Y=0;
	    			}	else {
	    				running = false;
	    			}
	    			}
	    			
	    			

	    		if ((KEx+KEy+PE) < ETHR) running = false; 							// If the sum of the energies is smaller than the threshold value of ETHR, the system stops running.
	    		
	    		if (MESG)
					System.out.printf("t: %.2f\t\t X: %.2f\t Y: %.2f\t Vx: %.2f\t Vy: %.2f\n",		// Display values for the ball
					time,X+Xo,Y+Yo,Vx,Vy);
	    		
	    		try {
					ppBall.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	// Update and display
	    		GPoint p = myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));							// Update ball location and display
	    		double ScrX=p.getX();
	    		double ScrY=p.getY();
	    		this.myBall.setLocation(ScrX,ScrY);       
	    		
	    		p=myTable.W2S(new GPoint(Xo+X, Yo+Y));
	    		ScrX=p.getX();
	    		ScrX=p.getY();
	    		if (traceButton.isSelected()) trace(Xo+X, Yo+Y);								   // Add a tracer to the display at the calculated position if the tracer button is toggled.

	    		time += TICK;
	    		GProgram.pause(TICK*TSCALE);
	    		if((KEx+KEy+PE)< ETHR) running = false;
				}
			}
			/**
			 * A method to plot a dot at the current location in screen coordinates. Taken from professor Ferrie's assignment 1 example.
			 * @param X scrX X-position in screen coordinates.
			 * @param Y scrY Y-position in screen coordinates.
			 * @return a black oval that looks like a dot at position (ScrX,ScrY).
			 */

			public void trace(double X, double Y) {
				GPoint p = myTable.W2S (new GPoint(X,Y));
				double ScrX = p.getX();
				double ScrY = p.getY();
				GOval pt = new GOval(ScrX,ScrY,PD,PD);
				pt.setColor(Color.BLACK);
				pt.setFilled(true);
				GProgram.add(pt);
			}
			/**
			 * A method to set the paddle and assign the instance variable to the right paddle.
			 * @param RightPaddle.
			 */
			public void setRightPaddle(ppPaddle RightPaddle) {
				this.RightPaddle = RightPaddle;
			}
			/**
			 * A method to set the left paddle and assign the instance variable to the left paddle.
			 * @param LeftPaddle.
			 */
			
			public void setLeftPaddle(ppPaddleAgent LeftPaddle)	{
				this.LeftPaddle = LeftPaddle;
			}
			/**
			 * A method to get the X and Y coordinates of an object.
			 * @return X+Xo or Y+Yo depending on getX() or getY().
			 */
			public GPoint getP()	{
				return new GPoint(X+Xo,Y+Yo);
			}
			/**
			 * A method to get the X and Y velocities of an object
			 * @return Vx or Vy depending on getX() or getY().
			 */
			public GPoint getV()	{
				return new GPoint(Vx,Vy);
				
			}
			/**
			 * A method to kill the simulation by setting the boolean running to false.
			 */
			void kill()	{
				running = false;
			}
}

