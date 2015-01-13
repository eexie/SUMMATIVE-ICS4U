package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Data extends Terrain {
	int size = 5;

	public Data(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		System.out.println("new data");
		super.size = size;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		System.out.println("newwwww");
		g.setColor(Color.cyan);
		g.fillRect(x, y, 25, 25);
		g.setColor(Color.WHITE);
		g.drawString("Data", x, y);
	}

	@Override
	public int active() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Rectangle getCollision() {
		return new Rectangle(x, y, 25, 25);
	}
}
