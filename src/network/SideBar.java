package network;

import java.awt.*;
import javax.swing.*;
public class SideBar extends JPanel{
	//the left panel- this player's grave yard
	private static final long serialVersionUID = 1L;
	private Board b;
	private boolean isMe;
	public SideBar(JFrame fr, Board b, boolean isMe){
		this.b=b;
		this.setPreferredSize(new Dimension(50,fr.getHeight()-50));
		this.isMe=isMe;
		if(isMe)	this.setBackground(Color.BLUE);
		else	this.setBackground(Color.RED);
	}
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