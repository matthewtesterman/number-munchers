import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 *
 *This class in the main game control... contains JPanek, timer, paint component and several method for handling 
 *data
 *
 */


public class GameControl extends JPanel implements ActionListener{

	//Global Variables
	private GameGrid gameGrid;
	private GameDraw gameDraw;
	private Timer timer;
	private Sprite hero;
	private int x, y, score, counter, troggleTimer;
	private boolean enter, escape, eat, stopTroggleShowUp, firstTime, go;
	private String selection, fixedDirection, face;
	private MenuScreen menuScreen;
	private KeyEvent e;
	

	//Set up Window, start timer
	public GameControl() 
	{				
		face = "DEFAULT";
		firstTime = true;
		eat = false; enter = false; stopTroggleShowUp = true; go = false;
		counter = 0; score = 0; troggleTimer = 0; //This is used repeatedly to show screens for a limited time.
		selection = "Game Menu";	fixedDirection = "RIGHT";
		setUpWindow();		
		
		gameGrid = new GameGrid(1,0,310,250);
		gameDraw = new GameDraw(gameGrid, hero);
		//gameDraw.setUpSprites();
		menuScreen = new MenuScreen();
		menuScreen.createImages();
		this.addKeyListener(new KeyGetClass());
		setupSprites("hero.png");	
		resetKeys();
		timer = new Timer(5, this);		
		timer.start();
		
	}	

	//Used with Timer (IMPORTANT)*****
	public void actionPerformed(ActionEvent e)
	{	
		if (selection == "Game")
			updateSpriteLocation();				
		repaint();	
	}		

	//Paint Method (Called continuously)
	public void paintComponent(Graphics g)
	{			
		setUpPaint(g);
		if (selection == "Game")
		{				
			drawSprites(g); 
			gameGrid.ifHeroCollision();
			gameDraw.paintGame(selection, g, gameGrid.getTroggleX(), gameGrid.getTroggleY(), face);
			
			if (escape)
			{
				gameGrid.setLevel(1);
			}
		}
		else if(selection == "Game Menu")
		{
			if (firstTime)//This condition is placed here because goal is prevent the enter key from spilling over onto the menu screan when winning a game
			{
				e = null; resetKeys();
				firstTime = false;
			}
			if (escape)//If esc key is pressed from the GAME screen
			{
				escape = false;
				gameGrid = null; gameGrid = new GameGrid(1,0,310,250);//Clear current data away
				gameDraw = null; gameDraw = new GameDraw(gameGrid, hero);//Flush current data away
			}
			
			selection = menuScreen.drawScreen(g, "NuuLju", e);	//e is keypressed
			e = null;//This nulls the key after it is pressed to prevent overlaping
			resetKeys();//Prevents overlapping in enter key!!
		}

		else if(selection == "Transition")
		{
			selection = "Transition";
			displayMessageScreen(g);	
		}

		else if(selection == "Game Over")
			displayMessageScreen(g);	
		else if(selection == "Winner")
			displayMessageScreen(g);
		else if (selection == "Instructions")
			menuScreen.drawInstruction(g);
		else if(selection == "Exit")
			System.exit(0);
	}

	//Resets the keys to default values.
	public void resetKeys()
	{
		enter = false;
		escape = false;
		x = 0;	y = 0;
	}

	//Sets up Window
	public void setUpWindow()
	{
		JFrame window = new JFrame("Number Munchers");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(150, 0);		
		setUpPanel();
		window.add(this);
		window.pack();		
		window.setVisible(true);		
	}	

