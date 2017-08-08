import java.util.Random;
/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 *
 *This class draws the grid and handles collison conditions and updates the grid
 *
 */

public class GameGrid {

	//Global Variables
	private GameBlock[][] gameBlock;
	private int level, lives, score, heroX, heroY, troggleX, troggleY;
	private boolean toggle, munch, activeTroggle;

	//Setup
	public GameGrid(int level, int score, int heroX, int heroY)
	{
		activeTroggle = false;
		troggleX = 0; troggleY =0;
		lives = 3;
		munch = false;
		this.level = level;
		this.score = score;
		this.heroX = heroX;
		this.heroY = heroY;
		toggle = false;
		
	}

	//Create Grid
	public void createGrid()
	{
		gameBlock = new GameBlock[5][6];
		for (int i = 0; i < gameBlock.length; i ++)
		{
			for (int b = 0; b <gameBlock[i].length; b++)
			{	
				int value = createRandomNumber();				
				if (value == 0)
					value++;
				gameBlock[i][b] = new GameBlock(value);
			}

		}
	}

	//Returns a string
	public String toString()
	{
		String results = "";		
		for (int i = 0; i < gameBlock.length; i ++)
		{
			for (int b = 0; b <gameBlock[i].length; b++)
			{				
				results += gameBlock[i][b].getValue() + "\t" ;
			}
			results += "\n";
		}
		return results;
	}	


	//determines Range of Values
	public int determineRangeOfValues()
	{
		int value = 56;
		switch(level)
		{
		case 1:
			value = 12;
			break;			
		case 2:
			value = 24;
			break;
		case 3:
			value = 36;
			break;
		case 4:
			value = 48;
			break;
		case 5: 
			value = 60;
			break;
		case 6:
			value = 72;
			break;
		case 7:
			value = 84;
			break;
		case 8:
			value = 96;
			break;
		case 9: 
			value = 108;
			break;			
		case 10:
			value = 120;
			break;
		case 11:
			value = 132;
			break;
		case 12:
			value = 144;
			break;			

		}
		return value;	
	}

	//Creates a specific random number
	public int createRandomNumber()
	{	

		Random random = new Random();
		int value;		

		int[] multiple = {level * 1, level * 2, level * 3, level * 4, level * 5, level * 6, level * 7, level * 8, level * 9, level * 10, level*11, level*12};

		if (toggle)
		{
			value = random.nextInt(determineRangeOfValues()+1);
			toggle = false;
		}
		else		
		{
			value = multiple[random.nextInt(12)];	

			toggle = true;
		}		
		return value;
	}

	//Get hero X Position
	public int getHeroXP()
	{
		return heroX;
	}

	//Get hero Y Position	
	public int getHeroYP()
	{
		return heroY;
	}

	//Updates Hero Location
	public void updateHeroLocation(int heroX, int heroY)
	{
		this.heroX += heroX;
		this.heroY += heroY;
	}

	//Gets Score
	public int getScore()
	{
		return score;
	}

	//Sets Score
	public void setScore(int score)
	{
		this.score = score;
	}

	//Sets Lives
	public void setsLives(int lives)
	{
		this.lives = lives;
	}

	//Gets Lives
	public int getsLives()
	{
		return lives;
	}

	//Gets Level
	public int getLevel()
	{
		return level;
	}

	//Sets Level
	public void setLevel(int level)
	{
		this.level = level;
	}

	//Sets X position of hero
	public void setHeroXP(int value)
	{
		this.heroX = value;
	}

	//Sets Y position of hero
	public void setHeroYP(int value)
	{
		this.heroY = value;
	}	
	//Gets GameBlock
	public GameBlock[][] getGameBlocks()
	{
		return gameBlock;
	}

	//Method that advances to next level
	public void advanceToNextLevel()
	{
		level++;	
		heroX = 310;
		heroY = 250;
	}	

	//Check to see if at level 13+ (which is winner)
	public boolean hasWonGame()
	{
		boolean value = false;
		if (level > 12)
		{
			value = true;
		}		
		return value;
	}

	//Set munch & hides gameBlock number
	public void eatNumber()
	{
		gameBlock[getRowLocation(heroY)][getColumnLocation(heroX)].hide();
		munch = true;
	}

	//Gets munch
	public boolean getMunch()
	{		
		return munch;
	}

	//Reset grid
	public void reset()
	{
		level = 1;
		heroX = 310;
		heroY = 250;
		lives = 3;
		score = 0;		
		createGrid();
	}

