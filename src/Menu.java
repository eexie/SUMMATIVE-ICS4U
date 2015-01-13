import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	public JButton play, instructions, load, about, exit;
	private JPanel buttonsHolder, grid;
	private GridBagConstraints gbc;

	public Menu() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		gbc.anchor=GridBagConstraints.CENTER;
		// initiate buttons
		play = new PrettyBtn(new ImageIcon("play-button.png"), new ImageIcon(
				"play-button-hover.png"));
		play.setOpaque(false);
		load = new PrettyBtn("LOAD GAME",1);
		instructions = new PrettyBtn("INSTRUCTIONS",1);
		about = new PrettyBtn("ABOUT",1);
		exit = new PrettyBtn("EXIT",1);

		buttonsHolder = new JPanel(new FlowLayout()); // holds bottom buttons
		buttonsHolder.setOpaque(false);

		grid = new JPanel(new GridBagLayout()); 
		grid.setOpaque(false);
		grid.add(play, gbc);

		buttonsHolder.add(load);
		buttonsHolder.add(instructions);
		buttonsHolder.add(about);
		buttonsHolder.add(exit);
		
		add(grid, BorderLayout.CENTER);
		add(buttonsHolder, BorderLayout.SOUTH); // buttons panel to main panel
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.setFont(DefFont.derived(32,0.6));
		g.drawString("SOURCE CODE", this.getWidth() / 2 - 215,
				this.getHeight() - 200);
		g.fillRect(0, this.getHeight() - 40, this.getWidth(), 40);
	}

}
