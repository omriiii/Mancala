import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Icon;

public class Pocket implements Icon
{
	int stones = 0;
	
	private int circle_r = 50;
	private int width = 60;
	private int height = 60;
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double pocket_outline = new Ellipse2D.Double(x, y, circle_r, circle_r);
		

		
		int c_x = x+(circle_r/2); // pocket center x coordinate
		int c_y = y+(circle_r/2); // pocket center y coordinate
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

		g2.draw(pocket_outline);
		for(int i = 0; i < stones_elipses.size(); i++)
		{
			g2.setColor(Color.black);
			g2.fill(stones_elipses.get(i));
			g2.draw(stones_elipses.get(i));
		}
	}
	
	public Ellipse2D.Double makeStoneElipse(int x, int y)
	{
		return new Ellipse2D.Double(x-4, y-4, 7, 7);
	}

	@Override
	public int getIconWidth() 
	{
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
}
