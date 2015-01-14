package terrain;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class CheckPoint extends Terrain {
	int count;
	boolean capt;
	private Image img = new ImageIcon("checkpoint.png").getImage();;

	public CheckPoint(int x, int y) {
		super(x, y);
		super.solid = false;
		count = 0;
		capt = false;
		super.size = 50;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		if (!capt) {
			float alpha = 0 + (float) count / 100;
			g.drawImage(img, this.x - size - x, this.y - size - y, size * 2,
					size * 2, null);
			((Graphics2D) g).setPaint(new Color(1, 1, 1, alpha));
			((Graphics2D) g).fill(new Rectangle(this.x - size - x, this.y
					- size - y, size * 2, size * 2));
		}
	}

	@Override
	public int active() {
		count++;
		if (count == 100) {
			capt = true;
			return 1;
		}
		return 0;

		// TODO Auto-generated method stub

	}
}
