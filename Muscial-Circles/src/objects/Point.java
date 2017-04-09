package objects;

public class Point {

	private double x,y;
	public Point(){
		
	}
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return (int)this.x;
	}
	
	public int getY(){
		return (int)this.y;
	}
}
