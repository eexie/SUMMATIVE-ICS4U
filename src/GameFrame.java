import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame implements ActionListener {

	private static Container c;
	private static CardLayout cards;
	private Menu menu = new Menu();
	private JButton unpause;
	private DefPanel pausePanel, instPanel, aboutPanel, gameOver;
	private static Game game;
	private MapEditor mapEditor;

	public GameFrame(String title) throws IOException {
		super(title);
		c = this.getContentPane();
		cards = new CardLayout();
		c.setLayout(cards);
		mapEditor = new MapEditor();
		game = new Game(mapEditor.getPath());
		pausePanel = new DefPanel("PAUSED");
		instPanel = new DefPanel("INSTRUCTIONS");
		instPanel.addLabel(new JLabel(new ImageIcon("instructions.png")));
		aboutPanel = new DefPanel("ABOUT");
		aboutPanel
				.addLabel("Source Code was created by Emma Xie and Kevin Zheng in ICS4U, 2014-15");
		gameOver = new DefPanel("GAME OVER");
		gameOver.addLabel("Check Points Conquered: ");
		unpause = new PrettyBtn("UNPAUSE", 2);
		pausePanel.addButton(unpause);
		pausePanel.toMenu.addActionListener(this);
		instPanel.toMenu.addActionListener(this);
		aboutPanel.toMenu.addActionListener(this);
		gameOver.toMenu.addActionListener(this);
		unpause.addActionListener(this);
		menu.instructions.addActionListener(this);
		menu.about.addActionListener(this);
		menu.play.addActionListener(this);
		menu.editor.addActionListener(this);
		menu.exit.addActionListener(this);
		mapEditor.play.addActionListener(this);
		mapEditor.menu.addActionListener(this);
		game.exit.addActionListener(this);
		game.pause.addActionListener(this);
		c.add(menu, "Menu");
		c.add(game, "Game Name");
		c.add(pausePanel, "Paused");
		c.add(mapEditor, "Map Editor");
		c.add(instPanel, "Instructions");
		c.add(aboutPanel, "About");
		c.add(gameOver, "Game Over");
		this.addKeyListener(game);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == menu.play) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == game.pause) {
			cards.show(c, "Paused");
			game.getTimer().stop();
		} else if (e.getSource() == unpause) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == game.exit
				|| e.getSource() == pausePanel.toMenu) {
			cards.show(c, "Menu");
			try {
				game.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == mapEditor.play) {
			mapEditor.save();
			cards.show(c, "Game Name");
		} else if (e.getSource() == menu.editor)
			cards.show(c, "Map Editor");
		else if (e.getSource() == menu.instructions)
			cards.show(c, "Instructions");
		else if (e.getSource() == menu.about)
			cards.show(c, "About");
		else if (e.getSource() == menu.exit)
			System.exit(0);
		else
			cards.show(c, "Menu");

	}

	public static void showGameOver() {
		cards.show(c, "Game Over");
		game.getTimer().stop();
		try {
			game.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		GameFrame frame = new GameFrame("Source Code");
		frame.setSize(1000, 700);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
