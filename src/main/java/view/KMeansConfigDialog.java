package view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.Stage;
import model.*;

import java.util.Optional;
/**
 * Our dialogbox for kmeans 
 */
public class KMeansConfigDialog implements ClusteringConfigDialog<KMeansConfig> {

    @Override
    public Optional<KMeansConfig> showAndWait(Window owner) {
        Dialog<KMeansConfig> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Configure K-Means");

        // UI controls
        TextField kField = new TextField("2");
        TextField maxIterField = new TextField("100");

        VBox box = new VBox(10,
            new Label("Number of Clusters (k):"), kField,
            new Label("Max Iterations:"), maxIterField
        );
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                int k = Integer.parseInt(kField.getText());
                int maxIter = Integer.parseInt(maxIterField.getText());
                Distance distance = new EucledianDistance(); // or choose later
                return new KMeansConfig(k, maxIter, distance);
            }
            return null;
        });

        return dialog.showAndWait(); 
    }
}
