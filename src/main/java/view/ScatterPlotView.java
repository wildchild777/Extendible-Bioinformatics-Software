package view;

import java.util.List;
import java.util.Map;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import model.Centroids;
import model.ClusteredData;
import model.Entry;
import model.FlatClusteredData;

public class ScatterPlotView extends VBox implements Observer, VisualizationView  {
	
	private ScatterChart<Number, Number> scatterChart;

	    public ScatterPlotView() {
	        NumberAxis xAxis = new NumberAxis();
	        NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("Gene X");
	        yAxis.setLabel("Gene Y");

	        scatterChart = new ScatterChart<>(xAxis, yAxis);
	        scatterChart.setTitle("K-Means Clustering Scatter Plot");
	        this.getChildren().add(scatterChart);
	    }
	    
	    @Override
	    public void update(ClusteredData data) {
	        displayClusters(data);
	    }
	    
	    
	    @Override
	    public void displayClusters(ClusteredData data) {
	        scatterChart.getData().clear();
	        //have to change this later - right now only checks if it's of FlatClsuterdData or not
	        if (!(data instanceof FlatClusteredData)) return;

	        FlatClusteredData flat = (FlatClusteredData) data;
	        Map<Centroids, List<Entry>> clusters = flat.getClusters();//get the clusters

	        int clusterIndex = 1;
	        for (Map.Entry<Centroids, List<Entry>> cluster : clusters.entrySet()) {
	            XYChart.Series<Number, Number> series = new XYChart.Series<>();
	            series.setName("Cluster " + clusterIndex++);

	            for (Entry entry : cluster.getValue()) {
	                Map<String, Double> geneData = entry.getGene();

	                // Pick 2 genes (for now we assume the first 2 are valid)
	                List<String> geneNames = geneData.keySet().stream().toList();

	                double x = geneData.get(geneNames.get(0));
	                double y = geneData.get(geneNames.get(1));

	                series.getData().add(new XYChart.Data<>(x, y));
	            }

	            scatterChart.getData().add(series);
	        }
	    }
}
