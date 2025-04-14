package controller;
import java.io.File;
import java.util.List;
import java.util.Map;

import model.*;
import plugin.ClusteringPlugin;
import plugin.PluginManager;
/**
 * Our main controller to bind the view and the model together
 */
public class ClusteringController {
	
	private ParserContext parsingContext;
	private ClusterContext clusterContext;
	private PluginManager pluginManager;
	
	public ClusteringController(ParserContext parsingContext,ClusterContext clusterContext) {
		this.parsingContext = parsingContext;
		this.clusterContext = clusterContext;
	} 
	public void setClusteringStrategy(ClusterStrategy strategy) {
		clusterContext.setClusterStrategy(strategy);
	}
	
	public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }
	 
	 public PluginManager getPluginManager() {
	        return this.pluginManager;
	}
	 
	public  ParsedData loadData(File filename) {
		if(filename.getName().endsWith(".soft")) {
			parsingContext.setParser(new SoftParser());
		}
		else {
			throw new IllegalArgumentException("Unsupported file type");
		}
		
		return parsingContext.executeParse(filename);
	}
	//bare-bone implementation of the clustering algo, we will update this.
	 public ClusteredData runClustering(ParsedData data, Map<String, Object> config) { 
	        return clusterContext.executeClustering(data, config); 
	    }
	  /**
     * Execute clustering using a plugin-based strategy.
     * This method looks up the clustering plugin by name and uses it.
     * 
     * @param data The parsed data to cluster.
     * @param pluginName The name of the clustering plugin to use.
     * @return The ClusteredData obtained from the plugin.
     */
    public ClusteredData runPluginClustering(ParsedData data, String pluginName, Map<String, Object> config) {
        if (pluginManager == null) {
            throw new IllegalStateException("PluginManager not set.");
        }
        // Find the plugin with the given name
        ClusteringPlugin selectedPlugin = pluginManager.getClustering().stream()
            .filter(plugin -> plugin.getName().equalsIgnoreCase(pluginName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No clustering plugin found with name: " + pluginName));
        
        // Execute the plugin's clustering method
        return selectedPlugin.fit(data, config);
    }
	
	public ClusterContext getClusterContext() {
		return this.clusterContext;
	}
	

}
