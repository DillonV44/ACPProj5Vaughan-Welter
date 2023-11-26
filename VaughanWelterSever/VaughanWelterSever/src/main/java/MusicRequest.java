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
        	// When doService is done, close the connection
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
	      // Grabbing the string from the client
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
		
		// setting the types of items to grab from commands
		String type = "";
		String brand = "";
		double maxCost = 0;
		int location = 0;
		
		// query response is the one line string that will be sent back to the client
		// strings with multiple lines cannot be sent using the print writer 
		String queryResponse = "NULL";
		
		// The main and only command of the program is RETRIEVE, once retrieve is 
		// hit here, then four parameters will be collected below
		if (command.equals("RETRIEVE")) {
			type = in.next();
			brand = in.next();
			maxCost = in.nextDouble();
			location = in.nextInt();
			System.out.println("I made it this far!");
			System.out.println("Type is: " + type + "\nBrand is: " + brand + "\nMax Cost is: " + maxCost + "\nLocation is: " + location);
			
			// Build the query string to search the database
			String queryString = queryStringBuilder(type, brand, maxCost, location);
			System.out.println(queryString);
			// execute the query, and in one move retrieve the response string from the database
			queryResponse = executeQuery(queryString);
			System.out.println(queryResponse);
		}
		
		 
		System.out.println("Here is what I got from the client");
		
		System.out.println("Type is: " + type + "\nBrand is: " + brand + "\nMax Cost is: " + maxCost + "\nLocation is: " + location);
		
		// Sending the response of the query to the client in one string 
		out.println(queryResponse);
		out.flush();
	 }
	 
	 /**
	  * queryStringBuilder will compile the needed query string needed to search
	  * for the desired parameters 
	  * @param type is the type of instrument
	  * @param brand is the brand of the instrument
	  * @param maxCost is the max cost, results that are less than the max cost will be returned
	  * @param location is the location in an integer representation 
	  * @return will return a string of query string to be used to search the database later in the program
	  */
	 public String queryStringBuilder(String type, String brand, Double maxCost, int location) {
		 StringBuffer queryString = new StringBuffer();
		 
		 boolean nonDefaultType = false;
		 boolean nonDefaultBrand = false;
		 boolean nonDefaultMaxCost = false;
		 boolean nonDefaultLoction = false;
		 boolean whereUsed = false;
		 
		 if (!type.contains("All")) {
			 nonDefaultType = true;
		 }
		 if (!brand.contains("All")) {
			 nonDefaultBrand = true;
		 }
		 if (maxCost != 0) {
			 nonDefaultMaxCost = true;
		 }
		 if (location != 0) {
			 nonDefaultLoction = true;
		 }
		 
		 // query string is the query string object and always starts out the same way
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
		 
		 // These if and else if statements ensure that a good SQL query is built. Each 
		 // parameter ensure that it is either the second value to be searched for, or the
		 // first 

		 
		 
		 if (nonDefaultType) {
			 if (whereUsed) {
				 queryString.append(" AND u.instName = '" + type + "' \n");
			 } else {
				 queryString.append("WHERE u.instName = '" + type + "' \n");
				 whereUsed = true;
			 }
		 }
		 
		 if (nonDefaultBrand) {
			 if (whereUsed) {
				 queryString.append(" AND u.descrip = '" + brand + "' \n");
			 } else {
				 queryString.append("WHERE u.descrip = '" + brand + "' \n");
				 whereUsed = true;
			 }
		 }
		 
		 if (nonDefaultMaxCost) {
			 if (whereUsed) {
				 queryString.append("AND u.cost <= " + maxCost + " \n");
			 } else {
				 queryString.append("WHERE u.cost <= " + maxCost + " \n");
				 whereUsed = true;
			 }
		 }
		 
		 if (nonDefaultLoction) {
			 if (whereUsed) {
				 queryString.append("AND b.lNumber = " + location + " \n");
			 } else {
				 queryString.append("WHERE b.lNumber = " + location + " \n");
				 whereUsed = true;
			 }
		 }
		 
		 
//		 if (!type.contains("All") && !brand.contains("All") && maxCost != 0 && location != 0) {
//			 queryString.append("WHERE u.instName = '" + type + "' \n");
//			 queryString.append("AND u.descrip = '" + brand + "' \n");
//			 queryString.append("AND u.cost < " + maxCost + " \n");
//			 queryString.append("AND b.lNumber = " + location + " \n");
//		 }
//		 
//		 if (!type.contains("All") && !brand.contains("All") && maxCost != 0) {
//			 queryString.append("WHERE u.instName = '" + type + "' \n");
//			 queryString.append("AND u.descrip = '" + brand + "' \n");
//			 queryString.append("AND u.cost < " + maxCost + " \n");
//		 }
//		 
//		 if (!type.contains("All") && !brand.contains("All")) {
//			 queryString.append("WHERE u.instName = '" + type + "' \n");
//			 queryString.append("AND u.descrip = '" + brand + "' \n");
//		 }
//		 
//		 if (!brand.contains("All") && maxCost != 0 && location != 0) {
//			 queryString.append("WHERE u.descrip = '" + brand + "' \n");
//			 queryString.append("AND u.cost < " + maxCost + " \n");
//			 queryString.append("AND b.lNumber = " + location + " \n");
//		 }
//		 
//		 if (maxCost != 0 && location != 0) {
//			 queryString.append("WHERE u.cost < " + maxCost + " \n");
//			 queryString.append("AND b.lNumber = " + location + " \n");
//		 }
//		 
//		 if (!type.contains("All") && brand.contains("All") && maxCost == 0 && location == 0) {
//			 queryString.append("WHERE u.instName = '" + type + "' \n");
//		 }
//		 
//		 if (!brand.contains("All") && type.contains("All") && maxCost == 0 && location == 0) {
//			 queryString.append("WHERE u.descrip = '" + brand + "' \n");
//		 }
//		 
//		 if (maxCost != 0 && type.contains("All") && brand.contains("All") && location == 0) {
//			 queryString.append("WHERE u.cost < " + maxCost + " \n");
//		 }
//		 
//		 if (location != 0 && type.contains("All") && brand.contains("All") && maxCost == 0) {
//			 queryString.append("WHERE b.lNumber = " + location + " \n");
//		 }
		 
		 
		 return queryString.toString();
	 }
	 
	 /**
	  * executeQuery will actually search the database using the query string that is 
	  * built by queryStringBuilder
	  * @param queryString is the built query string from queryStringBuilder
	  * @return will return a one line query response string of results 
	  */
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
