import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

/**
 * RentalDataBaseHandler handles all required SQL database operations
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
public class RentalDataBaseHandler {
	
	// Java ReflectionForCar will allow for the creation of the table
	private JavaReflectionForCar reflectionObject;
	// testRunReadFromFile takes in an arentalReaderay of strings of the lines of info from the file
	String[] testRunReadFromFile;
	// RentalCarFileReader object will allow for reading vehicles from file
	RentalCarFileReader rentalReader;
	// List of reievedLinesForSQL receives a list of individual objects used for databse inserts
	List<String> recievedLineForSQL;
	// final drop db is the string needed for the sql command to drop the SQL table
	private final String dropDB = "DROP TABLE RENT_VEHICLE";
	// String buffer SQLLog will be used to log all database calls.
	private StringBuffer SQLLog = new StringBuffer();
	// SQLLogger is the object used to access logging all database calls.
	VehicleAndSQLLogger SQLLogger = new VehicleAndSQLLogger();
	
	// Final Strings for the Queries that will be ran on the table
	private final String firstQuery = "SELECT * FROM RENT_VEHICLE";
	private final String secondQuery = "SELECT * FROM RENT_VEHICLE WHERE make = 'Chevy' or make = 'Toyota'";
	private final String thirdQuery = "SELECT * FROM RENT_VEHICLE WHERE weight > 2500";
	
	public RentalDataBaseHandler() {
		reflectionObject = new JavaReflectionForCar();
		
	}
	
	/**
	 * DataBaseRun is the method that drives all of the database methods.
	 * @throws Exception throws an exception if any of the database implementations do not work
	 */
	public void DataBaseRun() throws Exception {
		
		System.out.println("Beginning of the database run.");
		SimpleDataSource.init("database.properties");
		Connection conn = SimpleDataSource.getConnection();
		Statement stat = conn.createStatement();  
		
		// dropping the database
		dropDatabase(stat);
		// creating the database 
		createTable(stat);
		
		System.out.println("------------------");
		
		// Reading from file which will also insert in the table;
		readFromFile(stat);
		
		// running the queries
		runQueries(stat);
		
		conn.close();
		System.out.println("Closing the connection to the database.\n");
		
		System.out.println("Saving the string buffer of SQL logging information to file.");
		SQLLogger.printToSQLFile(SQLLog.toString());
	} 
	
	/**
	 * create table will create the table needed for the SQL project
	 * @param stat takes the statement object created in DataBaseRun()
	 * @throws Exception throws an exception if the table can not be created 
	 */
	public void createTable(Statement stat) throws Exception {
		
		System.out.println("Creating the table");
		reflectionObject.setSQLTypes();
		List<SQLElements> sqlTypes = reflectionObject.getSQLElements();

		StringBuffer createTable = new StringBuffer();
		createTable.append("CREATE TABLE RENT_VEHICLE(");
		
		for (int i = 0; i < sqlTypes.size(); i++) {
			createTable.append(sqlTypes.get(i));
			if (i != sqlTypes.size() - 1) {
				createTable.append(",");
			}
		}
			
		createTable.append(")");
		
//		System.out.println(createTable);
		SQLLog.append(createTable.toString() + "\n");
		stat.execute(createTable.toString());
		System.out.println("Succcesfully created table using the sql command: \n" + createTable.toString() + "\n");
	}
	
	/**
	 * runResult formats the result object into a readable format able to print out
	 * @param result takes in the result object of a query
	 * @throws Exception throws an exception if this is not able to be translated 
	 */
	public void runResult(ResultSet result) throws Exception {
		
		System.out.println("Translating return object of queries into a readable format");
		ResultSetMetaData rsm = result.getMetaData();
		int cols = rsm.getColumnCount();
		while(result.next())
		{
			for(int i = 1; i <= cols; i++)
				System.out.print(result.getString(i)+" ");
			System.out.println("");      
		}


	}
	
	/**
	 * dropDataBase() drops the table 
	 * @param stat takes the statement object created in DataBaseRun()
	 */
	public void dropDatabase(Statement stat) {
		try {  
			System.out.println("Attempting to drop database table for a clean run.");
			stat.execute(dropDB); 
			SQLLog.append(dropDB + "\n");
			System.out.println("Sucessfully dropped the database table using the SQL command: \n" + dropDB + "\n");
		}
		catch (Exception e)
		{ System.out.println("drop failed"); }    
	}
	
	/**
	 * readFromFile() uses the class RentalCarFileReader to read from the file.
	 * It also uses this RentalCarFileReader to break apart each string line into
	 * each piece that it is made of
	 * @param stat takes the statement object created in DataBaseRun()
	 */
	public List<String> readFromFile(Statement stat) throws Exception {
		
		try {
			System.out.println("Beginning the process of reading vehicles from file.\n");
			rentalReader = new RentalCarFileReader();
			testRunReadFromFile = rentalReader.readFromFile();
			for (int i = 0; i < testRunReadFromFile.length; i++) {
				
				// This is where I will send the line string 
				// back to the previous file.
				// Retrieving each line from the file
				recievedLineForSQL = rentalReader.returnEachItem(testRunReadFromFile[i]);
				// Retrieving each part of the line. 
				insertEachLineMethod(stat, recievedLineForSQL);
				
			}
			System.out.println("Successfully read vehicles from file.\n");
		} catch (Exception e) {
			System.out.println("FileReader did not successfully retrive vechicles from file.");
			recievedLineForSQL = null;
		}
		return recievedLineForSQL;
		
	}
	
	/**
	 *	insertEachLine() takes an arentalReaderay list of Strings needed to create a single insert 
	 * @param stat takes the statement object created in DataBaseRun()
	 * @param stringObj is the array list of objects needed to be inserted 
	 * @throws Exception thrown if the insert does not work
	 */
	public void insertEachLineMethod(Statement stat, List<String> stringObj) throws Exception {
		try {
			String insertObject = "INSERT INTO RENT_VEHICLE VALUES ('" + stringObj.get(0) + "','" + stringObj.get(1) + "', " + stringObj.get(2) + ", " + stringObj.get(3) + ")";
			System.out.println("Inserting into table using SQL command: " + insertObject);
			SQLLog.append(insertObject + "\n"); 
			stat.execute(insertObject);
			System.out.println("Successfully insert into table.\n");
		} catch (Exception e) {
			System.out.println("Failed to insert into table\n");
			System.out.println(e);
		}
	}
	
	/**
	 * runQueires creates result sets based off of queries that are ran on the database table
	 * runQueires also passes each result set to the runResult method that will print out each query.
	 * @param stat takes the statement object created in DataBaseRun()
	 * @throws Exception
	 */
	public void runQueries(Statement stat) throws Exception {
		System.out.println("Running first query to find all vehilces from the table using SQL command: \n" + firstQuery);
		ResultSet Query1 = stat.executeQuery(firstQuery);
		SQLLog.append(firstQuery + "\n");
		runResult(Query1);
		
		System.out.println("-------------------");
		
		System.out.println("Running second query to find all vehciles of make Chevy and Toyota using SQL command: \n" + secondQuery);
		ResultSet Query2 = stat.executeQuery(secondQuery);
		SQLLog.append(secondQuery + "\n");
		runResult(Query2);

		System.out.println("-------------------");
		
		System.out.println("Running third query where all vehicles are above 2500 pounds in weight using SQL command: \n" + thirdQuery);
		ResultSet Query3 = stat.executeQuery("SELECT * FROM RENT_VEHICLE WHERE weight > 2500");
		SQLLog.append(thirdQuery + "\n");
		runResult(Query3);
		
		System.out.print("\n\n\n");
	}
}
