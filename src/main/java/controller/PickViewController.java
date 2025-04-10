package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PickViewController {
	
	@FXML
	private ComboBox<String> datasetDrop;
	@FXML
	private ComboBox AlgorithmDrop;
	@FXML
	private ComboBox VizDrop;
	@FXML
	private Button executeConfig;
	//this map holds our String -> mapping like test -> ParseTestFile
	private final Map<String, File> datasetMap = new HashMap<>();
	private Stage stage; // passed from previous controller

	public void setStage(Stage stage) {
	    this.stage = stage;
	}
	
	@FXML
	public void initialize() {
	    // Hardcoding the file and their name now - later when we implement the, 
		//plug-in architecture we'll be able to populate the list in a different way
	    try {
	        File sample1 = new File(getClass().getResource("/ParseTestFile.soft").toURI());
	        //File sample2 = new File(getClass().getResource("/datasets/sample2.soft").toURI());

	        datasetMap.put("Dummy Test", sample1);
	        //datasetMap.put("Sample Dataset B", sample2);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // get's all the keys from the datasetMap and show it in dropdown menu
	    datasetDrop.getItems().addAll(datasetMap.keySet());

	    //This is really important as it helps browsing and pick a file from the system
	    datasetDrop.getItems().add("Browse from computer...");
	    // we call this whenever there is an action in the dataset dropbox
	    datasetDrop.setOnAction(e -> handleDatasetSelection());
	}
	
	private void handleDatasetSelection() {
	    String selected = (String) datasetDrop.getValue();
	    //if a file selector is selected we open the system to pick one 
	    if ("Browse from computer...".equals(selected)) {
	    	FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select Dataset File");
	        fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("SOFT Files", "*.soft")
	            //new FileChooser.ExtensionFilter("All Files", "*.*")
	        );
	        File file = fileChooser.showOpenDialog(stage);
		        if (file != null) {
		            String name = file.getName();
		            datasetMap.put(name, file);
		            // checks for duplicates and then if doesn't exist adds it above the browse thing
		            if (!datasetDrop.getItems().contains(name)) {
		                datasetDrop.getItems().add(datasetDrop.getItems().size() - 1, name); // insert before 'browse'
		            }
	
		            datasetDrop.setValue(name);
		        }
	   	}
	}
	
	
	
}
