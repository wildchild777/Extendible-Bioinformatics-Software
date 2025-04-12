package view;

import model.ClusteredData;
/**
 * Interface for views that can visualize clustered data.
 */
public interface VisualizationView {
	/**
     * Displays the result of a clustering operation.
     * @param data clustered data to display
     */
	void displayClusters(ClusteredData data);
}
