import javax.swing.JOptionPane;

public class MancalaTest 
{
	/*
	  
	TODO
		- Add all the funny rules for mancala...
			+ skip putting stone in enemy mancala, 
			+ putting last stone in your own mancala gives you a free turn
			+ dropping a stone in an empty pokcet on your side lets you capture both that stone and the stones across on the enemy's side
		- Add JavaDoc documentation to all functions and stuff
		
	*/
	public static void main(String[] args)
	{
		MancalaGame mancala_game = new MancalaGame();
		MancalaUI mancala_ui = new MancalaUI(mancala_game);
	}
}
