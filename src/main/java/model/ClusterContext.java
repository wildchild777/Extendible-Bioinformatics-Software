package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import view.Observer;

/**
 * This class passes request to the concrete subclasses after getting 
 * requests from clients 
 * 
 * This is used to implement the Strategy pattern
 */
public class ClusterContext {
	   private ClusterStrategy strategy;//privately held strategy
	   private final List<Observer> observers = new ArrayList<>();//list of the observers we have 
	   private ClusteredData clusteredData;//our data

	    // Set the clustering strategy
	    public void setClusterStrategy(ClusterStrategy strategy) {
	        this.strategy = strategy;
	    }

	    //Need to change this to take a ParsedData instance instead of List<Entry> etc
	    //Need to change this so it returns a type of ClusterdData instead of Map<Centroids, List<Entry>>
	    public ClusteredData executeClustering(ParsedData data, int k, Distance distance, int maxIterations) {
	        if (strategy == null) {
	            throw new IllegalStateException("ClusterStrategy is not set.");
	        }
	        clusteredData = strategy.fit(data,k,distance,maxIterations);
	        notifyObservers();
	        return clusteredData;
	    }
	    
	    public void addObserver(Observer observer) {
	        observers.add(observer);
	    }

	    public void removeObserver(Observer observer) {
	        observers.remove(observer);
	    }
	    
	    private void notifyObservers() {
	        for (Observer o : observers) {
	            o.update(clusteredData);
	        }
	}

}