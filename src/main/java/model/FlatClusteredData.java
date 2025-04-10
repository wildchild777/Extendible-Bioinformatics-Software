package model;

import java.util.List;
import java.util.Map;

/**
 * This is an class for the type of clustering algorithms that return a flatStrucuture like centroids -> entry in
 * the case of Kmeans, DBSCAN etc.
 * 
 * It would be better to have different types of data types since, different clustering algortihms return different
 * types of data strucutres, like heirarchal returns nested datastructures.
 */
public class FlatClusteredData implements ClusteredData{
	
	//privately held clusters 
    private final Map<Centroids, List<Entry>> clusters;
    
    //this initalises the clusters
    public FlatClusteredData(Map<Centroids, List<Entry>> clusters) {
        this.clusters = clusters;
    }
    //returns the mapping of the clusters from centroid -> entries
    public Map<Centroids, List<Entry>> getClusters() {
        return clusters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Centroids, List<Entry>> entry : clusters.entrySet()) {
            sb.append("Centroid: ").append(entry.getKey()).append("\n");
            for (Entry e : entry.getValue()) {
                sb.append("  Sample: ").append(e.getName()).append(" Data: ").append(e.getGene()).append("\n");
            }
        }
        return sb.toString();
    }
}
	


