package ppPackage;

import java.awt.Color;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
/**
 * The ppPaddleAgent class extends ppPaddle to allow it to use methods and values from ppPaddle. It creates the Left paddle, determines how to update the position and determine the velocity of the left paddle without actually having the parameters since the ball acts as the parameter setter and defines a method to get the position values of the ball from ppBall.
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 4 instructions and from professor Katrina Poulin's tutorial.
 */
public class ppPaddleAgent extends ppPaddle {
	
	ppBall myBall;
	
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram)	{
		super(X,Y,myColor,myTable,GProgram);
	}
	public void run()	{
		int ballSkip=0;
		int AgentLag=5;
		double lastX=X;
		double lastY=Y;
		while (true)	{	
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			GProgram.pause(TICK*TSCALE);
			if (ballSkip++ >= AgentLag)	{
				
				this.setP(new GPoint(this.getP().getX(),myBall.getP().getY()));
				ballSkip=0;
			}
		}
	}
	public void attachBall(ppBall myBall)	{
		this.myBall=myBall;
		
	}
}
