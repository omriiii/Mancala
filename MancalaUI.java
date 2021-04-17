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

public class MancalaUI 
{
	JFrame frame;
	MancalaGame game;
	
	public MancalaUI(MancalaGame game)
	{
		this.game = game;
		
		
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		
		// Mancala Board
		for(var i = 0; i < game.pockets.length; i++)
		{
			
			frame.add(new JLabel(game.pockets[i]));
		}

		// Buttons 
		JButton start_game_btn = new JButton("Start Game");
		start_game_btn.addActionListener(createStartGameListener());
		
		JButton undo_btn = new JButton("Undo");
		start_game_btn.addActionListener(undoListener());

		frame.add(start_game_btn);
		frame.add(undo_btn);
		
		// 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	public ActionListener createStartGameListener()
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				String result = (String) JOptionPane.showInputDialog(
			               frame,
			               "Enter the amount of stones per pockets", 
			               "Mancala Game Option",            
			               JOptionPane.PLAIN_MESSAGE,
			               null,            
			               null, 
			               "4");
				
				game.initializeBoard(Integer.parseInt(result));
			}
		};
	}

	public ActionListener undoListener()
	{
		return new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				game.undo();
			}
		};
	}
	
}
