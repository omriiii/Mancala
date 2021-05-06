public class MancalaTest 
{
	/**
	 * Starts a Mancala Game
	 * @param args - Please leave blank!
	 */
	public static void main(String[] args)
	{
		MancalaGame mancala_game = new MancalaGame();
		MancalaUI mancala_ui = new MancalaUI(mancala_game);
		mancala_ui.run();
	}
}
