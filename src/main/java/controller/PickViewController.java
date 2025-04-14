package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import plugin.ClusteringPlugin;
import plugin.VisualizationPlugin;
import view.*;
/**
 * This is our Controller which connects to the model the most, its main work is to set up the scene
 * and then delegate the logic to ClusteringController
 */
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
	/**
	 * This sets up the comboBoxes with the needed built in methods.
	 */
	@FXML
	public void initialize() {
	    // Hardcoded later plug-in
	    try {
	        File sample1 = new File(getClass().getResource("/GDS4794_full.soft").toURI());//hardcoded
	        String name = sample1.getName(); 
	        AlgorithmDrop.getItems().addAll("K-Means","Hierarchical");
	        VizDrop.getItems().addAll("Scatter Plot","Dendrogram");
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
	            currentData = clusteringController.loadData(selectedFile);
	        }
	    }
	}
	/**
	 * This handles our main Clustering logic, first it checks if the data is valid, if yes then allows the
	 * User to run clustering algorithms either the built in ones or the ones ran though the plug-in
	 */
	private void handleClusteringExecution() {
	    if (!validateSelections()) return; 

	    if (DimensionCheckBox.isSelected() && !isReduced) {
	    	System.out.println("Running PCA reduction to 2D");
	    	//this is when PCA is selected
	    if (!(currentData instanceof GeneExpressionParsedData)) {
        	showErrorDialog("Dataset Type Error", "PCA currently only supports GeneExpressionParsedData");
            return;
	        }
	    	GeneExpressionParsedData geneData = (GeneExpressionParsedData) currentData;
	        DimensionalityReducer reducer = new PcaReducer(); // maybe switch based on user later 
	        List<Entry> reduced = reducer.reduce(geneData.getEntries(), 2);
	        
	        GeneExpressionParsedData reducedData = new GeneExpressionParsedData();
	        for (Entry e : reduced) {
	            reducedData.add(e);
	        }
	        currentData = reducedData;
	        isReduced = true;
	    }
	    
	    
	    runClustering();

	    renderVisualization();
	}
	
	private boolean validateSelections() {
	    if (currentData == null) {
        	showErrorDialog("Dataset Error", "No dataset loaded.");
	        return false;
	    }
	    return true;
	} 
	/**
	 * Our clustering logic resides here, can select the algorithms from the drop down or when the plug-in refresh is called
	 */
	private void runClustering() {
	    String selectedAlgo = AlgorithmDrop.getValue();

	    if ("K-Means".equals(selectedAlgo)) {
	        // Show the config dialog
	        KMeansConfigDialog dialog = new KMeansConfigDialog();
	        Optional<KMeansConfig> configResult = dialog.showAndWait(stage);//lets the user input the arguments

	        if (configResult.isPresent()) {
	            KMeansConfig config = configResult.get(); // get k, maxIterations, distance
	           
	            Map<String, Object> confMap = new HashMap<>(); 
                confMap.put("k", config.getK()); 
                confMap.put("distance", config.getDistance());
                confMap.put("maxIterations", config.getMaxIterations()); 
	            
                clusteringController.setClusteringStrategy(new KmeansClustering());//since Kmeans is selected
	            result = clusteringController.runClustering(currentData, confMap);//pass the arguments and parsedData
	            System.out.println("K-Means Clustering completed: \n" + result);
	        } else {
	        	showErrorDialog("Execution Error", "K-Means configuration cancelled.");
	        }

	    } else if ("Hierarchical".equals(selectedAlgo)) {//same logic as Kmeans
	        clusteringController.setClusteringStrategy(new HierarchicalClustering());
	        Map<String, Object> confMap = new HashMap<>();
	        confMap.put("distance", new EucledianDistance());
	        result = clusteringController.runClustering(currentData,confMap); 
	        System.out.println("Hierarchical Clustering completed: \n" + result);

	    } 
	    else { //Plugin branch starts here
            ClusteringPlugin plugin = null; 
            for (ClusteringPlugin cp : clusteringController.getPluginManager().getClustering()) { 
                if (cp.getName().equalsIgnoreCase(selectedAlgo)) { 
                    plugin = cp; 
                    break; 
                } 
            } 
            
            if (plugin == null) { 
            	showErrorDialog("Clustering Error", "Unkown Clustering Alogrithm Selected");
                return; 
            } 
            
            Map<String, Object> confMap = new HashMap<>(); 
            if (plugin.requiresConfiguration()) {
                PluginConfigDialog dialog = new PluginConfigDialog(plugin.getParameters()); 
                dialog.initOwner(stage);
                Optional<Map<String, Object>> optConfig = dialog.showAndWait();  
                if (!optConfig.isPresent()) { 
                    System.out.println("Plugin configuration cancelled."); 
                    return; 
                } 
                confMap = optConfig.get(); 
            } else { 
                confMap = plugin.getParameters(); 
            } 
            clusteringController.setClusteringStrategy(plugin); 
            try {
            result = clusteringController.runPluginClustering(currentData, plugin.getName(), confMap); 
            System.out.println(plugin.getName() + " clustering completed: \n" + result); 
            }catch(Exception e) {
            	showErrorDialog("Execution Error", "Error during plugin clustering execution");
                e.printStackTrace(); 
                return; 
            }
            
	    }
	}
