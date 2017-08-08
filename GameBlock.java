/** 
 * @author Matthew Testerman
 * November 22, 2012
 * Radford Univeristy
 *
 *This class resperesent a single block
 *
 */

public class GameBlock {
	
	//Global Variables
	private boolean visible, occupied;
	private int value;
	
	//Constructor
	public GameBlock(int value) 
	{
		this.value = value;		
		visible = true;
		occupied = false;		
	}
	
	//Hide a Block
	public void hide()
	{
		visible = false;
	}
	
	//Checks if block is hidden
	
	public boolean isVisible()
	{
		return visible;
	}	
	
	//Get Value
	public int getValue()
	{
		return value;
	}	
	
	//occupied boolean
	public void setOccupied()
	{
		occupied = true;
	}
	
	//Unoccupied boolean
	public void setUnOccupied()
	{
		occupied = false;
	}

	
	//Gets occupied boolean
	public boolean getOccupied()
	{
		return occupied;
	}

}
