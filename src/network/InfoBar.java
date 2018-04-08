package network;

import java.awt.*;
import javax.swing.JPanel;

public class InfoBar extends JPanel{
	private static final long serialVersionUID = 1L;
	Board b;
	int YourFight=-1, OpponentFight=-1;
	public InfoBar(Board board) {
		b=board;
		this.setPreferredSize(new Dimension(b.getWidth(), 50));
	}
	public void paintComponent(Graphics g){
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		if(b.game.opToolFight==-1||b.game.MeToolFight==-1) return;
		OpponentFight=b.opponent.getTools()[b.game.opToolFight].getType();
		YourFight=b.opponent.getTools()[b.game.MeToolFight].getType();
		g.drawString("Your Tool was: "+YourFight, 50, 45);
		g.drawString("Opponent Tool was: "+OpponentFight, 500, 45);
	}
		
}