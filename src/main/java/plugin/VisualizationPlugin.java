package plugin;

import java.util.HashMap;
import java.util.Map;

import model.ClusteredData;
import view.VisualizationView;

/**
 * Plug-in interface for cluster visualization techniques.
 * All visualization plugins must implement this.
 */
public interface VisualizationPlugin extends PluginInterface {
	
    /**
     * @return The class of ClusteredData that this visualizer supports (e.g., FlatClusteredData.class).
     */
    Class<? extends ClusteredData> getSupportedClusteredDataType();

    /**
     * This method should return the view component that will visualize the clustered data.
     *
     * @param data The clustered data to visualize.
     * @return A JavaFX component implementing VisualizationView.
     */
    VisualizationView createView(ClusteredData data);

    /**
     * Indicates whether this visualization requires additional configuration before rendering.
     *
     * @return true if additional config is needed.
     */
    default boolean requiresConfiguration() {
        return false;
    }
    
    default Map<String, Object> getParameters() {
        return new HashMap<>();
    }
    /**
     * Identifies this plugin as a visualizer.
     */
    @Override
    default PluginType getPluginType() {
        return PluginType.VISUALIZATION;
    }
}
