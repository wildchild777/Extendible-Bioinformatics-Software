import java.util.List;
import java.util.Map;

//basically the issue as that i was using Map<String,List<Double>> name - which was i was thinking
//we could use the name of the column as the String and map to its expression values (all the genes)
// but this is very redundant as there is no way for me to refer to the column itself then 
// 
//by changing this to name as the column and then mapping the -> gene string to its expression value will reduce the complexoty
//of all the methods and clarify eveything as well.

/**
 * This class is used to handle a single entry of the column in the database 
 * basically a sample and it's gene expressions
 */
public class Entry {
	private final String name;
	private final Map<String,Double> sample;
	
	/**
	 * @param description used for the name of a singular entry, the sample name.
	 * @param sample will store a column of the entry of the SOFT file - which is the sample and it's expression values
	 */
	public Entry(String name, Map<String,Double> sample) {
		this.name=name;
		this.sample = sample;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String,Double> getGene(){
		return sample;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Sample Name: ").append(name).append("\n");
	    sb.append("Gene Expression Data:\n");
	    for (Map.Entry<String, Double> gene : sample.entrySet()) {
	        sb.append("  Gene: ").append(gene.getKey())
	          .append(" - Expression Value: ").append(gene.getValue()).append("\n");
	    }
	    return sb.toString();
	}
	
	
}
