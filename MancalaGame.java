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
	boolean turn_flag;
	
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
	    turn_flag = false;
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
	
	public boolean doAction(int pocket_index)
	{
		if((turn_flag && pocket_index < 6) || (!turn_flag && pocket_index > 6)) { return false; }
		
		turn_flag = !turn_flag;
		player_actions.add(new PlayerAction(pocket_index, pockets[pocket_index].stones));
		int stones = pockets[pocket_index].stones;
		pockets[pocket_index].stones = 0;
		
		while(stones > 0)
		{
			pocket_index=(pocket_index+1)%pockets.length;
			pockets[pocket_index].stones++;
			stones--;
		}
		
		return true;
	}
	
	public boolean isOver()
	{
		boolean player0_has_rocks = false;
		boolean player1_has_rocks = false;
		for(var i = 0; i < normal_pocket_idx.length/2; i++)
		{
			if(pockets[normal_pocket_idx[i]].getStones() > 0)
			{
				player0_has_rocks = true;
				break;
			}
		}
		
		for(var i = normal_pocket_idx.length/2; i < normal_pocket_idx.length; i++)
		{
			if(pockets[normal_pocket_idx[i]].getStones() > 0)
			{
				player1_has_rocks = true;
				break;
			}
		}
		
		return ((!player0_has_rocks) || (!player1_has_rocks));
	}
	
	// Move all stones to their respective MancalaPocket
	public void wrapUp()
	{
		for(var i = 0; i < normal_pocket_idx.length/2; i++)
		{
			pockets[6].stones = pockets[6].stones + pockets[normal_pocket_idx[i]].getStones();
			pockets[normal_pocket_idx[i]].stones = 0;
		}
		
		for(var i = normal_pocket_idx.length/2; i < normal_pocket_idx.length; i++)
		{
			pockets[13].stones = pockets[13].stones + pockets[normal_pocket_idx[i]].getStones();
			pockets[normal_pocket_idx[i]].stones = 0;
		}
	}
	
	public int getWinner()
	{
		if(pockets[6].getStones() < pockets[13].getStones())
		{
			return 1;
		}
		else if(pockets[6].getStones() > pockets[13].getStones())
		{
			return 0;
		}
		return -1; // It's a tie.
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
