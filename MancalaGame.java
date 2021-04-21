import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MancalaGame 
{
	
	Pocket[] pockets;
	Stack<PlayerAction> player_actions;
	final int[] normal_pocket_idx = {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12};
	
	public MancalaGame()
	{
		// 0  - A1
		// 1  - A2
		// ...
		// 5  - A6
		// 6  - Player A mancala pocket
		// 7  - B1
		// ...
		// 12 - B6
		// 13 - Player B mancala pocket
		pockets = new Pocket[14];
		for(var i = 0; i < normal_pocket_idx.length; i++)
		{
			pockets[normal_pocket_idx[i]] = new Pocket(normal_pocket_idx[i]);
		}
		pockets[6] = new MancalaPocket(6);
		pockets[13] = new MancalaPocket(13);
		
	    player_actions = new Stack<PlayerAction>();
	}


	public void initializeBoard(int stones_per_pocket)
	{
		System.out.print("Starting up a game of mancala with " + stones_per_pocket + " stones per pocket!\n");
		for(var i = 0; i < pockets.length; i++)
		{
			pockets[i].stones = stones_per_pocket;
		}
		pockets[6].stones = 0;
		pockets[13].stones = 0;
	}
	
	public void doAction(int pocket_index)
	{
		player_actions.add(new PlayerAction(pocket_index, pockets[pocket_index].stones));
		int stones = pockets[pocket_index].stones;
		pockets[pocket_index].stones = 0;
		
		while(stones > 0)
		{
			pocket_index=(pocket_index+1)%pockets.length;
			pockets[pocket_index].stones++;
			stones--;
		}
	}
	
	public void undo()
	{
		if(!player_actions.isEmpty())
		{
			PlayerAction action = player_actions.pop();
			// Do stuff here
		}
	}

	
}
