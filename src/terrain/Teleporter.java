package terrain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Teleporter extends Terrain {
	int count;
	boolean capt;
	int to;

	public Teleporter(int x, int y, int to) {
		super(x, y);
		capt = false;
		count = 0;
		this.to = to;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		 g.setColor(new Color(243 * to, 214 + 41 * count / 100, 116));
		((Graphics2D) g).setStroke(new BasicStroke(10));
		g.drawOval(this.x - size - x, this.y - size - y, size * 2, size * 2);
		((Graphics2D) g).setStroke(new BasicStroke(1));
	}

	@Override
	public int active() {
		count++;
		if (count == 99) {
			count = 0;
			return 2 + to;
		}
		return 0;
	}
}
