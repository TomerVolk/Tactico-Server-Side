package network;
import java.io.*;
import java.net.*;



public class Client {
	Socket socket;
	BufferedWriter out;

	public Client(Board board){
		try{ 
			socket=new Socket("192.168.1.112",12345);  
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			NetworkRead readThread=new NetworkRead(socket,board);
			Thread read=new Thread(readThread);
			read.start();

		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to server.");
			System.exit(1);
		}
	}


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
