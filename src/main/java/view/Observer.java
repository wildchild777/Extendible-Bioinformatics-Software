package view;

import model.ClusteredData;
/**
 * Observer interface for views that need to be updated when clustering completes.
 */
public interface Observer {
	/**
     * Called when the observed data changes.
     * @param data the updated clustered data
     */
	void update(ClusteredData data);
}
