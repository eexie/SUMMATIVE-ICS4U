import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import terrain.CheckPoint;
import terrain.Data;
import terrain.Teleporter;
import terrain.Terrain;
import terrain.Wall;

public class World {
	private int mx, my;
	private int level;
	private int tx, ty;
	private int cx, cy;
	static Sector[] sectors;
	final int[] A = { 0, -1, 1, 0 };
	final int[] B = { -1, 0, 0, 1 };

	public World() {
		level = 0;
		tx = 0;
		ty = 0;

		cx = 0;
		cy = 0;

		mx = 10;
		my = 10;
	}

	public World(String path) throws IOException {
		level = 0;
		tx = 0;
		ty = 0;
		cx = 0;
		cy = 0;
		mx = 0;
		my = 0;

		readFile(path);
	}

	public void readFile(String path) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader sc = new BufferedReader(new FileReader(path));
		sectors = new Sector[Integer.parseInt(sc.readLine())];
		String data;
		my = sectors.length;
		for (int i = 0; i < sectors.length; i++) {
			sc.readLine();
			data = sc.readLine();
			sectors[i] = new Sector(sc.readLine(), Integer.parseInt(data
					.substring(data.indexOf("x") + 1, data.indexOf("y"))),
					Integer.parseInt(data.substring(data.indexOf("y") + 1,
							data.indexOf("sx"))), Integer.parseInt(data
							.substring(data.indexOf("sx") + 2,
									data.indexOf("sy"))), Integer.parseInt(data
							.substring(data.indexOf("sy") + 2)), i);
			Terrain[] map = new Terrain[Integer.parseInt(sc.readLine())];
			for (int u = 0; u < map.length; u++) {
				data = sc.readLine();
				Terrain t =null;
				switch (data.charAt(0)) {
				case 'W':
					t = new Wall(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));
					
					break;
				case 'D':
					t = new Data(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));
				case 'C':
					t = new CheckPoint(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)));
					break;
				case 'T':
					t = new Teleporter(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)),
							0);
					break;
				case 't':
					t = new Teleporter(
							Integer.parseInt(data.substring(
									data.indexOf("x") + 1, data.indexOf("y"))),
							Integer.parseInt(data.substring(data.indexOf("y") + 1)),
							1);
					break;
				}
				map[u] = t;
			}
			ArrayList<Terrain> m = new ArrayList<>();
			for (Terrain j : map)
				m.add(j);
			sectors[i].setMap(m);
		}
	}

	public void draw(Graphics g) {
		tx += cx;
		ty += cy;
		if (tx < 0) {
			tx = 0;
		}
		if (ty < 0) {
			ty = 0;
		}
		sectors[level].draw(g, tx, ty);
	}

	public void change(int c) {
		level += c;
	}

	public Sector getSect() {
		return sectors[level];
	}

	public void setX(int a) {
		cx += a;
		if (Math.abs(cx) > 10) {
			cx -= a;
		}
	}

	public void setY(int a) {
		cy += a;
		if (Math.abs(cy) > 10) {
			cy -= a;
		}

	}

	public int getX() {
		return tx;
	}

	public int getY() {
		return ty;
	}
	public int getLevel(){
		return level;
	}

	// public static void main(String args[]){
	// ArrayList<Vector> path=new ArrayList<Vector>();
	// World test=new World();
	// test.show(0,0);
	// path=test.getPath(new Vector(sc.nextInt(),sc.nextInt()), new
	// Vector(sc.nextInt(),sc.nextInt()));
	// for(int i=0;i<path.size();i++){
	// test.show(path.get(i).getX(),path.get(i).getY());
	// System.out.println();
	// }
	// }
	//
	// public World(String path){
	// BufferedReader in;
	// try {
	// in=new BufferedReader(new FileReader(path));
	// in.read();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// private void getMap(){
	// map=new Terrain[my][mx];
	// for(int i=0;i<my;i++){
	// for(int j=0;j<mx;j++){
	// switch(data.charAt((i)*mx+(j))){
	// case ' ':
	// map[i][j]=new Wall();
	// break;
	// case 'O':
	// map[i][j]=new Path();
	// break;
	// }
	// }
	// }
	// }
	// //a is start, b is destination
	// public ArrayList<Vector> getPath(Vector a, Vector b){
	// Hashtable<Integer, Integer> visited= new Hashtable<Integer, Integer>();
	// ArrayList<Vector> path=new ArrayList<Vector>();
	// ArrayList<Vector> out=new ArrayList<Vector>();
	// path.add(a);
	// path: {
	// for(int i=0;i<path.size();i++){
	// for(int j=0;j<4;j++){
	// //checks each co-ordinate next to current co-ord we are checking. If in
	// map adds to the path.
	// //The visited hashtable remembers where each point came from.
	// if(checkCoord(path.get(i).getX()+A[j], path.get(i).getY()+B[j])){
	// if(!path.contains(new Vector(path.get(i).getX()+A[j],
	// path.get(i).getY()+B[j]))){
	//
	// //If the co-ord is inmap and not already in path. Remember where it came
	// from, add it to path.
	// visited.put(path.size(), i);
	// path.add(new Vector(path.get(i).getX()+A[j], path.get(i).getY()+B[j]));
	//
	// //If the co-ord is equal to the destination b, then break loop.
	// if(path.get(path.size()-1).equals(b)){
	// break path;
	// }
	// }
	// }
	// }
	// }
	// return out;
	// }
	// int count=path.size()-1;
	// do{
	// out.add(path.get(count));
	// count=visited.get(count);
	// }while(count!=0);
	//
	// return out;
	// // ArrayList<ArrayList<Vector>> paths=new ArrayList<ArrayList<Vector>>();
	// //
	// // if(){
	// // if(a.equals(b)){
	// //
	// // }
	// // }
	// }
	//
	// public Terrain getMap(int x, int y){
	// return map[y][x];
	// }
	//
	// public boolean checkCoord(int x, int y){
	// if(x<mx&&x>-1&&y<my&&y>-1){
	//
	// return !map[y][x].getSolid();
	// }
	// return false;
	// }
	//
	// public void show(int x, int y){
	// for(int i=0;i<my;i++){
	// for(int j=0;j<mx;j++){
	// if(i==y&&j==x){
	// System.out.print("+ ");
	// }
	// else{
	// if(map[i][j] instanceof Wall){
	// System.out.print("O ");
	// }
	// else{
	// System.out.print("  ");
	// }
	// }
	// }
	// System.out.println();
	// }
	// }
}
