import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 */

/**
 * About this class:
 * This class draws and event handles the menu Screen
 */
public class MenuScreen
{
	//Global Variables
	private String mode , selection;
	private BufferedImage logo, selector, bg, clouds, instructions;
	private int toggle, selectionPos, spriteX, spriteY;
	private Sprite heroSprite, troggleSprite;
	// Constructor that sets up the screen.
	public MenuScreen()
	{	 			
		 spriteX = 0; spriteY = 550;
		selection = "Game";
	}

	// Main Control that Draws the screen through other methods. (This method is called from GameControl.)
	public String drawScreen(Graphics g, String direction, KeyEvent e)
	{
		mode = "Game Menu";

		drawMenuText(g);	
		drawImages(g);
		drawSelection(g , e);
		drawHero(g);
		drawFooter(g);


		if (e != null && e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			mode = selection;
			e = null;
		}


		return mode;

	}

	//Draws all the text on the Menu Screen (Invoked by drawScreen method)
	public void drawMenuText(Graphics g)
	{	
		
		if (toggle < 5)
		{
			g.setColor(Color.GREEN);

			toggle++;
		}				
		else if (toggle >= 5)
		{
			g.setColor(Color.MAGENTA);
			toggle++;
		}
		if ( toggle == 10)
			toggle = 0;
		g.drawLine(0, 198, 1000, 198);		
		g.drawLine(0, 200, 1000, 200);	
		g.drawLine(0, 375, 1000, 375);
		g.drawLine(0, 377, 1000, 377);		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 50));	
		
		if (selection == "Game")
			drawSelection(g, "Play Game" , 300 , 250);
		else			
			g.drawString("Play Game", 300, 250);//Draws String: title
		
		if (selection == "Instructions")
			drawSelection(g, "Instructions" , 300 , 300);
		else					
			g.drawString("Instructions", 300, 300);//Draws String: Instructions		
		
		if (selection == "Exit")
			drawSelection(g, "Exit" , 300 , 350);
		else					
			g.drawString("Exit", 300, 350);//Draws String: Instructions		
	}
	
	//Makes the selected menu flash (Invoked by drawMenuText)
	public void drawSelection(Graphics g, String menuItem , int x , int y)
	{
		if (toggle < 5)
		{
			g.setColor(Color.GREEN);

			g.drawString(menuItem, x, y);//Draws Selected Menu Item			
		}				
		else if (toggle >= 5)
		{
			g.setColor(Color.MAGENTA);			g.drawString(menuItem, x, y);//Draws Selected Menu Item		
			
		}		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 50));	 

	}

	// Draws Selected Menu (invoked by drawScreen)
	public void drawSelection (Graphics g, KeyEvent e)
	{
		if (e != null)
		{	
			if (e.getKeyCode() == KeyEvent.VK_UP)
				handleUpCondition(e);			
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				handleDownCondition(e);
		}
	}

	//When the user presses Up during the menu.
	public void handleUpCondition(KeyEvent e)
	{		
		if (selectionPos == 210)
		{
			selectionPos = 310;
			selection = "Exit";
		}					

		else if (selectionPos == 310)
		{
			selectionPos = 260;
			selection = "Instructions";
		}

		else if (selectionPos == 260)	
		{
			selectionPos = 210;	
			selection = "Game";
		}	
	}
	
	//When the user presses DOWN during the menu.
	public void handleDownCondition(KeyEvent e)
	{
		if (selectionPos == 210)
		{
			selectionPos = 260;	
			selection = "Instructions";
		}

		else if (selectionPos == 260)
		{
			selectionPos = 310;		
			selection = "Exit";			
		}

		else if (selectionPos == 310)
		{
			selectionPos = 210;		
			selection = "Game";					
		}		
	}


	// Draws the Images on the menuScreen (invoked by control)
	public void drawImages(Graphics g)
	{		
		g.drawImage(logo, 250, 0, null);
		g.drawImage(selector, 250, selectionPos, null );
		g.drawImage(bg, 2, 565, null);
		g.drawImage(clouds, 200, 450,  null);
	}

	// Initially creates the images (invoked by GameControl Class)
	public void createImages()
	{
		heroSprite = new Sprite("hero.png");
		heroSprite.createSprites("hero.png");
		troggleSprite = new Sprite("troggle.png");
		troggleSprite.createSprites("troggle.png");		
		
		selectionPos = 210;
		toggle = 0;
		try 
		{
			logo = ImageIO.read(new File("title.png"));
			selector = ImageIO.read(new File("selection.png"));
			bg = ImageIO.read(new File("menuBg.png"));	
			clouds = ImageIO.read(new File("clouds.png"));	
			instructions = ImageIO.read(new File("instructions.png"));				
		} 
		catch (IOException e) 
		{
			System.out.print(false);
		} 
	}

	//This draws the instructions
	public void drawInstruction(Graphics g)
	{
		g.setColor(Color.MAGENTA);
		g.drawRect(50, 50, 690, 500);
		g.drawImage(instructions, 51, 51,  null);
	}
	
	//Animates hero/troggle them on menuScnreen.
	public void drawHero(Graphics g)
	{
		heroSprite.uppateLocation(spriteX, spriteY);
		troggleSprite.uppateLocation(spriteX-150, spriteY);
		if (spriteX == 1000)
			spriteX = 0;
		else
		{
			troggleSprite.animateSpriteRight(g);
			heroSprite.animateSpriteRight(g);			
			spriteX += 2;
		}
			
		
	}
	
	//Draws all the information at the bottom of the MenuScreen (invoked by drawScreen)
	public void drawFooter(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(168, 653, 495, 700);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Created By Matt Testerman", 300, 670);
		g.drawString("This game is based off of MECC's 1990 Number Munchers.", 190, 690);		
	}

}
