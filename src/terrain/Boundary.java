package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Boundary extends Terrain {
	int x, y, width, height;

	public Boundary(int x, int y, int width, int height) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		for (int i = 0; i < 50; i++) {
			g.setColor(new Color(0, 0, 255, 50 - i));
			g.drawRect(this.x - x + i, this.y - y + i, width - i * 2, height
					- i * 2);
		}
	}

	@Override
	public int active() {
		return 10;
	}

	@Override
	public Rectangle getCollision() {
		return new Rectangle(x, y, width, height);
	}

	public Rectangle getCheckCol() {
		return new Rectangle(x + 100, y + 100, width - 200, height - 200);
	}
}