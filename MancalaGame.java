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
	final int[] player_a_normal_pocket_idxs = {0, 1, 2, 3, 4, 5,};
	final int[] player_b_normal_pocket_idxs = {7, 8, 9, 10, 11, 12};
	final int[] normal_pocket_idxs = {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12};
	boolean turn_flag;
	boolean is_over;
	
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
		for(var i = 0; i < normal_pocket_idxs.length; i++)
		{
			pockets[normal_pocket_idxs[i]] = new Pocket(normal_pocket_idxs[i]);
		}
		pockets[6] = new MancalaPocket(6);
		pockets[13] = new MancalaPocket(13);
		
	    player_actions = new Stack<PlayerAction>();
	    turn_flag = false;
	    is_over = true;
	}


	public void initializeBoard(int stones_per_pocket)
	{
		is_over = false;
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
		if(is_over) { return true; }
		
		boolean player0_has_stones = false;
		boolean player1_has_stones = false;
		for(var i = 0; i < normal_pocket_idxs.length/2; i++)
		{
			if(pockets[normal_pocket_idxs[i]].getStones() > 0)
			{
				player0_has_stones = true;
				break;
			}
		}
		
		for(var i = normal_pocket_idxs.length/2; i < normal_pocket_idxs.length; i++)
		{
			if(pockets[normal_pocket_idxs[i]].getStones() > 0)
			{
				player1_has_stones = true;
				break;
			}
		}
		
		is_over = ((!player0_has_stones) || (!player1_has_stones));
		return is_over;
	}
	
	// Move all stones to their respective MancalaPocket
	public void wrapUp()
	{
		for(var i = 0; i < normal_pocket_idxs.length/2; i++)
		{
			pockets[6].stones = pockets[6].stones + pockets[normal_pocket_idxs[i]].getStones();
			pockets[normal_pocket_idxs[i]].stones = 0;
		}
		
		for(var i = normal_pocket_idxs.length/2; i < normal_pocket_idxs.length; i++)
		{
			pockets[13].stones = pockets[13].stones + pockets[normal_pocket_idxs[i]].getStones();
			pockets[normal_pocket_idxs[i]].stones = 0;
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
