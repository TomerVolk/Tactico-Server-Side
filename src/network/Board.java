package network;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import network.Game.Status;
/**
 * the class who is responsible for the graphics.
 * All the parameters are for the use of all classes
 *@author Tomer Volk
 */
public class Board extends JPanel implements MouseListener,WindowListener{
	static final long serialVersionUID = 1L;
	/**
	 * The place of the click- whether it was at the sides of center panels
	 */
	public enum Place{
		right, left, main;
	}
	/**
	 * The main frame of the game
	 */
	JFrame frame;
	/**
	 * the output of the retry panel
	 */
	int retry; 
	/**
	 * The Bar at the left side of the board
	 */
	SideBar leftBar;
	/**
	 * The Bar at the right side of the board
	 */
	SideBar rightBar;
	/**
	 * The Bar at the up side of the board
	 */
	InfoBar info;
	/**
	 * The panel of the board itself
	 */
	MainPanel main;
	/**
	 * The Opponent player
	 */
	Player opponent= new Player(false);
	/**
	 * The Current player
	 */
	Player me=new Player(true);
	/**
	 * The Calculations of the project
	 */
	Game game;
	/**
	 * The Class responsible of the connection to the server
	 */
	Client client;
	/**
	 * The id of the player
	 */
	int id;
	/**
	 * a field that indicates whether the player can move or act
	 */
	boolean doSomething = true;
	/**
	 * the constructor of the Class, that actually starts the game
	 * gives initial values to all fields and builds initial graphics
	 */
	public Board(){
		game=new Game(this);
		frame=new JFrame("Tactico");
		frame.setSize(1000, 950);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout=new BorderLayout();
		this.setLayout(layout);
		main=new MainPanel(this);
		this.add(main, BorderLayout.CENTER);
		if(game.status!=Status.OpponentWin&&game.status!=Status.YouWin) {
			info=new InfoBar(this);
			this.add(info, BorderLayout.NORTH);
			rightBar=new SideBar(this, true);
			this.add(rightBar, BorderLayout.WEST);
			leftBar=new SideBar(this, false);
			this.add(leftBar, BorderLayout.EAST);
		}
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.addWindowListener(this);
		frame.addMouseListener(this);
	}
	/**
	 * gets the initial data from the server
	 * @param turn- who's turn it is according to server
	 * @param serverString- the data from the string
	 */
	public void startGame(int turn, String serverString) {
		// analyzing the data from server at the beginning
		if(id == turn) {
			frame.setTitle("Start game. Your turn. Your id = "+id);
			doSomething  = true;
		}
		else {
			frame.setTitle("Start game. Wait for your turn. Your id = "+id);
			doSomething = false;
		}
		String[] data= serverString.split("data");
		for(int i=0;i<data.length;i++) {
			String[] tool= data[i].split("##");
			if(tool.length==3) {
				int x=0, y=0, type=0;
				if(tool[0]!="") {
					x= Integer.parseInt(tool[0]);
				}
				if(tool[1]!="") {
					y= Integer.parseInt(tool[1]);
				}
				if(tool[2]!="") {
					type= Integer.parseInt(tool[2]);
				}
				opponent.getTools()[type].start(x,9- y);
			}
		}
		game.status=Status.play;
		repaint();
	}
	/**
	 * the function that changes turns
	 * @param turn- indicates who's turn is it
	 */
	public void nextTurn(int turn) {
		if(id == turn) {
			frame.setTitle("Game continue. Your turn. Your id = "+id);
			doSomething = true;
		}
		else {
			frame.setTitle("Game continue. Wait for your turn. Your id = "+id);
			doSomething = false;
		}

	}
	/**
	 * automatically activated when the mouse is clicked
	 */
	public void mouseClicked(MouseEvent e) {
		if(!doSomething) return;
		if(!frame.contains(e.getPoint())||e.getY()<50) {
			return;
		}
		if(e.getX()< leftBar.getWidth()){
			game.getClick(e.getX(), e.getY()/(frame.getHeight()/11), Place.left, e.getButton());
			return;
		}
		if(main.contains(e.getPoint())){
			game.getClick((e.getX()-50)/(frame.getWidth()/11), (e.getY()-50)/(frame.getHeight()/11), Place.main, e.getButton());
			return;
		}
	}
	/**
	 * closes the client and the game when X is pressed
	 */
	public void windowClosing(WindowEvent arg0) {
		if(game != null) {
			System.out.println("game");
			if(client!=null) {
				client.send("bye");
			}
		}
	}
	/**
	 * if the server gets stuck
	 */
	public void stuck() {
		System.out.println("board stuck");
		JOptionPane.showConfirmDialog(null, "failed to connect to server, would you like to retry?","Alert",this.retry);
		if(this.retry==JOptionPane.OK_OPTION) {
			client=new Client(this);
		}
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
}
