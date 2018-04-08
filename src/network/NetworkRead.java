package network;
import java.io.*;
import java.net.*;


class NetworkRead implements Runnable{
	Socket socket;
	Board board;
	int id;
	int turn;

	public NetworkRead(Socket socket, Board board2){
		this.socket=socket;
		this.board=board2;

	}
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
								board.client.send("turn");
							}
							else
								if(serverString.startsWith("fight")) {
									board.game.fightAnalasys(serverString);
									board.client.send("turn");
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