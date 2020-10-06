package DrawShape;


public class Sqr {

	private int length;
	private int width;
	
	//The getters
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;	}
	
	public double getArea() {
		int getArea = (length*width);
		return getArea;
	}
	
	//The setters
	
	public void setLength(int L) {
		length = L;
	}
	
	public void setWidth(int W) {
		width = W;
	}
	
}
