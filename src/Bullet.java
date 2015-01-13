import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Unit {
	private static int size = 5; // width and height
	private int time, damage;
	public String type;

	// constructor
	public Bullet(int x, int y, int time, int damage, String type) {
		super(x, y);
		super.size=size;
		reached = true;
		this.damage = damage;
		this.time = time;
		this.type = type;
	}
	public Bullet(int x, int y, int time, int damage, String type,int speed){
		this(x,y,time,damage,type);
		super.speed=speed;
	}

	public void moveTo(int tx, int ty) {
		if (reached) {
			this.tx = tx;
			this.ty = ty;
		}
	}

	public void update() {
		time--;
		if (x >= 770 || y >= 675 || x <= 5 || y <= 5 || time == 0) {
			if (type.equals("bug")) {
				Game.bullets.remove(this);
			} else {
				Game.enemyBullets.remove(this);
			}
		} else {
			super.update();
			checkCollision();
		}
	}

	// returning all the necessary value of this class

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDamage() {
		return damage;
	}

	public void draw(Graphics g) {
		switch (damage) {
		case 2:
			g.setColor(Color.BLUE);
			break;
		case 4:
			g.setColor(Color.GREEN);
			break;
		case 6:
			g.setColor(Color.RED);
			break;
		}
		g.drawRect(getCollision().x, getCollision().y, getCollision().width,
				getCollision().height);
		g.fillRect(super.x - 5, super.y - 5, size * 2, size * 2);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	private void checkCollision() {
		if (type.equals("bug")) {
			System.out.println("size" + Game.enemies.size());
			for (int i = 0; i < Game.enemies.size(); i++) {
				Enemy curr = (Enemy) Game.enemies.get(i);
				if (curr.getBound().intersects(getCollision())) {
					if (((Enemy) curr).health > 0) {
						((Enemy) curr).health -= 1;
					}
					if (((Enemy) curr).health <= 0) {
						Game.score += 5;
						Game.enemies.remove(i);
					}
					Game.bullets.remove(this);
				}
			}
		} else {
			for (int i = 0; i < Game.bugs.size(); i++) {
				Unit curr = Game.bugs.get(i);
				if (getCollision().intersects(curr.getCollision())) {
					((Bug) curr).health -= 1;
				}
				if (((Bug) curr).health <= 0) {
					Game.score += 5;
					Game.bugs.remove(i);
				}
				Game.bullets.remove(this);
			}
		}
	}
}
