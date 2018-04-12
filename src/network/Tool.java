package network;

import javax.swing.ImageIcon;

/**
 * the tools in the game
 * @author tomer
 */
public class Tool {
	/**
	 * an array of all the pictures
	 */
	public static ImageIcon[] tool = new ImageIcon[12];
	/**
	 * an array of all the pictures marked
	 */
	public static ImageIcon[] toolred = new ImageIcon[12]; 
	/**
	 * the type number of the tool
	 * flag is 11, bomb is 0
	 */
	private int type = -1;
	/**
	 * the x coordination on board
	 */
	private int x=-1;
	/**
	 * the y coordination on board
	 */
	private int y=-1;
	/**
	 * whether the tool is dead
	 */
	private boolean isDead = true;
	static {
		for (int i = 0; i < 12; i++) {
			tool[i] = new ImageIcon("pic/" + i + ".PNG");
			toolred[i] = new ImageIcon("pic/" + i + "_1.PNG");
		}
	}

	/**
	 * Initiate a tool of a certain type
	 * @param t the type of the tool
	 */
	public Tool(int t) {
		this.type = t;
		if (tool == null)
			updatePic();
	}

	/**
	 * if the image array is null, initiates it
	 */
	public void updatePic() {
		tool = new ImageIcon[12];
	}

	/**
	 * kills the tool and sends it to (-1,-1)
	 */
	public void kill() {
		isDead = true;
		x=-1;
		y=-1;
	}

	/**
	 * brings the tool to life
	 * @param x the x coordinate where the tool is starting
	 * @param y the y coordinate where the tool is starting
	 */
	public void start(int x, int y) {
		this.x=x;
		this.y=y;
		isDead = false;
	}

	/**
	 * @return if the tool is dead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @return the place of the tool
	 */
	public Point getPlace() {
		return (new Point(x, y));
	}

	/**
	 * @return the type of the tool
	 */
	public int getType() {
		return type;
	}

	/**
	 * changes the tool location
	 * @param x  the x coordinate where the tool is moved
	 * @param y  the y coordinate where the tool is moved
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * A string describing the tool
	 */
	public String toString() {
		String ans="";
		ans+= x+"##"+y+"##"+type;
	return ans;
	}
}