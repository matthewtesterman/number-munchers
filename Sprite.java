import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 * 
 * This class Creates a sprite and animation for it
 */

public class Sprite {

	//Global Variables
	private int xPosition, yPosition, counter;
	private BufferedImage sprite, stationary, action1, moveRight1, moveRight2, moveDown1, moveDown2, moveLeft1, moveLeft2, moveUp1, moveUp2;


	//Constructor
	public Sprite(String fileLocation) 
	{	
		counter = 0;
		
						
	}

	//Get image and break apart into sprites
	public void createSprites(String filelocation)
	{		
		try 
		{   	 
			sprite = ImageIO.read(new File(filelocation));	
		} 
		catch (IOException ex)
		{
			System.out.print("error");
		}				
		stationary = sprite.getSubimage(0, 0, 39, 40);
		moveDown1 = sprite.getSubimage(0, 41, 39, 40);			
		moveDown2 = sprite.getSubimage(40, 41, 39, 40);	
		moveRight1 = sprite.getSubimage(40, 0, 39, 40);
		moveRight2 = sprite.getSubimage(80, 0, 39, 40);	
		moveLeft1 = sprite.getSubimage(0, 82, 39, 40);
		moveLeft2 = sprite.getSubimage(40, 82, 39, 40);	
		moveUp1 = sprite.getSubimage(0, 123, 39, 40);
		moveUp2 = sprite.getSubimage(40, 123, 39, 40);		
		action1 = sprite.getSubimage(0, 164, 39, 40);			
	}

	//Updates the location
	public void uppateLocation(int xPosition, int yPosition) 
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;		
	}
	
	//Draws Stationary front face
	public void animateStationary(Graphics g , int addX , int addY, String area)
	{
		if (area == "Game")
		{
			g.drawImage(stationary, xPosition + addX, yPosition + addY, null);
		}
		else if (area == "Lives")
		{
			g.drawImage(stationary, 625 + addX, 620 + addY, null);
		}
		
	}	

	
	
	
	public void animateSpriteLeft(Graphics g)
	{
		int y = -25;
		counter++;
			if(counter < 15)
			{
				if (counter > -1 && counter < 7)
					g.drawImage(moveLeft2, xPosition, yPosition+y, null);						
				if (counter >= 7 && counter < 15)
					g.drawImage(moveLeft1, xPosition, yPosition+y, null);				
				
			}
			else if (counter == 15)
			{
				g.drawImage(moveLeft1, xPosition, yPosition+y, null);	
				counter = 0;
			}	
	}
	
	public void animateSpriteRight(Graphics g)
	{
		int y = -25;
		counter++;
			if(counter < 15)
			{
				if (counter > -1 && counter < 7)
					g.drawImage(moveRight2, xPosition, yPosition+y, null);						
				if (counter >= 7 && counter < 15)
					g.drawImage(moveRight1, xPosition, yPosition+y, null);				
				
			}
			else if (counter == 15)
			{
				g.drawImage(moveRight1, xPosition, yPosition+y, null);	
				counter = 0;
			}	
	}	
	
	public void animateSpriteUp(Graphics g)
	{
		int y = -25;
		counter++;
			if(counter < 15)
			{
				if (counter > -1 && counter < 7)
					g.drawImage(moveUp2, xPosition+2, yPosition+y, null);						
				if (counter >= 7 && counter < 15)
					g.drawImage(moveUp1, xPosition+2, yPosition+y, null);				
				
			}
			else if (counter == 15)
			{
				g.drawImage(moveUp1, xPosition+2, yPosition+y, null);	
				counter = 0;
			}	
	}	
	
	public void animateSpriteDown(Graphics g)
	{
		int y = -10;
		counter= counter + 1;
		
			if(counter < 15)//10
			{
				if (counter > -1 && counter < 7)
					g.drawImage(moveDown2, xPosition+2, yPosition+y, null);						
				if (counter >= 7 && counter < 15)
					g.drawImage(moveDown1, xPosition+2, yPosition+y, null);				
				
			}
			else if (counter == 15)
			{
				g.drawImage(moveDown1, xPosition+2, yPosition+y, null);	
				counter = 0;
				
			}	
	}
	
	public void animateSpriteEat(Graphics g)
	{
		int y=-25;g.drawImage(action1, xPosition+2, yPosition + y, null);	
			if(counter < 10)
			{
				if (counter > -1 && counter < 15)
					g.drawImage(action1, xPosition+2, yPosition + y, null);						
				
			}
			else if (counter == 25)
			{				
				g.drawImage(action1, xPosition+2, yPosition + y, null);	
				counter = 0;
				
			}			
	}
	
	public void animateSpriteEatLives(Graphics g, int xPos, int yPos)
	{
		g.drawImage(action1, xPos, yPos, null);	
			if(counter < 10)
			{
				if (counter > -1 && counter < 15)
					g.drawImage(action1, xPos, yPos, null);						
				
			}
			else if (counter == 25)
			{				
				g.drawImage(action1, xPos, yPos, null);	
				counter = 0;
				
			}			
	}
}
