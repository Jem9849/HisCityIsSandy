import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int LEVIBLOCK = 4;
  public static final int CLEARBLOCK = 5;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[7];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[LEVIBLOCK] = "Leviblock";
    names[CLEARBLOCK] = "Clearblock";
    
    //1. Add code to initialize the data member grid with same dimensions
    grid = new int[numRows][numCols];
    
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
	 grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	  for (int i = 0; i < grid.length; i++)
	  {
		  for (int eye = 0; eye < grid[0].length; eye++)
		  {
			  if (grid[i][eye] == EMPTY)
			  {
				  display.setColor(i, eye, Color.BLACK);
			  }
			  
			  else if (grid[i][eye] == METAL)
			  {
				  display.setColor(i, eye, Color.GRAY);
			  }
			  
			  else if (grid[i][eye] == SAND)
			  {
				  display.setColor(i, eye, Color.yellow);
			  }
			  
			  else if (grid[i][eye] == WATER)
			  {
				  display.setColor(i, eye, Color.BLUE);
			  }
			  
			  else if (grid[i][eye] == LEVIBLOCK)
			  {
				  display.setColor(i, eye, Color.ORANGE);
			  }
			  
			  else if (grid[i][eye] == CLEARBLOCK)
			  {
				  display.setColor(i, eye, Color.WHITE);
			  }
		  }
	  }
    
  }
  
  public boolean checkBounds(int row, int col)
  {
	  if (row < grid.length && row > 0 && col < grid.length && col > 0)
	  {
		  return true;
	  }
	  
	  else
	  {
		  return false;
	  }
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
	  
	  int randomR = (int) (Math.random() * grid.length - 1);
	  int randomC = (int) (Math.random() * grid[0].length - 1);
	  
	  if (checkBounds(randomR, randomC) == true && 
			  grid[randomR][randomC] == SAND &&
			  grid[randomR + 1][randomC] == EMPTY)
	  {
		  grid[randomR][randomC] = EMPTY;
		  grid[randomR + 1][randomC] = SAND;
	  }
	  
	  else if (checkBounds(randomR, randomC) == true && grid[randomR][randomC] == WATER)
	  {
		  int random = (int) (Math.random() * 3);
		  
		  switch (random)
		  {
		  	 case 0:
		  	 {
		  		 if(checkBounds(randomR, randomC) == true && grid[randomR + 1][randomC] == EMPTY )
		  		 {
		  			 grid[randomR][randomC] = EMPTY;
		  			 grid[randomR + 1][randomC] = WATER;
		  		 }
		  		 break;
		  	 }
			   
			  case 1:
			  {
				  if (checkBounds(randomR, randomC) == true && grid[randomR][randomC - 1] == EMPTY)
				  {
					  grid[randomR][randomC] = EMPTY;
					  grid[randomR][randomC - 1] = WATER;
				  }
				 break;
			  }
			  
			  case 2:
			  {
				  if (checkBounds(randomR, randomC) == true && grid[randomR][randomC + 1] == EMPTY)
				  {
					  grid[randomR][randomC] = EMPTY;
					  grid[randomR][randomC + 1] = WATER;
				  }
				  break;
			  }
			  
			  default:
				  System.out.println("Something is broken.");
				  break;
		  	}
	  	}
		  
		else if (checkBounds(randomR, randomC) == true && grid[randomR][randomC] == SAND && 
				grid[randomR + 1][randomC] == WATER)
		{
			grid[randomR + 1][randomC] = EMPTY;
			grid[randomR][randomC] = SAND;
		}
	  	
		else if (checkBounds(randomR, randomC) == true && 
				checkBounds(randomR + 1, randomC + 1) == true &&
				checkBounds(randomR + 1, randomC - 1) == true &&
				checkBounds(randomR - 1, randomC + 1) == true &&
				checkBounds(randomR - 1, randomC - 1) == true &&
				checkBounds(randomR + 1, randomC) == true &&
				checkBounds(randomR - 1, randomC) == true)
		{
			if (grid[randomR][randomC] == LEVIBLOCK &&
				grid[randomR - 1][randomC + 1] == EMPTY &&
				grid[randomR - 1][randomC - 1] == EMPTY &&
				grid[randomR + 1][randomC + 1] == METAL || 
				grid[randomR + 1][randomC - 1] == METAL ||
				grid[randomR + 1][randomC] == METAL ||
				grid[randomR - 1][randomC] == METAL)
			{
				if (checkBounds(randomR, randomC) == true &&
						grid[randomR][randomC] == LEVIBLOCK &&
						grid[randomR + 1][randomC + 1] != EMPTY)
				{
					grid[randomR][randomC] = EMPTY;
					grid[randomR - 1][randomC + 1] = METAL;
					grid[randomR + 1][randomC + 1] = EMPTY;
				}
				
				if (checkBounds(randomR, randomC) == true && 
						grid[randomR][randomC] == LEVIBLOCK &&
						grid[randomR + 1][randomC - 1] != EMPTY)
				{
					grid[randomR][randomC] = EMPTY;
					grid[randomR - 1][randomC - 1] = METAL;
					grid[randomR + 1][randomC - 1] = EMPTY;
				}
				
				if (checkBounds(randomR, randomC) == true &&
						grid[randomR][randomC] == LEVIBLOCK &&
						grid[randomR + 1][randomC] == METAL)
				{
					grid[randomR][randomC] = METAL;
					grid[randomR + 1][randomC] = EMPTY;
				}
				
				if (checkBounds(randomR, randomC) == true &&
						checkBounds(randomR - 2, randomC) == true && 
						grid[randomR][randomC] == LEVIBLOCK &&
						grid[randomR - 1][randomC] == METAL)
				{
					grid[randomR][randomC] = EMPTY;
					grid[randomR - 1][randomC] = EMPTY;
					grid[randomR - 2][randomC] = METAL;
				}
			}
		}
	  
	  	if (checkBounds(randomR, randomC) == true &&
	  			grid[randomR + 1][randomC] == EMPTY
	  			&& grid[randomR][randomC] == CLEARBLOCK)
	  	{
	  		grid[randomR][randomC] = EMPTY;
	  		grid[randomR + 1][randomC] = CLEARBLOCK;
	  	}
	  
		if (checkBounds(randomR, randomC) == true &&
				grid[randomR + 1][randomC] == EMPTY &&
				grid[randomR][randomC] == LEVIBLOCK)
		{
			grid[randomR][randomC] = EMPTY;
			grid[randomR + 1][randomC] = LEVIBLOCK;
		}
		
		if (checkBounds(randomR, randomC) == true &&
				grid[randomR][randomC] == CLEARBLOCK)
		{
			if (grid[randomR][randomC] != EMPTY)
			{
				for (int i = 0; i < grid.length; i++)
				{
					for (int b = 0; b < grid[i].length; b++)
					{
						grid[i][b] = EMPTY;
					}
				}
			}
		}
	}
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
