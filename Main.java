
public class Main 
{
	//  TODO
	//  - Implement paintRocks for Pocket/MancalaPocket
	//  - Make sure the game board is being drawn correctly
	//  - Add some method that checks if the game is over and calculates who wins
	//  - Implement undo functionality
	//  - Should probably move Pocket and MancalaPocket to be private classes of MancalaGame...
	
	public static void main(String[] args)
	{
		MancalaGame mancala_game = new MancalaGame();
		MancalaUI mancala_ui = new MancalaUI(mancala_game);
	}
}
