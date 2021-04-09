
public class PlayerAction 
{
	private int pocket_idx;
	private int stone_cnt;
	
	public PlayerAction(int pocket_idx, int stone_cnt)
	{
		this.pocket_idx = pocket_idx;
		this.stone_cnt = stone_cnt;
	}
	
	public int getPocketIdx()
	{
		return pocket_idx;
	}
	
	public int getStoneCnt()
	{
		return stone_cnt;
	}
}
