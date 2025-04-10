import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.Centroids;
import model.ClusterContext;
import model.Entry;
import model.EucledianDistance;
import model.KmeansClustering;
import model.ParserContext;
import model.SoftParser;

public class ClusterContextTest {

	
	
	
	@Test
	public void Setup() {
		ParserContext context = new ParserContext();
		List<Entry> holder = new ArrayList<Entry>();
		context.setParser(new SoftParser());
		holder = context.executeParse("src/main/resources/ParseTestFile.soft");
		assertNotNull(holder);
		//System.out.println(holder);
		EucledianDistance length = new EucledianDistance();
		ClusterContext cluster_context = new ClusterContext();
		cluster_context.setClusterStrategy(new KmeansClustering());
		Map<Centroids, List<Entry>> result = cluster_context.executeClustering(holder,3,length,3);
		//System.out.println( "centroids: " + result.keySet());
		for (Map.Entry<Centroids, List<Entry>> entry : result.entrySet()) {
		    Centroids centroid = entry.getKey();  // Get the centroid
		    List<Entry> entries = entry.getValue(); // Get the associated list of entries

		    System.out.println("Centroid: " + centroid);
		    System.out.println("Entries: " + entries);
		}
	}
}
