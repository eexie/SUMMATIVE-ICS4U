import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public class Unit {
	protected int x, y; // position coordinates
	protected int tx, ty; // target coordinates
	protected double dx, dy; // velocity x & y (has direction)
	protected static int cx, cy; // Map Scrolling Displacement
	protected int speed = 5;
	protected int size;
	public boolean reached;
	public boolean moved = false;
	protected int type;
	protected int damage;
	protected int range;

	// Allows for interactions between units.
	public Unit attack;
	public Bug support;
	public boolean alive;
	public boolean combat;
	protected int health;
	protected int maxHealth;

	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		reached = false;
		tx = x;
		ty = y;
	}

	public void update() {
		// Interactions with other units.

		// move bug
		int differenceX = tx - x;
		int differenceY = ty - y;
		if (differenceX == 0 && differenceY == 0) {
			reached = true;
			dx = 0;
			dy = 0;
			return;
		} else {
			reached = false;
		}
		double radius = Math.sqrt(differenceX * differenceX + differenceY
				* differenceY);
		double cosangle = differenceX / radius;
		double sinangle = differenceY / radius;
		dx = speed * cosangle;
		dy = speed * sinangle;
		if (dx == -5 || (dx >= 4.1 && dx <= 4.5))
			dx = 0;
		if (dy == -5 || (dy >= 4.1 && dy <= 4.5))
			dy = 0;

		x += dx;
		y += dy;
		// keep bug within frame
		if (cx == 0) {
			if (x > 780 - size)
				x = 780 - size;

			if (x + cx < size)
				x = size;
		}
		if (cy == 0) {
			if (y > 680 - size)
				y = 680 - size;
			if (y + cy < size)
				y = size;
		}
	}

	public void draw(Graphics g, Color c) {
		Graphics2D graph = (Graphics2D) g;
		graph.setStroke(new BasicStroke(3));
		g.setColor(c);
		if (attack != null) {
			System.out.println("attack");
			graph.drawLine(x - cx, y - cy, attack.x - cx, attack.y - cy);// attack
																			// like
		} else if (support != null) {
			g.setColor(Color.green);
			graph.drawLine(x - cx, y - cy, support.x - cx, support.y - cy);
		}
	}

	public void attack(int identity) {
		if (attack != null) {
			combat = true;
			attack.health -= damage;
			if (attack.health <= 0) {
				attack = null;
				if (identity == 1) {// is a bug
					Game.enemies.get(Game.map.getLevel()).remove(attack);
				} else if (identity == 2)// is an enemy
					Game.bugs.remove(attack);
				combat = false;
			}
		}
		if (support != null) {
			combat = true;
			support.health++;
			if (support.health <=0) {
				support = null;
			}
		}
		if (combat) {
			return;
		}
	}

	public void setAttack(Unit opponent) {
		attack = opponent;
	}

	public void setSupport(Bug ally) {
		support = ally;
	}

	public void stop() {

	}

	public boolean intersects(Unit other) { // check collision with another bug
		return this.getCollision().intersects(other.getCollision());
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static void setXY(int x, int y) {
		cx = x;
		cy = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTx() {
		return tx;
	}

	public int getTy() {
		return ty;
	}

	public int getSize() {
		return size;
	}

	public Rectangle getCollision() {
		return new Rectangle(x - size - cx, y - size - cy, size * 2, size * 2);
	}

	public Ellipse2D.Double getRange() {
		return new Ellipse2D.Double(x - range - cx, y - range - cy,
				(int) (range * 2), (int) (range * 2));
	}
}
