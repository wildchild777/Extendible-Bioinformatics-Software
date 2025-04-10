// A temp class made to test the SoftParser

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TempMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane(); // A simple empty container

        Scene scene = new Scene(root, 800, 600); // Width x Height

        primaryStage.setTitle("Clustering App");
        primaryStage.setScene(scene);
        primaryStage.show(); // Launch the UI
    }

    public static void main(String[] args) {
        launch(args); // Start JavaFX lifecycle
    }
}

