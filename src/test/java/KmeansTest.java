import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;


public class KmeansTest {

	@Test
	void Entry_class_return() {
		SoftParser parser = new SoftParser();
		List<Entry> temp = new ArrayList<Entry>();
		temp = parser.parse("src/main/resources/ParseTestFile.soft");
		EucledianDistance length = new EucledianDistance();
		Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
		f.fit(temp, 3, length, 3);
		assertNotNull(f);
	}
	@Test
	void predicted_return() {
	SoftParser parser = new SoftParser();
	List<Entry> temp = new ArrayList<Entry>();
	temp = parser.parse("src/main/resources/KmeansTemp.soft");
	EucledianDistance length = new EucledianDistance();
	
	Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
	
	f = KmeansClustering.fit(temp, 3, length, 3);
	
	Centroids centroid = f.keySet().iterator().next(); // Get the first centroid
    Map<String, Double> centroidMap = centroid.getCoordinates(); // Get the map

    Collection<Double> values = centroidMap.values();
    double[] centroidArray = new double[values.size()];
    int i = 0;
    for (Double value : values) {
        centroidArray[i++] = value;
    }
	}
	
	@Test 
	void another_predicted() {
		SoftParser parser = new SoftParser();
		List<Entry> temp = new ArrayList<Entry>();
		temp = parser.parse("src/main/resources/KmeansTemp.soft");
		EucledianDistance length = new EucledianDistance();
		
		Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
		
		
		for (int i = 0; i < 15; i++) { // Run 5 times to see where centroids converge
		    Map<Centroids, List<Entry>> result = KmeansClustering.fit(temp, 3, length, 3);
		    System.out.println("Run " + (i + 1) + " centroids: " + result.keySet());
		}
	}
}
