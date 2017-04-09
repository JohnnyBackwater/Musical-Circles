package gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import objects.Ball;

public class Program implements Runnable {

	public static int AXIS = 1;
	public int step = 0;
	public Thread thread;
	public JFrame frame;
	public boolean run, lines, ballsOn;
	public ArrayList<Ball> balls = new ArrayList<>();
	public int t;
	public static int XOFFSET, YOFFSET;
	public Container container;
	public TextArea ballAmount;

	public Program(int width, int height, int maxballs) {

		frame = new JFrame();
		frame.setSize(new Dimension(width, height));
		XOFFSET = width / 2;
		YOFFSET = height / 2;
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ballsOn = true;
		ActionListener listener1 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("asd");
				step++;
			}
		};
		ActionListener listener2 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("asdasd");
				step--;
			}
		};
		ActionListener resetButtonAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InitBalls(getBallNumber());
			}
		};
		ActionListener toggleLinesAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lines) {
					lines = false;
				} else {
					lines = true;
				}
			}
		};

		container = frame.getContentPane();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));

		Button buttonfor = new Button();
		buttonfor.setLabel("Speed up");
		buttonfor.setVisible(true);
		buttonfor.addActionListener(listener1);
		frame.add(buttonfor);

		Button buttonback = new Button();
		buttonback.setLabel("Slow down");
		buttonback.setVisible(true);
		buttonback.addActionListener(listener2);
		frame.add(buttonback);

		Button resetButton = new Button();
		resetButton.setLabel("Reset");
		resetButton.setVisible(true);
		resetButton.addActionListener(resetButtonAction);
		frame.add(resetButton);

		Button toggleLines = new Button();
		toggleLines.setLabel("Lines");
		toggleLines.setVisible(true);
		toggleLines.addActionListener(toggleLinesAction);
		frame.add(toggleLines);

		Button axisToggle = new Button();
		axisToggle.setLabel("Axis");
		axisToggle.setVisible(true);
		axisToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * 4 modes: 0 = no axis visible 1 = positive x axis 2 = whole x
				 * axis 3 = x and y axis
				 */
				AXIS = (AXIS + 1) % 4;
			}
		});
		frame.add(axisToggle);

		Button toggleBalls = new Button();
		toggleBalls.setLabel("Balls");
		toggleBalls.setVisible(true);
		toggleBalls.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ballsOn) {
					ballsOn = false;
				} else {
					ballsOn = true;
				}
			}
		});
		frame.add(toggleBalls);
		
		ballAmount = new TextArea(1,10);
		ballAmount.setText("60");	
		ballAmount.setVisible(true);
		ballAmount.setBackground(Color.WHITE);
		frame.add(ballAmount);
		
		thread = new Thread(this);
		run = true;
		InitBalls(maxballs);
		
		thread.start();
	}
	
	protected int getBallNumber() {
		int number = 1;
		try {
			number = Integer.parseInt(ballAmount.getText().trim());
		} catch (NumberFormatException e) {
			System.err.println("ERROR PARSING THE NUMBER OF BALLS. CHECK THE INPUT");
		}
		return number;
	}

	private void InitBalls(int maxballs) {
		balls = new ArrayList<>();
		for (int i = 1; i <= maxballs; i++) {
			balls.add(new Ball(i, maxballs));
		}
		t = 0;
		step = 0;
	}

	@Override
	public void run() {
		frame.createBufferStrategy(2);
		BufferStrategy strat = frame.getBufferStrategy();
		long delta = System.nanoTime();
		while(run){
			if(System.nanoTime() - delta > 10000000){
				update();
				delta = System.nanoTime();
			}
			render(strat, frame);
		}
	}

	private void render(BufferStrategy strat, JFrame f) {
		do {
			do {
				Graphics g = strat.getDrawGraphics();
				g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
				g.setColor(Color.WHITE);
				g.fillRect(XOFFSET-2, YOFFSET-2, 4, 4);
				switch (AXIS) {
				case 0: {
					
					break;
				}
				case 1: {
					g.drawLine(XOFFSET, YOFFSET, frame.getWidth(), frame.getHeight()/2);
					break;
				}
				case 2: {
					g.drawLine(0, YOFFSET, frame.getWidth(), frame.getHeight()/2);
					break;
				}
				case 3: {
					g.drawLine(0, YOFFSET, frame.getWidth(), frame.getHeight()/2);
					g.drawLine(XOFFSET, 0, frame.getWidth()/2, frame.getHeight());
					break;
				}
				}
				for (int i = 0; i < balls.size(); i++) {
					g.setColor(balls.get(i).getColor());
					if(ballsOn){
						balls.get(i).render(g);
					}
					if(lines && (i < balls.size() - 1)){
						g.drawLine(balls.get(i).getx()+XOFFSET, balls.get(i).gety()+YOFFSET, balls.get(i + 1).getx()+XOFFSET, balls.get(i + 1).gety()+YOFFSET);
					}
				}
				
				g.dispose();
			} while (strat.contentsRestored());
			strat.show();
		} while (strat.contentsLost());
	
		
	}

	private void update() {		
		t += step;
		for (int i = 0; i < balls.size(); i++) {
			balls.get(i).update(t);
		}
		
	}

	public static void main(String[] args) {
		Program p = new Program(1600,1000, 60);
	}
	
}
