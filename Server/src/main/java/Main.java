/**
* Main is the driver method of a random car generator, 
* and SQL manager.
* @author Dillon Vaughan
* @Version 0.1
* @Date Sep 17, 2023
*/
public class Main 
{
   public static void main(String[] args) throws Exception
   {  
	  // Creating and saving random vehicles 
	  VehicleAndSQLLogger fw = new VehicleAndSQLLogger();
	  fw.printVehicles();
	  // Creating and running a new database run.
	  RentalDataBaseHandler RD = new RentalDataBaseHandler();
	  RD.DataBaseRun();

   }
}
