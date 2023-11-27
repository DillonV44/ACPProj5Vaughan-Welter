/****************************************************
 
@author Dillon Vaughn, Anthony Welter
File Name: Sever.java
COP4027	Project #: 5

****************************************************/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {
	
	public static void main(String[] args) throws IOException {
		
		final int SBAP_PORT = 8888;
		System.out.println("Server is running, waiting for clients to connect...");
		
		try (ServerSocket server = new ServerSocket(SBAP_PORT) ) {
			while (true) {
				Socket s = server.accept();
				MusicRequest musicRequest = new MusicRequest(s);
				Thread t = new Thread(musicRequest);
				t.start(); 
			}
		} catch(IOException ex) {
			System.err.println("Error starting thread.");
		} 
	}
}