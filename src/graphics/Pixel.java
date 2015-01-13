package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class Pixel {
	double x,y;
	int size;
	int width, height;
	Color color;
	double speed;
	public Pixel(Color color, int maxX, int maxY){
		width=maxX;
		height=maxY;
		makePixel();
		this.color=color;
	}
	public void draw(Graphics g,int cx,int cy){
		g.setColor(color);
		g.drawRect((int)x-cx*9/10-2, (int)y-cy*9/10-2, size, size);
		g.fillRect((int)x-cx, (int)y-cy, size, size);
		x-=Math.random()*speed/100;
		y+=Math.random()*speed/100;
		speed+=Math.random()/25;
		if(x<0){
			makePixel();
			x=width;
		}
		if(y>height){
			makePixel();
			y=0;
		}
	}
	public void makePixel(){
		x=(Math.random()*width);
		y=(Math.random()*height);
		size=6;
		speed=2;
	}
}
