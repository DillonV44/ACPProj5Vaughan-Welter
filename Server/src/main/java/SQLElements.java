/**
 * SQLElemnts is the base class for the name and the type of SQL element 
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
public class SQLElements {
	private String name;
	private String type;
	
	// parameterized constructor 
	public SQLElements(String name, String type) {
		setName(name);
		setType(type);
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	// toString method
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName() + " " + getType());
		return sb.toString();
	}
	
	
}
