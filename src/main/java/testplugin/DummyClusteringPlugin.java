package testplugin;

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
    public ClusteredData fit(ParsedData data, int k, Distance distance, int maxIterations) {
        System.out.println("Dummy clustering run on: " + data);
        return new FlatClusteredData(new java.util.HashMap<>()); // return empty cluster map
    }

    @Override
    public boolean requiresConfiguration() {
        return true;
    }

    @Override
    public Class<? extends ParsedData> supportedParsedType() {
        return GeneExpressionParsedData.class;
    }
}
