import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Public class JavaReflectionForCar gathers the instance field types and names from
 * class Car in order to later be used to create a SQL table for Rental Vehicles 
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
public class JavaReflectionForCar {
	
	// class name to pull abstraction from
	private String className = "Vehicle";
	// array of Field objects for field types 
	private Field[] fieldTypes;
	// Array lists to host the field names and types
	private ArrayList<String> fieldNameList = new ArrayList<>();
	private ArrayList<String> fieldTypeList = new ArrayList<>();
	// Array list to hold the SQL elements created from this file
	private List<SQLElements> SQLTypes;
	
	public JavaReflectionForCar() {
		getFields();
		setFieldTypes();
		setFieldNames();
		SQLTypes = setSQLTypes();
	}
	
	
	/**
	 * Public method getFields will set the 
	 * declared fields from the given class name
	 */
	@SuppressWarnings("rawtypes")
	public void getFields() {
		try {
			Class c = Class.forName(className);
			fieldTypes = c.getDeclaredFields();
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	/**
	 * public class setFieldTypes will
	 * gather the field type and load it 
	 * into the fieldType Array list
	 */
	public void setFieldTypes() {
		for (Field field : fieldTypes) 
			fieldTypeList.add(field.getType().toString());
	}
	
	/**
	 * public class setFieldNames will
	 * gather the field name and load it 
	 * into the fieldName Array list
	 */
	public void setFieldNames() {
		for (Field field : fieldTypes) 
			fieldNameList.add(field.getName().toString());
	}
	
	// Public class setSQLTypes will gather the list of names and types for the 
	// abstraction method. Each name of the instance field will have an attached type,
	// the type can either be CHAR(15) or Double 
	public List<SQLElements> setSQLTypes() {
		List<SQLElements> SQLTypesToReturn = new ArrayList<>();
		System.out.println("Using abstraction to gather the field types and names in order to user for database table creation");
		// iterating through each field type
		for (int i = 0; i < fieldTypes.length; i++) {
			
			String currentFieldType = fieldTypes[i].getType().toString();
			String currentFieldName = fieldTypes[i].getName().toString();

			if (currentFieldType.contains("String")) {
				SQLTypesToReturn.add(new SQLElements(currentFieldName, "CHAR(15)"));
			}
			if (currentFieldType.contains("double")) {
				SQLTypesToReturn.add(new SQLElements(currentFieldName, "DOUBLE"));
			}
			
		}
		System.out.println("Successfully used abstraction methods to pull information from the Car class to use for table creation.\n");
		return SQLTypesToReturn;
	}
	
	
	/** 
	 * public class getSQLElements will return the SQL elements of the program
	 * @return returns the list of SQL elements needs for database table creation 
	 */
	public List<SQLElements> getSQLElements() {
		return this.SQLTypes;
	}

}
