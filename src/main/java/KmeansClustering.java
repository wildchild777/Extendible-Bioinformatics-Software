import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class performs our main clustering on the gene data, it had a method which runs fit and in the end returns the clusters 
 */
public class KmeansClustering {
	//a random seed to make the rnadom positions for the centriods
	private static final Random random = new Random();
	
	
	private static List<Centroids> randomCentroids(List<Entry> dataset, int k){
		List<Centroids> centriods = new ArrayList<>();
		
		Map<Integer, Double> maxs = new HashMap<>();
		Map<Integer, Double> mins = new HashMap<>();
		
		for(Entry entry : dataset) {
			Map<String,List<Double>> expressionValues = entry.getSample();// gets a single instance of Entry object
			
			for(List<Double> geneValues : expressionValues.values()) {//gets the List<Double> of the all gene expressions
				for(int i =0; i < geneValues.size();i++) {// goes over all the gene values in the list 
					double value = geneValues.get(i);
					
					//update the max 
					maxs.put(i, Math.max(maxs.getOrDefault(i, Double.MIN_VALUE), value));

	                // Update minimum values
	                mins.put(i, Math.min(mins.getOrDefault(i, Double.MAX_VALUE), value));
				}
			}
			
		}
		
		//Now we will work on generating the Centriods
		
		for (int i=0; i<k;i++) {
			
		}
		
		
		return null;
	}
	
	
	/**
	 * 
	 * @param entry the class we use for our gene expression that we parse
	 * @param k the number of clusters
	 * @param distance the type of distance we are going to use to caluclate the distance between our vectors
	 * @param maxIterations the number of time we want the fit to run 
	 * @return returns a mapping of a centroid with the list of entries(our column vectors) that have been clustered together
	 */
	public static Map<Centroids, List<Entry>>fit (List<Entry> entry, int k, Distance distance, int maxIterations){
		return null;
	}
}
