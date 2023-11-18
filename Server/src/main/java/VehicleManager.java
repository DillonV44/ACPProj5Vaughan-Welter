/**
 * The file VehicleManager creates 10 lines of 
 * vehicles to be later used to write to a csv file.
 * Each line is written in csv format, with a comma
 * in between each string and no spaces. 
 * 
 * @author Dillon Vaughan
 * @Version 0.1
 * @Date Sep 17, 2023
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// enums for size
enum size {
	Compact,
	Intermediate,
	FullSize,
}

// enums for weight 
enum weight {
	Compact(1500, 2000),
	Intermediate(2000,2500),
	FullSize(2500, 4000);
	
	final int minWeight;
	final int maxWeight;
	
	weight (int minWeight, int maxWeight) {
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
	}
}

public class VehicleManager {
	private static final List<String> make = new ArrayList<>(List.of("Chevy", "Ford", "Toyota", "Nissan", "Hyundai"));
	private static final List<String> size = new ArrayList<>(List.of("Compact", "Intermediate", "Full Size"));
	private static final int engineSize = 100;
	private final Random rand;
	private static final int numberOfEntries = 10;
	
	public VehicleManager() {
		rand = new Random();
	}
	
	// public method generate() will build a string buffer of 10 lines of car
	// info to be used in the filewriter to write to a csv file. 
	// generate should produce 10 lines of "Chevy, Inermediate, 2150, 100"
	public String generate() throws Exception {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < numberOfEntries; i ++) {
			// the first step is getting a random make 
			String randMake = make.get(randomMake());
			sb.append(randMake).append(",");
			
			// the second step is to get a random size
			String randSize = size.get(randomSize());
			sb.append(randSize).append(",");
			
			// the third step is to get a random weight based on the size
			if (randSize.equalsIgnoreCase(size.get(0))) {
				sb.append(randomWeight(weight.Compact.minWeight, weight.Compact.maxWeight)).append(",");
			}
			else if (randSize.equalsIgnoreCase(size.get(1))) {
				sb.append(randomWeight(weight.Intermediate.minWeight, weight.Intermediate.maxWeight)).append(",");
			}
			else if (randSize.equalsIgnoreCase(size.get(2))) {
				sb.append(randomWeight(weight.FullSize.minWeight, weight.FullSize.maxWeight)).append(",");
			}
			else {
				throw new Exception("Error setting weight, could not find size.");
			}
			sb.append(engineSize + "\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param minWeight takes the minWeight from size
	 * @param maxWeight takes the maxWeight from size
	 * @return int between minWeight and maxWeight for given size
	 */
	public int randomWeight(int minWeight, int maxWeight) {
		return rand.nextInt(maxWeight - minWeight) + minWeight;
	}
	
	/**
	 * generate make array list between 0 and 5
	 * @return int between 0 and 5
	 */
	public int randomMake() {
		return rand.nextInt(5);
	}
	
	/**
	 * generate size array list between 0 and 3
	 * @return int between 0 and 3
	 */
	public int randomSize() {
		return rand.nextInt(3);
	}
	
}
