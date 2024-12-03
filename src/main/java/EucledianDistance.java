import java.util.List;
import java.util.Map;

public class EucledianDistance implements Distance{
	
	@Override
	public double calculate(Map<String,List<Double>>vector1,Map<String,List<Double>>vector2) {
		
		//we have two lists - we have to calculate the eucledian distance between their entires - i.e their gene expressions
		// so we'll calculate the distance of a column with all others
		double sum =0.0;
		// since there is only one list we can use the .iterator.next - this will give us the values of gene expression
		if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Sample names must exist in the map.");
        }
		List<Double> v1 = vector1.values().iterator().next();
		List<Double> v2 = vector2.values().iterator().next();
		

        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("Feature vectors must be of the same size.");
        }
		
		for (int i =0; i < v1.size(); i++) {
			sum += Math.pow(v1.get(i)-v2.get(i), 2);
		}
		
		
		return Math.sqrt(sum);
	}

}
