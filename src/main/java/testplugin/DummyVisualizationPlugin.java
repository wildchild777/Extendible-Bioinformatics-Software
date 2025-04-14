package testplugin;

import model.ClusteredData;
import model.FlatClusteredData;
import plugin.VisualizationPlugin;
import view.VisualizationView;

public class DummyVisualizationPlugin implements VisualizationPlugin {

    @Override
    public String getName() {
        return "Dummy Viz";
    }

    @Override
    public String getDescription() {
        return "Fake visualization for testing.";
    }

    @Override
    public Class<? extends ClusteredData> getSupportedClusteredDataType() {
        return FlatClusteredData.class;
    }

    @Override
    public VisualizationView createView(ClusteredData data) {
    	
        return new VisualizationView() {
            @Override
            public void displayClusters(ClusteredData data) {
                System.out.println("Dummy view displayed");
            }

        };
    }

    @Override
    public boolean requiresConfiguration() {
        return true;
    }
}
