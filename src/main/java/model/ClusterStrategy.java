package model;

import java.util.List;
import java.util.Map;

/**
 * This class implements the Strategy pattern to allow for dynamic switching between 
 * different clustering alogrithms.
 */
public interface ClusterStrategy {
	/**
	 * Every clustering algorithm needs to return a type of ClusteredData (for now entry)
	 * it runs on parsed data and returns the findings by running the ML algorithm.
	 * @return the list of centroids (centre point) of entries 
	 * -> entry(needs to be changed) with meaningful insights
	 */
	// this needs to return a proper subclass of the interface of CLusteredData
	Map<Centroids, List<Entry>>fit(ParsedData entry, int k, Distance distance, int maxIterations);
}
