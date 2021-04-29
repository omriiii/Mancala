import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MancalaUI 
{
	JFrame frame;
	MancalaGame game;
	
	ArrayList<JLabel> pocket_labels;
	
	public MancalaUI(MancalaGame game)
	{
		this.game = game;
		pocket_labels = new ArrayList<JLabel>();
		
		frame = new JFrame("Mancala");

		// Mancala Board
		addBoardToFrame(game);

		// Buttons 
		JButton start_game_btn = new JButton("Start Game");
		start_game_btn.addActionListener(createStartGameListener());
		start_game_btn.setSize(new Dimension(100, 40));
		
		
		JButton undo_btn = new JButton("Undo");
		undo_btn.addActionListener(undoListener());
		undo_btn.setSize(new Dimension(100, 40));
		
		
		addButtonToFrame(start_game_btn, 190, 155);
		addButtonToFrame(undo_btn, 300, 155);
		
		frame.add(new JLabel(""));
		
		// Display Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(590, 240);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void repaintBoard()
	{
		for(int i = 0; i < pocket_labels.size(); i++)
		{
			pocket_labels.get(i).repaint();
		}
	}

	public MouseAdapter createPocketListener() 
	{
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if(!game.doAction(((Pocket)((JLabel)e.getSource()).getIcon()).getIdx()))
				{
					JOptionPane.showMessageDialog(frame, "That's not your pocket!");
					return;
				}
				
				// Calculate if the game is over here!
				if(game.isOver())
				{
					game.wrapUp(); // Move all remaining stones to their respective MancalaPocket
					int winner_index = game.getWinner();
					if (winner_index >= 0)
					{
						JOptionPane.showMessageDialog(frame, "Game over!\nPlayer " + winner_index + " has won!");
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Game over!\nTie.");
					}
				}
				
				updatePocketHighlights();
				repaintBoard();
			}
		};
	}
	
	public ActionListener createStartGameListener()
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				String result = (String) JOptionPane.showInputDialog(
			               frame,
			               "Enter the amount of stones per pocket", 
			               "Mancala Game Option",            
			               JOptionPane.PLAIN_MESSAGE,
			               null,            
			               null, 
			               "4");
				
				game.initializeBoard(Integer.parseInt(result));
				updatePocketHighlights();
				repaintBoard();
			}
		};
	}

	public ActionListener undoListener()
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				game.undo();
				repaintBoard();
			}
		};
	}
	
	void updatePocketHighlights()
	{
		
		for(int i = 0; i < game.normal_pocket_idxs.length; i++)
		{
			game.pockets[game.normal_pocket_idxs[i]].setHighlight(false);
		}
		
		if(game.isOver()) { return; }
		
		int[] idxs = game.player_a_normal_pocket_idxs;
		if(game.turn_flag)
		{
			idxs = game.player_b_normal_pocket_idxs;
		}
		
		for(int i = 0; i < idxs.length; i++)
		{
			game.pockets[idxs[i]].setHighlight(true);
		}
	}
	
	// Helper Methods
	void addBoardToFrame(MancalaGame game)
	{
		int x;
		int y;
		
		for(var i = 0; i < game.pockets.length; i++)
		{
			pocket_labels.add(new JLabel(game.pockets[i]));
			
			//frame.add(pocket_labels.get(i));
			
			
			//c = new GridBagConstraints();
			//c.anchor = c.FIRST_LINE_START;
			//frame.add(pocket_labels.get(i), c);
		}
		
		int[] upper_idxs = {12, 11, 10, 9, 8, 7};
		int[] lower_idxs = {0, 1, 2, 3, 4, 5};
		
		addPocketToFrame(pocket_labels.get(13), 10, 10);
		addPocketToFrame(pocket_labels.get(6), 10+(70*(upper_idxs.length+1)), 10);
		
		int cntr = 0;
		for(int i = 0; i < upper_idxs.length; i++)
		{
			addPocketToFrame(pocket_labels.get(upper_idxs[i]), 10+(70*(i+1)), 10);
			addPocketToFrame(pocket_labels.get(lower_idxs[i]), 10+(70*(i+1)), 80);
		}
		
		for(var i = 0; i < game.normal_pocket_idxs.length; i++)
		{
			pocket_labels.get(game.normal_pocket_idxs[i]).addMouseListener(createPocketListener());
		}
	}
	
	void addPocketToFrame(JLabel l, int x, int y)
	{
		l.setBounds(x, y, ((Pocket) l.getIcon()).getIconWidth()+1, ((Pocket) l.getIcon()).getIconHeight()+1);
		frame.add(l);
	}
	
	
	void addButtonToFrame(JButton b, int x, int y)
	{
		b.setBounds(x, y, b.getWidth(), b.getHeight());
		frame.add(b);
	}
	
	
}
