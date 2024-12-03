import java.util.List;
import java.util.Map;


/**
 * This class is used to handle a single entry of the column in the database 
 * basically a sample and it's gene expressions
 */
public class Entry {
	private final String description;
	private final Map<String,List<Double>> sample;
	
	/**
	 * @param description used for the desciprtion of a singular entry
	 * @param sample will store a column of the entry of the SOFT file - which is the sample and it's expression values
	 */
	public Entry(String description, Map<String, List<Double>> sample) {
		this.description=description;
		this.sample = sample;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Map<String,List<Double>> getSample(){
		return sample;
	}
	
}
