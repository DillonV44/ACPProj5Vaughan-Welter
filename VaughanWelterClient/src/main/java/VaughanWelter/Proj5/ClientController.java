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
      
      String command = "RETRIEVE ALL ALL 0 ALL\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      String response = in.nextLine();
      System.out.println("Receiving: " + response);
      
      
      
      command = "RETRIEVE guitar ALL 0 ALL\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response);
      
      s.close();
   }

   

}
