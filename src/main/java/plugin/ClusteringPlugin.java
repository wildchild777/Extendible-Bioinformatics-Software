package plugin;

import java.util.Map;

import model.ClusterStrategy;
import model.ClusteredData;
import model.Distance;
import model.ParsedData;

public interface ClusteringPlugin extends PluginInterface, ClusterStrategy {


    /**
     * Check if this clustering algorithm supports the given ParsedData type.
     *
     * @param data The ParsedData to check compatibility with.
     * @return true if this clustering algorithm can work on the provided data type.
     */
    boolean supports(ParsedData data);

    /**
     * The core clustering logic. This method will be called at runtime to run the algorithm.
     *
     * @param data The dataset to be clustered.
     * @param k Number of clusters (ignored if not applicable).
     * @param distance Distance metric to use (can be ignored if algorithm doesn't use one).
     * @param maxIterations Maximum iterations (optional depending on the algorithm).
     * @return ClusteredData result from the clustering operation.
     */
    @Override
    ClusteredData fit(ParsedData data,Map<String, Object> config);

    /**
     * Indicates if this algorithm requires user configuration before execution.
     *
     * @return true if additional configuration is needed.
     */
    default boolean requiresConfiguration() {
        return false;
    }

    /**
     * @return The specific type of ParsedData this algorithm is designed to cluster.
     */
    default Class<? extends ParsedData> supportedParsedType() {
        return ParsedData.class;
    }

    /**
     * @return PluginType.CLUSTERING to identify this as a clustering plugin.
     */
    @Override
    default PluginType getPluginType() {
        return PluginType.CLUSTERING;
    }
}
