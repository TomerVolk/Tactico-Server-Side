package network;

/**
 * One player at the game
 * @author tomer
 */
public class Player {
	/**
	 * all the tools this player has
	 */
	private Tool[] tools= new Tool[40];
	/**
	 * indicates whether the tool is me or the opponent
	 */
	private boolean isMe;
	/**
	 * constructor that initiate all the tools and the isMe field
	 * @param isMe indicates if the player is me or not
	 */
	public Player(boolean isMe){
		this.isMe=isMe;
		tools[0]= new Tool(11); //creates the flag
		for(int i=1;i<7;i++){		//creates the bombs
			tools[i]=new Tool(0);
		}
		tools[7]= new Tool(1);	//creates the spy
		for(int i=8;i<16;i++){	//creates the level 2
			tools[i]=new Tool(2);	
		}
		for(int i=16;i<21;i++){	//creates the level 3
			tools[i]=new Tool(3);	
		}
		for(int i=21;i<25;i++){//create level 4
			tools[i]=new Tool(4);	
		}
		for(int i=25;i<29;i++){//creates level 5
			tools[i]=new Tool(5);	
		}
		for(int i=29;i<33;i++){//creates level 6
			tools[i]=new Tool(6);	
		}
		for(int i=33;i<36;i++){//creates level 7
			tools[i]=new Tool(7);	
		}
		for(int i=36;i<38;i++){//creates level 8
			tools[i]=new Tool(8);	
		}
		tools[38]= new Tool(9); //creates level 9
		tools[39]=new Tool(10); //creates level 10
	}
	/**
	 * @return if the player is me or not
	 */
	public boolean isMe(){
		return isMe;
	}
	/**
	 * @return an array of all the tools this player has
	 */
	public Tool[] getTools(){
		return tools;
	}
	/**
	 * changes the location of a certain tool
	 * @param x the x where the tool needs to be moved
	 * @param y the y where the tool needs to be moved
	 * @param i the index of the tool that need to be changed
	 */
	public void setTool(int x, int y, int i){
		tools[i].setLocation(x, y);
	}
}
