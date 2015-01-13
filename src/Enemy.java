import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Enemy extends Unit {
	private int size;
	protected int health;
	private int type;
	private int cooldown = 10, distance;
	private int direction;
	private String mDirection = "";

	public Enemy(int x, int y, int type, String mDirection, int distance,
			int speed) {
		super(x, y);
		size = 20 * type;
		super.size = size;
		this.type = type;
		this.mDirection = mDirection;
		this.distance = distance;
		this.speed = speed;
		System.out.println("distance:" + distance);
		setMovement(distance);
		health = (int) (90 + Math.pow(2, type));
		if (mDirection.equals("h"))

			direction = (int) (Math.random() * 2) + 3;
		else
			direction = (int) (Math.random() * 2) + 1;
		System.out.println("direction" + direction);
		System.out.println(type);
	}

	// public Enemy(int x, int y, int type){
	// super(x,y,size);
	// this.type = type;
	// size = 20 * type;
	// health = (int) (90 + Math.pow(2, type));
	// System.out.println(type);
	// if (type == 1 || type == 2)
	// direction = (int) (Math.random() * 4) + 1;
	// }

	public void attack() {
		switch (type) {
		case 1: // straight
			fireStraight(1);
			break;
		case 2: // straight, 2x damage
			fireStraight(2);
			break;
		case 3: // four streams of bullets
			fireFour(1);
			break;
		case 4: // eight streams of bullets
			fireEight(1);
			break;
		case 5: // eight streams of bullets,2x damage
			fireEight(2);
			break;
		}
	}

	public void update() {
		if (cooldown == 10) {
			attack();
			cooldown = 20;
		}
		cooldown--;
		if (tx == x && ty == y) {
			setMovement(-distance);
		} else if (tx != x || ty != y) {
			super.update();
		}
//		if (health < 0) {
//			System.out.println("dead");
//			Game.enemies.remove(this);
//		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		drawHCircle(x, y, size, type, g);
		drawVCircle(x, y, size, type, g);
		g.setColor(Color.RED);
		g.fillRect(x - size - cx, (int) (y + size * 3 - cy), (int) (health
				* type / 1.3), 10);
	}

	public void drawHCircle(int cx, int cy, int radius, int depth, Graphics g) {
		g.drawOval(cx, cy, radius * 2, radius * 2);
		if (depth > 0) { // what happens if you change the 8?
			drawHCircle(cx + radius * 3 / 2, cy + radius / 2, radius / 2,
					depth - 1, g);
			drawHCircle(cx - radius / 2, cy + radius / 2, radius / 2,
					depth - 1, g);
		}
	}

	public void drawVCircle(int cx, int cy, int radius, int depth, Graphics g) {
		g.drawOval(cx, cy, radius * 2, radius * 2);
		if (depth > 0) { // what happens if you change the 8?
			drawVCircle(cx + +radius / 2, cy + radius * 3 / 2, radius / 2,
					depth - 1, g);
			drawVCircle(cx + radius / 2, cy - radius / 2, radius / 2,
					depth - 1, g);
		}
	}

	public void fireStraight(int damage) {
		int x2 = x + size, y2 = y + size;
		Bullet newBullet = new Bullet(x2, y2, 30 * type, 1, "enemy", speed);
		switch (direction) {
		case 1: // move straight left
			newBullet.moveTo(0, y2 + 1);
			break;
		case 2: // move straight right
			newBullet.moveTo(1000, y2);
			break;
		case 3: // move straight up
			newBullet.moveTo(x2 + 1, 0);
			break;
		case 4: // move straight down
			newBullet.moveTo(x2, 700);
			break;
		}
		Game.enemyBullets.add(newBullet);
	}

	public void fireFour(int damage) {
		for (int i = 1; i <= 4; i++) {
			int x2 = x + size, y2 = y + size;
			Bullet newBullet = new Bullet(x2, y2, 60, 1, "enemy");
			switch (i) {
			case 1: // move straight left
				newBullet.moveTo(0, y2 + 1);
				break;
			case 2: // move straight right
				newBullet.moveTo(1000, y2);
				break;
			case 3: // move straight up
				newBullet.moveTo(x2 + 1, 0);
				break;
			case 4: // move straight down
				newBullet.moveTo(x2, 700);
				newBullet.ty = 700;
				break;
			}
			Game.enemyBullets.add(newBullet);
		}
	}

	public void fireEight(int damage) {
		for (int i = 1; i <= 8; i++) {
			int x2 = x + size, y2 = y + size;
			Bullet newBullet = new Bullet(x2, y2, 100, 1, "enemy");
			int n = (int) (Math.sqrt(1000000));
			switch (i) {
			case 1: // move straight left
				newBullet.moveTo(0, y2 + 1);
				break;
			case 2: // move straight right
				newBullet.moveTo(1000, y2);
				break;
			case 3: // move straight up
				newBullet.moveTo(x2 + 1, 0);
				break;
			case 4: // move straight down
				newBullet.moveTo(x2, 700);
				break;
			case 5: // move diagonal right down
				newBullet.moveTo(newBullet.tx + n + 1, newBullet.ty + n + 1);
				break;
			case 6: // move diagonal left up
				newBullet.moveTo(newBullet.tx - n - 1, newBullet.ty - n - 1);
				break;
			case 7: // move diagonal right up
				newBullet.moveTo(newBullet.tx + n + 1, newBullet.ty - n - 1);
				break;
			case 8: // move diagonal left down
				newBullet.moveTo(newBullet.tx - n - 1, newBullet.ty + n + 1);
				break;
			}
			Game.enemyBullets.add(newBullet);
		}
	}

	public Ellipse2D.Double getBound() {
		// return new Rectangle(x, y, size * 2, size * 2);
		return new Ellipse2D.Double(x, y, size * 2, size * 2);
	}

	public void setMovement(int distance) {
		this.distance = distance;
		switch (mDirection) {
		case "v":
			ty = y + distance;
			// tx=x+3;
			break;
		case "h":
			tx = x + distance;
			// ty=y+3;
			break;
		}
	}

	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
}
