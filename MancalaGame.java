import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MancalaGame 
{
	//  TODO
	//	- Add undo method (adding trigger to button to pop from stack. implementation can come later)
	//  - Add initialization method (adding trigger to button to give user prompt for a number and initialize each pocket with that amount of stones)
	//  - Draw the game board! (Add paintIcon methods to Pocket/MancalaPocket, add them to the JFrame and make sure theyre drawn correctly)
	//  - Implement paintRocks for Pocket/MancalaPocket
	
	public MancalaGame()
	{
		// 0  - A1
		// 1  - A2
		// ...
		// 5  - A6
		// 6  - Player A mancala pocket
		// 7  - B1
		// ...
		// 13 - B6
		// 14 - Player B mancala pocket
		Pocket[] pockets = new Pocket[14];
		
	    Stack<PlayerAction> player_actions = new Stack<PlayerAction>();
	}
	
	public void run()
	{
		JFrame frame = new JFrame();
		JButton start_game_btn = new JButton("Start Game");
		
		JButton undo_btn = new JButton("Undo");

		frame.setLayout(new FlowLayout());
		frame.add(start_game_btn);
		frame.add(undo_btn);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

}
