package network;

import network.Board.Place;



public class Game{
	//the class where the calculations run.
	public enum Status{
		flag,org, play, YouWin, OpponentWin, waiting
	}
	Status status=Status.flag;
	private int xMouse, yMouse; //the last mouse position
	int typeClicked =-1, counter=0; //used in the start, the type the player clicked on and how many tools he put
	private Place place;
	private Board board;
	int opToolFight=-1, MeToolFight=-1;
	private int button;
	private boolean IsFight;
	public Game(Board b){
		board=b;
	}
	private void game(){
		//the action which runs the game
		if(status==Status.org) {
			organize();
		}
		if(status==Status.flag){
			if(place==Place.main && yMouse<4){
				board.me.getTools()[0].start(xMouse,yMouse);
				board.repaint();
				status= Status.org;
				counter=1;
			}
		}
		if(status==Status.play) {
			//the game itself
			play();
		}
	}
	private void autoOrganize() {
		for(int i=0;i<40;i++) {
			board.me.getTools()[i].start(i%10, i/10);
		}
		status=Status.waiting;
		board.client = new Client(board);
		board.doSomething=false;
	}
	private void organize() {
		//the part of the game when each player organizes his tools

		//checks if there is a tool in the location and erases it
		if(button==3 && place == Place.main) {
			int i= HelpFunc.ToolByLoc(xMouse, yMouse, board.me);
			if(i> 0) {
				board.me.getTools()[i].kill();
				counter--;
				board.repaint();
			}
			return;
		}
		//checks if need to set tool number
		if(place == Place.left) {
			typeClicked= yMouse;
		}
		if(typeClicked!=-1){
			if(yMouse< 4 && place== Place.main) {
				int i= HelpFunc.FirstDead(board.me, typeClicked);
				int j= HelpFunc.ToolByLoc(xMouse, yMouse, board.me);
				if(i!=-1 && j== -1) {
					board.me.getTools()[i].start(xMouse,yMouse);
					counter++;
					typeClicked=-1;
					board.repaint();
				}
			}
		}
		//the end of organizing period

		if(counter== 40) {
			//connection to server
			status=Status.waiting;
			board.client = new Client(board);
			board.doSomething=false;

		}
	}
	private void play() {
		if(board.me.getTools()[0].isDead()) {
			status=Status.OpponentWin;
		}

		if(board.opponent.getTools()[0].isDead()&&status==Status.play) {
			status=Status.YouWin;
		}

		if(typeClicked== -1) {
			//the first click of the player at his turn (choosing a tool)
			typeClicked= HelpFunc.ToolByLoc(xMouse, yMouse, board.me);
			return;
		}
		if(!isValidClick(board.me.getTools()[typeClicked].getPlace())) {
			//if his press isn't valid, he changes tool
			typeClicked=HelpFunc.ToolByLoc(xMouse, yMouse, board.me);
			return;
		}
		if(HelpFunc.ToolByLoc(xMouse, yMouse, board.opponent)==-1) {
			//he pressed on an empty spot and moves to it
			Tool tool= board.me.getTools()[typeClicked];
			tool.setLocation(xMouse, yMouse);
			board.client.send("tool##"+ HelpFunc.turnOneToText(board.me, typeClicked));
			typeClicked=-1;
			return;
		}
		//he should fight the opponent tool
		int opT=HelpFunc.ToolByLoc(xMouse, yMouse, board.opponent);
		this.opToolFight=opT;
		this.MeToolFight=typeClicked;
		System.out.println(opT+" opt at "+board.opponent.getTools()[opT].getPlace());
		fight(opT,typeClicked);
		board.repaint();
		typeClicked=-1;
		board.client.send("turn");

	}
	private boolean isValidClick(Point p) {
		//checks whether the click was valid
		int x=(int) p.getX();
		int y=(int) p.getY();
		int type=board.me.getTools()[typeClicked].getType();
		if(type==11||type==0) return false;
		if(xMouse<0||xMouse>9||yMouse<0||yMouse>9) return false;
		if(x==xMouse&&y==yMouse) return false;
		if(type==2) {
			if(!(x!=xMouse^y!=yMouse)) 		return false;
			if(xMouse==x) {
				int min=Math.min(yMouse, y), max=Math.max(yMouse, y);
				for(int i=min+1;i<max;i++) {
					if(HelpFunc.ToolByLoc(x, i, board.me)!=-1||HelpFunc.ToolByLoc(x, i, board.opponent)!=-1) {
						return false;
					}
				}
			}
			if(yMouse==y) {
				int min=Math.min(xMouse, x), max=Math.max(xMouse, x);
				for(int i=min+1;i<max;i++) {
					if(HelpFunc.ToolByLoc(i, y, board.me)!=-1||HelpFunc.ToolByLoc(i, y, board.opponent)!=-1) return false;
				}
			}

		}
		else {
			if(!(Math.abs(x-xMouse)==1&&y==yMouse)&&!(Math.abs(y-yMouse)==1&&x==xMouse)) return false;
		}
		if(HelpFunc.ToolByLoc(xMouse, yMouse, board.me)!=-1) return false;
		return true;
	}
	private void fight(int opT, int meT) {
		Tool meTool=board.me.getTools()[meT],opTool=board.opponent.getTools()[opT];
		if(meTool.getType()==opTool.getType()) {
			meTool.kill();
			opTool.kill();
			board.client.send("fighttoolk##"+meT+"toolk##"+opT);
			board.client.send("turn");
			return;
		}
		if((meTool.getType()>opTool.getType()&&opTool.getType()!=0)||(meTool.getType()==1&& opTool.getType()==10)||(meTool.getType()==3&&opTool.getType()==0)||opTool.getType()==11) {
			//I won the fight
			board.me.getTools()[meT].setLocation(opTool.getPlace().x, opTool.getPlace().y);
			opTool.kill();
			board.client.send("fighttool##"+HelpFunc.turnOneToText(board.me, meT)+"toolk##"+opT);
			board.client.send("turn");
			return;
		}
		if(opTool.getType()>meTool.getType()||(opTool.getType()==0&&meTool.getType()!=3)) {
			//the opponent won the fight
			meTool.kill();
			board.client.send("fighttoolk##"+meT+"tool##"+HelpFunc.turnOneToText(board.opponent, opT));
			board.client.send("turn");
			return;
		}
	}
	public void getClick(int x, int y, Place place, int button){
		xMouse=x;
		yMouse=y;
		this.place=place;
		this.button=button;
		game();
		board.main.repaint();

	}
	public void ToolAnalasys(String serverString) {
		String[] data= serverString.split("tool");
		for(int i=0;i<data.length;i++) {
			int x=0, y=0, type=0;
			String[] tool= data[i].split("##");
			if(tool.length==4&&tool[0]!=null&&tool[1]!=null&&tool[2]!=null) {
				x= Integer.parseInt(tool[1]);
				y= Integer.parseInt(tool[2]);
				type= Integer.parseInt(tool[3]+"");
			}
			if(IsFight) {
				this.opToolFight=type;
				IsFight=false;
				board.repaint();
			}
			board.opponent.getTools()[type].setLocation(x, 9-y);
			board.repaint();
		}
	}
	public void fightAnalasys(String serverString) {
		//Analyzes the data from server if fight
		serverString=serverString.substring(5, serverString.length());
		//deletes the fight at the beginning of the string
		String [] tools= serverString.split("tool");
		//seperates the string to 2 tools
		if(tools.length<3) return;
		String [] meTool=tools[2].split("##");
		//seperates my tool into fields
		if(!meTool[0].equals("k")) {
			int x=0, y=0, type=-1;
			String[] tool= meTool[0].split("##");
			if(tool.length==4&&tool[0]!=null&&tool[1]!=null&&tool[2]!=null) {
				x= Integer.parseInt(tool[1]);
				y= Integer.parseInt(tool[2]);
				type= Integer.parseInt(tool[3]+"");
				this.MeToolFight=type;
				board.repaint();
			}
			if(type!=-1)	board.me.getTools()[type].setLocation(x, y);
			board.repaint();
		}		
		else {
			//kills my tool
			int type=Integer.parseInt(meTool[1]);
			this.MeToolFight=type;
			board.repaint();
			board.me.getTools()[type].kill();
		}

		String [] opTool=tools[1].split("##");
		//seperates the opponent tool into fields
		if(!opTool[0].equals("k")) {
			ToolAnalasys(tools[1]);
			IsFight=true;
		}
		else {
			//kills opponent tool
			int type=Integer.parseInt(opTool[1]);
			this.opToolFight=type;
			board.opponent.getTools()[type].kill();
		}
	}
}