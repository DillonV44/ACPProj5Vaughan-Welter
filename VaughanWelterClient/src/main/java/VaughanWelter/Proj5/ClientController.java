package VaughanWelter.Proj5;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class ClientController {
	
   public static void main(String[] args) throws IOException
   {
      final int SBAP_PORT = 8888;
      Socket s = new Socket("localhost", SBAP_PORT);
      InputStream instream = s.getInputStream();
      OutputStream outstream = s.getOutputStream();
      Scanner in = new Scanner(instream);
      PrintWriter out = new PrintWriter(outstream); 
      
      System.out.println("TEST ONE: TESTING ALL NULL VALUES");
      String command = "RETRIEVE ALL ALL 0 0\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      String response = in.nextLine();
      System.out.println("Receiving: " + response + "\n");
      
      
      System.out.println("TEST TWO: TESTING FOR TYPE guitar");
      command = "RETRIEVE guitar ALL 0 0\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response);
      
      System.out.println("TEST THREE: TESTING FOR ALL FIELDS");
      command = "RETRIEVE guitar yamaha 700 1\n";
      System.out.print("Sending: " + command + "\n");
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response + "\n");
      
      System.out.println("TEST FOUR: TESTING FOR BRAND yamaha");
      command = "RETRIEVE ALL yamaha 0 0\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response + "\n");
      
      System.out.println("TEST FIVE: TESTING FOR COST LESS THAN 450");
      command = "RETRIEVE ALL ALL 450 0\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response + "\n");
      
      System.out.println("TEST SIX: TESTING FOR LOCATION Charlotte North Carolina by integer 2");
      command = "RETRIEVE ALL ALL 0 2\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response + "\n"); 
     
      s.close();
   }

   

}
