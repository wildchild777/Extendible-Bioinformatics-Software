package controller;
import java.util.List;

import model.*;
/**
 * Our main controller to bind the view and the model together
 */
public class ClusteringController {
	
	private ParserContext parsingContext;
	private ClusterContext clusterContext;
	
	public ClusteringController(ParserContext parsingContext,ClusterContext clusterContext) {
		this.parsingContext = parsingContext;
		this.clusterContext = clusterContext;
	} 
	public void setClusteringStrategy(ClusterStrategy strategy) {
		clusterContext.setClusterStrategy(strategy);
	}
	
	public  ParsedData loadData(String filename) {
		if(filename.endsWith(".soft")) {
			parsingContext.setParser(new SoftParser());
		}
		else {
			throw new IllegalArgumentException("Unsupported file type");
		}
		
		return parsingContext.executeParse(filename);
	}
	//bare-bone implementation of the clustering algo, we will update this.
	public ClusteredData runClustering(ParsedData data, int k, Distance distance, int maxIterations) {
        return clusterContext.executeClustering(data, k, distance, maxIterations);
    }
	
	public ClusterContext getClusterContext() {
		return this.clusterContext;
	}
	

}
