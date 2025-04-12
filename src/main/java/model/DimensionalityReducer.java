package model;

import java.util.List;

public interface DimensionalityReducer {
	/**
     * Reduces high-dimensional data to a fixed number of dimensions 2d or 3d.
     * @param entries The original entries with all gene expression values.
     * @param targetDimensions The number of dimensions to reduce to.
     * @return A new list of entries with reduced dimensions.
     */
    List<Entry> reduce(List<Entry> entries, int targetDimensions);
}
