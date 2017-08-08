import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 * 
 * This class handles most of the drawing
 * 
 */


public class GameDraw {

	//Global Variables
	private GameGrid gameGrid;
	private GameBlock[][] gameBlock;
	private Sprite troggle;
	
	//Constructor
	public GameDraw(GameGrid gameGrid, Sprite hero)
	{
		this.gameGrid = gameGrid;
		gameGrid.createGrid();
	
		this.gameBlock = gameGrid.getGameBlocks();
		setUpSprites();
	}
	
	//Determines what to paint
	public void paintGame(String selection, Graphics g, int troggleX, int troggleY, String face)
	{		
		drawStats(g);		
		drawGrid(g);
		drawSprites(g, troggleX, troggleY-12, face);
	}	
	
	public void setUpSprites()
	{
		troggle = new Sprite("troggle.png");
		troggle.createSprites("troggle.png");
	}
	
	public void drawSprites(Graphics g, int troggleX, int troggleY, String face)
	{
		if(gameGrid.isTroggleActive())
		{	
			troggle.uppateLocation(troggleX, troggleY);
		
			if (face == "LEFT")			
				troggle.animateSpriteLeft(g);
			else if (face == "RIGHT")			
				troggle.animateSpriteRight(g);			
			else if (face == "UP")			
				troggle.animateSpriteUp(g);	
			else if (face == "DOWN")			
				troggle.animateSpriteDown(g);				
			else if (face == "DEFAULT")		
				troggle.animateStationary(g, 0, 0-12, "Game");
		}
		
		//troggle.animateStationary(g, 0, 0, "Game");

	}
	
	
	////GAME related methods:
	//Draws the grid for GAME
	public void drawGrid(Graphics g)
	{
		int row = 20;
		int col = 100;
		g.setColor(Color.MAGENTA);		
		g.drawRect(18, 98, 755, 500);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
		for (int i = 0; i < gameBlock.length; i++)
		{			
			for (int b = 0; b < gameBlock[i].length; b++)
			{
				g.setColor(Color.MAGENTA);		
				g.drawRect(row, col, 125, 100);	
				g.drawRect(row+1, col+1, 125, 100);	
				g.setColor(Color.WHITE);	
				if (gameBlock[i][b].isVisible())			
					g.drawString("" + gameBlock[i][b].getValue(), row+50, col+55);
				
				row += 125;
			}			
			row = 20;
			col += 100;
		}		
	}
	
	//Draws the score, lives, etc. for GAME
	public void drawStats(Graphics g)
	{
		g.setFont(new Font("Arial",Font.BOLD, 32));
		g.setColor(Color.WHITE);
		g.drawString("SCORE:", 10, 650);		
		g.drawString(gameGrid.getScore() + "", 200, 650);	
		g.setColor(Color.BLUE);		
		g.drawRect(135, 620, 150, 40);
		g.drawRect(136, 619, 150, 40);	
		g.setColor(Color.WHITE);
		g.drawString("LEVEL:", 10, 40);
		g.drawString("" + gameGrid.getLevel(), 50, 75);
		g.setColor(Color.ORANGE);
		g.fillRect(175, 20, 500, 4);
		g.fillRect(175, 80, 500, 4);
		g.setColor(Color.WHITE);
		g.drawString("Multiples of " + gameGrid.getLevel(), 300, 60);		
	}
}
