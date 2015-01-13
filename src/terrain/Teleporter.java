package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class Teleporter extends Terrain{
	int count;
	boolean capt;
	int to;
	public Teleporter(int x, int y, int to){
		super(x,y);
		capt=false;
		count=0;
		this.to=to;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(255*to,255-255*count/100,0));
		g.fillRect(this.x-size-x, this.y-size-y, size*2, size*2);
	}
	@Override
	public int active() {
		count++;
		if(count==100){
			count=0;
			return 2+to;
		}
		return 0;
	}
}
