import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.*;

public class ClusterContextTest {

	
	
	
	@Test
	public void Setup() {
		ParserContext context = new ParserContext();
		context.setParser(new SoftParser());
		ParsedData holder = context.executeParse("src/main/resources/KmeansTemp.soft");
		assertNotNull(holder);
		//System.out.println(holder);
		EucledianDistance length = new EucledianDistance();
		ClusterContext cluster_context = new ClusterContext();
		cluster_context.setClusterStrategy(new KmeansClustering());
		ClusteredData result = cluster_context.executeClustering(holder,3,length,3);
		assertNotNull(result);
		Map<Centroids, List<Entry>> clusters = ((FlatClusteredData) result).getClusters();
		for (Map.Entry<Centroids, List<Entry>> entry : clusters.entrySet()) {
		    Centroids centroid = entry.getKey();  // Get the centroid
		    List<Entry> entries = entry.getValue(); // Get the associated list of entries

		    System.out.println("Centroid: " + centroid);
		    System.out.println("Entries: " + entries);
		}
	}
}
 