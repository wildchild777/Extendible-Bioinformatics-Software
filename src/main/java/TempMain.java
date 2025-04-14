// A temp class made to test the SoftParser

import java.io.IOException;
import java.net.URISyntaxException;

import controller.*;
import controller.WelcomeViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;
import plugin.PluginManager;
/**
 * The start point of our application - bootstraps the controllers
 */
public class TempMain extends Application {
/**
 * Takes the stage and passes it onto the next view with all the controllers
 */
    @Override
    public void start(Stage primaryStage) {
	try {
		//set up the initial contexts to be used 
		ParserContext parserContext = new ParserContext();
        ClusterContext clusterContext = new ClusterContext();
        
        // Create an instance of your plug-in manager and load plugins from the directory.
        PluginManager pluginManager = new PluginManager();
        // Provide your plugin directory path 
        pluginManager.loadPlugins("src/main/java/plugin/"); 
        
        
        //manages the contexts - and sets up the main logic controller
        ClusteringController clusteringController = new ClusteringController(parserContext, clusterContext);
        clusteringController.setPluginManager(pluginManager);
        //basic FX
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeView.fxml"));
			Parent root = loader.load();
			/*Here we make the controller for the next view and pass all our controller as well
			 * 
			 * */
			WelcomeViewController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setClusteringController(clusteringController);
			
			Scene scene = new Scene(root, 800, 600); // Width x Height
	        primaryStage.setTitle("Clustering App");
	        primaryStage.setScene(scene);
	        primaryStage.show(); // Launch the UI
			} catch (IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}   catch (URISyntaxException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}   
    }

    public static void main(String[] args) {
        launch(args); // Start JavaFX lifecycle
    }
} 
 
