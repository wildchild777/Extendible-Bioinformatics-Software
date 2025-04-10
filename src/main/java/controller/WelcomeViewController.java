package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeViewController {

	@FXML
	private Button NextButton;
	private Stage stage;
	
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	public void changeView(ActionEvent e) {
		 try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChoiceView.fxml"));
			 Parent root = loader.load();
			 ChoiceViewController controller = loader.getController();
	         controller.setStage(stage); // pass it forward

	         stage.setScene(new Scene(root, 800, 600));
			
         
         } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
