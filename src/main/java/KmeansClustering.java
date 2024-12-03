import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class performs our main clustering on the gene data, it had a method which runs fit and in the end returns the clusters 
 */
public class KmeansClustering {
	//a random seed to make the rnadom positions for the centriods
	private static final Random random = new Random();
	
	/**
	 * 
	 * @param entry the class we use for our gene expression that we parse
	 * @param k the number of clusters
	 * @param distance
	 * @param maxIterations the number of time we want the fit to run 
	 * @return returns a mapping of a centroid with the list of entries(our column vectors) that have been clustered together
	 */
	public static Map<Centroids, List<Entry>>fit (List<Entry> entry, int k, Distance distance, int maxIterations){
		return null;
	}
}
