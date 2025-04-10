import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.Centroids;
import model.Entry;
import model.EucledianDistance;
import model.GeneExpressionParsedData;
import model.KmeansClustering;
import model.ParsedData;
import model.SoftParser;


public class KmeansTest {
	
	@Test
	void Entry_class_return() {
		SoftParser parser = new SoftParser();
		ParsedData temp = parser.parse("src/main/resources/ParseTestFile.soft");
		EucledianDistance length = new EucledianDistance();
		Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
		KmeansClustering cluster = new KmeansClustering();
		f = cluster.fit(temp, 3, length, 3);
		assertNotNull(f);
	}
	@Test
	void predicted_return() {
	SoftParser parser = new SoftParser();
	ParsedData temp = parser.parse("src/main/resources/KmeansTemp.soft");
	EucledianDistance length = new EucledianDistance();
	KmeansClustering cluster = new KmeansClustering();
	Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
	
	f = cluster.fit(temp, 3, length, 3);
	
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
		ParsedData temp = parser.parse("src/main/resources/KmeansTemp.soft");
		EucledianDistance length = new EucledianDistance();
		
		Map<Centroids, List<Entry>> f = new HashMap<Centroids, List<Entry>>();
		KmeansClustering cluster = new KmeansClustering();
		
		for (int i = 0; i < 15; i++) { // Run 5 times to see where centroids converge
		    Map<Centroids, List<Entry>> result = cluster.fit(temp, 3, length, 3);
		    System.out.println("Run " + (i + 1) + " centroids: " + result.keySet());
		}
	}
	
	@Test 
	void simple_sort() {
		  List<Entry> testEntries = new ArrayList<>();
		    testEntries.add(new Entry("Sample1", Map.of("Gene1", 1.0, "Gene2", 2.0, "Gene3", 3.0)));
		    testEntries.add(new Entry("Sample2", Map.of("Gene1", 1.1, "Gene2", 2.1, "Gene3", 3.1)));
		    testEntries.add(new Entry("Sample3", Map.of("Gene1", 9.0, "Gene2", 9.5, "Gene3", 10.0)));
		    testEntries.add(new Entry("Sample4", Map.of("Gene1", 9.2, "Gene2", 9.6, "Gene3", 10.2)));
		    
		    //changed this to add to the wrapped Gene class so we can use the interface ParsedData
		    //instead of concrete subclass Entry
		    GeneExpressionParsedData parsedData = new GeneExpressionParsedData();
		    for (Entry e : testEntries) {
		        parsedData.add(e);
		    }
		    
		    int k = 2;
		    int maxIterations = 100;
		    EucledianDistance length = new EucledianDistance();
		    KmeansClustering clustering = new KmeansClustering();
		    Map<Centroids, List<Entry>> clusters = clustering.fit(parsedData, k, length, maxIterations);

		    for (Map.Entry<Centroids, List<Entry>> cluster : clusters.entrySet()) {
		        System.out.println("Centroid: " + cluster.getKey());
		        for (Entry e : cluster.getValue()) {
		            System.out.println("  Sample: " + e.getName() + " Data: " + e.getGene());
		        }
		    }
	
	
	}
	
	
}
