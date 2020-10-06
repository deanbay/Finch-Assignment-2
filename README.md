package DrawShape;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

public class Main {

	static int v = 100; // velocity of wheel
	static final int lenMultiplier = 100; // nothing can affect my value when using length multiplier in final
	static Finch myFinch = new Finch(); // use of finch

	static ArrayList<Sqr> listofsquares = new ArrayList<Sqr>();// storing an array of squares where the getters are
																// stored
	static ArrayList<Tri> listoftriangles = new ArrayList<Tri>(); // storing an array of triangles where the getters are
																	// stored
	static DoubleSummaryStatistics TimeCollector = new DoubleSummaryStatistics(); // every new time gets added here

	public static void FinchLevel() throws InterruptedException {
		System.out.println("Finch is not level");

		while (!myFinch.isFinchLevel()) {
		}
		myFinch.buzz(4000, 400);
		;
		Thread.sleep(3000);

		myFinch.buzz(6000, 400);
		;
		System.out.println("Now that its level, program can now proceed");
		return;

	}

	public static void main(String args[]) throws InterruptedException {

		FinchLevel(); // first code to run for finch being level

		String s = "";

		// The Loop goes through the finch menu for selection based on the users choice
		do {
			s = FinchMenu();
			if (s.equals("Triangle")) {
				TriangleGUI();
			}
			if (s.equals("Square")) {
				SquareGUI();
			}
			if (s.equals("Quit")) {
				System.out.println("");
			}

		} while (!(s.equals("Quit")));

		// output final results and quits the Finch object.
		outputResults();
		myFinch.buzz(4000, 400);
		;
		myFinch.quit();
	}

	/**
	 * Uses JOptionPane so user can choose Triangle, Square or quit.
	 * 
	 * 
	 * @return user choice as String s
	 */
	private static String FinchMenu() {

		Object[] possibilities = { "Triangle", "Square", "Quit" };
		String s = (String) JOptionPane.showInputDialog(null, "Choose a shape: \n", "Draw Shape ",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "");

		if (s == null || s.length() == 0)
			s = "Quit";
		return (s);
	}

	/**
	 * Opens the GUI so the user can enter the dimension for the Square. Code
	 * here to take input and convert into Square object. Checks whether values are
	 * valid or not.
	 * 
	 */
	public static void SquareGUI() {
		JPanel myPanel = new JPanel();
		JButton testButton = new JButton("testing"); // new addition for testing
		// testButton.setAction(null);
		testButton.setBounds(1000, 1000, 1000, 1000); // dimensions of button

		JTextField widthField = new JTextField(5);
		JTextField lengthField = new JTextField(5);
		widthField.setText("0");
		lengthField.setText("0");

		myPanel.add(new JLabel("Width: "));
		myPanel.add(widthField);
		// myPanel.add(testButton); //button calling out for placement, add to panel
		myPanel.add(Box.createHorizontalStrut(10));
		myPanel.add(new JLabel("Length: "));
		myPanel.add(lengthField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Draw square", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null);

		if (result == JOptionPane.OK_OPTION) {

			try {
				int width, length;
				width = Integer.parseInt(widthField.getText());
				length = Integer.parseInt(lengthField.getText());

				if (checkValidDimension(length) && checkValidDimension(width)) {

					System.out.println("Draw Square (" + length + ", " + width + ")");
					Sqr sqr = new Sqr();
					sqr.setLength(length);
					sqr.setWidth(width);
					drawSqr(sqr);

				} else {
					JOptionPane.showMessageDialog(null, "Dimensions must be between 15-85cm. Please try again.");
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No text allowed, please enter numerical values! ");
			} // eliminating runtime errors by catching any exception

		}
	}

	/**
	 * Method which tells the Finch exactly how to draw square/rectangle r1.
	 */
	public static void drawSqr(Sqr r1) {

		int len = r1.getLength();
		int wid = r1.getWidth();
		int cornerMultiplier = 730;
		// 800 original for lab surface
		// 730 for wooden table

		long timestamp = System.nanoTime(); // start timer

		// first line and angle adjustment
		myFinch.setWheelVelocities(v, v, (len + 5) * lenMultiplier); // getting the length which is then multiplied by
																		// 5, and then by 100 as we are dealing with
																		// milliseconds based on the finch
		myFinch.setWheelVelocities(-v, -v, 1100); // the reverse function
		myFinch.setWheelVelocities(v, -v, cornerMultiplier);// rotation code, left then right wheel then time to hold via cornerMultiplier

		// second line and angle adjustment
		myFinch.setWheelVelocities(v, v, (wid + 5) * lenMultiplier);
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, cornerMultiplier);// rotation code

		// third line and angle adjustment
		myFinch.setWheelVelocities(v, v, (len + 5) * lenMultiplier);
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, cornerMultiplier);// rotation code

		// fourth line and angle adjustment
		myFinch.setWheelVelocities(v, v, (wid + 5) * lenMultiplier);
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, cornerMultiplier);// rotation code

