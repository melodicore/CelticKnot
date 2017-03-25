package me.datafox.celticknot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Vector;

/**
 * @author Datafox
 * 
 * Window.java handles all of the drawing in the CelticKnot program.
 * It stores all of the graphical components in vectors,
 * and draws those in it's paint function.
 * It also handles user input after the initial JOptionPanes,
 * and clears and redraws all of the necessary components every time an update happens.
 */

public class Window extends Frame {
	
	//Serialization ID that is not needed but my IDE kept nagging about it
	private static final long serialVersionUID = -1970643290508576293L;
	
	//Creates the graphics instances
	Graphics2D g2;
	BasicStroke stroke;
	
	//Creates the necessary vectors
	Vector<double[]> lines;
	Vector<double[]> pathx;
	Vector<double[]> pathy;
	Vector<Path2D.Double> path;
	
	/**
	 * Initializes all of the variables, listeners and other components
	 * 
	 * @param x		The initial window width
	 * @param y		The initial window height
	 */
	public Window(int x, int y) {
		
		//Sets the window title
		super("Celtic Knot");
		
		//Sets the window size
		setSize(x,y);
		
		//Initializes all of the vectors
		lines = new Vector<double[]>();
		pathx = new Vector<double[]>();
		pathy = new Vector<double[]>();
		path = new Vector<Path2D.Double>();
		
		//Initializes the stroke type used for drawing
		stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		
		//Adds a WindowListener to handle disposing when the user exits the program
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
		//Adds a ComponentListener to handle redrawing when the user resizes the window
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				
				//Clears all of the vectors
				lines.clear();
				pathx.clear();
				pathy.clear();
				path.clear();
				
				//Recalculates the knot
				CelticKnot.drawKnot();
				
				//Redraws everything
				repaint();
			}
		});
		
		//Adds a KeyListener so the user can change the variables
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				//Decreases the size of the knot
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					CelticKnot.decreaseSize();
				}
				
				//Increases the size of the knot
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					CelticKnot.increaseSize();
				}
				
				//Decreases the scale of the knot
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					CelticKnot.decreaseScale();
				}
				
				//Increases the scale of the knot
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					CelticKnot.increaseScale();
				}
				
				//Clears all of the vectors
				lines.clear();
				pathx.clear();
				pathy.clear();
				path.clear();
				
				//Recalculates the knot
				CelticKnot.drawKnot();
				
				//Redraws everything
				repaint();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		
		//Initializes the 2D graphics renderer
		g2 = (Graphics2D) g;
		
		//Turns on antialiasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		
		//Clears everything on the screen
		g2.clearRect(0, 0, getWidth(), getHeight());
		
		//Sets the drawing color to black
		g2.setColor(Color.BLACK);
		
		//Sets the stroke used for drawing
		g2.setStroke(stroke);
		
		//A for loop that draws all of the lines of the knot
		for(int i=0;i<lines.size();i++) {
			g2.draw(new Line2D.Double(lines.get(i)[0], lines.get(i)[1], lines.get(i)[2], lines.get(i)[3]));
		}
		
		//A for loop that draws all of the diamonds (squares) of the knot
		for(int i=0;i<pathx.size();i++) {
			
			//Adds a new path
			path.addElement(new Path2D.Double());
			
			//Traces the path
			path.get(i).moveTo(pathx.get(i)[0], pathy.get(i)[0]);
			path.get(i).lineTo(pathx.get(i)[1], pathy.get(i)[1]);
			path.get(i).lineTo(pathx.get(i)[2], pathy.get(i)[2]);
			path.get(i).lineTo(pathx.get(i)[3], pathy.get(i)[3]);
			path.get(i).closePath();
			
			//Draws the diamonds using the path
			g2.draw(path.get(i));
			g2.fill(path.get(i));
		}
		
		//Sets the color to red
		g.setColor(Color.RED);
		
		//Draws text to inform the user of the actions they may execute
		g.drawString("Use the arrow keys to morph the knot", 20, 50);
	}
	
	/**
	 * Forces redraw every time the window is updated
	 */
	public void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Adds a line to the lines vector
	 * 
	 * @param x1	The starting x position of the line
	 * @param y1	The starting y position of the line
	 * @param x2	The ending x position of the line
	 * @param y2	The ending y position of the line
	 */
	public void drawLine(double x1, double y1, double x2, double y2) {
		double[] temp = {x1, y1, x2, y2};
		lines.addElement(temp);
	}
	
	/**
	 * Adds a path to the path vectors that traces a diamond shape
	 * 
	 * @param x		The x position of the center of the diamond
	 * @param y		The y position of the center of the diamond
	 * @param r		The radius of the diamond, from the center to the corner
	 */
	public void drawDiamond(double x, double y, double r) {
		double[] tempx = {x, x+r, x, x-r};
		double[] tempy = {y-r, y, y+r, y};
		pathx.addElement(tempx);
		pathy.addElement(tempy);
	}
}
