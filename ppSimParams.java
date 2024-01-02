package ppPackage;

import javax.swing.JToggleButton;

/**
 * The resource class ppSimParams includes all the constant parameters useful for the other classes and allows other classes to make reference to these parameters. 
 * @author William Zhang
 * Parts of this code were taken from professor Ferrie's assignment 2 and assignment 1 instructions.
 */
public class ppSimParams {

	public static JToggleButton traceButton;
	
	public static final int WIDTH = 1280;							// n.b. screen coordinates
    public static final int HEIGHT = 600;
    public static final int OFFSET = 200;	
	
	// World and Screen Parameters
	public static final double ppTableXlen = 2.74;					// Length in meters of the ping pong table
	public static final double ppTableHgt = 1.52;					// Height in meters of the ping pong table
	public static final double XwallL = 0.05;						// Position of the left wall (meters)
	public static final double XwallR = 2.69; 						// Position of the right wall (meters)
    
   
	// Simulation Parameters
    
	public static final double g = 9.8;	       // gravitational constant
	public static final double k = 0.1316;        // air friction
	public static final double Pi = 3.1416;       // Pi to 4 places
	public static final double bSize = 0.02;      // Radius of ball (m)
	public static final double bMass = 0.0027;    // Mass of ball (kg)
	public static final double TICK = 0.01;			// Tick
	public static final double ETHR = 0.001;	   // Condition to stop the ball
	public static final double Xmin = 0.0;							// Minimum value of X (pp table)
    public static final double Xmax = ppTableXlen;					// Maximum value of X 
    public static final double Ymin = 0.0;							// Minimum value of Y
    public static final double Ymax = ppTableHgt;					// Maximum value of Y (height above table)
    public static final int xmin = 0;								// Minimum value of x
    public static final int xmax = WIDTH;							// Maximum value of x
    public static final int ymin = 0;								// Minimum value of y
    public static final int ymax = HEIGHT;							// Maximum value of y
    public static final double Xs = (xmax-xmin)/(Xmax-Xmin);		// Scale factor X (meters to pixels)
    public static final double Ys = (ymax-ymin)/(Ymax-Ymin);		// Scale factor Y (meters to pixels)
    public static final double Xinit = XwallL;	      // Initial X position of ball
	public static final double Yinit = Ymax/2;    // Initial Y position of ball
    public static final double PD = 1;								// Trace point diameter
    public static final double TSCALE = 2000;						// Scaling parameter for pause()
	
	// Paddle parameters
    static final double ppPaddleH=8*2.54/100;						// Paddle height
    static final double ppPaddleW=0.5*2.54/100;						// Paddle width
    static final double ppPaddleXinit=XwallR-ppPaddleW/2;			// Initial Paddle X
    static final double ppPaddleYinit=Yinit;						// Initial Paddle Y
    static final double ppPaddleXgain=1.5;							// Vx gain on paddle hit
    static final double ppPaddleYgain=1.7;							// Vy gain on paddle hit
    static final double LeftPaddleXinit = XwallL-ppPaddleW/2;		// Left Paddle parameters
    static final double LeftPaddleYinit = Yinit;
    static final double LeftPaddleXgain = 1.5;
    static final double LeftPaddleYgain = 1.7;
    static final double VoxMAX=10;									// Maximum horizontal speed allowed (playable game)
    	
    // Parameters used by the ppSim class
    static final double YinitMAX=0.75*Ymax;							// MAX initial height at 75% of range
    static final double YinitMIN=0.25*Ymax;							// MIN initial height at 25% of range
    static final double EMIN=0.2;									// Maximum loss coefficient
    static final double EMAX=0.2;									// Minimum loss coefficient
    static final double VoMIN=5.0;									// Minimum velocity
    static final double VoMAX=5.0;									// Maximum velocity
    static final double ThetaMIN=0.0;								// Minimum launch angle
    static final double ThetaMAX=20.0;								// Maximum launch angle
    static final long RSEED=8976232;								// Random number gen. seed value
    
    // Miscellaneous
    public static final boolean DEBUG = false;		// Debug msg. and single step if true
	public static final boolean MESG = true;		// Enable status messages on console
	public static final int STARTDELAY=1000;		// Delay between setup and start
}
