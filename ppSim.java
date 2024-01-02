package ppPackage;
import static ppPackage.ppSimParams.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * The ppSim class starts the program by resizing the window, implementing the table, gathering the random initial conditions, creating the ball and the paddle and adding buttons and sliders to the game interface. 
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 4 instructions and from Katrina Poulin's tutorial.
 */
public class ppSim extends GraphicsProgram {

	ppTable myTable;
	ppPaddle RightPaddle;
	ppPaddleAgent LeftPaddle;
	ppBall myBall;
	RandomGenerator rgen;
	int Tickvalue;
	

	public static void main(String[] args) {
		new ppSim().start(args);
	}

	public void init()	{
		this.resize(xmax+OFFSET,ymax+OFFSET);
		// Add three new buttons to play a new game, to quit and to add tracers on the screen.
		JButton newServeButton = new JButton("New Serve");
		JButton quitButton = new JButton("Quit");
		traceButton = new JToggleButton("Trace");
		
		add(newServeButton, SOUTH);
		add(quitButton, SOUTH);	
		add(traceButton, SOUTH);
		// Add slider labels to control the Agent difficulty level.
		add(new JLabel("-lag"), SOUTH);
		JSlider lagslider = new JSlider(0,30,5);
		add(lagslider, SOUTH);
		add(new JLabel("+lag"), SOUTH);
		add(new JLabel("-t"), SOUTH);
		JSlider tickslider = new JSlider(0,10000,1000);
		add(tickslider, SOUTH);
		add(new JLabel("+t"), SOUTH);
		
		addMouseListeners();						// Mouse listeners
		addActionListeners();						// Action listeners

		rgen = RandomGenerator.getInstance();		// Random number generator for ball initial conditions
		rgen.setSeed(RSEED);

		myTable = new ppTable(this);				// Add table
		myBall = newBall();							// Add ball
		newGame();									// Start new game
	}	
		ppBall newBall()	{
		
		Color iColor=Color.RED;
		double iYinit=rgen.nextDouble(YinitMIN,YinitMAX);			// Random Y between YinitMIN and YinitMAX
		double iLoss=rgen.nextDouble(EMIN,EMAX);					// Random loss between EMIN and EMAX
		double iVel=rgen.nextDouble(VoMIN,VoMAX);					// Random velocity between VoMIN and VoMAX
		double iTheta=rgen.nextDouble(ThetaMIN,ThetaMAX);			// Random initial angle value between ThetaMIN and ThetaMAX

		ppBall myBall= new ppBall(Xinit+bSize,iYinit,iVel,iTheta,iLoss,iColor,myTable,this);		// Ball created with random initial conditions for launch
		return myBall;
		}
			
		public void newGame()	{
		if (myBall != null) myBall.kill();															// If there is a ball, kill its instance.
		myTable.newScreen();																		// Reset screen
		myBall=newBall();																			// Add ball
		RightPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,Color.GREEN,myTable, this);			// Add right paddle
		LeftPaddle = new ppPaddleAgent(LeftPaddleXinit, LeftPaddleYinit,Color.BLUE, myTable, this); // Add left paddle
		LeftPaddle.attachBall(myBall);																// Attach left paddle to the ball movement in Y direction
		myBall.setRightPaddle(RightPaddle);														
		myBall.setLeftPaddle(LeftPaddle);
		pause(STARTDELAY);																			// Delay start of game
		myBall.start();																				// Start movements.
		LeftPaddle.start();
		RightPaddle.start();

	}
	/**
	 * Mouse Handler - a moved event moves the paddle up and down in Y.
	 */
	public void mouseMoved(MouseEvent e) {
		// convert mouse position to a position in screen coords
		if (myTable==null || RightPaddle==null) return;
		GPoint Pm=myTable.S2W(new GPoint(e.getX(),e.getY()));
		double PaddleX=RightPaddle.getP().getX();
		double PaddleY=Pm.getY();
		RightPaddle.setP(new GPoint(PaddleX,PaddleY));
	}
	/**
	 * Action Handler - a button pressed dictates the action to take (start a new game or quit).
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("New Serve"))	{
			newGame();
		}
		else if (command.equals("Quit")) {
			System.exit(0);
		}
	}
}
