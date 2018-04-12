package network;

import java.awt.*;
import javax.swing.*;
/**
 * The side panels of the game- each player's grave yard
 * @author tomer
 */
public class SideBar extends JPanel{
	private static final long serialVersionUID = 1L;
	/**
	 * the board of the game- the main frame
	 */
	private Board b;
	/**
	 * Determines whether it's this player grave- yard or the other
	 */
	private boolean isMe;
	/**
	 * Default constructor, initiate the fields and background color
	 * @param b the board of the game
	 * @param isMe determines whether it's this player's or opponent's grave- yard
	 */
	public SideBar(Board b, boolean isMe){
		this.b=b;
		this.setPreferredSize(new Dimension(50,b.frame.getHeight()-50));
		this.isMe=isMe;
		if(isMe)	this.setBackground(Color.BLUE);
		else	this.setBackground(Color.RED);
	}
	/**
	 * the function that draws the Bar
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Player tool;
		if(isMe) {
			g.setColor(Color.blue);
			tool=b.me;
		}
		else {
			g.setColor(Color.red);
			tool=b.opponent;
		}
		g.setFont(new Font(null, 0, 25));
		for(int i=0;i<11;i++){
			g.drawImage(Tool.tool[i].getImage(), 0, this.getHeight()/11*i,  this.getWidth(), this.getHeight()/11, null);
			g.drawString(HelpFunc.DeadOfType(tool, i)+"", 30, this.getHeight()/11*(i+1)-15);
			g.drawLine(0, this.getHeight()/11*(i+1), this.getWidth(),  this.getHeight()/11*(i+1));
		}
	}
}