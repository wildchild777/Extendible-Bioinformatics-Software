import model.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.*;

import org.junit.jupiter.api.Test;

public class HierarchicalClusteringTest {
	
	@Test
    public void simpleTest() {
        List<Entry> entries = new ArrayList<>();

        Map<String, Double> g1 = Map.of("gene1", 1.0, "gene2", 2.0);
        Map<String, Double> g2 = Map.of("gene1", 2.0, "gene2", 3.0);
        Map<String, Double> g3 = Map.of("gene1", 10.0, "gene2", 10.0);

        entries.add(new Entry("Sample1", g1));
        entries.add(new Entry("Sample2", g2));
        entries.add(new Entry("Sample3", g3));

        ParsedData data = new GeneExpressionParsedData();
        assertTrue(data instanceof EntryBasedData);
        
        for (Entry e : entries) {
            ((GeneExpressionParsedData) data).add(e);
        }

        HierarchicalClustering hc = new HierarchicalClustering();
        Map<String, Object> config = new HashMap<>(); 
        config.put("distance", new EucledianDistance()); 
        ClusteredData clustered = hc.fit(data, config); 

        System.out.println("=== Dendrogram ===");
        System.out.println(clustered);
    }
    
    @Test
    public void testHierarchicalClustering_FromSOFTFile() {
        ParserContext parserContext = new ParserContext();
        parserContext.setParser(new SoftParser());

        ParsedData data = parserContext.executeParse(new File("src/main/resources/HierarchalTemp.soft"));

        ClusterStrategy hc = new HierarchicalClustering();
        Map<String, Object> config = new HashMap<>(); 
        config.put("distance", new EucledianDistance()); 
        ClusteredData result = hc.fit(data, config);

        System.out.println("=== Test: From SOFT File ==="); 
        System.out.println(result);
    }
}
