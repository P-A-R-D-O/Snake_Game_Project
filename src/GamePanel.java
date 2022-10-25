import java.awt.*;
import java.awt.event.*;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	
	//Creating the arrays that will contain the coordenates of the snake body
	
	final int x[] = new int[GAME_UNIT];
	final int y[] = new int [GAME_UNIT];
	
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX;
	int appleY;
	
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw (Graphics g) {
		
		
		//Drawing the apple
		for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		
		//Drawing the snake
		for(int i = 0; i < bodyParts; i++) {
			if (i == 0) { //Head of the snake
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else { //Body of the snake
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				
			}
		}
		

	}
	
	public void newApple() {
		appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
		appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
	}
	 
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
	}
		
	}
	
	public void checkApple() {
		
	}
	
	public void checkCollisions() {
		//checks if Head of the snake collides with its body
		for(int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i] && y[0] == y[i])) {
				running = false;
			}
		}
		
		//checks if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		
		//checks if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		//checks if head touches top border
		if(y[0] < 0) {
			running = false;
		}

		//checks if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}

		if (!running) {
			timer.stop();
		}
		
		
	}
	
	public void gameOver(Graphics g) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		public void KeyPressed (KeyEvent e) {
			
		}
		
	}

}
