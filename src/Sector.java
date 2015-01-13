import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import terrain.Boundary;
import terrain.Terrain;

public class Sector {
	Rectangle rect;
	String data;
	private ArrayList<Terrain> map;
	ImageIcon bg;
	private int num;

	public Sector(String path, int x, int y, int sx, int sy, int num)
			throws NumberFormatException, IOException {
		bg = new ImageIcon(path);
		rect = new Rectangle(x, y, sx, sy);
		this.num = num;
		addEnemies();
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(bg.getImage(), 0, 0, 1024, 720, null);
		for (int i = 0; i < map.size(); i++) {
			map.get(i).draw(g, x, y);
		}
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setMap(ArrayList<Terrain> map) {
		this.map = map;
//		map.add(0, new Boundary(0, 0, rect.width, rect.height));
	}

	public ArrayList<Terrain> getMap() {
		return map;
	}

	public void addEnemies() throws NumberFormatException, IOException {
		System.out.println("s" + num + ".txt");
		System.out.println("numm" + num);
		BufferedReader br = new BufferedReader(new FileReader("s" + num
				+ ".txt"));
		int n = Integer.parseInt(br.readLine());
		for (int i = 0; i < n; i++) {
			String str = br.readLine();
			System.out.println(str);
			String[] st = str.split(" ");
			Enemy newE = new Enemy(Integer.parseInt(st[1]),
					Integer.parseInt(st[2]), Integer.parseInt(st[0]), st[3],
					Integer.parseInt(st[4]), Integer.parseInt(st[5]));
			Game.enemies.add(newE);
			System.out.println("size" + Game.enemies.size());

		}
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

}