package terrain;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Terrain {
	protected int x;
	protected int y;
	protected boolean solid;
	protected String type;
	protected int size;
	public Terrain(int x,int y){
		this.setX(x);
		this.setY(y);
		size=25;
	}
	public boolean getSolid(){
		return solid;
	}
	public abstract void draw(Graphics g, int x, int y);
	
	public Rectangle getCollision(){
		return new Rectangle(getX()-size,getY()-size,size*2,size*2);
	}
	public String toString(){
		return "Position: "+getX()+" "+getY();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public abstract int active();
}
