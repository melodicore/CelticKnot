package me.datafox.celticknot;

import javax.swing.JOptionPane;

/**
 * @author Datafox
 * 
 * CelticKnot is a simple program that draws an infinite Celtic knot pattern.
 * It asks the user for some simple parameters,
 * then draws a Celtic knot pattern according to the parameters.
 * The user can also change those parameters using the arrow keys,
 * as well as resizing the actual output window to their liking.
 */

public class CelticKnot {
	
	//Create an instance of Window.java
	static Window w;
	
	//Create the global variables used across the class
	static Integer size;
	static Double scale;
	
	//Initialize the program
	public static void main(String[] args) throws InterruptedException {
		
		//Asks the user for the window width and height
		Integer x = parseInt(JOptionPane.showInputDialog("Please enter the window width in pixels", "1280"));
		Integer y = parseInt(JOptionPane.showInputDialog("Please enter the window height in pixels", "720"));
		
		//Asks the user for the size for the produced Celtic knot
		size = parseInt(JOptionPane.showInputDialog("Please enter the size (minimum of 10)", "50"));
		
		//Enforces the size requirement
		if (size < 10) {
			JOptionPane.showMessageDialog(null, "Size must be at least 10, using default (50)");
			size = 50;
		}
		
		//Asks the user for the scale for the produced Celtic knot
		scale = parseDouble(JOptionPane.showInputDialog("Please enter the scale (a number between 0 and 0.5)", "0.3"));
		
		//Enforces the scale requirement
		if (scale < 0d || scale > 0.5d) {
			JOptionPane.showMessageDialog(null, "Scale must be between 0 and 0.5, using default (0.3)");
			scale = 0.3d;
		}
		
		//Initializes the Window.java class
		w = new Window(x, y);
		
		//Calls the function that specifies all of the knot's points on the canvas
		drawKnot();
	}
	
	/**
	 * Calculates all of the knot's points
	 */
	public static void drawKnot() {
		
		//Requests the width and the height of the window
		double width = w.getWidth();
		double height = w.getHeight();
		
		//A two-dimensional for loop that calculates all of the necessary points
		for(int i=0;i<width/size+1;i++) {
			for(int j=0;j<height/size+1;j++) {
				
				//Calculates the four lines in a single knot
				w.drawLine(i*size, (j+scale)*size, (i+1-scale)*size, (j+1)*size);
				w.drawLine((i+scale)*size, j*size, (i+1)*size, (j+1-scale)*size);
				w.drawLine((i-0.5+scale)*size, (j+0.5)*size, (i+0.5)*size, (j-0.5+scale)*size);
				w.drawLine((i-0.5)*size, (j+0.5-scale)*size, (i+0.5-scale)*size, (j-0.5)*size);
				
				//Calculates the two diamonds (a square rotated 45 degrees) in a single knot
				w.drawDiamond(i*size, (j-0.5)*size, (0.5-scale)*size);
				w.drawDiamond((i+0.5)*size, j*size, (0.5-scale)*size);
			}
		}
		//makes the window visible
		w.setVisible(true);
	}
	
	/**
	 * Increases the size of the knot
	 */
	public static void increaseSize() {
		size++;
	}
	
	/**
	 * Decreases the size of the knot
	 */
	public static void decreaseSize() {
		if(size>10) size--;
	}
	
	/**
	 * Increases the scale of the knot
	 */
	public static void increaseScale() {
		if(scale<0.5) scale += 0.01;
	}
	
	/**
	 * Decreases the scale of the knot
	 */
	public static void decreaseScale() {
		if(scale>0) scale -= 0.01;
	}
	
	/**
	 * Tries to parse an integer from text.
	 * If the parsing was not successful,
	 * notifies the user and terminates the program.
	 * 
	 * @param text	A string containing an integer
	 * @return		The parsed integer
	 */
	private static Integer parseInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input, terminating");
			System.exit(0);
			return null;
		}
	}
	
	/**
	 * Tries to parse a double from text.
	 * If the parsing was not successful,
	 * notifies the user and terminates the program.
	 * 
	 * @param text	A string containing a double
	 * @return		The parsed double
	 */
	private static Double parseDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid input, terminating");
			System.exit(0);
			return null;
		}
	}
}
