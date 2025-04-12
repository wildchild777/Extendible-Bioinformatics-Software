package model;
/**
 * Just a data-holder class for our Kmeans dialog box, which will be used by the .fit() method later
 */
public class KMeansConfig {
	private final int k;
    private final int maxIterations;
    private final Distance distance;

    public KMeansConfig(int k, int maxIterations, Distance distance) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.distance = distance;
    }

    public int getK() { return k; }
    public int getMaxIterations() { return maxIterations; }
    public Distance getDistance() { return distance; }
}

