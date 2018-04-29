package network;
import java.io.*;
import java.net.*;
/**
 * the class that gets the data from sever and pass it to the right place
 * @author tomer
 *
 */
class NetworkRead implements Runnable{
	/**
	 * the server socket
	 */
	Socket socket;
	/**
	 * the frame of the game
	 */
	Board board;
	/**
	 * the id of that player
	 */
	int id;
	/**
	 * indicates who's turn is it
	 */
	int turn;

	/**
	 * the constructor that initiates the fields
	 * @param socket the server socket
	 * @param board2 the board 
	 */
	public NetworkRead(Socket socket, Board board2){
		this.socket=socket;
		this.board=board2;

	}
	/**
	 * the function that runs and keep updating from the server
	 */
	public void run(){
		try{

			BufferedReader in= new BufferedReader( new InputStreamReader(socket.getInputStream()));
			String serverString;

			while ((serverString=in.readLine())!=null)
			{


				if(serverString.startsWith("Wait for start")) {
					String mar[]=serverString.split("##");
					id=Integer.parseInt(mar[1]);
					board.id = id;
					board.frame.setTitle("Welcome to game. Your id="+id+". Wait for start the game ");
				}
				else
					if(serverString.startsWith("start")) {
						String mar[]=serverString.split("##");
						turn=Integer.parseInt(mar[1]);
						//create data string
						String data=HelpFunc.turnAllToText(board.me);
						board.client.send("data"+data);
						board.info.repaint();

					}
					else
						if(serverString.startsWith("data")) {
							board.startGame(turn, serverString);
						}
						else
							if(serverString.startsWith("tool")) {
								board.game.ToolAnalasys(serverString);
								int p1=HelpFunc.numberAlive(board.me), p2=HelpFunc.numberAlive(board.opponent);
								if(id==0) {
									board.client.send("turn##"+p1+"##"+p2);
								}
								else {
									board.client.send("turn##"+p2+"##"+p1);
								}
							}
							else
								if(serverString.startsWith("fight")) {
									board.game.fightAnalasys(serverString);
									int p1=HelpFunc.numberAlive(board.me), p2=HelpFunc.numberAlive(board.opponent);
									if(id==0) {
										board.client.send("turn##"+p1+"##"+p2);
									}
									else {
										board.client.send("turn##"+p2+"##"+p1);
									}
								}
								else
									if(serverString.startsWith("turn")) {
										turn = 1-turn;
										board.nextTurn(turn);

									}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Disconnected from server");
		System.exit(0);
	}
}