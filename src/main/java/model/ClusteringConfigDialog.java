package model;

import javafx.stage.Window;
import java.util.Optional;

public interface ClusteringConfigDialog<T> {
	 /**
     * launches a dialog to collect parameters for the clustering algorithm.
     * @param owner The parent window 
     * @return Optional of config object (plugin-defined type) or empty if cancelled.
     */
    Optional<T> showAndWait(Window owner);

}
