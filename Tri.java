package DrawShape;

public class Tri {
	
	
	private int lenA, lenB, lenC;
	private int angA, angB, angC;
	
	//getters for lengths and angles 
	
	public int getL1() {
		return lenA;
	}
	
	public int getL2() {
		return lenB;
	}
	
	public int getL3() {
		return lenC;
	}
	
	public int getA() {
		return angA;
	}
	
	public int getB() {
		return angB;
	}
	
	public int getC() {
		return angC;
	}
	
	public double GetArea() { //Heron's formula applied for area
		double s = (lenA + lenB + lenC)/2.0d;
		double x = ((s) * (s-lenA) * (s-lenB)* (s-lenC));
		return Math.sqrt(x);	
	}
	//The setters for lengths and angles 
	public void setL1(int L) {
		lenA = L;
	}
	
	public void setL2(int L) {
		lenB = L;
	}
	
	public void setL3(int L) {
		lenC = L;
	}
	
	public void setA(int a) {
		angA = a;
	}
	
	public void setB(int b) {
		angB = b;
	}
	
	public void setC(int c) {
		angC = c;
	}
	
}
