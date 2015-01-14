package terrain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Data extends Terrain {
	private Image img = new ImageIcon("data.png").getImage();

	public Data(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		super.size = 10;
		super.solid = false;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		// g.setColor(Color.cyan);
		// g.fillRect(this.x - size - x, this.y - size - y, size * 2, size * 2);
		// g.setColor(Color.WHITE);
		// g.drawString("Data", x, y);
		g.drawImage(img, this.x - size - x, this.y - size - y, size * 2,
				size * 2, null);
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
