import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import graphics.*;

public class Game extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
	public static ArrayList<Bug> bugs = new ArrayList<>();
	public static ArrayList<Enemy> enemies = new ArrayList<>();
	static ArrayList<Bug> selectedBugs = new ArrayList<>();
	public static ArrayList<Bullet> bullets = new ArrayList<>();
	public static ArrayList<Bullet> enemyBullets = new ArrayList<>();

	public static boolean left_clicked, right_clicked, shifted, space;
	private int mx, my; // movement
	private Rectangle dragBox; // Selection
	private int sx, sy;
	public static World map;
	public static int score;
	public static int click_count;
	public JButton pause, exit, combine;
	private ArrayList<JButton> selectButtons = new ArrayList<>();
	PointerInfo a = MouseInfo.getPointerInfo();
	Point b = a.getLocation();

	// BackGround
	public JPanel background_1, background_2;
	public Pixel[] pixels;
	public Pixel[] pixels_2;

	public Game() throws IOException {
		score = 0;
		click_count = 0;
		right_clicked = false;
		left_clicked = false;
		dragBox = new Rectangle();
		mx = 0;
		my = 0;
		sx = 0;
		sy = 0;

		addBugs(bugs);
		selectedBugs.add((Bug) bugs.get(bugs.size() - 1));
		selectedBugs.get(0).selected = true;
		map = new World("test.txt");

		setBackground(50);

		setLayout(null);
		combine = new PrettyBtn("COMBINE", 2);
		pause = new PrettyBtn("PAUSE", 2);
		exit = new PrettyBtn("MENU", 2);
		add(combine);
		add(pause);
		add(exit);
		for (int i = 0; i < 4; i++) {
			JButton newJB = new JButton();
			newJB.addKeyListener(this);
			newJB.setBorderPainted(false);
			selectButtons.add(newJB);
			add(newJB);
			newJB.addActionListener(this);
		}
		addKeyListener(this);
		combine.addActionListener(this);
		combine.addKeyListener(this);
		pause.addKeyListener(this);
		exit.addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(new Color(225, 225, 225));
		timer = new Timer(17, this);
	}

	public void addBugs(ArrayList<Bug> arr) {
		int size = (int) Math.sqrt(10);
		for (int i = 0; i < 10; i++) {
			bugs.add(new Bug(300 + i % size * Bug.size * 2 - size * Bug.size,
					100 + i * Bug.size * 2 / size - size * Bug.size));
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i].draw(g, map.getX() / 2, map.getY() / 2);
			pixels_2[i].draw(g, map.getX() / 4, map.getY() / 4);
		}
		map.draw(g);

		// side bar commands
		Image sm = new ImageIcon("side-menu.png").getImage();
		g.drawImage(sm, getWidth() - 220, 0, 220, getHeight(), null);
		combine.setBounds(getWidth() - 190, getHeight() - 190, 170, 30);
		pause.setBounds(getWidth() - 190, getHeight() - 130, 170, 30);
		exit.setBounds(getWidth() - 190, getHeight() - 90, 170, 30);
		Unit.setXY(map.getX(), map.getY());

		// units
		for (Bug i : bugs) {
			i.draw(g);
		}
		for (Bullet i : bullets) {
			i.draw(g);
		}
		for (Bullet i : enemyBullets) {
			i.draw(g);
		}
		for (Enemy i : enemies)
			i.draw(g);

		// draw invisible buttons to deselect bugs
		for (int i = 0; i < selectButtons.size(); i++) {
			selectButtons.get(i).setBounds(getWidth() - 189 + i % 2 * 88,
					getHeight() - 419 + i / 2 * 116, 75, 70);
		}
		g.setColor(Color.white);
		// paint minimap
		int l = map.getLevel();
		// int l = 6;
		if (l > 3) {
			System.out.println(">");
			l -= 4;
			g.fillOval(getWidth() - 195 + l * 55, getHeight() - 646 + 35, 10,
					10);
		} else
			g.fillOval(getWidth() - 195 + l * 55, getHeight() - 646, 10, 10);
		// paint number of bugs, background, type,image for selected bugs
		g.drawString(bugs.size() + "", getWidth() - 120, getHeight() - 565);
		for (int i = 0; i < selectedBugs.size(); i++) {
			g.fillRect(getWidth() - 189 + i % 2 * 88, getHeight() - 535 + i / 2
					* 116, 75, 70);// background
			g.drawImage(selectedBugs.get(i).getImage(), getWidth() - 176 + i
					% 2 * 88, getHeight() - 525 + i / 2 * 116, Bug.size * 2,
					Bug.size * 2, null);// image
			g.drawString(selectedBugs.get(i).getType() + "", getWidth() - 129
					+ i % 2 * 88, getHeight() - 435 + i / 2 * 115);// type
																	// string
		}

		// Shows where user clicks.
		clicked(g);
		// Shows the box the user creates with left mouse click.
		dragBox(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (e.getX() < 780) {
				dragBox.width = (e.getX() + map.getX()) - dragBox.x;
				dragBox.height = (e.getY() + map.getY()) - dragBox.y;
			}
		} else if (SwingUtilities.isRightMouseButton(e)) { // ensure player is
															// clicking on game
															// panel
			click_count = 10;
			if (e.getX() < 780) {
				mx = e.getX() + map.getX();// Accounts for map scrolling
											// displacement
				my = e.getY() + map.getY();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (e.getX() < 780) {
				left_clicked = true;
				dragBox.x = e.getX() + map.getX();
				dragBox.y = e.getY() + map.getY();
			}
		} else if (SwingUtilities.isRightMouseButton(e)) {
			if (space) {
				sx = e.getX();
				sy = e.getY();
				return;
			}
			if (e.getX() < 780) { // ensure player is clicking on game panel
				right_clicked = true;
				click_count = 10;
				mx = e.getX() + map.getX();// Accounts for map scrolling
											// displacement
				my = e.getY() + map.getY();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		if (SwingUtilities.isLeftMouseButton(e)) {
			left_clicked = false;
			dragBox.width = 1;
			dragBox.height = 1;
		} else if (SwingUtilities.isRightMouseButton(e)) { // ensure player is
			if (space) {
				return;
			} // clicking on game // panel
			right_clicked = false;
			click_count = 10;
			// if(e.getX()<780){
			// mx = e.getX() + map.getX();//Accounts for map scrolling
			// displacement
			// my = e.getY() + map.getY();
			// }
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 38) {// up arrow
			if (selectedBugs.size() < 6) {
				Bug newSelected = (Bug) bugs.get(0);
				bugs.remove(newSelected);
				bugs.add(newSelected);
				// selected is empty, take first bug in bugs and add to selected
				selectedBugs.add(newSelected);
				newSelected.selected = true;
			}
		} else if (e.getKeyCode() == 40) {// down arrow
			if (selectedBugs.size() > 1) {
				Bug unSelected = selectedBugs.get(selectedBugs.size() - 1);
				unSelected.selected = false;
				selectedBugs.remove(unSelected);
			}
		} else if (e.getKeyCode() == 16)// shift
			shifted = true;
		else if (e.getKeyCode() == 32) { // space
			space = true;
			right_clicked = false;
			// if (shifted) {
			// for (Unit i : bugs) {
			// ((Bug) i).attack();
			// }
			// } else {
			// for (Bug i : selectedBugs) {
			// i.attack();
			// }
			// }
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			map.setY(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			map.setY(10);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			map.setX(-10);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			map.setX(10);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 16)// shift
			shifted = false;
		else if (e.getKeyCode() == 32) { // space
			space = false;
			sx = 0;
			sy = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_W)
			map.setY(10);
		else if (e.getKeyCode() == KeyEvent.VK_S)
			map.setY(-10);
		else if (e.getKeyCode() == KeyEvent.VK_A)
			map.setX(10);
		else if (e.getKeyCode() == KeyEvent.VK_D)
			map.setX(-10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == timer) {
			if (bugs.size() == 0) {// losing condition
				System.out.println("Game Over");
				GameFrame.showGameOver();
			}
			if (right_clicked) {
				int size = 0;
				int bug_size = 0;
				if (shifted) { // shift on, move all bugs
					if (bugs.size() == 1) {
						bugs.get(0).moveTo(mx, my);
					} else {
						size = (int) Math.sqrt(bugs.size());
						for (int i = 0; i < bugs.size(); i++) {
							bugs.get(i).moveTo(
									mx + i % size * Bug.size * 2 - size
											* Bug.size,
									my + i * Bug.size * 2 / size - size
											* Bug.size);
						}
					}
				} else {
					// shift off, only move selected bugs

					if (selectedBugs.size() == 1) {
						selectedBugs.get(0).moveTo(mx, my);
					} else {
						size = (int) Math.sqrt(selectedBugs.size());
						bug_size = bugs.get(0).size;
						for (int i = 0; i < selectedBugs.size(); i++) {
							selectedBugs.get(i).moveTo(
									mx + i % size * bug_size * 2 - size
											* bug_size,
									my + i * bug_size * 2 / size - size
											* bug_size);
						}
					}
				}
			}
			if (left_clicked) {
				selectedBugs.clear();
				Rectangle rect = normalize(dragBox);

				for (Bug i : bugs) {
					i.selected = false;
					if (i.getCollision().intersects(rect)
							&& selectedBugs.size() < 6) {
						selectedBugs.add(i);
						i.selected = true;
					}
				}
			}
			if (space) {
				for (Unit i : enemies) {
					if (i.getCollision()
							.intersects(new Rectangle(sx, sy, 1, 1))) {
						for (Unit j : selectedBugs) {
							j.setAttack((Enemy) i);
						}
					}
				}
				for (Unit i : bugs) {
					if (i.getCollision()
							.intersects(new Rectangle(sx, sy, 1, 1))) {
						for (Unit j : selectedBugs) {
							j.setSupport((Bug) i);
						}
					}
				}
			}
			for (int i = 0; i < bugs.size(); i++) {
				((Bug) bugs.get(i)).update(map);
			}
			for (int i = 0; i < enemies.size(); i++){
				((Enemy) enemies.get(i)).update();
				if(((Enemy) enemies.get(i)).getHealth()<0)
			}	
			for (int i = 0; i < selectedBugs.size(); i++) {
				if (!bugs.contains(selectedBugs.get(i)))
					selectedBugs.remove(selectedBugs.get(i));
			}
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update();
			}
			for (int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).update();
			}
			Point p = MouseInfo.getPointerInfo().getLocation();

			for (Bullet i : bullets) {
				i.moveTo((int) p.getX(), (int) p.getY() - 50);
			}
		}

		if (e.getSource() == combine) {/* combine */
			int newType = 0;
			requestFocus();
			Bug combined = new Bug(selectedBugs.get(0).getX(), selectedBugs
					.get(0).getY());
			while (selectedBugs.size() > 0) {
				if (newType + selectedBugs.get(0).getType() < 7)
					newType += selectedBugs.get(0).getType();
				else
					break;
				bugs.remove(selectedBugs.get(0));
				selectedBugs.remove(0);
			}
			combined.setType(newType);
			combined.updateImage();
			combined.selected = true;
			selectedBugs.add(combined);
			bugs.add(combined);
		}
		for (int i = 0; i < selectButtons.size(); i++) {
			if (e.getSource() == selectButtons.get(i)) {
				if (i < selectedBugs.size()) {
					Bug removeBug = selectedBugs.get(i);
					removeBug.selected = false;
					selectedBugs.remove(removeBug);
				}
			}
		}
		repaint();
	}

	public void clicked(Graphics g) {
		if (click_count < 1) {
			return;
		}
		g.setColor(Color.blue);
		g.fillOval(mx - click_count / 2 - map.getX(), my - click_count / 2
				- map.getY(), click_count, click_count);
		click_count--;

	}

	public void dragBox(Graphics g) {
		if (!left_clicked) {
			return;
		}
		Rectangle rect = normalize(dragBox);
		g.setColor(new Color(0, 255, 0, 25));
		g.fillRect(rect.x - map.getX(), rect.y - map.getY(), rect.width,
				rect.height);
	}

	public Rectangle normalize(Rectangle dragBox) {
		Rectangle rect = new Rectangle(dragBox);
		if (dragBox.width < 0) {
			rect.x += dragBox.width;
			rect.width *= -1;
		}
		if (dragBox.height < 0) {
			rect.y += dragBox.height;
			rect.height *= -1;
		}
		return rect;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setBackground(int size) {
		pixels = new Pixel[size];
		pixels_2 = new Pixel[size];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = new Pixel(new Color(255, 255, 255),
					map.getSect().rect.width, map.getSect().rect.height);
			pixels_2[i] = new Pixel(new Color(200, 200, 120),
					map.getSect().rect.width, map.getSect().rect.height);
		}
	}

	public void reset() throws IOException {
		bugs.clear();
		selectedBugs.clear();
		enemies.clear();
		bullets.clear();
		enemyBullets.clear();
		new Game();

	}
}
