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
	private int width = 100;
	private int height = 100;
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double pocket_outline = new Ellipse2D.Double(x, y, x+circle_r, y+circle_r);
		
		Line2D.Double front_windshield = new Line2D.Double(new Point2D.Double(x, y), new Point2D.Double(x+5, y+5));


		int pocket_center_x = x+(circle_r/2);
		int pocket_center_y = y+(circle_r/2);
		int stone_r = 10;
		ArrayList<Ellipse2D.Double> stones_elipses = new ArrayList<Ellipse2D.Double>();
		switch(stones)
		{
			case 1:
				stones_elipses.add(new Ellipse2D.Double(pocket_center_x-(circle_r/2), pocket_center_y-(circle_r/2), pocket_center_x+(circle_r/2), pocket_center_y+(circle_r/2)));
				break;
		}
		
		g2.draw(pocket_outline);
		
		for(int i = 0; i < stones_elipses.size(); i++)
		{
			g2.setColor(Color.black);
			g2.fill(stones_elipses.get(i));
			g2.draw(stones_elipses.get(i));
		}
	}

	@Override
	public int getIconWidth() 
	{
		return width;
	}

	@Override
	public int getIconHeight() {
		return width;
	}
}
