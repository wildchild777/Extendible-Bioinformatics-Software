package testplugin;


import java.util.HashMap;
import java.util.Map;

import model.ClusteredData;
import model.FlatClusteredData;
import model.ParsedData;
import plugin.ClusteringPlugin;

public class TestClusteringAlgorithm implements ClusteringPlugin {

    @Override
    public String getName() {
        return "test clust algo";
    }

    @Override
    public String getDescription() {
        // A short description for the plugin
        return "this prints its own parameters";
    }

    @Override
    public boolean supports(ParsedData data) {
    	//for now we just return true
        return true;
    }

    @Override
    public ClusteredData fit(ParsedData data, Map<String, Object> config) {
        // Retrieve configuration parameters and print them out.
        System.out.println("test Algorithm Execution:");
        
        // Retrieve the "name" parameter. If not provided, default to "undefined"
        String paramName = (String) config.get("name");
        String param1 = (String)config.get("parameter1") ;
        String param2 = (String)config.get("parameter2") ;
        String param3 = (String)config.get("parameter3") ;
       
        System.out.println("Name: " + paramName);
        System.out.println("Parameter1: " + param1);
        System.out.println("Parameter2: " + param2);
        System.out.println("Parameter3: " + param3);
        
        // this is just the test algo
        return new FlatClusteredData(new HashMap<>());
    }

    @Override
    public Map<String, Object> getParameters() {
        // Provide default configuration values for this plugin.
        Map<String, Object> params = new HashMap<>();
        params.put("name", "DummyDefaultName");
        params.put("parameter1", "Default1");
        params.put("parameter2", "Default2");
        params.put("parameter3", "Default3");
        return params;
    }
    
    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    
    @Override
    public Class<? extends ParsedData> supportedParsedType() {
        return ParsedData.class;
    }
}

