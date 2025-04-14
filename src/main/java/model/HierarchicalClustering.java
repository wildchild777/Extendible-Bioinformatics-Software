package model;

import java.util.*;

public class HierarchicalClustering implements ClusterStrategy {

    private Distance distanceMeasure;

    @Override
    public boolean supports(ParsedData data) {
        return data instanceof EntryBasedData;
    }
    
    /**
 * Since the signature of the interface needs this - we just get the important things out and 
 * pass it to local fit method 
 */
    @Override
    public ClusteredData fit(ParsedData data, Map<String, Object> config) {
    	 if (!(data instanceof EntryBasedData)) {
             throw new IllegalArgumentException("HierarchicalClustering supports only EntryBasedData.");
         }
    	 Distance distance = (Distance) config.get("distance");
         return fit((EntryBasedData) data, distance);
     }
    
    
    public ClusteredData fit(EntryBasedData data, Distance distance) {
        this.distanceMeasure = distance;
        List<Entry> entries = data.getEntries(); 

        //initialize each entry as a its own cluster nodde then we will recursively join them
        List<ClusterNode> nodes = new ArrayList<>();
        for (Entry entry : entries) {
            nodes.add(new ClusterNode(entry));
        }

        //we use a agglomerative approach 
        while (nodes.size() > 1) {//if size is more than one we proceed
            double minDistance = Double.MAX_VALUE;
            int idx1 = -1, idx2 = -1;//this helps with error checking and index finding

            // Find the closest pair
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    double dist = computeDistance(nodes.get(i), nodes.get(j));
                    if (dist < minDistance) {
                        minDistance = dist;
                        idx1 = i;
                        idx2 = j;
                    }
                }
            }

            // Merge closest pair into new ClusterNode
            //this creates a new internal node with two clsoest node idx1 and 2 with distance 
            ClusterNode merged = new ClusterNode(nodes.get(idx1), nodes.get(idx2), minDistance);
            nodes.remove(idx2); // remove higher index first to avoid shifting
            nodes.remove(idx1);
            nodes.add(merged);
        }

        return new HierarchicalClusteredData(nodes.get(0)); // Root node of dendrogram
    }

    private double computeDistance(ClusterNode a, ClusterNode b) {
        List<Entry> aEntries = flatten(a);//recursively get nodes from leaves and makes into a list
        List<Entry> bEntries = flatten(b);
        double min = Double.MAX_VALUE;

        for (Entry ea : aEntries) {
            for (Entry eb : bEntries) {
                double dist = distanceMeasure.calculate(ea.getGene(), eb.getGene());
                if (dist < min) min = dist;
            }
        }

        return min;
    }

    private List<Entry> flatten(ClusterNode node) {
        List<Entry> result = new ArrayList<>();
        collectEntries(node, result);
        return result;
    }

    private void collectEntries(ClusterNode node, List<Entry> out) {
        if (node.isLeaf()) {
            out.add(node.getEntry());
        } else {
            collectEntries(node.getLeft(), out);
            collectEntries(node.getRight(), out);
        }
    }
    
    
    /**
     * Added getParameters method to expose the required configuration.
     * For HierarchicalClustering we only require a "distance" parameter.
     */
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("distance", null);
        return params; 
    }
}
