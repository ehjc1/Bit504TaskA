import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics;



public class GameMain extends JPanel implements MouseListener
{
	public static final int ROWS = 3;     									//Constants for game 
	public static final int COLS = 3;  										// number of ROWS by COLS cell constants 
	public static final String TITLE = "Tic Tac Toe";	
	public static final int CELL_SIZE = 100;																					
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;				//constants for dimensions used for drawing cell width and height drawing canvas
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;																
	public static final int CELL_PADDING = CELL_SIZE / 6;    				//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;
	public Graphics g;
																			//*declare game object variables*/	
	public Board board;													// the game board 
	 	 
	public GameState currentState;											//Done: TODO: create the enumeration for the variable below (GameState currentState)
																			//Done: HINT all of the states you require are shown in the code within GameMain	
	
	public Player currentPlayer; 											// the current player
	
	public JLabel statusBar;       										// for displaying game status message
	
	/** Constructor to setup the UI and game components on the panel */
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public GameMain() 	
	{   	
		addMouseListener(this);													// TODO: This JPanel fires a MouseEvent on MouseClicked so add required event listener to 'this'.          
	    	      
		statusBar = new JLabel("Start Game");       							// Setup the status bar (JLabel) to display status message 
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
		statusBar.setOpaque(true);       
		statusBar.setBackground(Color.LIGHT_GRAY);  
		setLayout(new BorderLayout());       									//layout of the panel is in border layout
		add(statusBar, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));      // account for statusBar height in overall height
		
	}
																							
	public void createBoard(Graphics g) 												//TODO: call the method to initialise the game board
	{																		// TODO: Create a new instance of the game "Board"class.
		super.paintComponent(g);
		board = new Board(); 												//HINT check the variables above for the correct name
		board.paintBoard(g);
		currentPlayer = Player.Cross;
		currentState = GameState.Playing;
	}
				
	public static void main(String[] args) 
	{			// Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 			
			{																	//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);								
				frame.setSize(480, 320);										//TODO: create the new GameMain panel and add it to the frame
				frame.setResizable(false);
				frame.add(new GameMain());
		    	frame.setVisible(true);												
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		//TODO: set the default close operation of the frame to exit_on_close		
			    frame.setLocationRelativeTo(null);
			 		                				
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			
	         }
		 });
	}
//	
//	class DrawCanvas extends JPanel{
//	private static final long serialVersionUID = 1L;
//	}

	public void paintComponent(Graphics g) 	{											/** Custom painting codes on this JPanel */	 
		super.paintComponent(g);														//fill background and set colour to white
		setBackground(Color.WHITE);	
	//	board.paint(g);		
		createBoard(g);
		//ask the game board to paint itself		
		if (currentState == GameState.Playing) 											//set status bar message		
		{          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.Cross) 
			{   		
				statusBar.setForeground(Color.RED);  
				statusBar.setText("X's Turn!");											//TODO: use the status bar to display the message "X"'s Turn				
			} 			
			else 			
			{    						
				statusBar.setForeground(Color.BLUE);  
				statusBar.setText("O's Turn!"); 										//TODO: use the status bar to display the message "O"'s Turn			
			}  			
		} 				
		else if (currentState == GameState.Cross_won)
		{
			statusBar.setForeground(Color.RED);
			statusBar.setText("'X' Won! Click to play again.");
		}

		else if (currentState == GameState.Nought_won)
		{
			statusBar.setForeground(Color.RED);
			statusBar.setText("'O' Won! Click to play again.");
		}
		else if (currentState == GameState.Draw)
		{
			statusBar.setForeground(Color.RED);
			statusBar.setText("It's a Draw! Click to play again.");
		}
	}	
	
	 
	public void initGame()    															/** Initialise the game-board contents and the current status of GameState and Player) */
	{
		for (int row = 3; row < ROWS; ++row) {
			for (int col = 3; col < COLS; ++col)
			{

				board.cells[row][col].content = Player.Empty;           				// all cells empty
			}
		}
		 currentState = GameState.Playing;
		 currentPlayer = Player.Cross;
	}
		
		
	/**After each turn check to see if the current player hasWon by putting their symbol in that position,
	 * If they have the GameState is set to won for that player
	 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
	 *
	 */
	public void updateGame(Player thePlayer, int row, int col)
	{
		//check for win after play																// TODO: check which player has won and update the currentstate to the appropriate gamestate for the winner
		if(board.hasWon(thePlayer, row, col)) 													// TODO: set the currentstate to the draw gamestate
		{
			if (thePlayer == Player.Cross)
			{
				//otherwise no change to current state of playing
				currentState = GameState.Cross_won;
			}

		}
		else if(board.hasWon(thePlayer, row, col))
		{
			if (thePlayer == Player.Nought)
			{
				currentState = GameState.Nought_won;
			}

		}

		else if (board.isDraw(thePlayer, row, col))
		{
			currentState = GameState.Draw;
		}

		else
		{
			currentState = GameState.Playing;
		}
	}
	
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/
	
	public void mouseClicked(MouseEvent e) 
	{  
	    // get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// Get the row and column clicked             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;        
		
		if (currentState == GameState.Playing) {
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty)
			{
				board.cells[rowSelected][colSelected].content = currentPlayer;                    // move
				updateGame(currentPlayer, rowSelected, colSelected);                            // update currentState
			}
			if (currentPlayer == Player.Cross) 													// Switch player
			{
				currentPlayer =  Player.Nought;
			}
			else
			{
				currentPlayer = Player.Cross;
			}
		}
		else 
		{
			initGame();           																	// game over and restart
		}
	}
		//TODO: redraw the graphics on the UI          

		
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		//  Auto-generated, event not used
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		//  Auto-generated, event not used
	}
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// Auto-generated,event not used
	}
	@Override
	public void mouseExited(MouseEvent e) 
	{
		// Auto-generated, event not used
	}
}
	
	

