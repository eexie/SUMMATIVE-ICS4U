import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class MapEditor extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	public JButton save, load, play, newSector, menu, submitLvl;
	private ArrayList<ArrayList<String>> terrainData, enemyData;
	private int sectors, elements, enemies;
	private String path;
	private JButton[] createJBs;
	public Timer timer;
	private JTextField enemyLvl;
	private Enemy exampleE;
	private PrintWriter writer;

	public MapEditor() {
		elements = 0;
		enemies = 0;
		exampleE = new Enemy(848, 200, 2, "", 0, 0);
		exampleE.setSize(18);
		terrainData = new ArrayList<>();
		enemyData = new ArrayList<>();
		enemyLvl = new JTextField(1);
		setLayout(null);
		submitLvl = new PrettyBtn("SUBMIT", 2);
		submitLvl.setFont(DefFont.derived(10, 0.13));
		save = new PrettyBtn("SAVE MAP", 2);
		load = new PrettyBtn("LOAD MAP", 2);
		play = new PrettyBtn("PLAY", 2);
		newSector = new PrettyBtn("NEW SECTOR", 2);
		menu = new PrettyBtn("MENU", 2);
		add(submitLvl);
		add(save);
		add(load);
		add(play);
		add(newSector);
		add(menu);
		add(enemyLvl);
		setBackground(Color.WHITE);
		createJBs = new JButton[5];
		for (int i = 0; i < 5; i++) {
			JButton newJB = new JButton();
			newJB.addKeyListener(this);
			newJB.setBorderPainted(false);
			createJBs[i] = newJB;
			add(newJB);
			newJB.addActionListener(this);
		}
		submitLvl.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		newSector.addActionListener(this);
		play.addActionListener(this);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		timer = new Timer(17, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("Type" + exampleE.getType());
		Image es = new ImageIcon("editor-side.png").getImage();
		g.drawImage(es, getWidth() - 220, 0, 220, getHeight(), null);
		enemyLvl.setBounds(getWidth() - 55, getHeight() - 500, 47, 30);
		submitLvl.setBounds(getWidth() - 105, getHeight() - 465, 90, 20);
		newSector.setBounds(getWidth() - 195, getHeight() - 210, 173, 30);
		save.setBounds(getWidth() - 195, getHeight() - 170, 173, 30);
		load.setBounds(getWidth() - 195, getHeight() - 130, 173, 30);
		play.setBounds(getWidth() - 195, getHeight() - 90, 173, 30);
		menu.setBounds(getWidth() - 195, getHeight() - 50, 173, 30);
		g.setColor(Color.white);
		createJBs[0].setBounds(getWidth() - 195, getHeight() - 518, 85, 77);
		for (int i = 1; i < createJBs.length; i++) {
			createJBs[i].setBounds(getWidth() - 195 + (i - 1) % 2 * 88,
					getHeight() - 423 + (i - 1) / 2 * 97, 85, 77);
		}
		exampleE.draw(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitLvl) {

			if (!enemyLvl.getText().equals("")) {
				try {
					exampleE.setType(Integer.parseInt(enemyLvl.getText()));
					enemyLvl.setText("");
					System.out.println(exampleE.getType());
				} catch (NumberFormatException nfe) {
					enemyLvl.setText("");
				}
				// more code here to create a copy of enemy
			}
		}

		if (e.getSource() == createJBs[0]) {
			// enemy button;
			enemies++;
		}
		if (e.getSource() == createJBs[1]) {
			// wall button;
			elements++;
		}
		if (e.getSource() == createJBs[2]) {
			// data button;
			elements++;
		}
		if (e.getSource() == createJBs[3]) {
			// checkpoint button;
			elements++;
		}
		if (e.getSource() == createJBs[4]) {
			// teleporter button;
			elements++;
		}
		if (e.getSource() == newSector) {
			// save data to current sector
			terrainData.get(sectors).remove(0);
			terrainData.get(sectors).add(1, elements + "");
			enemyData.get(sectors).remove(0);
			enemyData.get(sectors).add(1, enemies + "");
			terrainData.get(sectors).add("-------");
			terrainData.get(sectors).add("-------");

			// code to reset frame for new sector

			// create new sector
			sectors++;
			terrainData.add(new ArrayList<String>());
			enemyData.add(new ArrayList<String>());
			elements = 0;
			enemies = 0;
		}
		if (e.getSource() == save) {
			save();
		}
		if (e.getSource() == load) {

		}
	}

	public void save() {
		path = (String) JOptionPane.showInputDialog(this,
				"What would you like to name your saved map?", null,
				JOptionPane.PLAIN_MESSAGE, null, null, null);
		// save button
		if (path!=null) {
			try {
				writer = new PrintWriter(path + "-elements.txt", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			}
			for (ArrayList<String> i : terrainData) {
				for (String j : i)
					writer.write(j);
			}
			try {
				writer = new PrintWriter(path + "-enemies.txt", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			}
			for (ArrayList<String> i : enemyData) {
				for (String j : i)
					writer.write(j);
			}
		}
	}

	public String getPath() {
		return path;
	}

}