	//Get row information; used in the getCurrentValue Method.	
	public int getRowLocation(int y)
	{
		int value = 0;
		switch(y)
		{
		case 150:
			value = 0;
			break;
		case 250:
			value = 1;
			break;
		case 350:
			value = 2;
			break;
		case 450:
			value = 3;
			break;			
		case 550:
			value = 4;
			break;	
		default:
			value = 9;
		}
		return value;
	}

	//Get Column information; used in the getCurrentValue Method.
	public int getColumnLocation(int x)
	{
		int value = 0;
		switch(x)
		{
		case 60:
			value = 0;
			break;
		case 185:
			value = 1;
			break;
		case 310:
			value = 2;
			break;
		case 435:
			value = 3;
			break;			
		case 560:
			value = 4;
			break;	
		case 685:
			value = 5;
			break;		
		default:
			value = 9;
		}		
		return value;
	}	

	//Returns the current value of the cell the muncher is standing on
	public int getCurrentValue()
	{
		int value;
		int row = getRowLocation(heroY);
		int column = getColumnLocation(heroX);	
		if (row == 9 || column == 9)
			value = 0;
		else
			value = gameBlock[row][column].getValue();
		return value;
	}

	//Check if win level.. uses calcMultiple
	public boolean wonLevel(String gameType)
	{
		boolean value = true;
		if(gameType == "Multiples")
		{
			for(int i = 0; i < gameBlock.length; i++)
				for(int b = 0; b <gameBlock[i].length; b++)
				{
					if(gameBlock[i][b].isVisible() && calcMultiple(gameBlock[i][b].getValue()))
					{
						value = false;
					}					
				}
		}		
		return value;
	}

	//used with wonLevel method to help determine if level is complete
	public boolean calcMultiple(int value)
	{
		return ((value % level) == 0);
	}

	//Checks if answer is right or wrong	
	public void checkBlock()
	{
		if (gameBlock[getRowLocation(heroY)][getColumnLocation(heroX)].isVisible())
		{
			if(calcMultiple(getCurrentValue()))
			{
				score += 50;			
			}
			else
			{			
				lives--;
			}
			eatNumber();
		}


	}
	
	//Activates Troggle
	public void activateTroggle()
	{
		Random random = new Random();
		int col = 0;
		int row = random.nextInt(4);
		if (row == 4 || row == 0)
			col = random.nextInt(5);

			
		troggleX = getXLocation(col);
		troggleY = getYLocation(row);
			
		activeTroggle = true;
	}
	
	//Gets Troggle X Location
	public int getTroggleX()
	{
		return troggleX;
	}
	//Gets Troggle Y Location	
	public int getTroggleY()
	{
		return troggleY;
	}	
	
	//Handles Collision with hero and Troggle
	public void ifHeroCollision()
	{
		int row = getRowLocation(heroY);
		int column = getColumnLocation(heroX);
		if (row == 9 || column == 9)
		{
			// do nothing -- prevent array out of bounds
		}
		else if (gameBlock[row][column].getOccupied())
		{
			lives--;
			gameBlock[row][column].setOccupied();
			activeTroggle = false;
		}
	}
	
	//Lets draw know if there are troggles on map to draw.
	public boolean isTroggleActive()
	{
		return activeTroggle;
	}
	
	public void setTroggleStartingLocation()
	{
		
	}

	
	//Get row information; used in the getCurrentValue Method.	
	public int getYLocation(int row)
	{
		int value = 0;
		switch(row)
		{
		case 0:
			value = 150;
			break;
		case 1:
			value = 250;
			break;
		case 2:
			value = 350;
			break;
		case 3:
			value = 450;
			break;			
		case 4:
			value = 550;
			break;	
		default:
			value = 9;
		}
		return value;
	}

	//Get Column information; used in the getCurrentValue Method.
	public int getXLocation(int column)
	{
		int value = 0;
		switch(column)
		{
		case 0:
			value = 60;
			break;
		case 1:
			value = 185;
			break;
		case 2:
			value = 310;
			break;
		case 3:
			value = 435;
			break;			
		case 4:
			value = 560;
			break;	
		case 5:
			value = 685;
			break;		
		default:
			value = 9;
		}		
		return value;
	}	
	
	public void moveTroggle( int x , int y)
	{
		troggleX += x;
		troggleY += y;
	
	}
	
	//sets Occupied
	public void setOccupied()
	{
				
	}	
}//End of Class
