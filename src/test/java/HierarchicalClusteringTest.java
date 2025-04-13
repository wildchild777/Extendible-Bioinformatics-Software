import model.*;
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
        for (Entry e : entries) {
            ((GeneExpressionParsedData) data).add(e);
        }

        HierarchicalClustering hc = new HierarchicalClustering();
        ClusteredData clustered = hc.fit(data, new EucledianDistance());

        System.out.println("=== Dendrogram ===");
        System.out.println(clustered);
    }
    
    @Test
    public void testHierarchicalClustering_FromSOFTFile() {
        ParserContext parserContext = new ParserContext();
        parserContext.setParser(new SoftParser());

        ParsedData data = parserContext.executeParse("src/main/resources/HierarchalTemp.soft");

        ClusterStrategy hc = new HierarchicalClustering();
        ClusteredData result = hc.fit(data, 0, new EucledianDistance(), 0);

        System.out.println("=== Test: From SOFT File ===");
        System.out.println(result);
    }
}
