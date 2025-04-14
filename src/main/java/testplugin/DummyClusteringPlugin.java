package testplugin;

import java.util.HashMap;
import java.util.Map;

import model.*;
import plugin.ClusteringPlugin;

public class DummyClusteringPlugin implements ClusteringPlugin {

    @Override
    public String getName() {
        return "Dummy Clustering Algo";
    }

    @Override
    public String getDescription() {
        return "Fake clustering algorithm for testing";
    }

    @Override
    public boolean supports(ParsedData data) {
        return data instanceof GeneExpressionParsedData;
    }

    @Override
    public ClusteredData fit(ParsedData data, Map<String, Object> config) {
        System.out.println("Dummy clustering run on: " + data);
        return new FlatClusteredData(new java.util.HashMap<>()); // return empty cluster map
    }

    @Override
    public boolean requiresConfiguration() {
        return true;
    }
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        return params; 
    }

    @Override
    public Class<? extends ParsedData> supportedParsedType() {
        return GeneExpressionParsedData.class;
    }
}
