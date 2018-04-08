package network;

public class Player {
	//the players in the game
	private Tool[] tools= new Tool[40];// all the tools this player has
	private boolean isMe; //return whether the tool is me or the opponent
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
	public boolean isMe(){
		return isMe;
	}
	public Tool[] getTools(){
		return tools;
	}
	public void setTool(int x, int y, int i){
		tools[i].setLocation(x, y);
	}
}
