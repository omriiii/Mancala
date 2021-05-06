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
	ArrayList<Integer> prior_board_state = null;
	boolean last_turn;
	
	final int[] player_a_normal_pocket_idxs = {0, 1, 2, 3, 4, 5,};
	final int[] player_b_normal_pocket_idxs = {12, 11, 10, 9, 8, 7};
	final int[] normal_pocket_idxs = {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12};
	boolean turn_flag;
	boolean is_over;
	int undo_timeout_cntr;
	int undo_cntr;
	
	/**
	 * Constructor for the MancalaGame object
	 */
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
		
	    turn_flag = false;
	    is_over = true;
	    undo_timeout_cntr = 0;
	    undo_cntr = 3;
	}


	/**
	 * Initializes the board with the proper parameters to start the game
	 * @param stones_per_pocket - Amount of stones per pocket to start the game with
	 */
	public void initializeBoard(int stones_per_pocket)
	{
		is_over = false;
		System.out.print("Starting up a game of mancala with " + stones_per_pocket + " stones per pocket!\n");
		for(int i = 0; i < pockets.length; i++)
		{
			pockets[i].setStones(stones_per_pocket);
		}
		pockets[6].setStones(0);
		pockets[13].setStones(0);
		
	}
	
	/**
	 * Makes a move of moving the stones in the given pocket index
	 * @param pocket_index - Pocket index the player wishes to move the stones for
	 * @return whether or not the move abides by the rules of the game (0=ok, 1=pokcet empty, 2=incorrect player's pocket)
	 */
	public int doAction(int pocket_index)
	{
		// turn_flag == false, player A
		// turn_flag == true,  player B

		if((turn_flag && pocket_index < 6) || (!turn_flag && pocket_index > 6)) { return 2; }
		if(pockets[pocket_index].getStones() == 0) { return 1; }
		
		undo_timeout_cntr--;
		
		// Saving the previous state of the board before the action is made
		prior_board_state = new ArrayList<Integer>();
		last_turn = !turn_flag;
		for(int i = 0; i < pockets.length; i++)
		{
			prior_board_state.add(pockets[i].getStones());
		}
		
		int stones = pockets[pocket_index].getStones();
		int[] valid_pockets = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		if(turn_flag)
		{
			valid_pockets = new int[]{0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13};
		}
		
		int i = 0;
		while(pocket_index != valid_pockets[i]) { i++; }
		
		pockets[pocket_index].setStones(0);

		while(stones > 0)
		{
			i = (i+1)%valid_pockets.length;
			pocket_index = valid_pockets[i];
			pockets[pocket_index].setStones(pockets[pocket_index].getStones()+1);
			stones--;
		}
		
		// Check if putting stone in an empty pocket on your side
		if((pockets[pocket_index].getStones() == 1) && ((!turn_flag && (pocket_index < 6)) || (turn_flag && (pocket_index > 6) && pocket_index != 13)))
		{
			int stolen_stones = 1;
			pockets[pocket_index].setStones(0);

			int adj_pocket_idx = Math.abs(pocket_index-12);
			stolen_stones = stolen_stones + pockets[adj_pocket_idx].getStones();
			pockets[adj_pocket_idx].setStones(0);
			
			if(!turn_flag) { pockets[6].setStones(pockets[6].getStones()+stolen_stones); }
			else { pockets[13].setStones(pockets[13].getStones()+stolen_stones); }
		}
			
		// Check if putting stone in my own Mancala Pocket
		if((!turn_flag && pocket_index == 6) || (turn_flag && pocket_index == 13))
		{
			turn_flag = !turn_flag; // flip the flag twice
		}
		
		turn_flag = !turn_flag;
		
		return 0;
	}
	
	/**
	 * Checks if the game is over and if not, verifies if it's true.
	 * @return if the game is over.
	 */
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
	
	/**
	 * Move all stones to their respective MancalaPocket once the game is over.
	 */
	public void wrapUp()
	{
		for(int i = 0; i < normal_pocket_idxs.length/2; i++)
		{
			pockets[6].setStones(pockets[6].getStones() + pockets[normal_pocket_idxs[i]].getStones());
			pockets[normal_pocket_idxs[i]].setStones(0);
		}
		
		for(int i = normal_pocket_idxs.length/2; i < normal_pocket_idxs.length; i++)
		{
			pockets[13].setStones(pockets[13].getStones() + pockets[normal_pocket_idxs[i]].getStones());
			pockets[normal_pocket_idxs[i]].setStones(0);
		}
	}
	
	/**
	 * Gets the winner of the match by comapring the respective player's MancalaPockets
	 * @return 0 if player 1 won, 1 if player 2 won, -1 if it's a tie.
	 */
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
	
	
	/**
	 * Undos the player's last turn given that it's within the rules of the match
	 * @return if the undo was successful and abides in the rule of the game (true/false)
	 */
	public boolean undo() 
	{
		if(undo_timeout_cntr <= 0)
		{
			undo_timeout_cntr = 2;
			undo_cntr = 3;
		}
		
		if(prior_board_state == null || (undo_cntr == 0)) { return false; }
		
		undo_timeout_cntr = 2;
		undo_cntr--;
		for(int i = 0; i < prior_board_state.size(); i++)
		{
			pockets[i].setStones(prior_board_state.get(i));
		}
		turn_flag = last_turn;
		prior_board_state = null;

		turn_flag = !turn_flag; //switch turn_flag to previous player
		return true;
	}
	
	/**
	 * Sets the pocket's background color getter object
	 * @param c - CustomColor object that contains the wanted pocket background color
	 */
	public void setPocketColors(CustomColors c)
	{
		for(int i = 0; i < pockets.length; i++)
		{
			pockets[i].setPocketBackgroundColor(c);
		}
	}

	
}
