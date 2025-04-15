package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import view.Observer;

/**
 * This class passes request to the concrete subclasses after getting 
 * requests from clients 
 * 
 * This is used to implement the Strategy pattern - classes ask this for a request to any clustering algo
 */
public class ClusterContext {
	   private ClusterStrategy strategy;//privately held strategy
	   private final List<Observer> observers = new ArrayList<>();//list of the observers we have 
	   private ClusteredData clusteredData;//our data

	    // Set the clustering strategy
	    public void setClusterStrategy(ClusterStrategy strategy) {
	        this.strategy = strategy;
	    }

	    public ClusteredData executeClustering(ParsedData data, Map<String, Object> config) {
	        if (strategy == null) {
	            throw new IllegalStateException("ClusterStrategy is not set.");
	        }
	        clusteredData = strategy.fit(data,config);
	        notifyObservers();
	        return clusteredData;
	    }
	    //add observers to be notified when clustering is finished
	    public void addObserver(Observer observer) {
	        observers.add(observer);
	    }
	    //removes observers
	    public void removeObserver(Observer observer) {
	        observers.remove(observer);
	    }
	    //updates observers(usually things that depend on clustering) - letting them know clustering is finished
	    //and they can request the data for the same
	    private void notifyObservers() {
	        for (Observer o : observers) {
	            o.update(clusteredData);
	        }
	}

}