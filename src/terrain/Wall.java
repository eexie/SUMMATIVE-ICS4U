package terrain;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Wall extends Terrain {
	private Image img = new ImageIcon("wall.png").getImage();

	public Wall(int x, int y) {
		super(x, y);
		super.solid = true;
		super.size = 25;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(img, this.x - size - x, this.y - size - y, size * 2 + 3,
				size * 2 + 3, null);
	}

	@Override
	public int active() {
		// TODO Auto-generated method stub
		return 0;
	}
}
