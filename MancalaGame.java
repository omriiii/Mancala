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
	
	int pocketStones0;
	int pocketStones1;
	int pocketStones2;
	int pocketStones3;
	int pocketStones4;
	int pocketStones5;
	int pocketStones6;
	int pocketStones7;
	int pocketStones8;
	int pocketStones9;
	int pocketStones10;
	int pocketStones11;
	int pocketStones12;
	int pocketStones13;
	
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
		
	    player_actions = new Stack<PlayerAction>();
	    turn_flag = false;
	    is_over = true;
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
		
		//Saving the previous state of the board before the action is made
		pocketStones0 = pockets[0].getStones();
		pocketStones1 = pockets[1].getStones();
		pocketStones2 = pockets[2].getStones();
		pocketStones3 = pockets[3].getStones();
		pocketStones4 = pockets[4].getStones();
		pocketStones5 = pockets[5].getStones();
		pocketStones6 = pockets[6].getStones();
		pocketStones7 = pockets[7].getStones();
		pocketStones8 = pockets[8].getStones();
		pocketStones9 = pockets[9].getStones();
		pocketStones10 = pockets[10].getStones();
		pocketStones11 = pockets[11].getStones();
		pocketStones12 = pockets[12].getStones();
		pocketStones13 = pockets[13].getStones();
		
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
	
	public int undo() //may have to make undo return something that signals that the undo was successful (so that we can control the highlight)
	{
		if(!player_actions.isEmpty()) //&& game is not over? && undo is not called repeatedly
		{
			PlayerAction action = player_actions.pop(); //the latest action, dont think we need to do anything with it

			// Somehow return to previous state, possibly record the number of stones in each pocket before a move
			pockets[0].stones = pocketStones0; 
			pockets[1].stones = pocketStones1; 
			pockets[2].stones = pocketStones2; 
			pockets[3].stones = pocketStones3; 
			pockets[4].stones = pocketStones4; 
			pockets[5].stones = pocketStones5; 
			pockets[6].stones = pocketStones6; 
			pockets[7].stones = pocketStones7; 
			pockets[8].stones = pocketStones8; 
			pockets[9].stones = pocketStones9; 
			pockets[10].stones = pocketStones10; 
			pockets[11].stones = pocketStones11; 
			pockets[12].stones = pocketStones12; 
			pockets[13].stones = pocketStones13; 
			
			turn_flag = !turn_flag; //switch turn_flag to previous player
			return 1;
		}
		return 0;
	}

	
}
