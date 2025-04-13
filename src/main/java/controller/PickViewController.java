package controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import view.*;

public class PickViewController {
	
	@FXML
	private ComboBox<String> datasetDrop;
	@FXML
	private ComboBox<String> AlgorithmDrop;
	@FXML
	private ComboBox<String> VizDrop;
	@FXML
	private Button executeConfig;
	@FXML 
	private CheckBox DimensionCheckBox;
	
	private final Map<String, File> datasetMap = new HashMap<>();//holds value -> file
	private Stage stage; // passed from previous controller
	private ClusteringController clusteringController;
	private ScatterPlotView scatterPlotView;
	//to hold our parsedData - for now it's just one field later we will add so we can hold multiple
	private ParsedData currentData;
	private ClusteredData result;
	private boolean isReduced = false;

	public void setStage(Stage stage) {
	    this.stage = stage;
	}
	
	public void setClusteringController(ClusteringController clusteringController) {
	    this.clusteringController = clusteringController;
	}
	
	@FXML
	public void initialize() {
	    // Hardcoded later plug-in
	    try {
	        File sample1 = new File(getClass().getResource("/GDS3310_full.soft").toURI());//hardcoded
	        String name = sample1.getName(); 
	        AlgorithmDrop.getItems().add("K-Means");
	        VizDrop.getItems().add("Scatter Plot");
	        datasetMap.put(name, sample1);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // get's all the keys from the datasetMap and show it in dropdown menu
	    datasetDrop.getItems().addAll(datasetMap.keySet());

	    //This is really important as it helps browsing and pick a file from the system
	    datasetDrop.getItems().add("Browse from computer...");
	    datasetDrop.setOnAction(e -> handleDatasetSelection());//action on dataset selection
	    executeConfig.setOnAction(e -> handleClusteringExecution());//action on button press
	}
	/**
	 * If "Browse from computer..." → open file picker → insert into dropdown → auto-select it.
	   If existing file → just get from map and parse it.
	 */
	private void handleDatasetSelection() {
	    String selected = datasetDrop.getValue();//name of the file

	    if ("Browse from computer...".equals(selected)) {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select Dataset File");
	        fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("SOFT Files", "*.soft")
	        );
	        File file = fileChooser.showOpenDialog(stage);
	        if (file != null) {
	            String name = file.getName();
	            datasetMap.put(name, file);
	            if (!datasetDrop.getItems().contains(name)) {
	                datasetDrop.getItems().add(datasetDrop.getItems().size() - 1, name);
	            }
	          //This line is clever — it sets the selection to the new filename which triggers the else branch again!
	            datasetDrop.setValue(name); 
	        }
	    } else {
	        // Handle both built-in and newly added files here
	    	//for now only the else branch will be triggered
	        File selectedFile = datasetMap.get(selected);
	        if (selectedFile != null) {
	            currentData = clusteringController.loadData(selectedFile.getAbsolutePath());
	        }
	    }
	}
	private void handleClusteringExecution() {
	    if (!validateSelections()) return;

	    if (DimensionCheckBox.isSelected() && !isReduced) {
	    	System.out.println("Running PCA reduction to 2D");
	        DimensionalityReducer reducer = new PcaReducer(); // maybe switch based on user later
	        List<Entry> reduced = reducer.reduce(currentData.getEntries(), 2);
	        currentData = new GeneExpressionParsedData();
	        for (Entry e : reduced) {
	            ((GeneExpressionParsedData) currentData).add(e);
	        }
	        isReduced = true;
	    }
	    
	    
	    runClustering();

	    renderVisualization();
	}
	
	private boolean validateSelections() {
	    if (currentData == null) {
	        System.out.println("No dataset loaded.");
	        return false;
	    }

	    String selectedAlgo = AlgorithmDrop.getValue();
	    if (!"K-Means".equals(selectedAlgo)) {
	        System.out.println("Only K-Means is supported right now.");
	        return false;
	    }

	    return true;
	}
	
	private void runClustering() {
		String selectedAlgo = AlgorithmDrop.getValue();
	    if (!"K-Means".equals(selectedAlgo)) return;

	    // Show the config dialog
	    KMeansConfigDialog dialog = new KMeansConfigDialog();
	    Optional<KMeansConfig> configResult = dialog.showAndWait(stage);

	    if (configResult.isPresent()) {
	        KMeansConfig config = configResult.get(); // get k, maxIterations, distance

	        clusteringController.setClusteringStrategy(new KmeansClustering());
	        result = clusteringController.runClustering(currentData, config.getK(), config.getDistance(), config.getMaxIterations());
	        System.out.println("Clustering completed: \n" + result);
	    } else {
	        System.out.println("K-Means configuration cancelled.");
	    }
	    
	}
	
	private void renderVisualization() {
        String selectedViz = VizDrop.getValue();

        if ("Scatter Plot".equals(selectedViz)) {
            scatterPlotView = new ScatterPlotView();
            clusteringController.getClusterContext().addObserver(scatterPlotView);
            scatterPlotView.update(result);
            openViewInNewWindow(scatterPlotView, "Cluster Visualization - Scatter Plot");
        }
    }
	
	private void openViewInNewWindow(javafx.scene.Node view, String title) {
        Stage vizStage = new Stage();
        vizStage.setTitle(title);
        Scene scene = new Scene(new StackPane(view), 800, 600);
        vizStage.setScene(scene);
        vizStage.show();
    }
	
	
	
	
	
	
}
