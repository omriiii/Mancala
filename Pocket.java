import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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

		g2.draw(pocket_outline);
		g2.draw(front_windshield);
		
	}

	@Override
	public int getIconWidth() {
		return 100;
	}

	@Override
	public int getIconHeight() {
		return 100;
	}
}