		// adds to the array for later printing
		listofsquares.add(r1);
		// print out imfo
		System.out.println("Area: " + r1.getArea());
		long endstamp = System.nanoTime(); // end timer
		long totaltime = endstamp - timestamp; // total timer
		int totaltimeinseconds = (int) (totaltime / Math.pow(10, 9));
		TimeCollector.accept(totaltimeinseconds);
		System.out.println("Time Taken: " + totaltimeinseconds);
		System.out.println("");
		myFinch.stopWheels();
		myFinch.buzz(4000, 400);
		;
	}

	public static void TriangleGUI() {

		JTextField lenField1 = new JTextField(5);
		JTextField lenField2 = new JTextField(5);
		JTextField lenField3 = new JTextField(5);
		lenField1.setText("0");
		lenField2.setText("0");
		lenField3.setText("0");
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Length 1: "));
		myPanel.add(lenField1);
		myPanel.add(Box.createHorizontalStrut(5));
		myPanel.add(new JLabel("Length 2: "));
		myPanel.add(lenField2);
		myPanel.add(Box.createHorizontalStrut(5));
		myPanel.add(new JLabel("Length 3: "));
		myPanel.add(lenField3);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Draw Triangle", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null); // popup message with getters and and setters in place

		if (result == JOptionPane.OK_OPTION) {
			try {
				int len1 = Integer.parseInt(lenField1.getText()); // gain from text field for value
				int len2 = Integer.parseInt(lenField2.getText());
				int len3 = Integer.parseInt(lenField3.getText());

				if (checkValidDimension(len1) && checkValidDimension(len2) && checkValidDimension(len3)) {
					Tri tri = new Tri();
					tri.setL1(len1);
					tri.setL2(len2);
					tri.setL3(len3);
					tri.setA(getTriAngles(len1, len2, len3));
					tri.setB(getTriAngles(len2, len1, len3));
					tri.setC(getTriAngles(len3, len2, len1));
					if (CanTriangleBeDrawn(tri)) {
						drawTriangle(tri);
					}

				}

				else {
					JOptionPane.showMessageDialog(null, "Dimensions must be between 15 - 85cm. Please try again.");

				}
			} catch (Exception e) { // catching the exceptions so no run times occur
				JOptionPane.showMessageDialog(null, "No text allowed, please enter numerical values!");
			}

		}
	}

	public static boolean CanTriangleBeDrawn(Tri t1) {
		int len1 = t1.getL1();
		int len2 = t1.getL2();
		int len3 = t1.getL3();
		if (len1 > len2 + len3 || (len2 > len1 + len3) || (len3 > len2 + len1)) {
			JOptionPane.showMessageDialog(null,
					"The values you've entered equate to the triangle inequality therom, one of your side values is greater than or eqaul to that of the sum of opposing sides and therefore cant be drawn.");
			return false;
		}
		if (len1 + len2 == len3 || (len2 + len1 == len3) || (len3 + len2 == len1)) {
			JOptionPane.showMessageDialog(null,
					"The values you've entered equate to the triangle inequality therom, one of your side values is greater than or eqaul to that of the sum of the opposing sides and therefore cant be drawn.");
			return false;
		}
		return true;
	}

	public static void drawTriangle(Tri t1) {
		long timestamp = System.nanoTime(); // start timer

		System.out.println("Draw Triangle(" + t1.getL1() + ", " + t1.getL2() + ", " + t1.getL3() + ")");
		System.out.println("Angles: " + t1.getA() + " " + t1.getB() + " " + t1.getC());
		System.out.println("Area: " + t1.GetArea());

		myFinch.setWheelVelocities(v, v, (t1.getL1() + 5) * lenMultiplier); // the length of side 1 of the triangle
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, (180 - t1.getA()) * 8);

		myFinch.setWheelVelocities(v, v, (t1.getL2() + 5) * lenMultiplier);
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, (180 - t1.getB()) * 8);

		myFinch.setWheelVelocities(v, v, (t1.getL3() + 5) * lenMultiplier);
		myFinch.setWheelVelocities(-v, -v, 1100);
		myFinch.setWheelVelocities(v, -v, (180 - t1.getC()) * 8);

		listoftriangles.add(t1);//adds to list

		myFinch.stopWheels();
		myFinch.buzz(4000, 400);
		;

		long endstamp = System.nanoTime(); // end timer
		long totaltime = endstamp - timestamp; // total timer
		int totaltimeinseconds = (int) (totaltime / Math.pow(10, 9));
		TimeCollector.accept(totaltimeinseconds);
		System.out.println("Time Taken: " + totaltimeinseconds);
		System.out.println("");
	}

	public static int getTriAngles(int l1, int l2, int l3) {
		double A;
		A = (180 / Math.PI) * Math.acos((Math.pow(l2, 2) + Math.pow(l3, 2) - Math.pow(l1, 2)) / (2 * l2 * l3));
		return (int) A;
	}

	public static Boolean checkValidDimension(int length) {

		if (length >= 15 && length <= 85) {
			return true;
		} else {
			return false;
		}
	}

	public static void outputResults() {
		int noOfTris = listoftriangles.size();
		int noOfSqrs = listofsquares.size();
		int total = noOfTris + noOfSqrs;

		Object myPanel = total;

		int result = JOptionPane.showConfirmDialog(null,
				"Total shapes drawn: " + myPanel + "\n" + "Thank you for testing my program!", "Results",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		System.out.println("== Total Number of Shapes drawn: " + total + " ==");

		printTriangles(listoftriangles);															 //printing the list
		printSquares(listofsquares);																 //printing the list
		System.out.println("This is the average time taken drawing: " + TimeCollector.getAverage()); // this is the
																										// average time
																										// taken

		PrintBiggestShape(listoftriangles, listofsquares);

		if (noOfSqrs == noOfTris) {
			System.out.println("Neither shapes were drawn more frequent than the other.");
		}

		if (noOfTris > noOfSqrs && noOfTris != 0) {
			System.out.println("Triangle was drawn more frequently: " + noOfTris);
		}

		if (noOfSqrs > noOfTris && noOfSqrs != 0) {
			System.out.println("Square was drawn more frequently: " + noOfSqrs);
		}
	}

	private static void PrintBiggestShape(ArrayList<Tri> listoftriangles2, ArrayList<Sqr> listofsquares2) {
		// TODO Auto-generated method stub
		String Biggest = "NaN"; // standard output with no value to begin with
		double BiggestArea = 0;

		for (int i = 0; i < listoftriangles2.size(); i++) {

			if (listoftriangles2.get(i).GetArea() > BiggestArea) {
				BiggestArea = listoftriangles2.get(i).GetArea();
				Biggest = "triangle";
			}
		}

		for (int j = 0; j < listofsquares2.size(); j++) {
			if (listofsquares2.get(j).getArea() > BiggestArea) {
				BiggestArea = listofsquares2.get(j).getArea();
				Biggest = "Square";
			}
		}
		System.out.println("The biggest shape (area) was " + Biggest + " with an area of: " + BiggestArea);
	}

	private static void printTriangles(ArrayList<Tri> listoftriangles2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listoftriangles2.size(); i++) { // goes through the list one by one until it gets to one
															// less than the size array list
			System.out.println("=================");
			System.out.println("This is Triangle number: " + (i + 1));// otherwise the list begins with 0
			System.out.println("This is the lengths: " + listoftriangles2.get(i).getL1() + ", "
					+ listoftriangles2.get(i).getL2() + ", " + listoftriangles2.get(i).getL3());
			System.out.println("This is the angles: " + listoftriangles2.get(i).getA() + ", "
					+ listoftriangles2.get(i).getB() + ", " + listoftriangles2.get(i).getC());
			System.out.println("This is the area: " + listoftriangles2.get(i).GetArea());
			System.out.println("=================");
		}

	}

	private static void printSquares(ArrayList<Sqr> listofsquares2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listofsquares2.size(); i++) { // goes through the list one by one until it gets to one less
															// than the size array list
			System.out.println("=================");
			System.out.println("This is Square number: " + (i + 1));// otherwise the list begins with 0
			System.out.println("This is the height: " + listofsquares2.get(i).getLength());
			System.out.println("This is the width: " + listofsquares2.get(i).getWidth());
			System.out.println("This is the area: " + listofsquares2.get(i).getArea());
			System.out.println("=================");
		}
	}
}
