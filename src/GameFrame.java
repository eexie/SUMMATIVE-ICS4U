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
	private JButton play, pause, exit, menuExit, unpause, menubtn, menubtn2,
			menubtn3, menubtn4, instructions, about;
	private DefPanel pausePanel, instPanel, aboutPanel, gameOver;
	private static Game game;

	public GameFrame(String title) throws IOException {
		super(title);
		c = this.getContentPane();
		cards = new CardLayout();
		c.setLayout(cards);
		game = new Game();
		play = menu.play;
		pause = game.pause;
		exit = game.exit;
		menuExit = menu.exit;
		about = menu.about;
		instructions = menu.instructions;
		pausePanel = new DefPanel("PAUSED");
		instPanel = new DefPanel("INSTRUCTIONS");
		instPanel.addLabel(new JLabel(new ImageIcon("instructions.png")));
		aboutPanel = new DefPanel("ABOUT");
		aboutPanel
				.addLabel("Source Code was created by Emma Xie and Kevin Zheng in ICS4U, 2014-15");
		gameOver = new DefPanel("GAME OVER");
		gameOver.addLabel("Check Points Conquered: ");
		unpause = new PrettyBtn("UNPAUSE", 2);
		instructions = menu.instructions;
		menubtn = pausePanel.toMenu;
		menubtn2 = instPanel.toMenu;
		menubtn3 = aboutPanel.toMenu;
		menubtn4 = gameOver.toMenu;
		pausePanel.addButton(unpause);
		menubtn.addActionListener(this);
		menubtn2.addActionListener(this);
		menubtn3.addActionListener(this);
		menubtn4.addActionListener(this);
		instructions.addActionListener(this);
		about.addActionListener(this);
		unpause.addActionListener(this);
		play.addActionListener(this);
		exit.addActionListener(this);
		menuExit.addActionListener(this);
		pause.addActionListener(this);
		c.add(menu, "Menu");
		c.add(game, "Game Name");
		c.add(pausePanel, "Paused");
		c.add(instPanel, "Instructions");
		c.add(aboutPanel, "About");
		c.add(gameOver, "Game Over");
		this.addKeyListener(game);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == play) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == pause) {
			cards.show(c, "Paused");
			game.getTimer().stop();
		} else if (e.getSource() == unpause) {
			cards.show(c, "Game Name");
			game.requestFocus();
			game.getTimer().start();
		} else if (e.getSource() == exit || e.getSource() == menubtn) {
			cards.show(c, "Menu");
			try {
				game.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == instructions)
			cards.show(c, "Instructions");
		else if (e.getSource() == about)
			cards.show(c, "About");
		else if (e.getSource() == menuExit)
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
