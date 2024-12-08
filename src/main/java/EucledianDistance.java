import java.util.List;
import java.util.Map;
/**
 * This is used to calculate the eucledian distance between two points.(implements distance)
 */
public class EucledianDistance implements Distance{
	
	@Override
	public double calculate(Map<String,Double>vector1,Map<String,Double>vector2) {
		
		//we have two lists - we have to calculate the eucledian distance between their entires - i.e their gene expressions
		// so we'll calculate the distance of a column with all others
		double sum =0.0;
		
		if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Sample names must exist in the map.");
        }
		
		if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("Feature vectors must be of the same size.");
        }
		
		
		for(String key : vector1.keySet()) {
			double v1 = vector1.get(key);
			double v2 = vector1.get(key);
			sum += Math.pow(v1-v2, 2);
		}

		return Math.sqrt(sum);
	}

}
