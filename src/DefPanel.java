import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefPanel extends JPanel {
	private JLabel label;
	public JButton toMenu;
	private GridBagConstraints gbc;

	public DefPanel(String title) {
		// TODO Auto-generated constructor stub
		setBackground(Color.BLACK);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy = 0;
		setLayout(new GridBagLayout());
		label = new JLabel(title);
		label.setFont(DefFont.derived(50, 0.4));
		label.setForeground(Color.WHITE);
		gbc.insets=new Insets(0,0,50,0);
		
		add(label, gbc);
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridy=20;
		toMenu = new PrettyBtn("BACK TO MENU",2);
		add(toMenu,gbc);
		gbc.gridy=1;
		
	}
	public void addButton(JButton button){
		gbc.gridy++;
		add(button,gbc);
	}
	public void addLabel(String text){
		JLabel label = new JLabel(text);
		label.setForeground(Color.WHITE);
		gbc.gridy++;
		gbc.insets = new Insets(10, 0, 50, 0);
		add(label,gbc);
	}
	public void addLabel(JLabel label){
		gbc.gridy++;
		gbc.insets = new Insets(10, 0, 50, 0);
		add(label,gbc);
	}

}
