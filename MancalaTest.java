
public class MancalaTest 
{
	//  TODO
	//  - Implement paintRocks for Pocket/MancalaPocket
	//  - Make sure the game board is being drawn correctly
	//  - Implement undo functionality
	//	- Add Board Style menu at the beginning and implement with Strategy pattern (Should probably do something simple like color of MancalaPocket)
	//  - Add "player x turn" functionality (the next 2 tasks are dependent on it)
	//  - Add proper limitation to undo per turn
	//	- Add all the funny rules for mancala (eg. skip putting stone in enemy mancala, putting last stone in your own mancala gives you a free turn, dropping a stone in an empty pokcet on your side lets you capture both that stone and the stones across on the enemy's side)
	//  NOT MANDATORY BUT PROBABLY GOOD PRACTICE
	//  - Should probably move Pocket and MancalaPocket to be private classes of MancalaGame...
	// 	- Crate Getters and Setters for Pocket object and private instance variables 
	
	public static void main(String[] args)
	{
		MancalaGame mancala_game = new MancalaGame();
		MancalaUI mancala_ui = new MancalaUI(mancala_game);
	}
}
