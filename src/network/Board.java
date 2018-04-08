package network;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import network.Game.Status;



public class Board extends JPanel implements MouseListener,WindowListener{
	//the class who is responsible for the graphics.
	public enum Place{
		right, left, main;
	}
	static final long serialVersionUID = 1L;
	JFrame frame;
	SideBar leftBar;
	SideBar rightBar;
	InfoBar info;
	Player opponent= new Player(false);
	Player me=new Player(true);
	MainPanel main;
	Game game;
	Client client;
	int id;
	boolean doSomething = true;; 
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
			rightBar=new SideBar(frame, this, true);
			this.add(rightBar, BorderLayout.WEST);
			leftBar=new SideBar(frame, this, false);
			this.add(leftBar, BorderLayout.EAST);
		}
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.addWindowListener(this);
		frame.addMouseListener(this);
	}
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
	public void mouseClicked(MouseEvent e) {
		if(!doSomething) return;
		if(!frame.contains(e.getPoint())||e.getY()<50) {
			System.out.println("out of frame");
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
	public void windowClosing(WindowEvent arg0) {
		if(game != null) {
			System.out.println("game");
			if(client!=null) {
				client.send("bye");
			}
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
