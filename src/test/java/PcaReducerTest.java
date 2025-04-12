import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.*;

public class PcaReducerTest {
	@Test
    public void testPcaReductionTo2D() {
        // Create a mock dataset with 3 samples, 3 genes
        List<Entry> mockData = new ArrayList<>();

        mockData.add(new Entry("Sample1", Map.of("Gene1", 1.0, "Gene2", 2.0, "Gene3", 3.0)));
        mockData.add(new Entry("Sample2", Map.of("Gene1", 2.0, "Gene2", 3.0, "Gene3", 4.0)));
        mockData.add(new Entry("Sample3", Map.of("Gene1", 3.0, "Gene2", 4.0, "Gene3", 5.0)));

        DimensionalityReducer reducer = new PcaReducer();
        List<Entry> reduced = reducer.reduce(mockData, 2); // reduce to 2D

        assertEquals(3, reduced.size(), "There should be 3 reduced entries (same number of samples)");

        for (Entry entry : reduced) {
            Map<String, Double> reducedValues = entry.getGene();
            assertEquals(2, reducedValues.size(), "Each reduced entry should have 2 principal components");
            assertTrue(reducedValues.containsKey("PC1"));
            assertTrue(reducedValues.containsKey("PC2"));
            System.out.println(entry.getName() + " → " + reducedValues);
        }
    }
	
	
	@Test
	void testPCAWithTwoDimensionalSpread() {
	    List<Entry> entries = new ArrayList<>();
	    entries.add(new Entry("Sample1", Map.of("Gene1", 1.0, "Gene2", 1.0)));
	    entries.add(new Entry("Sample2", Map.of("Gene1", 2.0, "Gene2", 3.0)));
	    entries.add(new Entry("Sample3", Map.of("Gene1", 3.0, "Gene2", 2.0)));

	    PcaReducer reducer = new PcaReducer();
	    List<Entry> reduced = reducer.reduce(entries, 2);

	    assertNotNull(reduced);
	    assertEquals(3, reduced.size());
	    assertEquals(2, reduced.get(0).getGene().size());
	    // Optional debug print
	    for (Entry e : reduced) {
	        System.out.println(e.getName() + " → " + e.getGene());
	    }
	}
	
	@Test
	void testPCAWithIdenticalSamples() {
	    List<Entry> entries = new ArrayList<>();
	    entries.add(new Entry("Sample1", Map.of("Gene1", 5.0, "Gene2", 5.0)));
	    entries.add(new Entry("Sample2", Map.of("Gene1", 5.0, "Gene2", 5.0)));
	    entries.add(new Entry("Sample3", Map.of("Gene1", 5.0, "Gene2", 5.0)));

	    PcaReducer reducer = new PcaReducer();
	    List<Entry> reduced = reducer.reduce(entries, 2);

	    assertNotNull(reduced);
	    assertEquals(3, reduced.size());
	    for (Entry e : reduced) {
	        for (Double v : e.getGene().values()) {
	            assertEquals(0.0, v); // No variance, all projections should be 0
	        }
	    }
	}
	
	@Test
    void reduceSoftParsedGeneExpressionTo2D() {
        // parse
        SoftParser parser = new SoftParser();
        ParsedData parsedData = parser.parse("src/main/resources/ParseTestFile.soft");
        List<Entry> entries = parsedData.getEntries();

        //PCA
        DimensionalityReducer reducer = new PcaReducer();
        List<Entry> reduced = reducer.reduce(entries, 2);

        // there should be 6 samples with PC1 and PC2
        assertEquals(6, reduced.size());
        for (Entry e : reduced) {
            assertEquals(2, e.getGene().size(), "Each entry should have 2 principal components");
            assertTrue(e.getGene().containsKey("PC1"));
            assertTrue(e.getGene().containsKey("PC2"));
            System.out.println(e.getName() + " → " + e.getGene());
        }
    }

}
