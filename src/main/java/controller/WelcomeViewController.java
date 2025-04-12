package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * This is the Controller for our home landing page, connected to our WelcomeView.fxml
 */
public class WelcomeViewController {
	/*
	 * These are our buttons to pass onto the next stage, the stage itself being passed from main
	 * and our clusteringController that is needed for PickView which actually uses our model.
	 * */
	@FXML
	private Button NextButton;
	private Stage stage;
	private ClusteringController clusteringController;
	
	/**
	 * Sets the stage that is passed from the previous method(here main)
	 * @param stage
	 */
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	/**
	 * Sets the clustering controller so it can be passes on - since it isn't used here
	 * @param clusteringController
	 */
	public void setClusteringController(ClusteringController clusteringController) {
        this.clusteringController = clusteringController;
    }
	/**
	 * This is used to change our view from this to the next one
	 * @param e
	 */
	public void changeView(ActionEvent e) {
		 try {
			 //our next view
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChoiceView.fxml"));
			 Parent root = loader.load();
			 //get and pass the controllers
			 ChoiceViewController controller = loader.getController();
	         controller.setStage(stage); // pass it forward
	         controller.setClusteringController(clusteringController);//pass the clus controller forward
	         stage.setScene(new Scene(root, 800, 600));
			
         
         } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
