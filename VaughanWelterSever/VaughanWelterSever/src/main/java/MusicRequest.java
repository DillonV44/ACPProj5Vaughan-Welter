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
        	 instrumentDB = new InstrumentDB();
    	   try {
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	   	// create scanners and print writer 
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            doService();            
         } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		String queryResponse = "NULL";
		 
		if (command.equals("RETRIEVE")) {
			type = in.next();
			brand = in.next();
			maxCost = in.nextDouble();
			location = in.next();
			
			String queryString = queryStringBuilder(type, brand, maxCost, location);
			System.out.println(queryString);
			queryResponse = executeQuery(queryString);
			System.out.println(queryResponse);
		}
		 
		System.out.println("Here is what I got from the client");
		 
		System.out.println("Type is: " + type + "\nBrand is: " + brand + "\nMax Cost is: " + maxCost + "\nLocation is: " + location);
 
		out.println(queryResponse);
		out.flush();
	 }
	 
	 public String queryStringBuilder(String type, String brand, Double maxCost, String location) {
		 StringBuffer queryString = new StringBuffer();
		 
		 queryString.append("""
				SELECT
				    u.instName,
				    u.descrip,
				    u.cost,
				    b.quantity,
				    x.address
				FROM
				    Instruments u
				JOIN
				    Inventory b ON u.instNumber = b.iNumber
				JOIN
				    Locations x ON x.locNumber = b.lNumber
		 		""");
		 
		 if (type != "ALL") {
			 queryString.append("WHERE u.instName = '" + type + "'");
			 
		 }
		 
		 return queryString.toString();
	 }
	 
	 public String executeQuery(String queryString) {
		 
		 
		 
		 String response;
		try {
			response = instrumentDB.runQueries(queryString);
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed query";
	 }
	 
	 
	 
   
}
