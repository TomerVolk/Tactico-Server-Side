package network;

import java.awt.*;
import javax.swing.JPanel;

import network.Game.Status;

/**
 * the bar at the top of the frame that shows what was the last fight
 * @author tomer
 */
public class InfoBar extends JPanel{
	private static final long serialVersionUID = 1L;
	/**
	 * the board of this player
	 */
	Board b;
	/**
	 * the indexes of the tool from this player from last fight
	 */
	int YourFight=-1;
	/**
 	 * the indexes of the tool from the opponent from last fight
	 */
	int OpponentFight=-1;
	/**
	 * the constructor of the Bar, initiates the board and the size
	 * @param board the board of this game
	 */
	public InfoBar(Board board) {
		b=board;
		this.setPreferredSize(new Dimension(b.getWidth(), 50));
	}
	/**
	 * the function that draws everything
	 * @param g the graphics of the draw
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		if(b.game.status== Status.flag) g.drawString("place your flag", 10, 40);
		if(b.game.status== Status.org) g.drawString("organize your tools", 10, 40);
		if(b.game.opToolFight==-1||b.game.MeToolFight==-1) return;
		OpponentFight=b.opponent.getTools()[b.game.opToolFight].getType();
		YourFight=b.opponent.getTools()[b.game.MeToolFight].getType();
		g.drawString("Your Tool was: "+YourFight, 50, 45);
		g.drawString("Opponent Tool was: "+OpponentFight, 500, 45);
	}
		
}