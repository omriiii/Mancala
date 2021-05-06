import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Icon;

public class Pocket implements Icon
{
	protected int stones = 0;
	protected int idx;
	
	protected CustomColors bg_color;
	protected int width = 60;
	protected int height = 60;
	private boolean highlight = false;
	
	/**
	 * Creates a pocket with a given indentifier index
	 * @param idx - The index identifier of the pocket
	 */
	public Pocket(int idx)
	{
		this.idx = idx;
	}
	
	/**
	 * Paints the pocket with the approrpriate amount of stones in the given x and y coordinate
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		// Pocket Outline
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double pocket_outline = new Ellipse2D.Double(x, y, width, height);
		
		
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
		
		// Draw Pocket Label
		String pocket_name;
		if(idx < 6) { pocket_name = "A" + String.valueOf(idx+1); }
		else { pocket_name = "B" + String.valueOf(idx-6); }
		int name_w = (int) g2.getFontMetrics().getStringBounds(pocket_name, g2).getWidth();
		
		
		
		

        if(highlight)
        {
			g2.setColor(Color.yellow);
			g2.fill(pocket_outline);
        }
        else
        {
			g2.setColor(bg_color.getColor());
			g2.fill(pocket_outline);
        }
		g2.setColor(Color.black);
		g2.draw(pocket_outline);
		
		
        g2.drawString(pocket_name,c_x-(name_w/2),y+height-7);
		
		for(int i = 0; i < stones_elipses.size(); i++)
		{
			g2.setColor(Color.black);
			g2.fill(stones_elipses.get(i));
			g2.draw(stones_elipses.get(i));
		}
	}
	
	/**
	 * Retuns an Elipse2D object of a stone in the given location
	 * @param x - x coordinate of the stone
	 * @param y - y coordinate of the stone
	 * @return
	 */
	public Ellipse2D.Double makeStoneElipse(int x, int y)
	{
		return new Ellipse2D.Double(x-4, y-4, 7, 7);
	}
	
	
	/**
	 * Returns the width of the pocket
	 */
	@Override
	public int getIconWidth() 
	{
		return width;
	}

	/**
	 * Returns the height of the pocket
	 */
	@Override
	public int getIconHeight() 
	{
		return height;
	}
	
	// Getters & Setters
	
	/**
	 * Returns the index identifier of the pocket
	 * @return - index identifier of the pocket
	 */
	
	public int getIdx() { return idx; }
	/**
	 * Returns the amount of stones that are in the pocket
	 * @return - amount of stones that are in the pocket
	 */
	public int getStones() { return stones; }
	
	/**
	 * Sets if the current pocket should be highlighted or not
	 * @param highlight - If you want the pocket to be highlighted (true/false)
	 */
	public void setHighlight(boolean highlight) { this.highlight = highlight; }
	
	/**
	 * Sets the amount of stones inside this pocket
	 * @param stones - The amount of stones you want this pocket instance to have
	 */
	public void setStones(int stones) { this.stones = stones; }
	
	/**
	 * Sets the background's CustomColor object that dictates the color the background of the pocket will be drawn with
	 * @param bg_color - The CustomColor object that dictates the color the background of the pocket
	 */
	public void setPocketBackgroundColor(CustomColors bg_color) { this.bg_color = bg_color; }
}
