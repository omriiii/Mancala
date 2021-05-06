import javax.swing.JOptionPane;

public class MancalaTest 
{
	/*
	TODO
		- Fix undo
		- Add JavaDoc documentation to all functions and stuff
	*/
	public static void main(String[] args)
	{
		MancalaGame mancala_game = new MancalaGame();
		MancalaUI mancala_ui = new MancalaUI(mancala_game);
	}
}
