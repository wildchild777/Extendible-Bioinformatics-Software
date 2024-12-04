import java.util.List;
import java.util.Map;

/**
 * Creating an interface since later on we can need different types of distances, 
 * 
 */
public interface Distance {
/**
 * 	
 * @param vector1 This will be the first vector (a sample in the database)
 * @param vector2 This will be the second vector (another sample in the database)
 * @return
 */
	double calculate(Map<String,Double>vector1,Map<String,Double>vector2);
}
