package model;

import java.util.List;
import java.util.Map;

/**
 * This class implements the Strategy pattern to allow for dynamic switching between 
 * different clustering alogrithms.
 */
public interface ClusterStrategy {
	 /**
     * Clusters the parsed data using a dynamically provided configuration.
     *
     * @param data the parsed data to be clustered
     * @param config a map containing configuration parameters
     * @return a ClusteredData object representing the clustering result
     */
    ClusteredData fit(ParsedData data, Map<String, Object> config);
    
    /**
     * Provides a map of required parameters along with their default values or null.
     *
     * @return a map with parameter names as keys and their default values or null as values
     */
    Map<String, Object> getParameters();
	
	 /**
     * Check if the clustering algorithm can handle the given ParsedData type.
     */
    default boolean supports(ParsedData data) {
        return true; // by default allow all
    }
}
