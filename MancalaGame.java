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
	Stack<ArrayList<Integer>> board_states;
	final int[] player_a_normal_pocket_idxs = {0, 1, 2, 3, 4, 5,};
	final int[] player_b_normal_pocket_idxs = {7, 8, 9, 10, 11, 12};
	final int[] normal_pocket_idxs = {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12};
	boolean turn_flag;
	boolean is_over;
	int undo_timeout_cntr;
	
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
		for(int i = 0; i < normal_pocket_idxs.length; i++)
		{
			pockets[normal_pocket_idxs[i]] = new Pocket(normal_pocket_idxs[i]);
		}
		pockets[6] = new MancalaPocket(6);
		pockets[13] = new MancalaPocket(13);
		
		board_states = new Stack<ArrayList<Integer>>();
	    turn_flag = false;
	    is_over = true;
	    undo_timeout_cntr = 0;
	}


	public void initializeBoard(int stones_per_pocket)
	{
		is_over = false;
		System.out.print("Starting up a game of mancala with " + stones_per_pocket + " stones per pocket!\n");
		for(int i = 0; i < pockets.length; i++)
		{
			pockets[i].stones = stones_per_pocket;
		}
		pockets[6].stones = 0;
		pockets[13].stones = 0;
		
	}
	
	public boolean doAction(int pocket_index)
	{
		if((turn_flag && pocket_index < 6) || (!turn_flag && pocket_index > 6)) { return false; }
		
		undo_timeout_cntr--;
		
		// Saving the previous state of the board before the action is made
		ArrayList<Integer> prior_board_state = new ArrayList<Integer>();
		for(int i = 0; i < pockets.length; i++)
		{
			prior_board_state.add(pockets[i].getStones());
		}
		board_states.push(prior_board_state);
		
		
		turn_flag = !turn_flag;
		int stones = pockets[pocket_index].stones;
		pockets[pocket_index].stones = 0;
		
		
		// Funny rules should probably be put around here
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
		for(int i = 0; i < normal_pocket_idxs.length/2; i++)
		{
			if(pockets[normal_pocket_idxs[i]].getStones() > 0)
			{
				player0_has_stones = true;
				break;
			}
		}
		
		for(int i = normal_pocket_idxs.length/2; i < normal_pocket_idxs.length; i++)
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
		for(int i = 0; i < normal_pocket_idxs.length/2; i++)
		{
			pockets[6].stones = pockets[6].stones + pockets[normal_pocket_idxs[i]].getStones();
			pockets[normal_pocket_idxs[i]].stones = 0;
		}
		
		for(int i = normal_pocket_idxs.length/2; i < normal_pocket_idxs.length; i++)
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
	
	public boolean undo() //may have to make undo return something that signals that the undo was successful (so that we can control the highlight)
	{
		if(board_states.isEmpty() || undo_timeout_cntr > 0) { return false; }
		
		undo_timeout_cntr = 2;
		ArrayList<Integer> board_state = board_states.pop();
		for(int i = 0; i < board_state.size(); i++)
		{
			pockets[i].setStones(board_state.get(i));
		}

		turn_flag = !turn_flag; //switch turn_flag to previous player
		return true;
	}

	
}
