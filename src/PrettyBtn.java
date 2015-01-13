import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PrettyBtn extends JButton implements MouseListener {
Color c1,c2;
	public PrettyBtn(String title,int type) {
		// TODO Auto-generated constructor stub
		this();
		if(type==1){
		c1 = Color.BLACK;
		c2 = Color.WHITE;
		}
		else if(type==2){
			c1 = Color.WHITE;
			c2 = Color.BLACK;
		}
		setText(title);
		setFont(DefFont.derived(12,0.4));
		setSize(120, 30);
		setBackground(c1);
		setForeground(c2);
		addMouseListener(this);
	}
	public PrettyBtn(){
		setBorderPainted(false);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setOpaque(true);
		
	}
	public PrettyBtn(ImageIcon icon, ImageIcon hover){
		this();
		setIcon(icon);
		setBackground(c2);
		this.setRolloverEnabled(true);
		this.setRolloverIcon(hover);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

		if (e.getSource() == this) {
			this.setBackground(c2);
			this.setForeground(c1);
		}

	}

	public void mouseExited(MouseEvent e) {

		if (e.getSource() == this) {
			this.setBackground(c1);
			this.setForeground(c2);
		}

	}

}
