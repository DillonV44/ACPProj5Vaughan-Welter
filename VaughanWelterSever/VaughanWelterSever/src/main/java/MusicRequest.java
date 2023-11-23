/****************************************************
 
@author Dillon Vaughan, Anthony Welter
File Name: MusicRequest.java
COP4027	Project #: 5

****************************************************/
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MusicRequest implements Runnable {
   private Socket s;
   private Scanner in;
   private PrintWriter out;
   private InstrumentDB instrumentDB;
   
   public MusicRequest(Socket aSocket) {
	   s = aSocket;
	
   }
   
   /**
    * Run is the method to be used from class Runnable
    * run will set up the scanner and print writer, and then factor out
    * Functionality 
    */
   public void run()
   {
      try
      {
         try
         {	
           // Try creating the db, drop it first, then create the db.
    	   try {
    			instrumentDB = new InstrumentDB();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	   	// create scanners and print writer 
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            doService();            
         }
         finally
         {
            s.close();
         }
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
   }
   
   
    /**
	*  Executes all commands until the end of input.
	*/
	public void doService() throws IOException
	{      
	   while (true)
	   {  
	      if (!in.hasNext()) return;
	      String command = in.next();
	              
	      executeCommand(command);
	   }
	}
	
	
	 /**
	    Executes a single command.
	    @param command the command to execute
	 */
	 public void executeCommand(String command) {
		 
		System.out.println("Command at start of executeCommand is " + command);
		 
		String type = "";
		String brand = "";
		double maxCost = 0;
		String location = "";
		 
		if (command.equals("RETRIEVE")) {
			type = in.next();
			brand = in.next();
			maxCost = in.nextDouble();
			location = in.next();
		}
		 
		System.out.println("Here is what I got from the client");
		 
		System.out.println("Type is: " + type + "\nBrand is: " + brand + "\nMax Cost is: " + maxCost + "\nLocation is: " + location);
 
		out.println("Thanks for the info");
		out.flush();
	 }	
   
}
