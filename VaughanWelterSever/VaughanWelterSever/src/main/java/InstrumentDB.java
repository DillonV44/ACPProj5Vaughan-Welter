/****************************************************
 
@author Dillon Vaughan, Anthony Welter
File Name: InstrumentDB.java
COP4027	Project #: 5

****************************************************/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class InstrumentDB {
	Connection con;
	Statement stat;
   
    /*
	 * InstrumentDB constructor
	 */
	public InstrumentDB() throws Exception {
		SimpleDataSource.init("database.properties");
		con = SimpleDataSource.getConnection();
		stat = con.createStatement();

		try {
			stat.execute("DROP TABLE Instruments");
			stat.execute("DROP TABLE Locations");
			stat.execute("DROP TABLE Inventory");
		} 
      catch (Exception e) {
			System.err.println("Attempted to drop tables, tables did not exist!");
		}

		createInstruments(stat);
		createLocations(stat);
		createInventory(stat);
	}
   
    /*
	 * Drops tables and closes connection
	 */
	public void endDB() throws SQLException {
		try {
			stat.execute("DROP TABLE Instruments");
			stat.execute("DROP TABLE Locations");
			stat.execute("DROP TABLE Inventory");
		} catch (Exception e) {
			System.err.println("Error, failed to drop tables!");
		} finally {
			con.close();
			System.out.println("Dropped Tables, closed connection and ending program");
		}
	}
   
    /*
	 * Creates table Instruments and returns table
	 * @param Statement stat in order execute string based SQL string commands
     * @return Instruments table
	 */
	public ResultSet createInstruments(Statement stat) throws Exception
   {
         stat.execute("CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");
         stat.execute("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
         stat.execute("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
         stat.execute("INSERT INTO Instruments VALUES ('bass',3,250.0,'fender')");
         stat.execute("INSERT INTO Instruments VALUES ('keyboard',4,600.0,'roland')");
         stat.execute("INSERT INTO Instruments VALUES ('keyboard',5,500.0,'alesis')");
         stat.execute("INSERT INTO Instruments VALUES ('drums',6,1500.0,'ludwig')");
         stat.execute("INSERT INTO Instruments VALUES ('drums',7,400.0,'yamaha')");
         ResultSet result = stat.executeQuery("SELECT * FROM Instruments");
         return result;
   }
   
    /*
	 * Creates table Locations and returns table
	 * @param Statement stat in order execute string based SQL string commands
     * @return Locations table
	 */
	public ResultSet createLocations(Statement stat) throws Exception
   {
         stat.execute("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");
         stat.execute("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
         stat.execute("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
         stat.execute("INSERT INTO Locations VALUES ('DFW',3,'Dallas Fort Worth Texas')");
         ResultSet result = stat.executeQuery("SELECT * FROM Locations");
         return result;
   }
   
    /*
	 * Creates table Inventory and returns table
	 * @param Statement stat in order execute string based SQL string commands
     * @return Inventory table
	 */
	public ResultSet createInventory(Statement stat) throws Exception
   {
         stat.execute("CREATE TABLE Inventory (iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");
         stat.execute("INSERT INTO Inventory VALUES (1,1,15)");
         stat.execute("INSERT INTO Inventory VALUES (1,2,27)");
         stat.execute("INSERT INTO Inventory VALUES (1,3,20)");
         stat.execute("INSERT INTO Inventory VALUES (2,1,10)");
         stat.execute("INSERT INTO Inventory VALUES (2,2,10)");
         stat.execute("INSERT INTO Inventory VALUES (2,3,35)");
         stat.execute("INSERT INTO Inventory VALUES (3,1,45)");
         stat.execute("INSERT INTO Inventory VALUES (3,2,10)");
         stat.execute("INSERT INTO Inventory VALUES (3,3,17)");
         stat.execute("INSERT INTO Inventory VALUES (4,1,28)");
         stat.execute("INSERT INTO Inventory VALUES (4,2,10)");
         stat.execute("INSERT INTO Inventory VALUES (4,3,16)");
         stat.execute("INSERT INTO Inventory VALUES (5,1,28)");
         stat.execute("INSERT INTO Inventory VALUES (5,2,10)");
         stat.execute("INSERT INTO Inventory VALUES (5,3,1)");
         stat.execute("INSERT INTO Inventory VALUES (6,1,2)");
         stat.execute("INSERT INTO Inventory VALUES (6,2,10)");
         stat.execute("INSERT INTO Inventory VALUES (6,3,16)");
         stat.execute("INSERT INTO Inventory VALUES (7,1,16)");
         stat.execute("INSERT INTO Inventory VALUES (7,2,4)");
         stat.execute("INSERT INTO Inventory VALUES (7,3,12)");     
         ResultSet result = stat.executeQuery("SELECT * FROM Inventory");
         return result;
   }
	
	/**
	 * Method runQueries will run the query string passed in and generate
	 * a ResultSet Object that is then parsed in the runResult method to be returned
	 * as a single string.
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public String runQueries(String queryString) throws Exception {
		ResultSet resultSet = stat.executeQuery(queryString);
		return runResult(resultSet);
	}
	
	
	
	public String runResult(ResultSet result) throws Exception {
		StringBuffer queryResult = new StringBuffer();
		System.out.println("Translating return object of queries into a readable format");
		ResultSetMetaData rsm = result.getMetaData();
		int cols = rsm.getColumnCount();
		while(result.next())
		{
			for(int i = 1; i <= cols; i++) {
				queryResult.append(result.getString(i)+" ");
				System.out.print(result.getString(i)+" ");
			}
			queryResult.append(" ");
			System.out.println("");      
		}
		
		return queryResult.toString();

	}

}