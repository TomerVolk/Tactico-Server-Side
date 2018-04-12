package network;
import java.awt.*;
import javax.swing.JPanel;
import network.Game.Status;
/**
 * the panel where the board is, and where the tools are
 * @author tomer
 */
public class MainPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	/**
	 * the frame of the board
	 */
	private Board board;

	/**
	 * the constructor, initiates the board field and the size and background
	 * @param b
	 */
	public MainPanel(Board b){
		this.board=b;
		this.setPreferredSize(new Dimension(b.frame.getWidth()-100, b.frame.getHeight()-50));
		this.setBackground(new Color(0, 153, 0));
	}
	/**
	 * the function that draws the panel, including all the tools
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(board.game.status==Status.OpponentWin) {
			g.setFont(new Font(Font.BOLD+"", Font.BOLD, 200));
			g.drawString("You \n Lost", 500, 500);
			return;
		}
		if(board.game.status==Status.YouWin) {
			g.setFont(new Font(Font.BOLD+"", Font.BOLD, 200));
			g.drawString("You \n Won", 500, 500);
			return;
		}
		for(int i=0;i<40;i++){
			Tool[] OTools=board.opponent.getTools();
			if (!OTools[i].isDead()) {
				Point p= OTools[i].getPlace();
				g.setColor(Color.red);
				g.fillRect(this.getWidth()/10*(int) p.getX(), this.getHeight()/10*(int)p.getY(),  this.getWidth()/10, this.getHeight()/10);
				g.setColor(Color.BLACK);
			}
		}
		
		g.setColor(Color.black);
		for(int i=1;i<10;i++){
			g.drawLine(this.getWidth()/10*i, 0, this.getWidth()/10*i, this.getHeight());
			g.drawLine(0, this.getHeight()/10*i, this.getWidth(), this.getHeight()*i/10);
		}
		for(int i=0;i<40;i++){
			Tool[] myTools=board.me.getTools();
			if (!myTools[i].isDead()&& i!=board.game.typeClicked) {
				Point p= myTools[i].getPlace();
				g.drawImage(Tool.tool[myTools[i].getType()].getImage(), this.getWidth()/10*(int) p.getX(), this.getHeight()/10*(int)p.getY(),  this.getWidth()/10, this.getHeight()/10, null);
			}
			if (!myTools[i].isDead()&& i==board.game.typeClicked) {
				Point p= myTools[i].getPlace();
				g.drawImage(Tool.toolred[myTools[i].getType()].getImage(), this.getWidth()/10*(int) p.getX(), this.getHeight()/10*(int)p.getY(),  this.getWidth()/10, this.getHeight()/10, null);
			}
		}
		
	}
}