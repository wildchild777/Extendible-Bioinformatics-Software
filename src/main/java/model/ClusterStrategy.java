package model;

import java.util.List;
import java.util.Map;

/**
 * This class implements the Strategy pattern to allow for dynamic switching between 
 * different clustering alogrithms.
 */
public interface ClusterStrategy {
	/**
	 * Every clustering algorithm needs to return a type of ClusteredData
	 * it runs on parsed data and returns the findings by running the ML algorithm.
	 * @return the list of centroids (centre point) of entries 
	 * 
	 * Now it returns any type of class as long as it adheres to ClusterdData
	 * This was needed since different Clsuterign algorithms return different tpye of data structures
	 */
	// this needs to return a proper subclass of the interface of CLusteredData
	ClusteredData fit(ParsedData entry, int k, Distance distance, int maxIterations);
}
