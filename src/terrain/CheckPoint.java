package terrain;

import java.awt.Color;
import java.awt.Graphics;

public class CheckPoint extends Terrain{
	int count;
	boolean capt;
	public CheckPoint(int x, int y){
		super(x,y);
		super.solid=false;
		count=0;
		capt=false;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		if(!capt){
			g.setColor(new Color(0,0,255-255*count/100));
			g.fillRect(this.x-size-x, this.y-size-y, size*2, size*2);
		}
	}

	@Override
	public int active() {
		count++;
		if(count==100){
			capt=true;
			return 1;
		}
		return 0;
		
		// TODO Auto-generated method stub
		
	}
}
