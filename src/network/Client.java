package network;
import java.io.*;
import java.net.*;

/**
 *the class responsible for server communication
 * @author tomer
 */
public class Client {
	/**
	 * the socket of the server
	 */
	Socket socket;
	/**
	 * the field for writing to server 
	 */
	BufferedWriter out;
	/**
	 * the constructor of the Client
	 * initiate all fields and starts communicating with server 
	 * @param board- the main frame and all the graphics
	 */
	public Client(Board board){
		try{ 
			socket=new Socket("192.168.174.205",12345); 
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			NetworkRead readThread=new NetworkRead(socket,board);
			Thread read=new Thread(readThread);
			send("computer");
			read.start();
		}
		catch(IOException e){
			System.out.println("failed to connect");
			//System.exit(1);
			board.stuck();
		}
	}


	/**
	 * the function that sends the data to server
	 * @param line- the string to send to the server
	 */
	public void send(String line){
		try{
			out.write(line + "\n");
			out.flush();

		}catch (IOException e){
			System.err.println("Couldn't read or write");
			System.exit(1);
		}

	}
	
}
