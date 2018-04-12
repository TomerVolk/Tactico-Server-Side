package network;
import network.Board.Place;

/**
 * the class responsible for all calculations of the game
 * @author tomer
 */
public class Game{
	/**
	 * indicates at which stage the game is (organizing period, game or if someone lost
	 * @author tomer
	 */
	public enum Status{
		flag,org, play, YouWin, OpponentWin, waiting
	}
	Status status=Status.flag;
	/**
	 * the last mouse position (x,y coordinates)
	 */
	private int xMouse, yMouse; 
	/**
	 * the index of the tool the player clicked on
	 */
	int typeClicked =-1;
	/**
	 *used in the beginning, the type the player clicked on and how many tools he put
	 */
	int counter=0;
	/**
	 * the place where the last clicked was (left, right or center)
	 */
	private Place place;
	/**
	 * the main frame and the place where all graphics happen
	 */
	private Board board;
	/**
	 * the indexes of the tool from the opponent from last fight
	 */
	int opToolFight=-1;
	/**
	 * the indexes of the tool from this player from last fight
	 */
	int MeToolFight=-1;
	/**
	 * the index of button pressed last time
	 */
	private int button;
	/**
	 * indicates if the last move was a fight
	 */
	private boolean IsFight;
	/**
	 * the constructor of the class, initiates the board
	 * @param b the board (main frame of the game)
	 */
	public Game(Board b){
		board=b;
	}
	/**
	 *the action which runs the game
	 */
	private void game(){
		if(status==Status.org) {
			autoOrganize();
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
		System.out.println("client started");
		board.doSomething=false;
	}
	/**
	 *the part of the game when each player organizes his tools
	 */
	private void organize() {
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
	/**
	 * Runs one turn in the main part of the game
	 */
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
		System.out.println("game play "+opToolFight+" me- "+ MeToolFight);
		fight(opT,typeClicked);
		board.repaint();
		typeClicked=-1;
		board.client.send("turn");

	}
	/**
	 * Gets a point and checks if the tool clicked on can go there
	 * @param p the point where the click was made
	 * @return if the tool can go there
	 */
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
	/**
	 * does all the calculations if the player decides to fight
	 * @param opT the opponent's tool's index in the fight
	 * @param meT this player's tool's index in the fight
	 */
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
	/**
	 * gets the click from the board and updates the relevant fields
	 * @param x the x coordinate of the mouse according to the location
	 * @param y the y coordinate of the mouse according to the location
	 * @param place the place where the clicked happened (left, right or center)
	 * @param button the index of the button clicked
	 */
	public void getClick(int x, int y, Place place, int button){
		xMouse=x;
		yMouse=y;
		this.place=place;
		this.button=button;
		game();
		board.main.repaint();

	}
	/**
	 * analyzes the data from server when sent a single opponent tool
	 * @param serverString- the data from the server
	 */
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
				System.out.println("game tool analisys "+opToolFight+" me- "+ MeToolFight);
			}
			board.opponent.getTools()[type].setLocation(x, 9-y);
			board.repaint();
		}
	}
	/**
	 * analyzes the data from server in cases of fight
	 * @param serverString the data from server
	 */
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
			if(meTool[0]!=null&&meTool[1]!=null&&meTool[2]!=null) {
				x= Integer.parseInt(meTool[1]);
				y= Integer.parseInt(meTool[2]);
				type= Integer.parseInt(meTool[3]+"");
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
			System.out.println(MeToolFight+" me at kills my tool");
			board.repaint();
			board.me.getTools()[type].kill();
		}

		String [] opTool=tools[1].split("##");
		//seperates the opponent tool into fields
		if(!opTool[0].equals("k")) {
			IsFight=true;
			ToolAnalasys(tools[1]);
		}
		else {
			//kills opponent tool
			int type=Integer.parseInt(opTool[1]);
			this.opToolFight=type;
			System.out.println("game opponent tool killed "+opToolFight+" me- "+ MeToolFight);
			board.opponent.getTools()[type].kill();
		}
	}
}