package network;

import javax.swing.ImageIcon;

public class Tool {
	// the tools in the game
	public static ImageIcon[] tool = new ImageIcon[12]; // an array of all the pictures
	public static ImageIcon[] toolred = new ImageIcon[12]; // an array of all the pictures
	private int type = -1; // the type number of the tool, flag is 11
	private int x=-1, y=-1; // the x and y coordination on board
	private boolean isDead = true; // whether the tool is alive
	static {
		for (int i = 0; i < 12; i++) {
			tool[i] = new ImageIcon("pic/" + i + ".PNG");
			toolred[i] = new ImageIcon("pic/" + i + "_1.PNG");
		}
	}

	public Tool(int t) {
		this.type = t;
		if (tool == null)
			updatePic();
	}

	public void updatePic() {
		tool = new ImageIcon[12];
	}

	public void kill() {
		//kills the tool and sends it to (-1,-1)
		isDead = true;
		x=-1;
		y=-1;
	}

	public void start(int x, int y) {
		//brings the tool to life
		this.x=x;
		this.y=y;
		isDead = false;
	}

	public boolean isDead() {
		// returns if the tool is dead
		return isDead;
	}

	public Point getPlace() {
		// returns the place of the tool
		return (new Point(x, y));
	}

	public int getType() {
		// returns the type of the tool
		return type;
	}

	public void setLocation(int x, int y) {
		// changes the tool location to the x,y given
		this.x = x;
		this.y = y;
	}
	public String toString() {
		String ans="";
		ans+= x+"##"+y+"##"+type;
	return ans;
	}
}