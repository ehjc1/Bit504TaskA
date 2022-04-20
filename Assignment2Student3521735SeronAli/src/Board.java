import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;


public class Board {
	// grid line width
	public static final int GRID_WIDTH = 8;
	// grid line half width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;
	
	//2D array of ROWS-by-COLS Cell instances
	Cell [][] cells;
	
	/** Constructor to create the game board */
	public Board() {
		cells = new Cell [GameMain.ROWS][GameMain.COLS];
		
		for (int row = 0; row < GameMain.ROWS; row++) {
			for (int col = 0; col < GameMain.COLS; col++) {
				cells[row][col] = new Cell(row, col);
				
			}
		}
	}

	 /** Return true if it is a draw (i.e., no more EMPTY cells) */ 
	public boolean isDraw(Player Value, int playerRow, int playerCol) {
		 
		// TODO: Check whether the game has ended in a draw. 
		// Hint: Use a nested loop (see the constructor for an example). Check whether any of the cells content in the board grid are Player.Empty. If they are, it is not a draw.
		// Hint: Return false if it is not a draw, return true if there are no empty positions left
		   
		if(cells[playerRow][0].content != Value && cells[playerRow][1].content != Value 								// check if player has 3-in-that-row
				&& cells[playerRow][2].content != Value )
			return true;
		
		if(cells[playerCol][0].content != Value && cells[playerCol][1].content != Value								// TODO: Check if the player has 3 in the playerCol.
				&& cells[playerCol][2].content != Value )															 		// Hint: Use the row code above as a starting point, remember that it goes cells[row][column]
			return true;
		
	
		if( cells[0][0].content != Value && cells[1][1].content != Value && cells[2][2].content != Value)			 // 3-in-the-diagonal
			return true;
		 
		
		if( cells[2][2].content != Value && cells[1][1].content != Value && cells[0][0].content != Value)			// TODO: Check the diagonal in the other direction
			return true;

		
		else 
		{
			for (int row = 0; row < GameMain.ROWS; row++) {
				for (int col = 0; col < GameMain.COLS; col++) {
					if (cells[row][col].content == Player.Empty) {
						return true;
					}
			
				}
			}
//				{
////				return GameState.Draw != null;
//				}
		}
		return false;
	}
	
	/** Return true if the current player "thePlayer" has won after making their move  */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		 
		if(cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer 								// check if player has 3-in-that-row
				&& cells[playerRow][2].content == thePlayer )
			return true; 
		
		if(cells[playerCol][0].content == thePlayer && cells[playerCol][1].content == thePlayer 								// TODO: Check if the player has 3 in the playerCol.
				&& cells[playerCol][2].content == thePlayer)															 		// Hint: Use the row code above as a starting point, remember that it goes cells[row][column]
			return true; 		
		
	
		if( cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer)			 // 3-in-the-diagonal
			return true;
		 
		
		if( cells[2][2].content == thePlayer && cells[1][1].content == thePlayer && cells[0][0].content == thePlayer)			// TODO: Check the diagonal in the other direction
			return true;

		else
		{
			return false;																										//no winner, keep playing
		}
		
	}
	
	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the
	 * Cells to paint themselves into the grid
	 */
	public void paintBoard(Graphics g)
	{
		// Draws board/grid
		g.setColor(Color.gray);
		for (int row = 0; row < GameMain.ROWS; ++row) 
		{          
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,                
					GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,                
					GRID_WIDTH, GRID_WIDTH);       
		}
		for (int col = 0; col < GameMain.COLS; ++col)
		{          
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 8,                
					GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,                
					GRID_WIDTH, GRID_WIDTH);
		}
																							
		for (int row = 0; row < GameMain.ROWS; ++row) 	//Draw the cells
		{          	
			for (int col = 0; col < GameMain.COLS; ++col)
			{  
				cells[row][col].paint(g);
			}
		}
	}


}
