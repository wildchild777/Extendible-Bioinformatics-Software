package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This will be updated later to haev history so it will be run config instead of configure
 * for now only the config run works
 */
public class ChoiceViewController {

	private Stage stage;
	private ClusteringController clusteringController;
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	
	public void setClusteringController(ClusteringController clusteringController) {
        this.clusteringController = clusteringController;
    }
	
	public void changeView(ActionEvent e) {
		 try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/PickView.fxml"));
			 Parent root = loader.load();
			 //get and pass the stage and controllers
			 PickViewController controller = loader.getController();
	         controller.setStage(stage); // pass it forward
	         controller.setClusteringController(clusteringController);//pass the clus controller forward
	         stage.setScene(new Scene(root, 800, 600));
			
        
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
