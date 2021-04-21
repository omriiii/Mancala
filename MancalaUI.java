import java.awt.FlowLayout;
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
		
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		
		// Mancala Board
		for(var i = 0; i < game.pockets.length; i++)
		{
			pocket_labels.add(new JLabel(game.pockets[i]));
			frame.add(pocket_labels.get(i));
		}

		for(var i = 0; i < game.normal_pocket_idx.length; i++)
		{
			pocket_labels.get(game.normal_pocket_idx[i]).addMouseListener(createPocketListener());
		}

		// Buttons 
		JButton start_game_btn = new JButton("Start Game");
		start_game_btn.addActionListener(createStartGameListener());
		
		JButton undo_btn = new JButton("Undo");
		start_game_btn.addActionListener(undoListener());

		frame.add(start_game_btn);
		frame.add(undo_btn);
		
		// Display Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
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
				game.doAction(((Pocket)((JLabel)e.getSource()).getIcon()).getIdx());
				repaintBoard();
				// Calculate if the game is over here!
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
	
}
