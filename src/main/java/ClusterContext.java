import java.util.List;
import java.util.Map;

/**
 * This class passes request to the concrete subclasses after getting 
 * requests from clients 
 * 
 * This is used to implement the Strategy pattern
 */
public class ClusterContext {
	   private ClusterStrategy strategy;//privately held strategy

	    // Set the clustering strategy
	    public void setClusterStrategy(ClusterStrategy strategy) {
	        this.strategy = strategy;
	    }

	    //Need to change this to take a ParsedData instance instead of List<Entry> etc
	    //Need to change this so it returns a type of ClusterdData instead of Map<Centroids, List<Entry>>
	    public Map<Centroids, List<Entry>> executeClustering(List<Entry> data, int k, Distance distance, int maxIterations) {
	        if (strategy == null) {
	            throw new IllegalStateException("ClusterStrategy is not set.");
	        }
	        return strategy.fit(data,k,distance,maxIterations);
	    }
	}