/**
 * This renders the ClusteredData on the screen.
 */
	private void renderVisualization() {
        String selectedViz = VizDrop.getValue();
        if (result instanceof FlatClusteredData) {
            Map<Centroids, List<Entry>> clusters = ((FlatClusteredData) result).getClusters();
            if (clusters == null || clusters.isEmpty()) {//sanity check on clusteredData
                showErrorDialog("Clustering Error", "No clusters were formed.");
                return;
            }
        }
        if ("Scatter Plot".equals(selectedViz)) {
            scatterPlotView = new ScatterPlotView();
            clusteringController.getClusterContext().addObserver(scatterPlotView); 
            if (result == null) {
                showErrorDialog("Clustering Error", "No clustering result was produced.");
                return;
            }
            scatterPlotView.update(result);
            openViewInNewWindow(scatterPlotView, "Cluster Visualization - Scatter Plot");
        }else if ("Dendrogram".equals(selectedViz)) {
            if (result instanceof HierarchicalClusteredData) {
            	 HierarchicalClusteredData dendroData = (HierarchicalClusteredData) result;
            	 if (result == null) {
            	        showErrorDialog("Clustering Error", "No clustering result was produced.");
            	        return;
            	    }
                DendrogramView dendrogram = new DendrogramView();
                dendrogram.displayClusters(dendroData);
                openViewInNewWindow(dendrogram, "Cluster Visualization - Dendrogram");
            } else {
                System.out.println("Dendrogram can only be shown for hierarchical clustering.");
            }
        }
    }
	
	private void openViewInNewWindow(javafx.scene.Node view, String title) {
        Stage vizStage = new Stage();
        vizStage.setTitle(title);
        Scene scene = new Scene(new StackPane(view), 800, 600);
        vizStage.setScene(scene);
        vizStage.show();
    }
	private void showErrorDialog(String title, String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	/**
	 * This main class loads plug-ins from PlugIn manager
	 */
	@FXML
	private void loadPlugin() {
	    try {
	        // Call the PluginManager to load plugins 
	        clusteringController.getPluginManager().loadPlugins("src/main/java/plugin/");
	        
	        // Update the plugin-related UI controls 
	        // Start by removing any plugin entries you might have previously added.
	        AlgorithmDrop.getItems().clear();
	        // Add built-in options 
	        AlgorithmDrop.getItems().addAll("K-Means", "Hierarchical");
	        
	        // get the loaded clustering plugins from the PluginManager.
	        for (ClusteringPlugin cp : clusteringController.getPluginManager().getClustering()) {
	            AlgorithmDrop.getItems().add(cp.getName());
	        }
	        
	        // Similarly update your visualization dropdown
	        VizDrop.getItems().clear();
	        // Optionally add built-in visualization names...
	        VizDrop.getItems().addAll("Scatter Plot", "Dendrogram");
	        for (VisualizationPlugin vp : clusteringController.getPluginManager().getVisualizations()) {
	            VizDrop.getItems().add(vp.getName());
	        }
	        System.out.println("Plugins refreshed successfully.");
	        
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	        System.out.println("Failed to refresh plugins.");
	    }
	}
	
	
	
	 
	
}
