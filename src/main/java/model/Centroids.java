package model;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * This represents our centroids , which have gene and it's expressions for each sample.
 */
public class Centroids {
 //We'll make the centriod which will basically exist in the same dimensions as all the other vectors(Columns)
	private final Map<String, Double> coordinates;
	
	  public Centroids(Map<String, Double> coordinates) {
	        this.coordinates = coordinates;
	    }

	    public Map<String, Double> getCoordinates() {
	        return coordinates;
	    }
	    @Override
	    public String toString() {
	        return "Centroid{" +
	               "coordinates=" + coordinates +
	               '}';
	    }
	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Centroids centroid = (Centroids) o;
	        return Objects.equals(coordinates, centroid.coordinates);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(coordinates);
	    }
}
