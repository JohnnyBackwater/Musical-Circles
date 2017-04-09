package objects;

import gui.Program;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	private double r, dist;
	private double a = Math.PI / 200;
	private double kx, ky;
	Color color;
	int id;

	/**
	 * 
	 * @param dist
	 *            distance from the origin
	 * @param radius
	 *            radius of the ball
	 * @param ID
	 */
	public Ball(int ID, int balls) {
		this.r = 4 + 16/balls + ID/6;
		this.dist = (6 + (80/balls)) * ID;
		this.id = ID;
		a = (Math.PI / (balls*200))*((balls-ID+1)%(balls+1));
		if(ID < balls/2){
			color = new Color(255, ((255/balls)*(ID-1))*2, 0);
		} else {
			color = new Color((255 - ((255 / balls) * (ID - (balls / 2)))), 255, 0);
		}
	}

	public void render(Graphics g) {
		g.fillOval((int) (kx * dist - r) + Program.XOFFSET, (int) (ky * dist - r) + Program.YOFFSET, (int) r * 2, (int) r * 2);
	}

	public void update(int t) {
		kx = Math.cos(a * t);
		ky = Math.sin(a * t);
	}

	public int getx() {
		int xpos = (int)(kx*this.dist);
		return xpos;
	}

	public int gety() {
		int ypos = (int)(ky*this.dist);
		return ypos;
	}

	public Color getColor() {
		return this.color;
	}
}
