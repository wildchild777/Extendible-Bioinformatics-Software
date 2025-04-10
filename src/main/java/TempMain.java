// A temp class made to test the SoftParser

import java.io.IOException;

import controller.WelcomeViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TempMain extends Application {

    @Override
    public void start(Stage primaryStage) {
	try {
			FXMLLoader loader = FXMLLoader.load(getClass().getResource("/WelcomeView.fxml"));
			Parent root = loader.load();
			
			WelcomeViewController controller = loader.getController();
			controller.setStage(primaryStage);
			
			Scene scene = new Scene(root, 800, 600); // Width x Height
	        primaryStage.setTitle("Clustering App");
	        primaryStage.setScene(scene);
	        primaryStage.show(); // Launch the UI
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}   
    }

    public static void main(String[] args) {
        launch(args); // Start JavaFX lifecycle
    }
}

