package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener, MouseListener
{
	public static Snake game;

	public Renderer renderer;

	private final int WIDTH = 900, HEIGHT = 900;

	private int xSpeed = 0, ySpeed = 0, ticks, score, highScore;

	private Random rand;

	private enum STATE {
		MENU,
		COLORS,
		GAME
	};

	public static final Color brown = new Color(102, 51, 0);

	private STATE State = STATE.MENU;

	private boolean north, south, east, west, started = false, gameOver = false, up, down, left, right;

	private boolean green = true, yellow = false, lightBlue  = false, darkBlue  = false, orange  = false;

	private ArrayList<Rectangle> snake, apple;

	private Rectangle playButton, colorButton, greenButton, yellowButton, lightBlueButton, darkBlueButton, orangeButton, backButton, exitButton;
	
	/** Constructor
	 * - Sets jframe
	 * - Gives ArrayLists & Buttons non-null values
	 * - Sets game to default, ready-to-begin, state
	 */
	public Snake()
	{
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		rand = new Random();

		jframe.add(renderer);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setResizable(false);
		jframe.addMouseListener(this);
		jframe.setTitle("Snake");
		jframe.addKeyListener(this);

		snake = new ArrayList<Rectangle>();
		apple = new ArrayList<Rectangle>();
		playButton = new Rectangle(WIDTH / 2 - 90, HEIGHT / 2 - 50, 200, 100);
		colorButton = new Rectangle(100, 650, 200, 100);
		greenButton = new Rectangle(50, 275, 200, 100);
		yellowButton = new Rectangle(350, 275, 200, 100);
		lightBlueButton = new Rectangle(650, 275, 200, 100);
		darkBlueButton = new Rectangle(650, 575, 200, 100);
		orangeButton = new Rectangle(350, 575, 200, 100);
		backButton = new Rectangle(50, 575, 200, 100);
		exitButton = new Rectangle(50, 50, 200, 50);

		timer.start();
		addSnake(true);
		addApple();

	}

	public static void main(String[] args) 
	{
		game = new Snake();
	}

	@Override
	/** The game mechanics
	 * - Speed of snake controlled by ticks
	 * - Determines if snake is hitting the wall or itself
	 * - Sets high score
	 * - Resets for each game by clearing snake and apple at death
	 * - Calls on renderer to repaint, regardless of game state
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(State == STATE.GAME)
		{
			ticks++;
			if(started)
			{
				if(xSpeed == 0 && ticks % 3 == 0)
				{
					Rectangle s = snake.get(0);
					for(int i = snake.size() - 1; i > 0; i--)
					{
						snake.get(i).x = snake.get(i-1).x;
						snake.get(i).y = snake.get(i-1).y;
					}
					s.y += ySpeed;

				}
				if(ySpeed == 0 && ticks % 3 == 0)
				{
					Rectangle s = snake.get(0);
					for(int i = snake.size() - 1; i > 0; i--)
					{
						snake.get(i).x = snake.get(i-1).x;
						snake.get(i).y = snake.get(i-1).y;
					}
					s.x += xSpeed;
				}
				if(gameOver != true && snake.get(0).intersects(apple.get(0)))
				{
					score++;
					apple.clear();
					addApple();
					addSnake(false);
				}
				if(snake.get(0).x < 0 || snake.get(0).x > 900 || snake.get(0).y < 0 || snake.get(0).y > 900)
				{
					gameOver = true;
					apple.clear();
					xSpeed = 0;
					ySpeed = 0;
					if(score > highScore)
					{
						highScore = score;
					}
				}
				for(int i = 1; i < snake.size()-1; i++)
				{
					if(snake.get(0).intersects(snake.get(i)))
					{
						gameOver = true;
						apple.clear();
						xSpeed = 0;
						ySpeed = 0;
						if(score > highScore)
						{
							highScore = score;
						}
					}
				}
			}
		}
		renderer.repaint();
	}

	/** Moves snake upwards
	 * - Stops snake if it dies and is moving up
	 */
	public void moveUp()
	{
		if(State == STATE.GAME)
		{
			if(gameOver)
			{
				snake.clear();
				apple.clear();
				score = 0;
				xSpeed = 0;
				ySpeed = 0;

				addSnake(true);
				addApple();

				State = STATE.MENU;
				ySpeed = 98;
			}
			if(!started) 
			{
				started = true;
			}
			if(ySpeed == 98) 
			{
				ySpeed = 0;
			}
			else if(ySpeed != 0)
			{

			}
			else
			{
				xSpeed = 0;
				ySpeed -= 30;
			}
		}
	}

	/** Moves snake downwards
	 * - Stops snake if it dies and is moving down
	 */
	public void moveDown()
	{
		if(State == STATE.GAME)
		{
			if(gameOver)
			{
				snake.clear();
				apple.clear();
				score = 0;
				xSpeed = 0;
				ySpeed = 0;

				addSnake(true);
				addApple();

				State = STATE.MENU;
				ySpeed = 98;
			}
			if(!started) 
			{
				started = true;
			}
			if(ySpeed == 98)
			{
				ySpeed = 0;
			}
			else if(ySpeed != 0) 
			{

			}
			else
			{
				xSpeed = 0;
				ySpeed += 30;
			}
		}
	}

	/** Moves snake right
	 * - Stops snake if it dies and is moving right
	 */
	public void moveRight()
	{
		if(State == STATE.GAME)
		{
			if(gameOver)
			{
				snake.clear();
				apple.clear();
				score = 0;
				xSpeed = 0;
				ySpeed = 0;

				addSnake(true);
				addApple();

				State = STATE.MENU;
				xSpeed = 98;
			}
			if(!started) 
			{
				started = true;
			}
			if(xSpeed == 98)
			{
				xSpeed = 0;
			}
			else if(xSpeed != 0)
			{

			}
			else
			{
				ySpeed = 0;
				xSpeed += 30;
			}
		}
	}

	/** Moves snake left
	 * - Stops snake if it dies and is moving left
	 */
	public void moveLeft()
	{
		if(State == STATE.GAME)
		{
			if(gameOver)
			{
				snake.clear();
				apple.clear();
				score = 0;
				xSpeed = 0;
				ySpeed = 0;

				addSnake(true);
				addApple();

				State = STATE.MENU;
				xSpeed = 98;
			}
			if(!started) 
			{
				started = true;
			}
			if(xSpeed == 98)
			{
				xSpeed = 0;
			}
			else if(xSpeed != 0)
			{

			}
			else
			{
				ySpeed = 0;
				xSpeed -= 30;
			}
		}
	}

	/** Randomly generates an apple on any of the 900 squares on the map
	 * 
	 */
	public void addApple()
	{
		int width = 30;
		int height = 30;

		apple.add(new Rectangle(30 * rand.nextInt(29), 30 * rand.nextInt(29), width, height));
	}

	/** Draws the red apple body
	 * 
	 * @param g - graphics to be drawn
	 * @param a - apple being painted, used for coordinates of where to draw apple
	 */
	public void paintApple(Graphics g, Rectangle a)
	{
		g.setColor(Color.red.darker());
		g.fillRect(a.x, a.y, a.width, a.height);
	}

	/** Draws the brown apple stem
	 * 
	 * @param g - graphics to be drawn
	 * @param a - apple being painted, used for coordinates of where to draw apple
	 */
	public void paintStem(Graphics g, Rectangle a)
	{
		g.setColor(brown);
		g.fillRect(a.x + 10, a.y - 4, 10, 13);
	}

	/** Based upon the direction of the last piece of the snake, this method adds another piece of snake to the end.
	 * This is done by comparing the position of the last piece of the snake to the second to last piece, unless it is
	 * the head of the snake, in which case it uses boolean values to determine the direction.
	 * 
	 * @param start - determines if it's the snake head, or if its just a body piece of the snake
	 */
	public void addSnake(boolean start)
	{
		int width = 30;
		int height = 30;

		if(start)
		{
			snake.add(new Rectangle(WIDTH/2, HEIGHT/2, width, height));
		}
		else if(north)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x, snake.get(snake.size()-1).y - 30, width, height));
			north = false;
		}
		else if(south)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x, snake.get(snake.size()-1).y + 30, width, height));
			south = false;
		}
		else if(east)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x - 30, snake.get(snake.size()-1).y, width, height));
			east = false;
		}
		else if(west)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x + 30, snake.get(snake.size()-1).y, width, height));
			west = false;
		}
		else if(snake.get((snake.size() - 1)).x < snake.get((snake.size() - 2)).x)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x - 30, snake.get(snake.size()-1).y, width, height));
		}
		else if(snake.get((snake.size() - 1)).x > snake.get((snake.size() - 2)).x)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x + 30, snake.get(snake.size()-1).y, width, height));
		}
		else if(snake.get((snake.size() - 1)).y < snake.get((snake.size() - 2)).y)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x, snake.get(snake.size()-1).y + 30, width, height));
		}
		else if(snake.get((snake.size() - 1)).y > snake.get((snake.size() - 2)).y)
		{
			snake.add(new Rectangle(snake.get((snake.size()-1)).x, snake.get(snake.size()-1).y - 30, width, height));
		}
	}

	/** Draws each piece of the snake body
	 * 
	 * @param g - graphics to be drawn
	 * @param s - snake being painted, used for coordinates of where to draw snake
	 */
	public void paintSnake(Graphics g, Rectangle s)
	{
		if(green)
		{
			g.setColor(Color.green);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(yellow)
		{
			g.setColor(Color.yellow);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(lightBlue)
		{
			g.setColor(Color.cyan);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(darkBlue)
		{
			g.setColor(Color.blue);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(orange)
		{
			g.setColor(Color.orange);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
	}

	/** Draws the snake's head, eyes and tongue
	 * 
	 * @param g - graphics to be drawn
	 * @param s - snake head being painted, used for coordinates of where to draw snake head
	 */
	public void paintSnakeHead(Graphics g, Rectangle s)
	{
		if(green)
		{
			g.setColor(Color.green);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(yellow)
		{
			g.setColor(Color.yellow);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(lightBlue)
		{
			g.setColor(Color.cyan);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(darkBlue)
		{
			g.setColor(Color.blue);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(orange)
		{
			g.setColor(Color.orange);
			g.fillRect(s.x, s.y, s.width, s.height);
		}
		if(up == true || !started)
		{
			g.setColor(Color.black);
			g.fillRect(s.x + 3, s.y + 3, 8, 8);
			g.fillRect(s.x + 19, s.y + 3, 8, 8);
			g.setColor(Color.white);
			g.fillRect(s.x + 5, s.y + 5, 2, 2);
			g.fillRect(s.x + 21, s.y + 5, 2, 2);
			g.setColor(Color.red);
			g.fillRect(s.x + 14, s.y - 15, 2, 15);
		}
		if(down == true)
		{
			g.setColor(Color.black);
			g.fillRect(s.x + 3, s.y + 19, 8, 8);
			g.fillRect(s.x + 19, s.y + 19, 8, 8);
			g.setColor(Color.white);
			g.fillRect(s.x + 5, s.y + 21, 2, 2);
			g.fillRect(s.x + 21, s.y + 21, 2, 2);
			g.setColor(Color.red);
			g.fillRect(s.x + 14, s.y + 30, 2, 15);
		}
		if(left == true)
		{
			g.setColor(Color.black);
			g.fillRect(s.x + 3, s.y + 3, 8, 8);
			g.fillRect(s.x + 3, s.y + 19, 8, 8);
			g.setColor(Color.white);
			g.fillRect(s.x + 5, s.y + 5, 2, 2);
			g.fillRect(s.x + 5, s.y + 21, 2, 2);
			g.setColor(Color.red);
			g.fillRect(s.x - 15, s.y + 14, 15, 2);
		}
		if(right == true)
		{
			g.setColor(Color.black);
			g.fillRect(s.x + 19, s.y + 3, 8, 8);
			g.fillRect(s.x + 19, s.y + 19, 8, 8);
			g.setColor(Color.white);
			g.fillRect(s.x + 21, s.y + 5, 2, 2);
			g.fillRect(s.x + 21, s.y + 21, 2, 2);
			g.setColor(Color.red);
			g.fillRect(s.x + 30, s.y + 14, 15, 2);
		}

	}

	/** Makes calls to the correct draw methods when dealing with apples and snake pieces.
	 * Manually draws on all buttons and text, determined by the value of the enum State.
	 * 
	 * @param g - graphics to be drawn
	 */
	public void repaint(Graphics g) 
	{
		if(State == STATE.GAME)
		{
			g.setColor(Color.green.darker().darker());
			g.fillRect(0, 0, WIDTH, HEIGHT);
			for(int i = 0; i < snake.size(); i++)
			{
				if(i == 0)
				{
					paintSnakeHead(g, snake.get(i));
				}
				else
				{
					paintSnake(g, snake.get(i));
				}
			}
			for(Rectangle a: apple)
			{
				paintApple(g, a);
				paintStem(g, a);
			}

			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1, 50));

			if(gameOver)
			{
				g.drawString("Game Over!", 300, 100);
				g.drawString("Score: " + score, 350, 400);
				g.drawString("High Score: " + highScore, 275, 500);
				g.drawString("Hit any arrow key to return", 125, 800);
				g.drawString("to the menu", 300, 850);
			}
			
			if(!gameOver && !started)
			{
				g.setColor(Color.white);
				g.fillRect(exitButton.x, exitButton.y, 200, 50);
				g.setFont(new Font("Arial", 1, 20));
				g.setColor(Color.black);
				g.drawString("<- Back to Menu", exitButton.x + 18, exitButton.y + 30);
			}
			
			if(!gameOver && started) {
				g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
			}
		}
		else if(State == STATE.MENU)
		{
			started = false;
			up = false;
			down = false;
			left = false;
			right = false;
			g.setColor(Color.gray);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setFont(new Font("Arial", Font.BOLD, 75));
			g.setColor(Color.white);
			g.drawString("SNAKE", 335, 100);

			g.setFont(new Font("Arial", 1, 37));
			g.fillRect(playButton.x, playButton.y, 200, 100);
			g.fillRect(colorButton.x, colorButton.y, 200, 100);
			g.setColor(Color.green.darker());
			g.drawString("Play", playButton.x + 62, playButton.y + 60);
			g.setFont(new Font("Arial", 1, 25));
			g.setColor(Color.red);
			g.drawString("Choose Color", colorButton.x + 20, colorButton.y + 60);
		}
		else if(State == STATE.COLORS)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("PRESS A BOX TO CHOOSE YOUR SNAKE'S COLOR", 150, 100);
			
			g.setColor(Color.green);
			g.fillRect(greenButton.x, greenButton.y, 200, 100);
			
			g.setColor(Color.yellow);
			g.fillRect(yellowButton.x, yellowButton.y, 200, 100);
			
			g.setColor(Color.cyan);
			g.fillRect(lightBlueButton.x, lightBlueButton.y, 200, 100);
			
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.white);
			g.fillRect(backButton.x, backButton.y, 200, 100);
			g.setColor(Color.black);
			g.drawString("<- Back to Menu", backButton.x + 20, backButton.y + 55);
			
			g.setColor(Color.blue);
			g.fillRect(darkBlueButton.x, darkBlueButton.y, 200, 100);
			
			g.setColor(Color.orange);
			g.fillRect(orangeButton.x, orangeButton.y, 200, 100);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			moveUp();
			if(down != true)
			{
				up = true;
				down = false;
				left = false;
				right = false;
			}
			if(snake.size() == 1)
			{
				north = true;
				south = false;
				west = false;
				east = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moveDown();
			if(up != true)
			{
				up = false;
				down = true;
				left = false;
				right = false;
			}
			if(snake.size() == 1)
			{
				north = false;
				south = true;
				west = false;
				east = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveLeft();
			if(right != true)
			{
				up = false;
				down = false;
				left = true;
				right = false;
			}
			if(snake.size() == 1)
			{
				north = false;
				south = false;
				west = true;
				east = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight();
			if(left != true)
			{
				up = false;
				down = false;
				left = false;
				right = true;
			}
			if(snake.size() == 1)
			{
				north = false;
				south = false;
				west = false;
				east = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		int mx = e.getX();
		int my = e.getY();

		if(mx > playButton.x && mx < playButton.x + 200 && my > playButton.y + 30 && my < playButton.y + 130 && State == State.MENU)
		{
			State = STATE.GAME;
			gameOver = false;
		}
		if(mx > colorButton.x && mx < colorButton.x + 200 && my > colorButton.y + 30 && my < colorButton.y + 110 && State == State.MENU)
		{
			State = STATE.COLORS;
		}
		if(mx > greenButton.x && mx < greenButton.x + 200 && my > greenButton.y + 30 && my < greenButton.y + 130 && State == State.COLORS)
		{
			green = true;
			orange = false;
			yellow = false;
			lightBlue = false;
			darkBlue = false;
			State = STATE.MENU;
		}
		if(mx > yellowButton.x && mx < yellowButton.x + 200 && my > yellowButton.y + 30 && my < yellowButton.y + 130 && State == State.COLORS)
		{
			green = false;
			orange = false;
			yellow = true;
			lightBlue = false;
			darkBlue = false;
			State = STATE.MENU;
		}
		if(mx > orangeButton.x && mx < orangeButton.x + 200 && my > orangeButton.y + 30 && my < orangeButton.y + 130 && State == State.COLORS)
		{
			green = false;
			orange = true;
			yellow = false;
			lightBlue = false;
			darkBlue = false;
			State = STATE.MENU;
		}
		if(mx > lightBlueButton.x && mx < lightBlueButton.x + 200 && my > lightBlueButton.y + 30 && my < lightBlueButton.y + 130 && State == State.COLORS)
		{
			green = false;
			orange = false;
			yellow = false;
			lightBlue = true;
			darkBlue = false;
			State = STATE.MENU;
		}
		if(mx > darkBlueButton.x && mx < darkBlueButton.x + 200 && my > darkBlueButton.y + 30 && my < darkBlueButton.y + 130 && State == State.COLORS)
		{
			green = false;
			orange = false;
			yellow = false;
			lightBlue = false;
			darkBlue = true;
			State = STATE.MENU;
		}
		if(mx > backButton.x && mx < backButton.x + 200 && my > backButton.y + 30 && my < backButton.y + 130 && State == State.COLORS)
		{
			State = STATE.MENU;
		}
		if(mx > exitButton.x && mx < exitButton.x + 200 && my > exitButton.y + 25 && my < exitButton.y + 75 && State == State.GAME)
		{
			State = STATE.MENU;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
