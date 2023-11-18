import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Public class RentalCarFileReader reads all vehicles from the file
 * readFromFile creates and returns an array of strings for each line there is in the file.
 * return 
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
public class RentalCarFileReader {
	
	private final int linesToParse = 10;
	
	/**
	 * readFromFile creates and returns an array of strings for each line there is in the file.
	 * @return returns an array of lines from the file Vehicles.csv
	 */
	public String[] readFromFile() {
		
		// Array string nextVehcile will store the array of lines 
		// from the file
		String[] nextVehicle;
		
		
		try {
			// scanning Vehicles.csv
			File myFile = new File("Vehicles.csv");
			
			Scanner fileReader = new Scanner(myFile);

			nextVehicle = new String[linesToParse];
			int vehicleIterator = 0;
			
			// While there are lines left in the file, input 
			// into the nectVehcile array
			while (fileReader.hasNextLine()) {
				nextVehicle[vehicleIterator] = fileReader.nextLine();
				vehicleIterator++;
			}
			// closing the connection
			fileReader.close();
			// returning the array 
			return nextVehicle;
		} catch (Exception e) {
			nextVehicle = null;
			System.out.println(e);
		}
		
		return nextVehicle;
		
	}
	
	/**
	 * returnEachItem creates a list of Strings that split each line passed into the method
	 * @param recievedLine is the lines separated out by readFromFile.
	 * @return returns a list of Strings that contains the objects needed from the line.
	 */
	public List<String> returnEachItem(String recievedLine) {
		// Creating the list of strings to return 
		List<String> listToReturn = Arrays.asList(recievedLine.split(","));
		// returning the list of Strings 
		return listToReturn;
		
	}
	
	

}
