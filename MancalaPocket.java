import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class MancalaPocket extends Pocket
{
	public MancalaPocket(int idx) 
	{
		super(idx);
		height = 130;
	}

	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		// Pocket Outline
		Graphics2D g2 = (Graphics2D) g;
		RoundRectangle2D.Double pocket_outline = new RoundRectangle2D.Double(x, y, width, height, 30, 30);
		
		
		// Pocket Stones
		int c_x = x+(width/2); // pocket center x coordinate
		int c_y = y+(height/2); // pocket center y coordinate
		ArrayList<Ellipse2D.Double> stones_elipses = new ArrayList<Ellipse2D.Double>();
		
		int stone_cnt = stones;
		int line_height = 12;
		int d_y = c_y+(Math.max(0,(stone_cnt/3)-1)*line_height);
		
		for(int i = 0; i < ((stones/3)+1); i++)
		{
			if(stone_cnt == 1)
			{
				stones_elipses.add(makeStoneElipse(c_x, d_y));
			}
			else if(stone_cnt == 2)
			{
				stones_elipses.add(makeStoneElipse(c_x-7, d_y));
				stones_elipses.add(makeStoneElipse(c_x+7, d_y));
			}
			else if(stone_cnt > 2)
			{
				stones_elipses.add(makeStoneElipse(c_x-15, d_y));
				stones_elipses.add(makeStoneElipse(c_x,    d_y));
				stones_elipses.add(makeStoneElipse(c_x+15, d_y));
				stone_cnt = stone_cnt - 3;
			}
			
			d_y = d_y - line_height;
		}
		
		
		String player_letter = "A";
		if(idx != 6) { player_letter = "B"; }
		int name_w = (int) g2.getFontMetrics().getStringBounds(player_letter, g2).getWidth();
		
		


		g2.setColor(Color.white);
		g2.fill(pocket_outline);
		g2.setColor(Color.black);
		g2.draw(pocket_outline);
		
		for(int i = 0; i < stones_elipses.size(); i++)
		{
			g2.setColor(Color.black);
			g2.fill(stones_elipses.get(i));
			g2.draw(stones_elipses.get(i));
		}
		
        g2.drawString(player_letter,c_x-(name_w/2),y+height-7);
	}
    
}