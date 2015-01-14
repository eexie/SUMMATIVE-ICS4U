import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import terrain.Terrain;

public class Bug extends Unit {
	private int type;
	private Image timg;
	public boolean selected;
	public static int size = 25;

	public Bug(int x, int y) {
		super(x, y);
		super.size = size;
		type = 1;
		updateImage();
		maxHealth = 100 + 100 * type;
		health = maxHealth;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) { // types are added together for new type
		this.type = type;
	}

	public void updateImage() {
		timg = new ImageIcon("t" + type + ".png").getImage();
	}

	public void draw(Graphics g) {
		super.draw(g, Color.RED);
		g.setColor(Color.RED);
		g.drawImage(timg, x - size - cx, y - size - cy, size * 2, size * 2,
				null);
		if (selected) {
			g.setColor(new Color(240, 233, 34));
			((Graphics2D) g).setStroke(new BasicStroke(3));
			g.drawRect(x - size - cx, y - size - cy, size * 2, size * 2);
		}
		((Graphics2D) g).setStroke(new BasicStroke(1));

		// draw healthbar
		double one = (double) size * 2 / maxHealth;
		if (health > maxHealth / 2)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.fillRect(x - size - cx, y - size - cy, (int) (health * one), 5); // health
																			// bar
		g.drawOval(x - range - cx, y - range - cy, (int) (range * 2),
				(int) (range * 2));
		// g.drawRect(x - size - cx, y - size - cy, size * 2, size *
		// 2);//collision box

	}

	public Image getImage() {
		return timg;
	}

	// public void attack() {
	// switch (type) {
	// case 1:
	// fire(40, 5);
	// break;
	// case 2:
	// fire(40, 10);
	// break;
	// case 3:
	// fire(80, 5);
	// break;
	// case 4:
	// fire(80, 10);
	// break;
	// case 5:
	// fire(80, 20);
	// break;
	//
	// }
	// }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// public void fire(int time, int damage) {
	// Bullet newBullet = new Bullet(x, y, time, damage, "bug");
	// // move toward nearest enemy
	// Game.bullets.add(newBullet);
	// }

	public int getHealth() {
		return health;
	}

	public void update(World map) { // collision rect
		if (health <= 0)
			Game.bugs.remove(this);
		for (int i = 0; i < map.getSect().getMap().size(); i++) {
			// check collision with terrain
			Terrain cur = map.getSect().getMap().get(i);
			if (cur.getSolid()
					&& super.getCollision().intersects(cur.getCollision())) {
				if (Math.abs(tx - x) > Math.abs(ty - y)) {
					if (x > cur.getX())
						x = cur.getX() + cur.getSize() + 1 + size;
					else if (x < cur.getX())
						x = cur.getX() - cur.getSize() - 1 - size;
				} else {
					if (y > cur.getY())
						y = cur.getY() + cur.getSize() + 1 + size;
					else if (y < cur.getY())
						y = cur.getY() - cur.getSize() - 1 - size;
				}
				tx = x;
				ty = y;
			} else {
				if (!cur.getSolid()
						&& super.getCollision().intersects(cur.getCollision())) {
					int active = cur.active();

					// ACTIVES RETURNED BY THE TERRAIN
					//
					//
					//
					//
					//
					switch (active) {
					case -1:// gathered data, create new bug
						map.getSect().getMap().remove(i);
						i--;
						Game.bugs
								.add(new Bug(
										Game.bugs.get(0).getX() + Bug.size,
										Game.bugs.get(0).getY() + Bug.size));
						break;
					case 1:
						// Check point captured. Show's the captured point.
						break;
					case 2:
						// Moves to a different level of Map.
						map.change(1);
						break;
					case 3:
						map.change(-1);
						break;
					case 100: // picked up resource for new bug
						Game.bugs.add(new Bug(cur.getX(), cur.getY()));
						map.getSect().getMap().remove(i);
						break;
					}
				}
				super.update();
				switch (type) {
				case 1:
					range = 150;
					damage = 1;
					break;
				case 2:
					range = 150;
					damage = 2;
					break;
				case 3:
					range = 600;
					damage = 1;
				case 4:
					range = 400;
					damage = 2;
					break;
				case 5:
					range = 200;
					damage = 15;
					break;
				case 6:
					range = 400;
					damage = 10;
					break;
				}
				attack(1);
			}
		}

		// Mechanics.
		if (health > maxHealth) {
			health = maxHealth;
		}
	}

	public void moveTo(int tx, int ty) {
		attack = null;
		support = null;
		combat = false;
		this.tx = tx;
		this.ty = ty;
		// this.tx += (int) (Math.random() * 50);
		// this.tx -= (int) (Math.random() * 50);
		// this.ty += (int) (Math.random() * 50);
		// this.ty -= (int) (Math.random() * 50);
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
