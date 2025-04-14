import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.GeneExpressionParsedData;
import plugin.*;

import java.util.List;

public class PluginManagerTest {

    @Test
    void testLoadsDummyCSVParser() throws Exception {
        PluginManager pm = new PluginManager();
        pm.loadPlugins("src/main/java/plugin/");

        List<ParserPlugin> parsers = pm.getParsers();
        assertFalse(parsers.isEmpty(), "Parser plugins should not be empty");

        boolean found = parsers.stream().anyMatch(p -> p.getName().contains("Dummy CSV"));
        assertTrue(found, "Dummy CSV Parser should be loaded");
    }

    @Test
    void testLoadsDummyClusteringPlugin() throws Exception {
        PluginManager pm = new PluginManager();
        pm.loadPlugins("src/main/java/plugin/");

        List<ClusteringPlugin> clusterers = pm.getClustering();
        assertFalse(clusterers.isEmpty(), "Clustering plugins should not be empty");

        ClusteringPlugin dummy = clusterers.stream()
            .filter(c -> c.getName().contains("Dummy"))
            .findFirst()
            .orElseThrow();

        assertEquals("Dummy Clustering Algo", dummy.getName());
        assertEquals("Fake clustering algorithm for testing", dummy.getDescription());
        assertTrue(dummy.requiresConfiguration());
        assertEquals(plugin.PluginType.CLUSTERING, dummy.getPluginType());
        assertEquals(GeneExpressionParsedData.class, dummy.supportedParsedType());
    }

    @Test
    void testLoadsDummyVisualizationPlugin() throws Exception {
        PluginManager pm = new PluginManager();
        pm.loadPlugins("src/main/java/plugin/");

        List<VisualizationPlugin> vis = pm.getVisualizations();
        assertFalse(vis.isEmpty(), "Visualization plugins should not be empty");

        VisualizationPlugin dummy = vis.stream()
            .filter(v -> v.getName().contains("Dummy"))
            .findFirst()
            .orElseThrow();

        assertEquals("Dummy Viz", dummy.getName());
        assertEquals("Fake visualization for testing.", dummy.getDescription());
        assertTrue(dummy.requiresConfiguration());
        assertEquals(plugin.PluginType.VISUALIZATION, dummy.getPluginType());
        assertEquals(model.FlatClusteredData.class, dummy.getSupportedClusteredDataType());
    }
}
