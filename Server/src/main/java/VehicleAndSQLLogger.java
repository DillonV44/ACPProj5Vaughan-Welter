import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * Class VehicleAndSQLLogger is the class responsible for
 * printing out information for both vehicles and sql commands. 
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
public class VehicleAndSQLLogger {
	VehicleManager vehicleManager;
	private String completeVehicleInfo;
	private static final String vehicleFile = "Vehicles.csv";
	private static final String SQLLoggerFile = "dbOperations.log";
	
	
	/**
	 * printVechilces creates a vehicle manager object that will
	 * create 10 random vehicles and save them to a csv file
	 */
	public void printVehicles() {
		try {
			// Vehicle manager takes care of creating random cars.
			 System.out.println("Starting the process of making 10 random vehciles and saving to a csv file.");
			 vehicleManager = new VehicleManager();
			 
			 // The generate object in vehicle manager creates 10 random vehicles
			 // and returns a string of those vehicles
			 completeVehicleInfo = vehicleManager.generate();
			 System.out.println("Completed random car making process.");
			 
			 // print to Car File will save the String object of 
			 // 10 random cars to the vehicle file Vechicles.csv
			 printToCarFile(completeVehicleInfo);
			 
			 
			 
			}
			catch (Exception e) {
				System.out.println("Random car generator failed.");
				System.out.println(e);
			}
			
	}
	
	/**
	 * printToCarFile is the method that writes the information of random
	 * cars to a file
	 * @param info takes in the entire string object of random cars and
	 * outputs it to a file
	 * @throws IOException thrown exception of writing to a file does not work.
	 */
	public void printToCarFile(String info) throws IOException {
		BufferedWriter writeToCar = new BufferedWriter(new FileWriter(vehicleFile));
		writeToCar.write(info);
		System.out.println("Successfully saved the random cars to the csv file: " + vehicleFile + "\n");
		writeToCar.close();
	}
	
	/**
	 * printToSQLFile takes a string object of info which is compromised 
	 * of SQL executions to log and writes it to the SQL logger file.
	 * @param info Takes in a string object of SQL commands
	 * @throws IOException thrown exception of writing to a file does not work.
	 */
	public void printToSQLFile(String info) throws IOException {
		BufferedWriter writeToCar = new BufferedWriter(new FileWriter(SQLLoggerFile));
		writeToCar.write(info);
		System.out.println("Successfully saved the SQL logging iformation to the csv file: " + SQLLoggerFile + "\n");
		writeToCar.close();
	}
	
}
