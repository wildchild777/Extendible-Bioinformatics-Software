package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import view.ScatterPlotView;

public class PickViewController {
	
	@FXML
	private ComboBox<String> datasetDrop;
	@FXML
	private ComboBox<String> AlgorithmDrop;
	@FXML
	private ComboBox<String> VizDrop;
	@FXML
	private Button executeConfig;
	private final Map<String, File> datasetMap = new HashMap<>();//holds value -> file
	private Stage stage; // passed from previous controller
	private ClusteringController clusteringController;
	private ScatterPlotView scatterPlotView;
	//to hold our parsedData - for now it's just one field later we will add so we can hold multiple
	private ParsedData currentData;

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
	        File sample1 = new File(getClass().getResource("/KmeansTemp.soft").toURI());//hardcoded
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
	
	//have to refactor this later
	private void handleClusteringExecution() {
		//first we have to select a dataset for things to run
		if (currentData == null) {
	        System.out.println("No dataset loaded.");
	        return;
	    }
		//get the name of the algorithm selected 
	    String selectedAlgo = AlgorithmDrop.getValue();
	    //if it's anything else than K-means we'll say no
	    if (!"K-Means".equals(selectedAlgo)) {
	        System.out.println("Only K-Means is supported right now.");
	        return;
	    }
	    clusteringController.setClusteringStrategy(new KmeansClustering());
	    // You could use defaults or ask the user later
	    int k = 2;
	    int maxIterations = 100;
	    Distance distance = new EucledianDistance();

	    ClusteredData result = clusteringController.runClustering(currentData, k, distance, maxIterations);
	    System.out.println("Clustering completed: \n" + result);
	//here we get the selected vizualization 
	    String selectedViz = VizDrop.getValue();

	    if ("Scatter Plot".equals(selectedViz)) {
	        // Create the view
	        scatterPlotView = new ScatterPlotView();

	        // Register as observer :do this before clustering
	        clusteringController.getClusterContext().addObserver(scatterPlotView);

	        // Manually update the view with results (safe approach)
	        scatterPlotView.update(result);

	        // Show in new window
	        Stage vizStage = new Stage();
	        vizStage.setTitle("Cluster Visualization - Scatter Plot");
	        Scene scene = new Scene(scatterPlotView, 800, 600);
	        vizStage.setScene(scene);
	        vizStage.show();
	    }
	
	
	
	}
	 
	
	
}
