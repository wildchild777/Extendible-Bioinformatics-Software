import java.util.ArrayList;
import java.util.Collection;
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
	
	/**
	 * This generates a list of random centroids in the same plane as our dataset.
	 * @param dataset our dataset - i.e list of entires
	 * @param k the number of centroids we want to generate 
	 * @return a list of random centroids in the same plane as our dataset.
	 */
	private static List<Centroids> randomCentroids(List<Entry> dataset, int k){
		
		List<Centroids> centroids = new ArrayList<>();
		
		Map<String, Double> mins = new HashMap<>();//a gene and it's minimum values
		Map<String, Double> maxs = new HashMap<>();//a gene and it's maximum values
		
		
			// this will give us an entry object from the list of entry objects - which will be a GSM sample
			// that contain mapping from String -> Double - gene and their gene expression for a particular Entry (GSM sample)
			
			//our goal is to get max and min numbers from the range of gene expressions from each GSM sample
			//create centroids within this range so they converge faster
			
			//so go over an entry(GSM sample) get the values -> get max min for a gene ->
			//generate random values for that gene -> give these to a centroid.
			
			// so go over entry object - which will contain a list of entry objects, (string(gene)->double(expression value))
			// get the string -> get values - set min max -> iterate over all the entires.
			
			//generate these centroids and return
			
		for(Entry entry : dataset) {
			
			//first lets store the entry object
			Map<String, Double> currentGene = entry.getGene();// this stores a sample with it's list of gene -> exp
			
			
			for(String temp : currentGene.keySet()) {//goes through all the genes one at a time 
				
				double expression = currentGene.get(temp);//gets the genes expression value
				
				if(maxs.get(temp)==null) {// if it doesn't exist 
					maxs.put(temp, expression);//create an entry
				}
				
				// if we get here that means that we do have a value in the maxs hashmap	
				else if(maxs.get(temp)<expression) {//if the expression currently held in the hasmap is less than the expression then we update
						maxs.put(temp, expression);
					}
				
				if(mins.get(temp)==null) {// if it doesn't exist 
					maxs.put(temp, expression);//create an entry
				}
				
				// if we get here that means that we do have a value in the mins hashmap
				else if(mins.get(temp)>expression) {//if the expression currently held in the hasmap is more than the expression then we update
						mins.put(temp, expression);	
				}
				
			}	
		}
		
		//Now we have finished populating the lists of maxs and mins 
		//now we can generate a centroid with gene names and expression values - we want it to be between the max and min for every gene

		for(int i =0; i<k;i++) {// for the number of centroids
			Map<String, Double> coordinates= new HashMap<>();
			for(String temp : maxs.keySet()){// can use min as well since we just need the keyset which should be the same
				//get max and min number from each map
				Double current_max = maxs.get(temp);
				Double current_min= mins.get(temp);	
				coordinates.put(temp, random.nextDouble() * (current_max - current_min) + current_min);
			}
			centroids.add(new Centroids(coordinates));
		}	
		return centroids;
	}
	
	/**
	 * This is used to find the nearest centroid for a given entry
	 * @param entry entry we are trying to find the nearest centroid to 
	 * @param centroids our list of centroids 
	 * @param distance the type of distance we are using (eg, eucledian etc)
	 * @return the nearest centroid to our entry
	 */
	private static Centroids nearestCentroid(Entry entry, List<Centroids> centroids, Distance distance) {
		
		double minimum_distance = Double.MAX_VALUE;// going to find the minimum distance between a sample and all the centroids
		Centroids nearest = null;
		
		for(Centroids centroid : centroids) {// for all the centroids we have
			double current_distance = distance.calculate(entry.getGene(), centroid.getCoordinates());//finds the current distance between the sample and centroids
			
			if(current_distance < minimum_distance) {
				minimum_distance = current_distance;
				nearest = centroid;
			}	
		}
		
		return nearest;
	}

	/**
	 * This is used to update the clusters 
	 * @param clusters the list of clusters (centroid and it's list of entry)
	 * @param entry an individual entry that we want to check against or add to the list
	 * @param centroid an individual centroid that we want to check against or add to the list
	 */
	private static void assignToCluster(Map<Centroids, List<Entry>> clusters, Entry entry, Centroids centroid) {
		
		if(clusters.containsKey(centroid)) {//check to see if we have anything assigned to this cluster - if yes
			List<Entry> entries = clusters.get(centroid);//gets the mapping of entry
			entries.add(entry);//adds to it 
			clusters.put(centroid, entries);//updates the list 
		}else{// if it doesn't exist
			List<Entry> newEntries = new ArrayList<>();//creates a new list for entry
			newEntries.add(entry);//adds to it 
			clusters.put(centroid, newEntries);//updates the main list for the clusters
		}
		
	}
	/**
	 * This is used to find the average values for every gene in the given list of entry samples, and then update our centroid 
	 * with these values.
	 * @param centroid a centroid 
	 * @param entry and it's list of assigned samples(it's cluster)
	 * @return
	 */
	private static Centroids average(Centroids centroid, List<Entry> entry) {
		
		//if records are empty then just return the centorid 
		if(entry == null || entry.isEmpty()) {
			return centroid;
		}
		
		//get the entries of the centroid
		Map<String, Double> average= centroid.getCoordinates();
		//this goes through the list and updates all the keys to be 0 this helps to do a redundancy check and make sure all 
		//values are 0
		for(Entry entries : entry) {
			for(String key : entries.getGene().keySet()) {
				if(!average.containsKey(key)) {
					average.put(key,0.0);
				}
			}
		}
		//this goes through the list of entry that we get passed
		//get's the entry for a particular gene and adds it to our average Map.
		for (Entry entries : entry) {
			Map<String,Double> temp = entries.getGene();
			for (Map.Entry<String, Double> features : temp.entrySet()) {
	            String key = features.getKey();
	            Double value = features.getValue();
	            if (average.containsKey(key)) {
	                average.put(key, average.get(key) + value);//this will just put the value in 
	            } else {
	                average.put(key, value);
	            }
	        }
		}
		
		//this averages out all the entries for a particular gene from all the samples that we get.
		for (Map.Entry<String, Double> entries : average.entrySet()) {
	        String key = entries.getKey();
	        Double totalValue = entries.getValue();
	        average.put(key, totalValue / entry.size());
	    }
		//returns the new averaged out map that contains our average entry from all the samples that is assigned to our 
		//centroid
		return new Centroids(average);
	}
	/**
	 * This is used to move our centroids to the centre of the cluster
	 * @param clusters mapping from a centroid to it's assigned cluster of samples
	 * @return updated list of centroids with new coordinates 
	 */
	private static List<Centroids> relocateCentroids(Map<Centroids, List<Entry>> clusters) {
	    List<Centroids> newCentroids = new ArrayList<>();
	    
	    // Iterate through each entry in the clusters map
	    for (Map.Entry<Centroids, List<Entry>> entry : clusters.entrySet()) {
	        Centroids currentCentroid = entry.getKey();//this will give us the centroid
	        List<Entry> entries = entry.getValue();// and it's assigned samples
	        
	        // Calculate the new centroid by averaging the assigned records
	        Centroids relocatedCentroid = average(currentCentroid, entries);//calculate the average for a centroid for it relocation and entries 
	        
	        // Add the new centroid to the list
	        newCentroids.add(relocatedCentroid);
	    }
	    
	    return newCentroids;
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
		List<Centroids> centroids = randomCentroids(entry,k);
		Map<Centroids, List<Entry>> clusters = new HashMap<>();
		Map<Centroids, List<Entry>> lastState= new HashMap<>();
			
		//go over the loop for the number of maxIterations we have 
		for(int i =0;i < maxIterations; i++) {
			boolean isLastIteration = (i == maxIterations-1);
			
			//for each loop we find a nearest centoid for all the records we have 
			for (Entry entries : entry) {
				Centroids centroid = nearestCentroid(entries, centroids, distance);
				assignToCluster(clusters, entries,centroid);
			}
			
			//if the coordinates dont change we terminate our loop
			boolean end = isLastIteration || clusters.equals(lastState);
			lastState=  clusters;
			if(end) {break;}
			
			//now if we have reached this far we chnage the coordinates and start again 
			centroids = relocateCentroids(clusters);
			clusters = new HashMap<>();
		}
		return lastState;
	}
}