	//Sets up Panel
	public void setUpPanel()
	{		
		this.setOpaque(true);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(800, 700));
		this.setBackground(Color.BLACK);	
	}

	//Draws Sprites
	public void drawSprites(Graphics g)
	{
		drawHeroFace(g);		
	}

	//Draws hero facing direction; used with drawSprites()
	public void drawHeroFace(Graphics g)
	{
		hero.uppateLocation(gameGrid.getHeroXP(), gameGrid.getHeroYP());
		if (x == -5)
			hero.animateSpriteLeft(g);
		else if (x == 5)
			hero.animateSpriteRight(g);
		else if (y == -5)
			hero.animateSpriteUp(g);		
		else if (y == 5)
			hero.animateSpriteDown(g);	
		else if (eat)
		{
			
			hero.animateSpriteEat(g);
			timeMunch();
			//eat = false;
		}
		else
			hero.animateStationary(g, 2, -25, "Game");
			//g.drawImage(hero.getSpriteFace(spriteFace), gameGrid.getHeroXP()+2, gameGrid.getHeroYP()-25, null);
		drawLives(g);

	}
	
	//Draws troggle location
	public void drawTroggle(Graphics g)
	{
		if(gameGrid.isTroggleActive())
			g.drawRect(gameGrid.getTroggleX(), gameGrid.getTroggleY(), 20, 10);
	
	}

	//Sets up Paint
	public void setUpPaint(Graphics g)
	{		
		super.paintComponent(g);		
		setBackground(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 32));
	}

	//Set up character sprites
	public void setupSprites(String FileName)
	{
		hero = new Sprite(FileName);
		hero.createSprites("hero.png");		
	}

	//KeyListener Section: Private class
	private class KeyGetClass implements KeyListener
	{			
		public void keyPressed(KeyEvent e) 
		{	
			GameControl.this.e = e;
			//This if condition prevents muncher from back tracking in the middle of a transition between two blocks.
			if ((gameGrid.getHeroXP() == 685 || gameGrid.getHeroXP() == 560 || gameGrid.getHeroXP() == 435 ||gameGrid.getHeroXP() == 310 || gameGrid.getHeroXP() == 185 || gameGrid.getHeroXP() == 60)
					&& (gameGrid.getHeroYP() == 150 || gameGrid.getHeroYP() == 250 || gameGrid.getHeroYP() == 350 ||gameGrid.getHeroYP() == 450 || gameGrid.getHeroYP() == 550) )
			{
				switch(e.getKeyCode())
				{
				case KeyEvent.VK_UP:
					resetKeys(); y= -5;
					break;	
				case KeyEvent.VK_DOWN:
					resetKeys(); y = 5;				
					break;					
				case KeyEvent.VK_LEFT:
					resetKeys(); x = -5; 					
					break;					
				case KeyEvent.VK_RIGHT:
					resetKeys(); x = 5;
					break;	
				case KeyEvent.VK_ENTER:
					resetKeys(); enter = true; 
					if (selection == "Game")
						eat = true;
					break;
				case KeyEvent.VK_ESCAPE:
					resetKeys(); selection = "Game Menu"; escape = true;
					break;	
				}
			}

		}		

		public void keyReleased(KeyEvent e) 
		{
		}

		public void keyTyped(KeyEvent e) 
		{
		}
	}

	//Stop Sprite when necessary & updates location
	public void updateSpriteLocation()
	{
		gameGrid.updateHeroLocation(x, y);//This updates (officially writes it to gameGrid)		
		//Tells sprite to stop at theese specific cordinates.
		if (gameGrid.getHeroXP() == 685 || gameGrid.getHeroXP() == 560 || gameGrid.getHeroXP() == 435 ||gameGrid.getHeroXP() == 310 || gameGrid.getHeroXP() == 185 || gameGrid.getHeroXP() == 60 && selection == "Game")
		{
			x = 0;			
		}

		if (gameGrid.getHeroYP() == 150 || gameGrid.getHeroYP() == 250 || gameGrid.getHeroYP() == 350 ||gameGrid.getHeroYP() == 450 || gameGrid.getHeroYP() == 550 && selection == "Game")
		{
			y = 0;
		}
		//Prevents hero from an escape from map
		if (gameGrid.getHeroYP() == -20)
		{
			gameGrid.setHeroYP(725);
		}
		else if (gameGrid.getHeroYP() == 725)
		{
			gameGrid.setHeroYP(-20);
		}		

		if (gameGrid.getHeroXP() == -20)
		{
			gameGrid.setHeroXP(800);
		}	

		else if (gameGrid.getHeroXP() == 800)
		{
			gameGrid.setHeroXP(-20);
		}	

		if(enter)//MAY BE A LARGE CONDITION IN HERE BECAUSE OF THE CIRCUMSTANCES INVOLVING ENTER
		{			
			//Add something about eating a number here.
			if (selection == "Game")
			{
				resetKeys();
				enterGame();				
			}	

		}//End of if Game		
		troggleAppearance();
	}

	//This is the 'enter key' control method for the GAME	
	public void enterGame()
	{
		gameGrid.checkBlock();

		if(gameGrid.wonLevel("Multiples"))
		{	
			selection = "Transition";
			gameGrid.setsLives(3);
			gameGrid.advanceToNextLevel();//Increment Level	
			gameDraw = new GameDraw(gameGrid, hero);
		}

		//If game is beaten:	
		if (gameGrid.hasWonGame())//Determine if won
		{				
			selection = "Winner";
			gameGrid.reset();	
			gameDraw = new GameDraw(gameGrid, hero);			
		}

		if (gameGrid.getsLives() == -1)
		{
			score = gameGrid.getScore();
			gameGrid.reset();	
			selection = "Game Over";			
			gameDraw = new GameDraw(gameGrid, hero);			
		}
	}

	//Displays the winner screen
	public void displayMessageScreen(Graphics g)
	{		
		firstTime = true;//Get ready to go back to menu screen safely to prevent keys from overlaping
		g.setColor(Color.WHITE);	
		if(selection == "Winner" && counter < 1000)
		{
			counter++;
			g.drawString("WINNER", 280, 300);
			g.drawString("FINAL SCORE: "  + score, 280, 400);								
		}
		else if (selection == "Transition" && counter < 700)
		{
			counter = counter + 5;
			g.drawString("LEVEL " + gameGrid.getLevel(), 350, 300);			
		}
		else if (selection == "Game Over" && counter < 700)
		{
			counter += 5;
			g.drawString("GAME OVER", 280, 300);		
			g.drawString("FINAL SCORE: "  + score, 280, 400);					
		}
		else
		{			
			counter = 0;
			resetKeys();
			if (selection == "Transition")//Fragile code-Don't touch (Allows transisiton to succeed
				selection = "Game";
			else
				selection = "Game Menu";

		}
	}

	//Draws lives
	public void drawLives(Graphics g)
	{
		int a = 0;
		g.setColor(Color.WHITE);
		g.drawString("LIVES:", 500, 650);
		int clock = 0;
		for (int i = 0; i < gameGrid.getsLives(); i++)
		{
			if(eat && clock < 20)
			{				
				hero.animateSpriteEatLives(g, 625+a, 620);
				clock++;
			}
				
			else
				hero.animateStationary(g, a, 0 , "Lives");
				//g.drawImage(hero.getSpriteFace("stationary"), 625 + a, 620, null);
			a += 50;
		}		
	}	

	//Draws stationary hero
	public void drawHero(Graphics g)
	{
		switch(x)
		{
		case 0:
			hero.animateStationary(g, 0, 0 , "Game");
			break;
		case -5:
			//Method
			break;
		case 5:
			//Method
			break;			
		}
	}

	//This is used with the muncher to slow chomp down
	public void timeMunch()
	{		
		if (counter < 10 && eat)
		{
			counter++;
		}
		else
		{
			eat = false;
			counter = 0;
		}
	}
	
	//Determines when the troggle should make an appearance
	public void troggleAppearance()
	{
		if (selection == "Game" && stopTroggleShowUp)
		{			
			troggleTimer++;
			
			if (troggleTimer == 250)
			{
				gameGrid.activateTroggle();
				stopTroggleShowUp = false;//stops timer
				troggleTimer = 0;
				
			}			
		}
		else if (selection != "Game")
		{
			troggleTimer = 0;
		}
		else
		{
			autoMoveTroggle();
		}
		
	}
	


	
	//Determines which direction for the troggle to move
	public String getDirectionForTroggle()
	{
		String value = "DOWN";
		int heroXPos = gameGrid.getHeroXP();
		int heroYPos = gameGrid.getHeroYP();
		int troggleXPos = gameGrid.getTroggleX();
		int troggleYPos = gameGrid.getTroggleY();
		
		if (heroYPos < troggleYPos)		
			value = "UP";					
		else if (heroYPos > troggleYPos)	
			value = "DOWN";
		else if (heroYPos == troggleYPos)
		{
			if (heroXPos > troggleXPos)
				value = "RIGHT";
			else if (heroXPos < troggleXPos)
				value = "LEFT";
			else if (heroXPos == troggleXPos)
				value = "RIGHT";
		}		
		return value;
	}
	
	public void computeDirection(String direction)
	{
		int xValue = 0; int yValue = 0;
		if (direction == "LEFT")
		{			
			xValue = -5;yValue = 0;
		}
			
		else if (direction == "RIGHT")
		{
			xValue = 5;yValue = 0;
		}
			
		else if (direction == "UP")
		{
			yValue = -5; xValue = 0;
		}			
		else if (direction == "DOWN")
		{
			yValue = 5;	xValue = 0;
		}				
		else
		{
			xValue = 0; yValue = 0;
		}	
		
		gameGrid.moveTroggle(xValue, yValue);
	}
	
	
	//AI takes over and moves troggle
	public void autoMoveTroggle()
	{	
		if (troggleTimer <= 75 && go == false)
		{
			troggleTimer++;
			gameGrid.setOccupied();
		
		}			
		else if (troggleTimer > 75)
		{
			troggleTimer = 0;
			go = true;
			
		}
		else if (go)
		{			
		computeDirection(fixedDirection);	
		
			if ((gameGrid.getTroggleX() == 685 || gameGrid.getTroggleX() == 560 || gameGrid.getTroggleX() == 435
					|| gameGrid.getTroggleX() == 310 || gameGrid.getTroggleX() == 185 || gameGrid.getTroggleX() == 60)
				&& (gameGrid.getTroggleY() == 150 || gameGrid.getTroggleY() == 250 || gameGrid.getTroggleY() == 350
					|| gameGrid.getTroggleY() == 450 || gameGrid.getTroggleY() == 550))
			{
				
				fixedDirection = getDirectionForTroggle();	
				
				face = fixedDirection;
				go = false;				
				
			}	
		}
	}
}
