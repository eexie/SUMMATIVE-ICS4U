package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends Terrain {
int size = 25;
	public Wall(int x, int y) {
		super(x, y);
		super.solid = true;
		super.size=size;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.red);
		g.fillRect(this.x-size-x, this.y-size-y, size*2, size*2);
	}

	@Override
	public int active() {
		// TODO Auto-generated method stub
		return 0;
	}
}
